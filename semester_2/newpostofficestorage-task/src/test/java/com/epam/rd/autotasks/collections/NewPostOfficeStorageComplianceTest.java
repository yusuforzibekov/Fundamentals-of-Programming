package com.epam.rd.autotasks.collections;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtLambda;
import spoon.reflect.visitor.Filter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.core.domain.JavaCall.Predicates.target;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.epam.rd.autotasks", importOptions = {
        ImportOption.DoNotIncludeTests.class,
        ImportOption.DoNotIncludeArchives.class,
        ImportOption.DoNotIncludeJars.class
})
class NewPostOfficeStorageComplianceTest {

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
                    "containsAll".equals(target.getName())
            ) &&
                    target.getOwner().isAssignableTo(List.class) &&
                    (long) target.getParameterTypes().size() == 1
    )));

    @ArchTest
    ArchRule ruleCollection = noClasses().should().implement(Collection.class);

    @ArchTest
    ArchRule ruleMap = noClasses().should().implement(Map.class);

    @Test
    void testComplianceLambda() {
        List<CtLambda<?>> list = ctModel.filterChildren((Filter<CtLambda<?>>) el -> true).list();
        Assertions.assertTrue(list.isEmpty(),
                "Lambdas are not allowed in this project but was: " +
                list);
    }

    @Test
    void testComplianceFori() {
        List<CtLambda<?>> list = ctModel.filterChildren((Filter<CtFor>)el -> true).list();
        Assertions.assertTrue(list.isEmpty(),
                "Only foreach and while loops are allowed in this project but was: " +
                list);
    }
}
