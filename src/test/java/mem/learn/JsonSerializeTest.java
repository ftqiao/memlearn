package mem.learn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * 说明：
 */
public class JsonSerializeTest {
    /**
     * 1. 属性转换名称 username -> user_name
     * 2. 忽略字段 price
     * 3. 日期格式化 birthday
     */
    @Test
    public void testJsonProperty() throws JsonProcessingException {
        Auser auser = new Auser();
        auser.setUsername("zhangs");
        auser.setPassword("123");
        auser.setSex("1");
        auser.setBirthday(new Date());
        String result = new ObjectMapper().writeValueAsString(auser);
        System.out.println(result);
        Assert.assertTrue(result.contains("user_name"));
        Assert.assertFalse(result.contains("sex"));
    }

    /**
     * 类上面增加注解，过滤掉对应字段
     */
    @Test
    public void testJsonIgnore() throws JsonProcessingException {
        Pet pet = new Pet();
        pet.setUsername("zhangs");
        pet.setPassword("123");
        pet.setPrice(123D);
        String result = new ObjectMapper().writeValueAsString(pet);
        System.out.println(result);
        Assert.assertFalse(result.contains("username"));
        Assert.assertTrue(result.contains("price"));
    }

    /**
     *  忽略 null 值
     */
    @Test
    public void testNullIgnore() throws JsonProcessingException {
        Pen p = new Pen();
        p.setColor("red");
        String result = new ObjectMapper().writeValueAsString(p);
        System.out.println(result);
        Assert.assertTrue(result.contains("color"));
        Assert.assertFalse(result.contains("length"));
        Assert.assertFalse(result.contains("price"));
    }
    /**
     *  自定义序列化
     */
    @Test
    public void testCustomSerialize() throws JsonProcessingException {
        CustomSerialize custome = new CustomSerialize();
        custome.setUsername("cust");
        custome.setPassword("abc");
        custome.setPrice(425.56458D);
        String result = new ObjectMapper().writeValueAsString(custome);
        System.out.println(result);
    }

}


@Data
class Auser {
    @JsonProperty("user_name")
    private String username;
    private String password;

    @JsonIgnore
    private String sex;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date birthday;

}

@JsonIgnoreProperties({"username", "password"})
@Data
class Pet {
    private String username;
    private String password;
    private Double price;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
class Pen {
    private String color;
    private String length;
    private Double price;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
class CustomSerialize {
    private String username;
    private String password;
    @JsonSerialize(using= MyJsonSerializer.class)
    private Double price;
}
