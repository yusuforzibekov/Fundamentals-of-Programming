package com.epam.rd.autocode.collection.list;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class ComplianceTest {

    private static final java.util.Set<String> ALLOWED_CLASSES = new TreeSet<>(asList(
            "java.util.Iterator",
            "java.util.NoSuchElementException",
            "java.util.Objects",
            "java.util.Optional"));
    static CtModel ctModel;

    @BeforeAll
    static void init() {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/main/java/");
        ctModel = spoon.buildModel();
    }

    @Test
    void testUsages() {
        java.util.List<String> types = ctModel
                .getElements(new TypeFilter<>(CtTypeReference.class))
                .stream()
                .filter(r -> r.toString().startsWith("java.util."))
                .map(CtTypeReference::getQualifiedName)
                .distinct()
                .sorted()
                .toList();

        assertTrue(types.size() <= ALLOWED_CLASSES.size(),
                "You can use exactly specified types from 'java.util.*'" +
                        " packages and theirs subpackages:\nexpected: " +
                        ALLOWED_CLASSES + "\nbut found " + types  + "\n");
        types.forEach(t -> assertTrue(ALLOWED_CLASSES.contains(t),
                "You can use exactly specified types from 'java.util.*'" +
                        " packages and theirs subpackages:\nexpected: " +
                        ALLOWED_CLASSES + "\nbut found " + types  + "\n"));
    }

    @Test
    void testSizeMustReturnFieldValue() {
        List<CtElement> statements = ctModel.filterChildren((Filter<CtType<?>>) el ->
                        el.getQualifiedName().equals(SingleLinkedListImpl.class.getName()))
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
    void testNoMoreFieldsInSingleLinkedListImpl() {
        List<CtField<?>> fields = ctModel.filterChildren((Filter<CtType<?>>) el ->
                        el.getQualifiedName().equals(SingleLinkedListImpl.class.getName()))
                .filterChildren((CtField<?> el) -> true)
                .list();

        // Impl
        List<CtField<?>> implFields = fields.stream()
                .filter(el -> el.getParent(new TypeFilter<>(CtType.class))
                        .getQualifiedName()
                        .equals(SingleLinkedListImpl.class.getName()))
                .toList();
        assertEquals(1, implFields.size(),
                "You must not add any additional fields into 'SingleLinkedListImpl' class");

        Map<String, String> actual = implFields.stream().
                collect(Collectors.toMap(f -> f.getSimpleName(),
                        f -> f.getReference().getType().getQualifiedName()));
        assertEquals("com.epam.rd.autocode.collection.list.SingleLinkedListImpl$Node", actual.get("head"));
    }

    @Test
    void testNoMoreFieldsInNode() {
        List<CtField<?>> fields = ctModel.filterChildren((Filter<CtType<?>>) el ->
                        el.getQualifiedName().equals("com.epam.rd.autocode.collection.list.SingleLinkedListImpl$Node"))
                .filterChildren((CtField<?> el) -> true)
                .list();
        Map<String, String> nodeFields = fields.stream()
                .collect(Collectors.toMap(f -> f.getSimpleName(),
                        f -> f.getType().getQualifiedName()));
        assertEquals(2, nodeFields.size(),
                "You must not add any additional fields in the 'Node' class");
        assertEquals("com.epam.rd.autocode.collection.list.SingleLinkedListImpl$Node", nodeFields.get("next"),
                "You must not change fields in the 'Node' class");
        assertEquals("java.lang.Object", nodeFields.get("data"),
                "You must not change fields in the 'Node' class");
    }

    @Test
    void testNoMoreMethodsInNode() {
        CtType<?> node = ctModel.filterChildren((Filter<CtType<?>>) el ->
                        el.getQualifiedName().equals("com.epam.rd.autocode.collection.list.SingleLinkedListImpl$Node"))
                .first();
        List<CtMethod<?>> methods = node
                .filterChildren((Filter<CtMethod<?>>) el -> true)
                .list();
        List<CtMethod<?>> expected = methods.stream().filter(m -> !m.getSimpleName().equals("toString")).toList();
        assertEquals(0, expected.size(),
                "You must not add any additional methods " +
                        "to 'SingleLinkedListImpl.Node' class except constructors, \n" +
                        "but was: " + expected.stream().map(Object::toString).toList());
    }
}
