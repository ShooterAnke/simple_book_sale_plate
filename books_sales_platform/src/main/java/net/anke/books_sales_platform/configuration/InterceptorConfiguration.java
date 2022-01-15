
package net.anke.books_sales_platform.configuration;

import net.anke.books_sales_platform.Interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer{

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/api/user/pri/*")
                .addPathPatterns("/api/book/pri/*")
                .addPathPatterns("/api/cart/pri/*")
                .addPathPatterns("/api/order/pri/*");

    }

    //映射  当访问/img/下时相当于访问后面所设置的本地路径中的文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("file:" + UPLOAD_FOLDER);
    }
}
