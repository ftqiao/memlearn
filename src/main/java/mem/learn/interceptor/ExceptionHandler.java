package mem.learn.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ExceptionHandler.class.getName());

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String Url = httpServletRequest.getRequestURL().toString();
        try {
            String requestContent = org.apache.commons.io.IOUtils.toString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
            logger.error("Url:" + Url + ",RequestContent:" + requestContent, e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ModelAndView mv = new ModelAndView();
        /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        InnerResponse commonType = new InnerResponse();
        commonType.IsSuccess = false;
        commonType.ResponseCode = "70110020";
        commonType.ResponseMessage = e.getMessage();
        commonType.WarningMessage = "";
        attributes.put("Status", JSON.toJSON(commonType));
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }

    class InnerResponse {
        public String WarningMessage;
        public String ResponseMessage;
        public boolean IsSuccess;
        public String ResponseCode;
    }
}
