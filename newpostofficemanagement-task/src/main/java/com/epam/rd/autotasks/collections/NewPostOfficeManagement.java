package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This interface describes the business logic of New Post storage service.
 */
public interface NewPostOfficeManagement {
    /**
     * Returns a parcel by the specified id
     */
    Optional<Box> getBoxById(int id);

    /**
     * Gets all parcels as a String, sorted in descending order of weight
     */
    String getDescSortedBoxesByWeight();

    /**
     * Gets all parcels as a String, sorted in ascending order of cost
     */
    String getAscSortedBoxesByCost();

    /**
     * Gets all parcels of the specified recipient
     */
    List<Box> getBoxesByRecipient(String recipient);
}
