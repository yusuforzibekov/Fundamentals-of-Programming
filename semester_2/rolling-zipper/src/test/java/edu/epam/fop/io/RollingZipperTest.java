package edu.epam.fop.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RollingZipperTest {

  private RollingZipper zipper;

  @BeforeEach
  void setUp() {
    zipper = new RollingZipper();
  }

  @ParameterizedTest(name = "{0} test")
  @MethodSource
  void test(String caseName, Iterable<File> inputs, RollingPolicy policy, List<List<ZipEntryExpectation>> expectations)
      throws IOException {
    OutputsIterator output = new OutputsIterator();

    zipper.zip(inputs, policy, output);

    assertEquals(expectations.size(), output.streams.size(), "Total ZIP files differ from expected");
    int filesCount = expectations.size();
    for (int i = 0; i < filesCount; ++i) {
      ByteArrayOutputStream stream = output.streams.get(i);
      List<ZipEntryExpectation> fileExpectations = expectations.get(i);
      try (ZipInputStream in = new ZipInputStream(new ByteArrayInputStream(stream.toByteArray()))) {
        int totalFiles = 0;
        Iterator<ZipEntryExpectation> entriesIt = fileExpectations.iterator();
        ZipEntry entry;
        while ((entry = in.getNextEntry()) != null) {
          ++totalFiles;
          ZipEntryExpectation expectation = entriesIt.next();
          byte[] actualContent = in.readAllBytes();
          assertEquals(expectation.name(), entry.getName(), "ZIP entry name is incorrect");
          assertEquals(expectation.size(), entry.getCompressedSize(), "File was compressed incorrectly");
          assertEquals(new String(expectation.content()), new String(actualContent),
              "ZIP and file contents are not identical");
        }
        assertEquals(fileExpectations.size(), totalFiles, "Total files in ZIP archive differ from expected");
      }
    }
  }

  static Stream<Arguments> test() {
    return Stream.of(
        new TestCase("size-based-1000", RollingPolicyFactory.sizePolicy(1000))
            .add(1, 2).add(3, 4).add(5).args(),
        new TestCase("size-based-600", RollingPolicyFactory.sizePolicy(600))
            .add(1).add(2, 3).add(4, 5).args(),
        new TestCase("amount-based-2", RollingPolicyFactory.amountPolicy(2))
            .add(1, 2).add(3, 4).add(5).args(),
        new TestCase("size-based-2000", RollingPolicyFactory.sizePolicy(2000))
            .add(1).add(2, 4).add(5, 8).add(9, 12).add(13, 18).add(19, 22).add(23, 25).args(),
        new TestCase("amount-based-7", RollingPolicyFactory.amountPolicy(7))
            .add(1, 7).add(8, 14).add(15, 21).add(22, 25).args(),
        new TestCase("amount-based-30", RollingPolicyFactory.amountPolicy(30))
            .add(1, 25).args()
    );
  }

  private static class TestCase {

    private static final String PREFIX = "file-";
    private static final String FMT = PREFIX + "%02d";
    private static final int DEF_BUFFER = 1 << 12;

    private final String caseName;
    private final RollingPolicy policy;
    private final List<List<ZipEntryExpectation>> structure = new ArrayList<>();

    private TestCase(String caseName, RollingPolicy policy) {
      this.caseName = caseName;
      this.policy = policy;
    }

    TestCase add(int value) {
      return add(value, value);
    }

    TestCase add(int begin, int end) {
      structure.add(IntStream.rangeClosed(begin, end)
          .mapToObj(i -> String.format(FMT, i))
          .map(this::toExpectation)
          .toList()
      );
      return this;
    }

    Arguments args() {
      return arguments(
          caseName,
          inputs(),
          policy,
          structure
      );
    }

    private Iterable<File> inputs() {
      return Optional.of(getClass())
          .map(Class::getClassLoader)
          .map(cl -> cl.getResource(caseName))
          .map(url -> {
            try {
              return url.toURI();
            } catch (URISyntaxException e) {
              throw new IllegalStateException(e);
            }
          })
          .map(File::new)
          .map(dir -> dir.listFiles((__, name) -> name.startsWith(PREFIX)))
          .stream()
          .flatMap(Arrays::stream)
          .sorted(Comparator.comparing(File::getName))
          .toList();
    }

    private ZipEntryExpectation toExpectation(String fileName) {
      byte[] content = readContent(fileName);
      return new ZipEntryExpectation(fileName, calcCmpSize(content), content);
    }

    private int calcCmpSize(byte[] content) {
      Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, true);

      try {
        deflater.setInput(content);
        deflater.finish();
        return deflater.deflate(ByteBuffer.allocate(DEF_BUFFER));
      } finally {
        deflater.end();
      }
    }

    private byte[] readContent(String fileName) {
      try {
        return Optional.of(RollingZipperTest.class)
            .map(Class::getClassLoader)
            .map(cl -> cl.getResourceAsStream(caseName + "/" + fileName))
            .orElseThrow(() -> new IllegalStateException("Missing test resource"))
            .readAllBytes();
      } catch (IOException e) {
        throw new IllegalStateException("Can't read test resource", e);
      }
    }
  }

  private static record ZipEntryExpectation(String name, long size, byte[] content) {

  }

  private static class OutputsIterator implements Iterator<OutputStream> {

    private final List<ByteArrayOutputStream> streams = new ArrayList<>();

    @Override
    public boolean hasNext() {
      return true;
    }

    @Override
    public OutputStream next() {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      streams.add(output);
      return output;
    }
  }
}