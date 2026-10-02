package hexlet.code.formatters;

import hexlet.code.DiffNode;
import java.util.List;

public class StylishFormatter {

    public static String format(List<DiffNode> diff) {
        StringBuilder result = new StringBuilder("{\n");
        for (DiffNode node : diff) {
            switch (node.status()) {
                case ADDED -> appendLine(result, '+', node.key(), node.newValue());
                case REMOVED -> appendLine(result, '-', node.key(), node.oldValue());
                case UNCHANGED -> appendLine(result, ' ', node.key(), node.oldValue());
                case CHANGED -> {
                    appendLine(result, '-', node.key(), node.oldValue());
                    appendLine(result, '+', node.key(), node.newValue());
                }
                default -> throw new IllegalStateException("Unknown status: " + node.status());
            }
        }
        return result.append("}").toString();
    }

    private static void appendLine(StringBuilder result, char sign, String key, Object value) {
        result.append("  ")
                .append(sign)
                .append(' ')
                .append(key)
                .append(": ")
                .append(value)
                .append('\n');
    }
}
