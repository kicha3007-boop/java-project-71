package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DifferTest {

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName).toAbsolutePath();
    }

    private static String readFixture(String fileName) throws Exception {
        return Files.readString(getFixturePath(fileName)).trim();
    }

    private static String fixture(String fileName) {
        return getFixturePath(fileName).toString();
    }

    @ParameterizedTest
    @ValueSource(strings = {"json", "yml"})
    void testDefaultFormat(String extension) throws Exception {
        String actual =
                Differ.generate(fixture("file1." + extension), fixture("file2." + extension));
        assertEquals(readFixture("expected_stylish.txt"), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"json", "yml"})
    void testStylish(String extension) throws Exception {
        String actual =
                Differ.generate(
                        fixture("file1." + extension), fixture("file2." + extension), "stylish");
        assertEquals(readFixture("expected_stylish.txt"), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"json", "yml"})
    void testPlain(String extension) throws Exception {
        String actual =
                Differ.generate(
                        fixture("file1." + extension), fixture("file2." + extension), "plain");
        assertEquals(readFixture("expected_plain.txt"), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"json", "yml"})
    void testJson(String extension) throws Exception {
        String actual =
                Differ.generate(
                        fixture("file1." + extension), fixture("file2." + extension), "json");
        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(readFixture("expected_json.json")), mapper.readTree(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {"json", "yaml"})
    void testFlatFiles(String extension) throws Exception {
        String actual =
                Differ.generate(fixture("flat1." + extension), fixture("flat2." + extension));
        assertEquals(readFixture("expected_flat.txt"), actual);
    }

    @Test
    void testRelativePaths() throws Exception {
        String actual =
                Differ.generate(
                        "src/test/resources/fixtures/flat1.json",
                        "src/test/resources/fixtures/flat2.json");
        assertEquals(readFixture("expected_flat.txt"), actual);
    }

    @Test
    void testMixedDataFormats() throws Exception {
        String actual = Differ.generate(fixture("file1.json"), fixture("file2.yml"));
        assertEquals(readFixture("expected_stylish.txt"), actual);
    }

    @Test
    void testMissingFile() {
        assertThrows(
                Exception.class,
                () -> Differ.generate(fixture("absent.json"), fixture("file2.json")));
    }

    @Test
    void testUnknownDataFormat() {
        assertThrows(
                Exception.class,
                () -> Differ.generate(fixture("unknown.txt"), fixture("file2.json")));
    }

    @Test
    void testUnknownOutputFormat() {
        assertThrows(
                Exception.class,
                () -> Differ.generate(fixture("file1.json"), fixture("file2.json"), "xml"));
    }
}
