package edu.cdtu.hele_backend.config;

import edu.cdtu.hele_backend.entity.RestBean;
import edu.cdtu.hele_backend.utils.Const;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

public class SecurityConfiguration {
    /**
     * 针对于 SpringSecurity 6 的新版配置方法
     *
     * @param http 配置器
     * @return 自动构建的内置过滤器链
     * @throws Exception 可能的异常
     */
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers("/api/v1/**", "/error").permitAll()
                        .anyRequest().hasAnyRole(Const.ROLE_DEFAULT)
                )
                .formLogin(conf -> conf
                        .loginProcessingUrl("/api/v1/login")
                        .failureHandler(this::onAuthenticationFailure)
                        .successHandler(this::onAuthenticationSuccess)
                )
                .logout(conf -> conf
                        .logoutUrl("/api/v1/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

    // 登录失败
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(RestBean.failure(401, exception.getMessage()) .asJsonString());
    }

    // 登录成功
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(RestBean.success().asJsonString());
    }

    // 退出登录
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("退出登录");
    }
}
