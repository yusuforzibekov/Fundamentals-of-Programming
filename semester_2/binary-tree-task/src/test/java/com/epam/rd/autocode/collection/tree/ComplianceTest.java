package com.epam.rd.autocode.collection.tree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.*;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplianceTest {

    private static final Set<String> ALLOWED_CLASSES = new TreeSet<>(asList(
            "java.util.Optional", "java.util.Objects"));
    private static final List<String> FORBIDDEN_CLASSES = List.of("java.util.*");
    private static final String FORBIDDEN_CLASSES_PATTERN = toPattern(FORBIDDEN_CLASSES);

    static CtModel ctModel;

    static String toPattern(List<String> strings) {
        return strings.stream()
                .map(s -> s.replace(".", "\\."))
                .map(s -> s.replace("*", ".*"))
                .collect(Collectors.joining(".+$)||(^", "(^", ".+$)"));
    }

    @BeforeAll
    static void init() {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/main/java/");
        ctModel = spoon.buildModel();
    }

    @Test
    void testUsages() {
        List<String> types = ctModel
                .getElements(new TypeFilter<>(CtTypeReference.class))
                .stream()
                .map(CtTypeReference::getQualifiedName)
                .distinct()
                .filter(r -> r.matches(FORBIDDEN_CLASSES_PATTERN))
                .sorted()
                .toList();
        assertTrue(types.size() <= ALLOWED_CLASSES.size(),
                "You can use exactly specified types from " + FORBIDDEN_CLASSES +
                        " packages and theirs subpackages:\nexpected: " +
                        ALLOWED_CLASSES + "\nbut found " + types  + "\n");
        types.forEach(t -> assertTrue(ALLOWED_CLASSES.contains(t),
                "You can use exactly specified types from " + FORBIDDEN_CLASSES +
                        " packages and theirs subpackages:\nexpected: " +
                        ALLOWED_CLASSES + "\nbut found " + types  + "\n"));
    }

    @Test
    void testSizeMustReturnFieldValue() {
        List<CtElement> statements = ctModel.filterChildren((Filter<CtType<?>>) el ->
                        el.getQualifiedName().equals(BinaryTree.class.getName()))
                .filterChildren((Filter<CtMethod<?>>) el -> el.getSimpleName().equals("size"))
                .filterChildren(el -> el.getRoleInParent().equals(CtRole.STATEMENT))
                .list();
        assertEquals(1, statements.size(),
                "This method must not do any calculations. " +
                        "It should just return a value of the 'size' field. ");
        List<CtElement> executables = statements.stream()
                .filter(el -> el.getRoleInParent().equals(CtRole.EXECUTABLE_REF)).toList();
        assertEquals(0, executables.size(),
                "This method must return just a value of the 'size' field.");
    }

    @Test
    void testNoMoreFieldsInImpl() {
        List<CtField<?>> fields = ctModel.filterChildren((Filter<CtType<?>>) el ->
                        el.getQualifiedName().equals(BinaryTree.class.getName()))
                .filterChildren((CtField<?> el) -> el.getParent(new TypeFilter<>(CtType.class))
                        .getQualifiedName().equals(BinaryTree.class.getName()))
                .select((CtField<?> el) -> !(el.isStatic() && el.isFinal()))
                .list();

        // Impl
        List<CtField<?>> implFields = fields.stream()
                .filter(el -> el.getType().isArray())
                .toList();
        assertEquals(0, implFields.size(),
                "You must not add any array fields into 'BinaryTree' class");
    }

    @Test
    void testNoMorePublicMethods() {
        List<CtMethod<?>> methods = ctModel.filterChildren((Filter<CtType<?>>) el ->
                        el.getQualifiedName().equals(BinaryTree.class.getName()))
                .filterChildren((CtMethod<?> m) -> m.getParent(new TypeFilter<>(CtType.class))
                        .getQualifiedName().equals(BinaryTree.class.getName()))
                .list();
        List<String> actual = methods.stream()
                .filter(CtMethod::isPublic)
                .map(CtMethod::getSimpleName)
                .sorted()
                .toList();
        assertEquals(5, actual.size(),
                "You can add only private methods but found: " + actual);
        actual = methods.stream()
                .filter(m -> !(m.isPublic() || m.isPrivate() || m.isProtected()))
                .map(CtMethod::getSimpleName)
                .filter(m -> !m.equals("asTreeString"))
                .toList();
        assertEquals(0, actual.size(),
                "You can add only private methods but found: " + actual);
        actual = methods.stream()
                .filter(CtMethod::isProtected)
                .map(CtMethod::getSimpleName)
                .toList();
        assertEquals(0, actual.size(),
                "You can add only private methods but found: " + actual);
    }

    @Test
    void testNoArrays() {
        List<CtVariable<?>> actual = ctModel
                .filterChildren(new TypeFilter<>(CtVariable.class))
                .select((CtVariable<?> el) -> el.getRoleInParent() != CtRole.PARAMETER)
                .select((CtVariable<?> el) -> el.getType().isArray())
                .list();
        assertEquals(0, actual.size(),
                "You must not use arrays but found: " + actual);
    }
}
