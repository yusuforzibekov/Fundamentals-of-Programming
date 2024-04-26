package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

public class NewPostOfficeStorageImpl implements NewPostOfficeStorage {
    private final List<Box> parcels;

    /**
     * Creates internal storage for becoming parcels
     */
    public NewPostOfficeStorageImpl() {
        parcels = new LinkedList<>();
    }

    /**
     * Creates own storage and appends all parcels into own storage.
     * It must add either all the parcels or nothing, if an exception occurs.
     *
     * @param boxes a collection of parcels.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} values.
     */
    public NewPostOfficeStorageImpl(Collection<Box> boxes) {
        parcels = new ArrayList<>();
        for (Box box : boxes) {
            if (box == null)
                throw new NullPointerException();
            parcels.add(box);
        }
    }

    @Override
    public boolean acceptBox(Box box) {
        if (box == null)
            throw new NullPointerException();
        parcels.add(box);
        return true;
    }

    @Override
    public boolean acceptAllBoxes(Collection<Box> boxes) {
        for (Box box : boxes) {
            if (box == null) {
                throw new NullPointerException();
            }
            parcels.add(box);
        }
        return true;
    }

    @Override
    public boolean carryOutBoxes(Collection<Box> boxes) {
        for (Box box : boxes) {
            if (box == null)
                throw new NullPointerException();
        }

        return parcels.removeAll(boxes);
    }

    @Override
    public List<Box> carryOutBoxes(Predicate<Box> predicate) {
        if (predicate == null)
            throw new NullPointerException();
        List<Box> deleteBoxes = new ArrayList<>();

        Iterator<Box> iterator = parcels.iterator();

        while (iterator.hasNext()) {
            Box box = iterator.next();
            if (predicate.test(box)) {
                deleteBoxes.add(box);
                iterator.remove();
            }
        }
        return deleteBoxes;
    }

    @Override
    public List<Box> getAllWeightLessThan(double weight) {
        if (weight <= 0)
            throw new IllegalArgumentException();
        return searchBoxes(new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                return box.getWeight() < weight;
            }
        });

    }

    @Override
    public List<Box> getAllCostGreaterThan(BigDecimal cost) {
        if (cost.doubleValue() < 0)
            throw new IllegalArgumentException();

        return searchBoxes(new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                return box.getCost().compareTo(cost) > 0;
            }
        });
    }

    @Override
    public List<Box> getAllVolumeGreaterOrEqual(double volume) {
        if (volume < 0)
            throw new IllegalArgumentException();
        return searchBoxes(new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                return box.getVolume() >= volume;
            }
        });
    }

    @Override
    public List<Box> searchBoxes(Predicate<Box> predicate) {
        List<Box> boxes = new ArrayList<>();
        for (Box parcel : parcels) {
            if (predicate.test(parcel)) {
                boxes.add(parcel);
            }
        }
        return boxes;
    }

    @Override
    public void updateOfficeNumber(Predicate<Box> predicate, int newOfficeNumber) {
        for (Box parcel : parcels) {
            if (predicate.test(parcel)) {
                parcel.setOfficeNumber(newOfficeNumber);
            }
        }
    }
}