package com.epam.rd.autocode.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Demo {
	
	public static void main(String[] args) {
		Project project = new Project(
			new Role(Position.DEVELOPER, Level.A1, Skill.JAVA, Skill.DATABASE),
			new Role(Position.KEY_DEVELOPER, Level.A2, Skill.JAVA, Skill.DATABASE, Skill.SPRING),
			new Role(Position.TESTER, Level.A3, Skill.TESTING_TOOLS, Skill.AWS),
			new Role(Position.TESTER, Level.A3, Skill.AWS));
		
		Set<Member> team = new HashSet<>(Arrays.asList(
			new Member("Name1", Level.A1, Skill.JAVA, Skill.DATABASE),
			new Member("Name2", Level.A2, Skill.JAVA, Skill.DATABASE, Skill.SPRING),
			new Member("Name3", Level.A3, Skill.TESTING_TOOLS, Skill.AWS),
			new Member("Name4", Level.A3, Skill.TESTING_TOOLS)));

		System.out.println(project.getConformity(team));
	}

}
