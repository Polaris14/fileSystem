package com.tch.filesystem.Interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Configuration
public class WebAppConfig{

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("/");
                strings.add("/login");
                strings.add("/register");
                strings.add("/css/**");
                strings.add("/fonts/**");
                strings.add("/images/**");
                strings.add("/js/**");
                strings.add("/lib/**");
                strings.add("/webjars/**");
                registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        HttpSession session = request.getSession();
                        Object root = session.getAttribute("root");
                        if(root == null){
                            try{
                                response.sendRedirect("/");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            return false;
                        }
                        return true;
                    }
                }).addPathPatterns("/**").excludePathPatterns(strings);
            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
            }

        };
    }

}
