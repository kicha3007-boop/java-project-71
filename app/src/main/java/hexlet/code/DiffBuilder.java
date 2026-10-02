package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/** Строит внутреннее представление дифа: по узлу на каждый ключ, ключи по алфавиту. */
public class DiffBuilder {

    public static List<DiffNode> build(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> keys = new TreeSet<>(data1.keySet());
        keys.addAll(data2.keySet());

        List<DiffNode> diff = new ArrayList<>();
        for (String key : keys) {
            Object value1 = data1.get(key);
            Object value2 = data2.get(key);
            if (!data1.containsKey(key)) {
                diff.add(new DiffNode(key, DiffNode.Status.ADDED, null, value2));
            } else if (!data2.containsKey(key)) {
                diff.add(new DiffNode(key, DiffNode.Status.REMOVED, value1, null));
            } else if (Objects.equals(value1, value2)) {
                diff.add(new DiffNode(key, DiffNode.Status.UNCHANGED, value1, value2));
            } else {
                diff.add(new DiffNode(key, DiffNode.Status.CHANGED, value1, value2));
            }
        }
        return diff;
    }
}
