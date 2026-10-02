package hexlet.code;

import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import java.util.List;

public class Formatter {

    public static String format(List<DiffNode> diff, String formatName) throws Exception {
        return switch (formatName) {
            case "stylish" -> StylishFormatter.format(diff);
            case "plain" -> PlainFormatter.format(diff);
            case "json" -> JsonFormatter.format(diff);
            default -> throw new Exception("Unknown output format: '" + formatName + "'");
        };
    }
}
