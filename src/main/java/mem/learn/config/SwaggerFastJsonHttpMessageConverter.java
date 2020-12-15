package mem.learn.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import springfox.documentation.swagger.web.SwaggerResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by liuyb on 2017/4/11.
 */
public class SwaggerFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void writeInternal(Object obj, //
                                 HttpOutputMessage outputMessage //
    ) throws IOException, HttpMessageNotWritableException {
        if (obj.getClass().getTypeName().contains("mem.learn")) {
            super.writeInternal(obj, outputMessage);
        } else if (obj.getClass().getTypeName().equals("java.lang.String")) {
            HttpHeaders headers = outputMessage.getHeaders();
            ByteArrayOutputStream outnew = new ByteArrayOutputStream();
            outnew.write(Strings.toByteArray((String) obj));
            headers.setContentLength(outnew.size());
            OutputStream out = outputMessage.getBody();
            outnew.writeTo(out);
            outnew.close();
        } else {
            HttpHeaders headers = outputMessage.getHeaders();
            ByteArrayOutputStream outnew = new ByteArrayOutputStream();
            mapper.writeValue(outnew, obj);
            outnew.flush();
            headers.setContentLength(outnew.size());
            OutputStream out = outputMessage.getBody();
            outnew.writeTo(out);
            outnew.close();
        }
    }

    private boolean isSwaggerRelated(Object obj) {
        if (obj instanceof ArrayList) {
            if (((ArrayList) obj).get(0) instanceof SwaggerResource) {
                return true;
            }
        }
        return false;
    }

}
