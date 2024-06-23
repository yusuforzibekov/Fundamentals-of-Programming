package edu.epam.fop.io.headline;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import edu.epam.fop.io.headline.ReferenceFormat.ReferenceValue;
import edu.epam.fop.io.headline.ReferenceFormat.Type;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReferencedTextTest {

  private static final String HEADLINE = """
      ${CEO} is a CEO of ${company}. ${company} stock price was changed by ${stock change} ${change date},
      it has around ${employees number} employees. Budget change estimation for the end of this year is ${estimation}$.
      """;

  @ParameterizedTest(name = "test {index}")
  @MethodSource
  @DisplayName("Tests correct references substitution")
  void testHeadlineFormatting(Map<String, ReferenceValue> references, ReferenceFormat format, String expectedHeadline) {
    ReferencedText text = new ReferencedText(HEADLINE, references);
    String actualHeadline = assertDoesNotThrow(() -> text.format(format));
    assertEquals(expectedHeadline, actualHeadline);
  }

  static Stream<Arguments> testHeadlineFormatting() {
    return Stream.of(
        arguments(
            refs("Oolong", "Tusk", "Edison", -13.46,
                LocalDateTime.of(2020, Month.APRIL, 13, 15, 45),
                26, -4750000d),
            ReferenceFormatFactory.simpleFormat(),
            """
                Oolong TUSK is a CEO of "Edison". "Edison" stock price was changed by -13.5% on 2020-04-13 at 15 o'clock,
                it has around 26 employees. Budget change estimation for the end of this year is -4,750,000.00$.
                """
        ),
        arguments(
            refs("John", "Cool", "Bar", +2.147,
                LocalDateTime.of(2020, Month.APRIL, 13, 7, 0),
                24763, 5.725d),
            ReferenceFormatFactory.simpleFormat(),
            """
                John COOL is a CEO of "Bar". "Bar" stock price was changed by +2.1% on 2020-04-13 at 7 o'clock,
                it has around 24,763 employees. Budget change estimation for the end of this year is 5.73$.
                """
        ),
        arguments(
            refs("Brian", "August", "Queen", -13.46,
                LocalDateTime.of(2020, Month.APRIL, 13, 15, 45),
                26, -4750000d),
            ReferenceFormatFactory.formalFormat(),
            """
                August, Brian is a CEO of QUEEN LLC. QUEEN LLC stock price was changed by -13.46% at 15:45 of 13 Apr 2020,
                it has around  26 employees. Budget change estimation for the end of this year is -4.75E+06$.
                """
        ),
        arguments(
            refs("Key", "Space", "Enter", +5.1,
                LocalDateTime.of(2020, Month.JANUARY, 1, 15, 45),
                1226, 1d),
            ReferenceFormatFactory.formalFormat(),
            """
                Space, Key is a CEO of ENTER LLC. ENTER LLC stock price was changed by 05.10% at 15:45 of 1 Jan 2020,
                it has around  1226 employees. Budget change estimation for the end of this year is +1.00E+00$.
                """
        )
    );
  }

  private static Map<String, ReferenceValue> refs(String ceoName, String ceoLastName, String company,
      Double stockChange, LocalDateTime changeDate, Integer employeesNumber, Double estimation) {
    return Map.of(
        "CEO", new ReferenceValue(Type.PERSON_NAME, ceoName, ceoLastName),
        "company", new ReferenceValue(Type.COMPANY, company),
        "stock change", new ReferenceValue(Type.PERCENT, stockChange),
        "change date", new ReferenceValue(Type.DATE_TIME, changeDate),
        "employees number", new ReferenceValue(Type.INTEGER, employeesNumber),
        "estimation", new ReferenceValue(Type.DOUBLE, estimation)
    );
  }
}