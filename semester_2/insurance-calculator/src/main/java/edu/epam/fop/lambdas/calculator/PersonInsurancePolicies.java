package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.*;
import edu.epam.fop.lambdas.insurance.Accommodation.EmergencyStatus;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;

public final class PersonInsurancePolicies {

  private PersonInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static InsuranceCalculator<Person> childrenDependent(int childrenCountThreshold) {
    return person -> {
      if (person == null
          || person.family().isEmpty()
          || person.family().get().children() == null)
        return Optional.of(InsuranceCoefficient.MIN);

      if (person.family().isPresent()
          && person.family().get().children().size() != 0) {
        int childrenCount = person.family().get().children().size();
        double threshold = childrenCount / (double) childrenCountThreshold;
        int coefficient = (int) (threshold * 100);

        if (coefficient > 100)
          return Optional.of(InsuranceCoefficient.MAX);
        return Optional.of(InsuranceCoefficient.of(coefficient));
      }
      return Optional.of(InsuranceCoefficient.MIN);
    };
  }

  public static InsuranceCalculator<Person> employmentDependentInsurance(BigInteger salaryThreshold,
      Set<Currency> currencies) {
    return person -> {
      if (person == null
          || person.account() == null
          || person.accommodations() == null)
        return Optional.empty();

      boolean isOkSalary = false;

      // Check if salary is met of condition
      Optional<RepeatablePayment> salary = person.employmentHistory().last().salary();
      if (salary.isPresent()) {
        for (Currency currency : currencies) {
          if (currency.equals(salary.get().currency())
              && salary.get().amount().intValue() >= salaryThreshold.intValue()) {
            isOkSalary = true;
          }
        }

        // Check other conditions
        if ((person.employmentHistory().size() >= 4)
            && (person.injuries() == null)
            && (person.accommodations().size() > 0)
            && person.employmentHistory().last().endDate().isEmpty()
            && isOkSalary) {
          return Optional.of(InsuranceCoefficient.MED);
        }
      }
      return Optional.empty();
    };
  }

  public static InsuranceCalculator<Person> accommodationEmergencyInsurance(Set<EmergencyStatus> statuses) {
    return person -> {
      if (person == null
          || person.accommodations() == null)
        return Optional.empty();

      for (Accommodation accommodation : person.accommodations()) {
        if (accommodation.emergencyStatus().isPresent()) {
          if (statuses.contains(accommodation.emergencyStatus().get())) {
            int emergencyOrdinal = accommodation.emergencyStatus().get().ordinal();
            double coefficient = 100 * (1.0 - emergencyOrdinal / (double) EmergencyStatus.values().length);

            return Optional.of(InsuranceCoefficient.of((int) coefficient));
          }
        }
      }
      return Optional.empty();
    };
  }

  public static InsuranceCalculator<Person> injuryAndRentDependentInsurance(BigInteger rentThreshold) {
    return person -> {
      if (person == null
          || person.injuries() == null
          || person.accommodations() == null)
        return Optional.empty();

      boolean isInjuryCausedByAnotherPerson = false;
      boolean isAccommodationRent = false;

      for (Injury injury : person.injuries()) {
        if (injury.culprit().isPresent()) {
          isInjuryCausedByAnotherPerson = true;
          break;
        }
      }

      for (Accommodation accommodation : person.accommodations()) {
        if (accommodation.rent().isPresent() && accommodation.rent().get().currency().equals(Currency.GBP)) {
          isAccommodationRent = true;
        }
        if (isInjuryCausedByAnotherPerson && isAccommodationRent) {
          double rentAmount = accommodation.rent().get().amount().doubleValue();
          double coefficient = 100 * rentAmount / rentThreshold.doubleValue();
          return Optional.of(InsuranceCoefficient.of((int) coefficient));
        }
      }
      return Optional.empty();
    };

  }
}