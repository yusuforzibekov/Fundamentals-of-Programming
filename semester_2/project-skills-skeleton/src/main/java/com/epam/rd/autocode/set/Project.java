package com.epam.rd.autocode.set;

import java.util.*;

public class Project {

    private final List<Role> roles;

    public Project(Role... roles) {
        this.roles = Arrays.asList(roles);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public int getConformity(Set<Member> team) {
        List<Entry> projectEntries = new ArrayList<>();
        for (Role role : getRoles()) {
            for (Skill skill : role.getSkills()) {
                projectEntries.add(new Entry(role.getLevel(), skill));
            }
        }
        int originalSize = projectEntries.size();

        List<Entry> teamEntries = new ArrayList<>();

        for (Member member : team) {
            for (Skill skill : member.getSkills()) {
                teamEntries.add(new Entry(member.getLevel(), skill));
            }
        }
        comparatorEntry(projectEntries);
        comparatorEntry(teamEntries);


//        projectEntries.removeAll(teamEntries);

        Iterator<Entry> iterProject = projectEntries.iterator();
        Iterator<Entry> iterTeam = teamEntries.iterator();

//        while (iterProject.hasNext()) {
//            Entry proj = iterProject.next();
//            while (iterTeam.hasNext()) {
//                Entry teamS = iterTeam.next();
//                if (proj.equals(teamS)) {
//                    iterProject.remove();
//                }
//            }
//        }

        for (int i = 0; i < projectEntries.size(); i++) {
            for (int j = 0; j < teamEntries.size(); j++) {
                if (projectEntries.get(i).equals(teamEntries.get(j))) {
                    projectEntries.remove(projectEntries.get(i));
                    teamEntries.remove(teamEntries.get(j));
                    i = 0;
                    j = - 1;
                }
            }
        }

        int difference = originalSize - projectEntries.size();
        return (difference * 100) / originalSize;

    }

    private void comparatorEntry(List<Entry> projectEntries) {
        projectEntries.sort(new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                int level = e1.level.compareTo(e2.level);
                return level != 0 ? level : e1.skill.compareTo(e2.skill);
            }
        });
    }

    private static class Entry implements Comparable<Entry> {
        private final Level level;
        private final Skill skill;

        public Entry(Level level, Skill skill) {
            this.level = level;
            this.skill = skill;
        }


        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(level, entry.level) &&
                    Objects.equals(skill, entry.skill);
        }

        @Override
        public int hashCode() {
            return Objects.hash(level, skill);
        }

        @Override
        public int compareTo(Entry o) {
            int level = this.level.compareTo(o.level);
            return level != 0 ? level : this.skill.compareTo(o.skill);
        }
    }

}
