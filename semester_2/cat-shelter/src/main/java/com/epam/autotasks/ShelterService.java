package com.epam.autotasks;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ShelterService {
    private static Cat.Staff attendant() {
        var staff = Cat.Staff.values();
        int randomIndex = (int) (Math.random() * staff.length);
        return staff[randomIndex];
    }

    public void assignAttendants(List<ShelterRoom> rooms) {
        rooms.stream()
                .flatMap(room -> room.getCats().stream())
                .forEach(cat -> {
                    if (cat.getAttendant() == null)
                        cat.setAttendant(attendant());
                });
    }

    public List<Cat> getCheckUpList(List<ShelterRoom> rooms, LocalDate date) {
        return rooms.stream()
                .flatMap(room -> room.getCats().stream())
                .filter(cat -> cat.getLastCheckUpDate() != null && cat.getLastCheckUpDate().isBefore(date))
                .collect(Collectors.toList());
    }

    public List<Cat> getCatsByBreed(List<ShelterRoom> rooms, Cat.Breed breed) {
        return rooms.stream()
                .flatMap(room -> room.getCats().stream())
                .filter(cat -> cat.getBreed() != null && cat.getBreed().equals(breed))
                .collect(Collectors.toList());
    }
}
