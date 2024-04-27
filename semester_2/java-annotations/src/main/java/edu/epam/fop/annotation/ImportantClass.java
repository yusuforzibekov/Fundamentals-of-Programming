package edu.epam.fop.annotation;

import java.io.FileNotFoundException;
import java.lang.annotation.*;
import java.security.GeneralSecurityException;
import java.util.Properties;

@Tracked(value = "important-class-track-number")
public class ImportantClass {

  @Mutable(reason = "")
  private final Properties properties;

  @Source(origin = Source.Origin.DB)
  public ImportantClass(@Mutable(reason = "Needs to filter out invalid properties") Properties properties) {
    this.properties = properties;
  }

  @ExceptionMapping(status = 400, message = "Bad Request", types = {IllegalArgumentException.class, IllegalStateException.class})
  @ExceptionMapping(status = 404, message = "Not Found", types = {FileNotFoundException.class})
  @ExceptionMapping(status = 401, message = "Unauthorized", types = {GeneralSecurityException.class})
  public void execute() {
    // some business logic here
  }

}

@Documentation
@Documented
@Inherited
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Documentation {
  // no additional parameters required
}

@Repeatable(ExceptionMappings.class)
@Documentation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface ExceptionMapping {
  int status();
  String message();
  Class<? extends Throwable>[] types();
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface ExceptionMappings {
  ExceptionMapping[] value();
}

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@interface Mutable {
  String reason() default "";
}

@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
@interface Source {
  Origin origin();

  enum Origin {
    DB,
    PROPERTIES,
    SERVER
  }
}

@Documentation
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Tracked {
  String value();
}
