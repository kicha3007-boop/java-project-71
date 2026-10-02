package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Differ {

    public static final String DEFAULT_FORMAT = "stylish";

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, DEFAULT_FORMAT);
    }

    public static String generate(String filePath1, String filePath2, String formatName)
            throws Exception {
        Map<String, Object> data1 = readData(filePath1);
        Map<String, Object> data2 = readData(filePath2);
        List<DiffNode> diff = DiffBuilder.build(data1, data2);
        return Formatter.format(diff, formatName);
    }

    private static Map<String, Object> readData(String filePath) throws Exception {
        Path path = Path.of(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new Exception("File '" + path + "' does not exist");
        }
        String content = Files.readString(path);
        return Parser.parse(content, getDataFormat(filePath));
    }

    private static String getDataFormat(String filePath) {
        int index = filePath.lastIndexOf('.');
        return index > 0 ? filePath.substring(index + 1).toLowerCase() : "";
    }
}
