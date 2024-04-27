package edu.epam.fop.annotation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import edu.epam.fop.annotation.*;
import java.io.FileNotFoundException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class AnnotationTest {

  @ParameterizedTest(name = "{0} must have RUNTIME retention")
  @ValueSource(classes = {
      Documentation.class,
      ExceptionMapping.class,
      Source.class,
      Tracked.class
  })
  @DisplayName("All custom annotations must have RUNTIME retention policy")
  void retentionPolicyTest(Class<?> annotationType) {
    Retention retentionAnnotation = annotationType.getDeclaredAnnotation(Retention.class);
    assertNotNull(retentionAnnotation);
    assertEquals(RetentionPolicy.RUNTIME, retentionAnnotation.value());
  }
  
  @ParameterizedTest(name = "{0} must have {1} as target")
  @MethodSource
  @DisplayName("All custom annotations must have proper target")
  void targetTest(Class<?> annotationType, ElementType[] expectedTarget) {
    Target targetAnnotation = annotationType.getDeclaredAnnotation(Target.class);
    assertNotNull(targetAnnotation);
    assertArrays(expectedTarget, targetAnnotation.value(), Comparator.comparing(Enum::ordinal));
  }
  
  static Arguments[] targetTest() {
    return new Arguments[]{
        arguments(Documentation.class, new ElementType[]{ElementType.ANNOTATION_TYPE}),
        arguments(ExceptionMapping.class, new ElementType[]{ElementType.METHOD}),
        arguments(Source.class, new ElementType[]{ElementType.CONSTRUCTOR}),
        arguments(Mutable.class, new ElementType[] {ElementType.FIELD, ElementType.PARAMETER}),
        arguments(Tracked.class, new ElementType[]{ElementType.TYPE}),
    };
  }
  
  @Test
  @DisplayName("Documentation annotation must be Documented and Inherited")
  void documentationAnnotationTest() {
    assertNotNull(Documentation.class.getDeclaredAnnotation(Documented.class));
    assertNotNull(Documentation.class.getDeclaredAnnotation(Inherited.class));
  }

  @Test
  @DisplayName("Target type must be annotated by @Tracked with proper parameters")
  void typeLevelTest() {
    Tracked annotation = ImportantClass.class.getDeclaredAnnotation(Tracked.class);
    assertNotNull(annotation);
    assertEquals("important-class-track-number", annotation.value());
  }
  
  @Test
  @DisplayName("@Tracked and @ExceptionMapping must be annotated by @Documentation")
  void annotationLevelTest() {
    assertNotNull(Tracked.class.getDeclaredAnnotation(Documentation.class));
    assertNotNull(ExceptionMapping.class.getDeclaredAnnotation(Documentation.class));
  }
  
  @Test
  @DisplayName("Target type's constructor must be annotated by @Source with proper parameters")
  void constructorLevelTest() throws Exception {
    Constructor<ImportantClass> constructor = ImportantClass.class.getDeclaredConstructor(Properties.class);
    assertNotNull(constructor);
    Source annotation = constructor.getDeclaredAnnotation(Source.class);
    assertNotNull(annotation);
    assertEquals(Origin.DB, annotation.origin());
  }

  @Test
  @DisplayName("Target method must be annotated by @ExceptionMapping annotations with proper parameters")
  void methodLevelTest() throws Exception {
    Method method = ImportantClass.class.getMethod("execute");
    assertNotNull(method);
    method.setAccessible(true);
    ExceptionMapping[] annotations = method.getDeclaredAnnotationsByType(ExceptionMapping.class);
    assertNotNull(annotations);
    assertEquals(3, annotations.length);
    Arrays.sort(annotations, Comparator.comparing(ExceptionMapping::status));
    assertAll(
        () -> assertAnnotationMapping(annotations[0], 400, "Bad Request",
            IllegalStateException.class, IllegalArgumentException.class),
        () -> assertAnnotationMapping(annotations[1], 401, "Unauthorized",
            GeneralSecurityException.class),
        () -> assertAnnotationMapping(annotations[2], 404, "Not Found",
            FileNotFoundException.class));
  }

  @SafeVarargs
  private void assertAnnotationMapping(ExceptionMapping annotation, int status, String message,
      Class<? extends Throwable>... types) {
    assertEquals(status, annotation.status());
    assertEquals(message, annotation.message());
    assertArrays(types, annotation.types(), Comparator.comparing(Class::getSimpleName));
  }
  
  @Test
  @DisplayName("Target field must be annotated by @Mutable without any provided reason")
  void fieldLevelTest() throws Exception {
    var field = ImportantClass.class.getDeclaredField("properties");
    var annotation = field.getDeclaredAnnotation(Mutable.class);
    assertNotNull(annotation);
    assertEquals("", annotation.reason());
  }
  
  @Test
  @DisplayName("Target parameter must be annotated by @Mutable with provided reason")
  void parameterLevelTest() throws Exception {
    var constructor = ImportantClass.class.getConstructor(Properties.class);
    var annotation = constructor.getParameters()[0].getDeclaredAnnotation(Mutable.class);
    assertNotNull(annotation);
    assertEquals("Needs to filter out invalid properties", annotation.reason());
  }
  
  private <T> void assertArrays(T[] expected, T[] actual, Comparator<T> cmp) {
    Arrays.sort(expected, cmp);
    Arrays.sort(actual, cmp);
    assertArrayEquals(expected, actual);
  }
}