package mem.learn.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * druid 配置属性
 *
 * @author ruoyi
 */
@Configuration
public class DruidProperties {

    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;
    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;


    public DruidDataSource dataSource(DruidDataSource datasource) {
        /** 配置初始化大小、最小、最大 */
        datasource.setInitialSize(initialSize);
        datasource.setMaxActive(maxActive);
        datasource.setMinIdle(minIdle);
        /** 配置获取连接等待超时的时间 */
        datasource.setMaxWait(maxWait);
        /**
         * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
         */
        datasource.setValidationQuery(validationQuery);
        return datasource;
    }
}
