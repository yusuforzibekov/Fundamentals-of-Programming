package com.epam.rd.autotasks.collections;

import java.util.*;

public class NewPostOfficeManagementImpl implements NewPostOfficeManagement {
    private final List<Box> parcels;

    /**
     * Creates own storage and appends all parcels into it.
     * It must add either all the parcels or nothing, if an exception occurs.
     *
     * @param boxes a collection of parcels.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} values.
     */
    public NewPostOfficeManagementImpl(Collection<Box> boxes) {
        for (Box box : boxes) {
            if (box == null)
                throw new NullPointerException();
        }
        this.parcels = new ArrayList<>(boxes);
        this.parcels.sort(new BoxComparator());
    }

    @Override
    public Optional<Box> getBoxById(int id) {
        parcels.sort(new BoxComparator());
        int index = Collections.binarySearch(parcels, new BoxTemplate(id), new BoxComparator());
        if (index >= 0) {
            return Optional.of(parcels.get(index));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String getDescSortedBoxesByWeight() {
        parcels.sort(new WeightComparator());
        StringBuilder sb = new StringBuilder();
        for (Box parcel : parcels) {
            sb.append(parcel).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public String getAscSortedBoxesByCost() {
        parcels.sort(new CostComparator());
        StringBuilder sb = new StringBuilder();

        for (Box parcel : parcels) {
            sb.append(parcel).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public List<Box> getBoxesByRecipient(String recipient) {
        Objects.requireNonNull(recipient);
        parcels.sort(new RecipientComparator().thenComparing(new BoxComparator()));

        List<Box> recipientBoxes = new ArrayList<>();
        int startIndex = Collections.binarySearch(parcels, new BoxTemplate(recipient), new RecipientComparator());
        if (startIndex < 0) {
            return recipientBoxes;
        }
        for (Box parcel : parcels) {
            if (parcel.getRecipient().equals(recipient)) {
                recipientBoxes.add(parcel);
            }
        }
        return recipientBoxes;
    }

    private static class RecipientComparator implements Comparator<Box> {
        @Override
        public int compare(Box o1, Box o2) {
            return o2.getRecipient().compareTo(o1.getRecipient());
        }
    }

    private static class BoxComparator implements Comparator<Box> {
        @Override
        public int compare(Box o1, Box o2) {
            return Integer.compare(o1.getId(), o2.getId());
        }
    }

    private static class CostComparator implements Comparator<Box> {
        @Override
        public int compare(Box o1, Box o2) {
            return Double.compare(o1.getCost().doubleValue(), o2.getCost().doubleValue());
        }
    }

    private static class WeightComparator implements Comparator<Box> {
        @Override
        public int compare(Box o1, Box o2) {
            return Double.compare(o2.getWeight(), o1.getWeight());
        }
    }
}