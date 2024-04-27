package edu.epam.fop.lambdas;

import java.util.Comparator;

import static java.util.Comparator.*;

public interface Comparators {
    Comparator<Address> ZIP_CODE_COMPARATOR = nullsFirst(comparing(Address::getZipCode, reverseOrder()));
    Comparator<Address> REGION_COMPARATOR = (o1, o2) -> {
        if (o1 == o2) return 0;
        if (o1 == null) return - 1;
        if (o2 == null) return 1;
        return getRegionComparator().compare(o1, o2);
    };
    Comparator<Address> getAddressComparator = nullsLast(comparing(Address::getCountry, nullsLast(naturalOrder())))
            .thenComparing(Address::getCity, nullsLast(naturalOrder()))
            .thenComparing(Address::getZipCode, reverseOrder())
            .thenComparing(Address::getStreet, nullsLast(naturalOrder()))
            .thenComparing(Address::getBuilding, nullsLast(naturalOrder()))
            .thenComparing(Address::getApartment, nullsLast(naturalOrder()));

    Comparator<Address> ADDRESS_COMPARATOR = (o1, o2) -> {
        if (o1 == o2) return 0;
        if (o1 == null) return 1;
        if (o2 == null) return - 1;
        return getAddressComparator.compare(o1, o2);
    };
    Comparator<Person> PERSON_COMPARATOR = getPersonComparator();
    Comparator<Company> COMPANY_COMPARATOR = getCompanyComparator();

    Comparator<Person> FULL_NAME_COMPARATOR = (o1, o2) -> {
        if (o1 == o2) return 0;
        if (o1 == null) return - 1;
        if (o2 == null) return 1;
        return getFullNamePersonComparator().compare(o1, o2);
    };
    Comparator<Person> BIRTHDATE_COMPARATOR = (o1, o2) -> {
        if (o1 == o2) return 0;
        if (o1 == null) return - 1;
        if (o2 == null) return 1;
        return getBirthdayComparator().compare(o1, o2);
    };
    Comparator<Company> REGISTRATION_ID_COMPARATOR = getRegisterIdComparator();
    Comparator<Address> STREET_COMPARATOR = (o1, o2) -> {
        if (o1 == o2) return 0;
        if (o1 == null) return - 1;
        if (o2 == null) return 1;
        return getStreetComparator().compare(o1, o2);
    };

    private static Comparator<Address> getRegionComparator() {
        return nullsLast(comparing(Address::getCountry, nullsLast(naturalOrder())))
                .thenComparing(Address::getCity, nullsLast(naturalOrder()))
                .thenComparing(Address::getZipCode, reverseOrder());
    }

    static Comparator<Address> getStreetComparator() {
        return nullsLast(comparing(Address::getStreet, nullsLast(naturalOrder())))
                .thenComparing(Address::getBuilding, nullsLast(naturalOrder()))
                .thenComparing(Address::getApartment, nullsLast(naturalOrder()));
    }

    private static Comparator<Company> getRegisterIdComparator() {
        return nullsLast(comparing(Company::getRegistrationUuid, naturalOrder()));
    }

    private static Comparator<Company> getCompanyComparator() {
        return nullsLast(comparing(Company::getName, nullsLast(naturalOrder())))
                .thenComparing(Company::registrationUuid, naturalOrder())
                .thenComparing(company -> company.director().name(), naturalOrder())
                .thenComparing(company -> company.director().surname(), naturalOrder())
                .thenComparing(company -> company.director().birthdate(), nullsLast(reverseOrder()))
                .thenComparing(company -> company.director().getAddress(), ADDRESS_COMPARATOR)
                .thenComparing(Company::officeAddress, ADDRESS_COMPARATOR);
    }

    private static Comparator<Person> getPersonComparator() {
        return nullsLast(comparing(Person::getName, naturalOrder())
                .thenComparing(Person::getSurname, naturalOrder())
                .thenComparing(Person::getBirthdate, nullsLast(reverseOrder())))
                .thenComparing(Person::getAddress, nullsLast(ADDRESS_COMPARATOR));
    }

    private static Comparator<Person> getBirthdayComparator() {
        return nullsLast(comparing(Person::birthdate, nullsLast(reverseOrder())));
    }

    private static Comparator<Person> getFullNamePersonComparator() {
        return comparing(Person::getName, naturalOrder())
                .thenComparing(Person::getSurname, naturalOrder());
    }


}