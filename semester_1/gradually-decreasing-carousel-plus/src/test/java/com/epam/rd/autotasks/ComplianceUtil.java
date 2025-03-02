package com.epam.rd.autotasks;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.AbstractFilter;

import java.util.List;
import java.util.stream.BaseStream;

class ComplianceUtil {

    static boolean checkTask() {
        //check inheritance
        if(DecrementingCarousel.class != GraduallyDecreasingCarousel.class.getSuperclass()) {
            return false;
        }
        if(CarouselRun.class != GraduallyDecreasingCarouselRun.class.getSuperclass()) {
            return false;
        }

        //check for unused collections and streams
        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/main/java/");
        spoon.buildModel();
        CtPackage spoonRootPackage = spoon.getFactory().Package().getRootPackage();

        CtTypeReference<?> baseCollectionRefType = new Launcher().getFactory().Code().createCtTypeReference(Iterable.class);

        final List<CtInvocation<?>> methodCallsReturningCollections = spoonRootPackage.getElements(
                new AbstractFilter<>() {
                    @Override
                    public boolean matches(final CtInvocation invocation) {
                        return invocation.getType().isSubtypeOf(baseCollectionRefType);
                    }
                }
        );

        final List<CtVariable<?>> collectionsVariable = spoonRootPackage.getElements(
                new AbstractFilter<>() {
                    @Override
                    public boolean matches(final CtVariable var) {
                        return var.getType().isSubtypeOf(baseCollectionRefType);
                    }
                }
        );

        CtTypeReference<?> baseStreamRefType = new Launcher().getFactory().Code().createCtTypeReference(BaseStream.class);

        final List<CtInvocation<?>> methodCallsReturningStreams = spoonRootPackage.getElements(
                new AbstractFilter<>() {
                    @Override
                    public boolean matches(final CtInvocation invocation) {
                        return invocation.getType().isSubtypeOf(baseStreamRefType);
                    }
                }
        );


        return methodCallsReturningCollections.isEmpty()
                && collectionsVariable.isEmpty()
                && methodCallsReturningStreams.isEmpty();
    }

}
