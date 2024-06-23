package edu.epam.fop.io.headline;

import edu.epam.fop.io.headline.ReferenceFormat.ReferenceValue;
import java.util.Map;

public record ReferencedText(String text, Map<String, ReferenceValue> references) {

  public String format(ReferenceFormat format) {
    String formattedText = text;
    for (Map.Entry<String, ReferenceValue> entry : references.entrySet()) {
      String placeholder = "${" + entry.getKey() + "}";
      ReferenceValue referenceValue = entry.getValue();
      String formattedValue = format.format(referenceValue);
      formattedText = formattedText.replace(placeholder, formattedValue);
    }
    return formattedText;
  }
}