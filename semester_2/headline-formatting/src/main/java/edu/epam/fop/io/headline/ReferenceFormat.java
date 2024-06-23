package edu.epam.fop.io.headline;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public interface ReferenceFormat {

  enum Type {
    COMPANY(String.class),
    PERSON_NAME(String.class, String.class),
    INTEGER(Integer.class),
    PERCENT(Double.class),
    DOUBLE(Double.class),
    DATE_TIME(LocalDateTime.class);

    private final Class<?>[] validParameters;

    Type(Class<?>... validParameters) {
      this.validParameters = validParameters;
    }

    private boolean isValid(Object[] parameters) {
      if (validParameters.length != parameters.length) {
        return false;
      }
      for (int i = 0; i < validParameters.length; i++) {
        if (!validParameters[i].isAssignableFrom(parameters[i].getClass())) {
          return false;
        }
      }
      return true;
    }
  }

  record ReferenceValue(Type type, Object... parameters) {

    public ReferenceValue {
      if (!type.isValid(parameters)) {
        throw new IllegalArgumentException(type + " does not accept " + Arrays.toString(parameters) + " as input");
      }
    }
  }

  default String format(ReferenceValue value) {
    // TODO write your code here
    Objects.requireNonNull(value, "ReferenceValue cannot be null");
    Type type = value.type();
    Object[] parameters = value.parameters();

    switch (type) {
      case COMPANY:
        return formatCompany(parameters);
      case PERSON_NAME:
        return formatPersonName(parameters);
      case INTEGER:
        return formatInteger(parameters);
      case PERCENT:
        return formatPercent(parameters);
      case DOUBLE:
        return formatDouble(parameters);
      case DATE_TIME:
        return formatDateTime(parameters);
      default:
        throw new IllegalArgumentException("Unsupported reference type: " + type);
    }
  }

  String formatCompany(Object[] parameters);

  String formatPersonName(Object[] parameters);

  String formatInteger(Object[] parameters);

  String formatPercent(Object[] parameters);

  String formatDouble(Object[] parameters);

  String formatDateTime(Object[] parameters);
}
