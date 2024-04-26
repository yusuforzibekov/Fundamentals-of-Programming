package com.epam.rd.autocode.set;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import static java.util.Arrays.asList;

public class Role {

    private final Level level;

    private final Position position;

    private final Set<Skill> skills;

    public Role(Position position, Level level, Skill... skills) {

        this.position = position;
        this.level = level;
        this.skills = EnumSet.copyOf(Arrays.asList(skills));

    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Level getLevel() {
        return level;
    }

    public Position getPosition() {
        return position;
    }
}
