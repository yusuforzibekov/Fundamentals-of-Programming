package com.epam.autotasks;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ContestResult {

    private final Integer running;
    private final Integer jumping;
    private final Integer purring;
    private final Integer sum;

    public ContestResult(Integer running, Integer jumping, Integer purring) {
        this.running = running;
        this.jumping = jumping;
        this.purring = purring;
        this.sum = countResults(running, jumping, purring);
    }

    public Integer countResults(Integer running, Integer jumping, Integer purring) {
        return running + jumping + purring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof ContestResult that)) return false;
        return Objects.equals(getRunning(), that.getRunning()) && Objects.equals(getJumping(), that.getJumping()) && Objects.equals(getPurring(), that.getPurring()) && Objects.equals(getSum(), that.getSum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRunning(), getJumping(), getPurring(), getSum());
    }
}