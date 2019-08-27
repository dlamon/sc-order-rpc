package cn.net.liaowei.sc.order.rpc.server;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableFeignClients(basePackages = "cn.net.liaowei.sc.product.client")
@EnableSwagger2Doc
//@ComponentScan(basePackages = "cn.net.liaowei.sc")
public class ScOrderRpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScOrderRpcApplication.class, args);
    }

//    @Bean
//    public ServletRegistrationBean getServlet(){
//        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
//        registrationBean.setLoadOnStartup(1);
//        registrationBean.addUrlMappings("/actuator/hystrix.stream");
//        registrationBean.setName("HystrixMetricsStreamServlet");
//        return registrationBean;
//    }

}
