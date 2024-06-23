package edu.epam.fop.io.headline;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class ReferenceFormatFactory {

  private ReferenceFormatFactory() {
  }

  public static ReferenceFormat simpleFormat() {
    return new SimpleFormat();
  }

  public static ReferenceFormat formalFormat() {
    return new FormalFormat();
  }

  private static class SimpleFormat implements ReferenceFormat {
    @Override
    public String formatCompany(Object[] parameters) {
      return "\"" + parameters[0] + "\"";
    }

    @Override
    public String formatPersonName(Object[] parameters) {
      String firstName = (String) parameters[0];
      String lastName = ((String) parameters[1]).toUpperCase();
      return firstName + " " + lastName;
    }

    @Override
    public String formatInteger(Object[] parameters) {
      int value = (int) parameters[0];
      return String.format(Locale.US, "%,d", value);
    }

    @Override
    public String formatPercent(Object[] parameters) {
      double value = (double) parameters[0];
      return String.format(Locale.US, "%s" + "%.1f%%", (value > 0) ? "+" : "", value);
    }

    @Override
    public String formatDouble(Object[] parameters) {
      double value = (double) parameters[0];
      return String.format(Locale.US, "%,.2f", value);
    }

    @Override
    public String formatDateTime(Object[] parameters) {
      LocalDateTime dateTime = (LocalDateTime) parameters[0];
      String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("H 'o''clock'"));
      return "on " + formattedDate + " at " + formattedTime;
    }
  }

  private static class FormalFormat implements ReferenceFormat {
    @Override
    public String formatCompany(Object[] parameters) {
      return ((String) parameters[0]).toUpperCase() + " LLC";
    }

    @Override
    public String formatPersonName(Object[] parameters) {
      String lastName = ((String) parameters[1]);
      String firstName = (String) parameters[0];
      return lastName + ", " + firstName;
    }

    @Override
    public String formatInteger(Object[] parameters) {
      int value = (int) parameters[0];
      return String.format(Locale.US, " %d", value);
    }

    @Override
    public String formatPercent(Object[] parameters) {
      double value = (double) parameters[0];
      return String.format(Locale.US, "%05.2f%%", value);
    }

    @Override
    public String formatDouble(Object[] parameters) {
      double value = (double) parameters[0];
      return String.format(Locale.US, "%s%.2E", (value > 0) ? "+" : "", value);
    }

    @Override
    public String formatDateTime(Object[] parameters) {
      LocalDateTime dateTime = (LocalDateTime) parameters[0];
      String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH));
      String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
      return "at " + formattedTime + " of " + formattedDate;
    }
  }
}
