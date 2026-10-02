package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.Map;

public class Parser {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};

    public static Map<String, Object> parse(String content, String dataFormat) throws Exception {
        ObjectMapper mapper =
                switch (dataFormat) {
                    case "json" -> new ObjectMapper();
                    case "yml", "yaml" -> new ObjectMapper(new YAMLFactory());
                    default -> throw new Exception("Unknown data format: '" + dataFormat + "'");
                };
        return mapper.readValue(content, MAP_TYPE);
    }
}
