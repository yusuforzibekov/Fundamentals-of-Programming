package com.epam.rd.autocode.set;


import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import static java.util.Arrays.asList;

public class Member {

    private final String name;

    private final Level level;

    private final Set<Skill> skills;

    public Member(String name, Level level, Skill... skills) {

        this.name = name;
        this.level = level;
        this.skills = EnumSet.copyOf(Arrays.asList(skills));
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

}
