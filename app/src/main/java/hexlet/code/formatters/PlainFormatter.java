package hexlet.code.formatters;

import hexlet.code.DiffNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlainFormatter {

    public static String format(List<DiffNode> diff) {
        List<String> lines = new ArrayList<>();
        for (DiffNode node : diff) {
            String property = "Property '" + node.key() + "'";
            switch (node.status()) {
                case ADDED ->
                        lines.add(
                                property + " was added with value: " + stringify(node.newValue()));
                case REMOVED -> lines.add(property + " was removed");
                case CHANGED ->
                        lines.add(
                                property
                                        + " was updated. From "
                                        + stringify(node.oldValue())
                                        + " to "
                                        + stringify(node.newValue()));
                case UNCHANGED -> {}
                default -> throw new IllegalStateException("Unknown status: " + node.status());
            }
        }
        return String.join("\n", lines);
    }

    private static String stringify(Object value) {
        if (value instanceof Map<?, ?> || value instanceof List<?>) {
            return "[complex value]";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }
}
