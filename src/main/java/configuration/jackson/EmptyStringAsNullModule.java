package configuration.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class EmptyStringAsNullModule extends SimpleModule {

    public EmptyStringAsNullModule() {
        this.addDeserializer(String.class, new StdDeserializer<>(String.class) {
            @Override
            public String deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
                String result = StringDeserializer.instance.deserialize(parser, ctx);
                if (StringUtils.isEmpty(result)) {
                    return null;
                }
                return result;
            }
        });
    }

}
