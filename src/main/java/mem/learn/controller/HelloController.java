package mem.learn.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "哈罗";
    }

    /**
     * WebConfig 内不指定编码格式，使用 produces方式，页面访问仍是乱码
     *
     * @return
     */
    @RequestMapping(value = "/say", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public String say() {
        return "say哈罗";
    }
}
