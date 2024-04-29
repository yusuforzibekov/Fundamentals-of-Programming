package edu.epam.fop.lambdas.insurance;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

public class Employment implements Comparable<Employment> {

  private static final Comparator<Employment> CMP = Comparator.comparing(Employment::startDate);

  private String company;
  private String title;
  private RepeatablePayment salary;
  private LocalDate startDate;
  private LocalDate endDate;

  public Employment() {
  }

  public Optional<RepeatablePayment> salary() {
    return Optional.ofNullable(salary);
  }

  public Optional<LocalDate> endDate() {
    return Optional.ofNullable(endDate);
  }

  @Override
  public int compareTo(Employment o) {
    return CMP.compare(this, o);
  }

  public String company() {
    return this.company;
  }

  public String title() {
    return this.title;
  }

  public LocalDate startDate() {
    return this.startDate;
  }

  public Employment company(String company) {
    this.company = company;
    return this;
  }

  public Employment title(String title) {
    this.title = title;
    return this;
  }

  public Employment salary(RepeatablePayment salary) {
    this.salary = salary;
    return this;
  }

  public Employment startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  public Employment endDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Employment)) {
      return false;
    }
    final Employment other = (Employment) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$company = this.company;
    final Object other$company = other.company;
    if (this$company == null ? other$company != null : !this$company.equals(other$company)) {
      return false;
    }
    final Object this$title = this.title;
    final Object other$title = other.title;
    if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
      return false;
    }
    final Object this$salary = this.salary;
    final Object other$salary = other.salary;
    if (this$salary == null ? other$salary != null : !this$salary.equals(other$salary)) {
      return false;
    }
    final Object this$startDate = this.startDate;
    final Object other$startDate = other.startDate;
    if (this$startDate == null ? other$startDate != null : !this$startDate.equals(other$startDate)) {
      return false;
    }
    final Object this$endDate = this.endDate;
    final Object other$endDate = other.endDate;
    if (this$endDate == null ? other$endDate != null : !this$endDate.equals(other$endDate)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Employment;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $company = this.company;
    result = result * PRIME + ($company == null ? 43 : $company.hashCode());
    final Object $title = this.title;
    result = result * PRIME + ($title == null ? 43 : $title.hashCode());
    final Object $salary = this.salary;
    result = result * PRIME + ($salary == null ? 43 : $salary.hashCode());
    final Object $startDate = this.startDate;
    result = result * PRIME + ($startDate == null ? 43 : $startDate.hashCode());
    final Object $endDate = this.endDate;
    result = result * PRIME + ($endDate == null ? 43 : $endDate.hashCode());
    return result;
  }

  public String toString() {
    return "Employment(company=" + this.company + ", title=" + this.title + ", salary=" + this.salary + ", startDate="
        + this.startDate + ", endDate=" + this.endDate + ")";
  }
}
