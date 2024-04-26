package com.epam.rd.autocode.collections;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

public class StudentGradebookImpl implements StudentGradebook {

	private final Map<Student, Map<String, BigDecimal>> map;
	private final Comparator<Student> studentComparator;

	public StudentGradebookImpl() {
		studentComparator = new Comparator<Student>() {
			@Override
			public int compare(Student s1, Student s2) {
				int result = s1.getLastName().compareTo(s2.getLastName());
				if (result == 0) {
					result = s1.getFirstName().compareTo(s2.getFirstName());
				}
				if (result == 0) {
					result = s1.getGroup().compareTo(s2.getGroup());
				}
				return result;
			}
		};
		map = new TreeMap<>(studentComparator);
	}

	@Override
	public boolean addEntryOfStudent(Student student, String discipline, BigDecimal grade) {
		for (Student student1 : map.keySet()) {
			for (Map<String, BigDecimal> value : map.values()) {
				for (String dis : value.keySet()) {
					if (student1.equals(student) && dis.equals(discipline))
						return false;
					break;
				}
				break;
			}
			break;
		}
		map.computeIfAbsent(student, new Function<Student, Map<String, BigDecimal>>() {
			@Override
			public Map<String, BigDecimal> apply(Student k) {
				return new HashMap<>();
			}

		}).put(discipline, grade);

		return true;

	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Comparator<Student> getComparator() {
		return studentComparator;
	}

	@Override
	public List<String> getStudentsByDiscipline(String discipline) {
		List<String> result = new ArrayList<>();

		for (Map.Entry<Student, Map<String, BigDecimal>> entry : map.entrySet()) {
			Student student = entry.getKey();
			Map<String, BigDecimal> grades = entry.getValue();
			BigDecimal grade = grades.get(discipline);

			if (grade != null) {
				result.add(student.getFirstName() + "_" + student.getLastName() + " : " + grade);
			}
		}

		return result;
	}

	@Override
	public Map<Student, Map<String, BigDecimal>> removeStudentsByGrade(BigDecimal grade) {
		Map<Student, Map<String, BigDecimal>> removedStudents = new TreeMap<>(studentComparator);

		Iterator<Map.Entry<Student, Map<String, BigDecimal>>> iterator = map.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<Student, Map<String, BigDecimal>> entry = iterator.next();
			Map<String, BigDecimal> grades = entry.getValue();

			boolean belowGrade = false;
			for (BigDecimal g : grades.values()) {
				if (g.compareTo(grade) < 0) {
					belowGrade = true;
					break;
				}
			}

			if (belowGrade) {
				removedStudents.put(entry.getKey(), entry.getValue());
				iterator.remove();
			}
		}

		return removedStudents;
	}

	@Override
	public Map<BigDecimal, List<Student>> getAndSortAllStudents() {
		Map<BigDecimal, List<Student>> result = new TreeMap<>(Collections.reverseOrder());

		for (Map.Entry<Student, Map<String, BigDecimal>> entry : map.entrySet()) {
			Student student = entry.getKey();
			Map<String, BigDecimal> grades = entry.getValue();

			BigDecimal summ = BigDecimal.ZERO;
			for (BigDecimal bigDecimal : grades.values()) {
				summ = summ.add(bigDecimal);
			}

			BigDecimal average = summ.divide(BigDecimal.valueOf(grades.size()));

			result.computeIfAbsent(average, new Function<BigDecimal, List<Student>>() {
				@Override
				public List<Student> apply(BigDecimal k) {
					return new ArrayList<>();
				}
			}).add(student);
		}

		return result;
	}
}