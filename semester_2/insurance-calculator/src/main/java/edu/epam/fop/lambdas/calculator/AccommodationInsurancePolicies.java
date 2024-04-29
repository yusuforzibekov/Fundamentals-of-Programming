package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Accommodation;
import edu.epam.fop.lambdas.insurance.Currency;

import java.math.BigInteger;
import java.time.Period;
import java.util.Optional;

public final class AccommodationInsurancePolicies {

  private AccommodationInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  static InsuranceCalculator<Accommodation> rentDependentInsurance(BigInteger divider) {

    return entity -> {
      if (entity == null)
        return Optional.empty();
      if (entity.rent().isPresent()) {
        BigInteger amount = entity.rent().get().amount();
        Period month = entity.rent().get().unit();
        Currency currency = entity.rent().get().currency();

        if (amount.doubleValue() != 0
            && month.getMonths() >= 1
            && currency.equals(Currency.USD)) {

          double resultRent = amount.doubleValue() / divider.doubleValue();
          double rent = resultRent * 100;

          if (rent == 75.0) {
            return Optional.of(InsuranceCoefficient.of(75));
          } else if (rent == 50.0) {
            return Optional.of(InsuranceCoefficient.MED);
          } else
            return Optional.of(InsuranceCoefficient.MAX);
        }
      }
      return Optional.empty();
    };
  }

  static InsuranceCalculator<Accommodation> priceAndRoomsAndAreaDependentInsurance(BigInteger priceThreshold,
      int roomsThreshold, BigInteger areaThreshold) {
    return entity -> {
      if (entity == null
          || entity.price() == null
          || entity.rooms() == null
          || entity.area() == null)
        return Optional.of(InsuranceCoefficient.MIN);

      BigInteger price = entity.price();
      int rooms = entity.rooms();
      BigInteger area = entity.area();

      if (price.doubleValue() >= priceThreshold.doubleValue()
          && rooms >= roomsThreshold
          && area.doubleValue() >= areaThreshold.doubleValue()) {
        return Optional.of(InsuranceCoefficient.MAX);
      }

      return Optional.of(InsuranceCoefficient.MIN);
    };
  }
}