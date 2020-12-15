package mem.learn;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.Converter;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 说明：
 */
public class MyJsonSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null)
            gen.writeString(BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP).toString()); // ROUND_HALF_UP四舍五入
    }
}
