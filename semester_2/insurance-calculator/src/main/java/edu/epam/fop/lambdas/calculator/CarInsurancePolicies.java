package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Car;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public final class CarInsurancePolicies {

  private CarInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static InsuranceCalculator<Car> ageDependInsurance(LocalDate baseDate) {

    return entity -> {
      if (entity == null
          || entity.manufactureDate() == null)
        return Optional.empty();

      long chronoUnit = ChronoUnit.DAYS.between(entity.manufactureDate(), baseDate);

      if (chronoUnit > 0 && chronoUnit <= 366) {
        return Optional.of(InsuranceCoefficient.MAX);
      } else if (chronoUnit > 366 && chronoUnit <= 1826) {
        return Optional.of(InsuranceCoefficient.of(70));
      } else if (chronoUnit > 1826 && chronoUnit <= 3652) {
        return Optional.of(InsuranceCoefficient.of(30));
      }
      return Optional.of(InsuranceCoefficient.MIN);
    };
  }

  public static InsuranceCalculator<Car> priceAndOwningOfFreshCarInsurance(LocalDate baseDate,
      BigInteger priceThreshold, Period owningThreshold) {
    return entity -> {
      if (entity == null
          || entity.price() == null
          || entity.purchaseDate() == null)
        return Optional.empty();
      int carPrice = entity.price().intValue();

      int ownYears = entity.purchaseDate().getYear() + owningThreshold.getYears();
      if (entity.soldDate().isEmpty()
          && ownYears >= baseDate.getYear()) {

        int threeTimes = priceThreshold.multiply(new BigInteger("3")).intValue();
        int twoTimes = priceThreshold.multiply(BigInteger.TWO).intValue();

        if (carPrice >= twoTimes
            && carPrice < threeTimes) {
          return Optional.of(InsuranceCoefficient.MED);
        } else if (carPrice >= threeTimes) {
          return Optional.of(InsuranceCoefficient.MAX);
        } else
          return Optional.of(InsuranceCoefficient.MIN);
      }
      return Optional.empty();
    };
  }
}