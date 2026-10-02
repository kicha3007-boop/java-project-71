package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffNode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter {

    public static String format(List<DiffNode> diff) throws Exception {
        List<Map<String, Object>> items = diff.stream().map(JsonFormatter::toMap).toList();
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(items);
    }

    private static Map<String, Object> toMap(DiffNode node) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("key", node.key());
        item.put("status", node.status().name().toLowerCase());
        switch (node.status()) {
            case ADDED -> item.put("value", node.newValue());
            case REMOVED, UNCHANGED -> item.put("value", node.oldValue());
            case CHANGED -> {
                item.put("oldValue", node.oldValue());
                item.put("newValue", node.newValue());
            }
            default -> throw new IllegalStateException("Unknown status: " + node.status());
        }
        return item;
    }
}
