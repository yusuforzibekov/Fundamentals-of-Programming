package com.epam.rd.autotasks.collections;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Filter;

import java.util.*;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.core.domain.JavaCall.Predicates.target;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.*;

@AnalyzeClasses(packages = "com.epam.rd.autotasks", importOptions = {
        ImportOption.DoNotIncludeTests.class,
        ImportOption.DoNotIncludeArchives.class,
        ImportOption.DoNotIncludeJars.class
})
class NewPostOfficeManagementComplianceTest {

    static CtModel ctModel;

    @BeforeAll
    static void getCtModel() {
        SpoonAPI spoon;
        spoon = new Launcher();
        spoon.addInputResource("src/main/java/");
        ctModel = spoon.buildModel();
    }

    @ArchTest
    ArchRule ruleStreams = noClasses()
            .should()
            .callMethodWhere(target(describe("Methods Collection#stream() should not be used",
                    target -> "stream".equals(target.getName()) &&
                            target.getOwner().isAssignableTo(Collection.class) &&
                            target.getParameterTypes().isEmpty()
            )));

    @ArchTest
    ArchRule ruleIndexOfContainsContainsAll = noClasses()
            .should()
            .callMethodWhere(target(describe("Methods List#indexOf(), List#contains(), " +
                            "List#containsAll() should not be used",
                    target -> (
                            "indexOf".equals(target.getName()) ||
                                    "contains".equals(target.getName()) ||
                                    "containsAll".equals(target.getName())) &&
                            target.getOwner().isAssignableTo(List.class) &&
                            (long) target.getParameterTypes().size() == 1))
            );

    @ArchTest
    ArchRule ruleCollection = noClasses().should().implement(Collection.class);

    @ArchTest
    ArchRule ruleMap = noClasses().should().implement(Map.class);

    @Test
    void testComplianceLambda() {
        List<CtLambda<?>> list = ctModel.filterChildren((Filter<CtLambda<?>>) el -> true).list();
        assertTrue(list.isEmpty(),
                "Lambdas are not allowed in this project but was: " +
                        list);
    }

    @Test
    void testOnlyComparatorFields() {
        List<CtField<?>> list = ctModel.filterChildren((Filter<CtField<?>>) el -> true)
                .select((CtField<?> f) -> f.getParent((Filter<CtClass<?>>) el -> true)
                        .getQualifiedName()
                        .equals(NewPostOfficeManagementImpl.class.getName()))
                .select((CtField<?> f) -> !f.getType().getQualifiedName().equals("java.util.Comparator"))
                .select((CtField<?> f) -> !(f.getType().getQualifiedName().equals("java.util.List") &&
                        f.getSimpleName().equals("parcels")))
                .list();
        assertTrue(list.isEmpty(),
                "You don't need to add any other fields except Comparators,\nbut was: " +
                        list);
    }

    @Test
    void testSortInGetBoxesByRecipient() {
        CtMethod<?> method = ctModel.filterChildren((CtClass<?> el) -> el.getQualifiedName().equals(NewPostOfficeManagementImpl.class.getName()))
                .filterChildren((CtMethod<?> el) -> {
                    String simpleName = el.getSimpleName();
                    return simpleName.equals("getBoxesByRecipient");
                })
                .first();
        assertNotNull(method, "The method must be present: getBoxesByRecipient");

        List<String> statements = method.getBody().getStatements().stream()
                .map(Objects::toString)
                .filter(m -> m.contains("sort(") || m.contains("Collections.binarySearch"))
                .toList();
        List<String> actual = new ArrayList<>();
        statements.forEach(m -> {
            if (m.contains("parcels.sort(") || m.contains("java.util.Collections.sort(parcels,")) {
                actual.add("sort(");
            } else if (m.contains("Collections.binarySearch")) {
                actual.add("Collections.binarySearch");
            }
        });
        List<String> expected = List.of("sort(", "Collections.binarySearch");
        assertIterableEquals(expected, actual, "");
    }

    @Test
    void testGetBoxByIdShouldNotContainAnyLoops() {
        CtMethod<?> method = ctModel.filterChildren((CtClass<?> el) ->
                        el.getQualifiedName()
                                .equals(NewPostOfficeManagementImpl.class.getName()))
                .filterChildren((CtMethod<?> el) -> {
                    String simpleName = el.getSimpleName();
                    return simpleName.equals("getBoxById");
                })
                .first();
        assertNotNull(method, "The method must be present: getBoxesByRecipient");
        List<CtStatement> statements = method.getBody().getStatements().stream()
                .filter(s -> s instanceof CtLoop)
                .toList();
        assertEquals(0, statements.size(),
                "You can use an index returned by binarySearch instead of " +
                        "organize loops:\n" + statements);
    }
}
