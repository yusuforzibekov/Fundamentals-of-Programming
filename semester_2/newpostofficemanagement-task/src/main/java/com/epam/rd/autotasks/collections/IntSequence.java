package com.epam.rd.autotasks.collections;

import java.util.concurrent.atomic.AtomicInteger;

class IntSequence {
    static AtomicInteger base = new AtomicInteger();

    static synchronized void reset() {
        base = new AtomicInteger();
    }

    static int next() {
        return base.incrementAndGet();
    }

}
