package edu.epam.fop.io;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SymbolsDistributorTest {
  
  private SymbolsDistributor distributor;
  
  @BeforeEach
  void setUp() {
    distributor = new SymbolsDistributorFactory().getInstance();
  }

  @DisplayName("Factory must return non-null value")
  @Test
  void testFactory() {
    assertNotNull(distributor);
  }

  @DisplayName("Simple test for characters")
  @ParameterizedTest(name = "{0}")
  @MethodSource
  void simpleTest(String caseName, Map<String, Predicate<Integer>> configure) {
    FilesManager manager = new FilesManager(caseName, configure.keySet());
    distributor.distribute(manager.input(), configure.entrySet().stream().collect(Collectors.toMap(
        Entry::getValue,
        e -> manager.target(e.getKey())
    )));
    assertAll(configure.keySet().stream()
        .map(name -> () -> assertArrayEquals(read(manager.expected(name)), read(manager.target(name)),
            "For file " + name))
    );
  }

  static Stream<Arguments> simpleTest() {
    return Stream.of(
        arguments("case_01", Map.<String, Predicate<Integer>>of(
            "letters", Character::isLetter,
            "digits", Character::isDigit
        )),
        arguments("case_02", Map.<String, Predicate<Integer>>of(
            "letters", Character::isLetter,
            "digits", Character::isDigit
        )),
        arguments("case_03", Map.<String, Predicate<Integer>>of(
            "letters", Character::isLetter,
            "digits", Character::isDigit,
            "punctuation", c -> Pattern.matches("\\p{Punct}", String.valueOf((char) c.intValue()))
        )),
        arguments("case_04", Map.<String, Predicate<Integer>>of(
            "letters", Character::isLetter,
            "digits", Character::isDigit,
            "whitespaces", Character::isWhitespace,
            "punctuation", c -> Pattern.matches("\\p{Punct}", String.valueOf((char) c.intValue()))
        ))
    );
  }
  
  @DisplayName("Test for a non-existent input file")
  @Test
  void nonExistentInputFile() {
    assertThrows(IllegalArgumentException.class, () -> distributor.distribute(new File("ahsjkflahuh"), Map.of(
        c -> true, new File("all")
    )));
  }
  
  @DisplayName("Test for a non-readable input file")
  @Test
  void nonReadableInputFile() throws Exception {
    File file = Files.createTempFile("", "").toFile();
    file.setReadable(false);
    assertThrows(IllegalArgumentException.class, () -> distributor.distribute(file, Map.of(
        c -> true, new File("all")
    )));
  }
  
  @DisplayName("Test for a non-normal input file (e.g. directory)")
  @Test
  void nonNormalInputFile() throws Exception {
    File file = Files.createTempDirectory("").toFile();
    assertThrows(IllegalArgumentException.class, () -> distributor.distribute(file, Map.of(
        c -> true, new File("all")
    )));
  }

  private static class FilesManager {

    private final File root;
    private final File input;
    private final Map<String, File> targets;
    private final Map<String, File> expecteds;
    private final Random random;

    FilesManager(String path, Set<String> names) {
      URL url = getClass().getClassLoader().getResource(path);
      if (url == null) {
        throw new IllegalStateException();
      }
      try {
        this.root = new File(url.toURI());
      } catch (URISyntaxException e) {
        throw new IllegalStateException(e);
      }
      this.input = new File(root, "input");
      this.targets = new HashMap<>();
      this.expecteds = new HashMap<>();
      this.random = new Random(System.currentTimeMillis());
      names.forEach(name -> {
        targets.put(name, new File(root, random.nextInt(100, 1000) + "_" + name));
        expecteds.put(name, new File(root, "expected/" + name));
      });
    }

    File input() {
      return input;
    }

    File target(String name) {
      return targets.get(name);
    }

    File expected(String name) {
      return expecteds.get(name);
    }
  }

  private File getDirectory(String path) {
    URL url = getClass().getClassLoader().getResource(path);
    if (url == null) {
      throw new IllegalStateException();
    }
    try {
      return new File(url.toURI());
    } catch (URISyntaxException e) {
      throw new IllegalStateException(e);
    }
  }

  private byte[] read(File file) {
    try (FileInputStream is = new FileInputStream(file)) {
      return is.readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}