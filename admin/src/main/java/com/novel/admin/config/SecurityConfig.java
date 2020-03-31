/*
 * 作者：刘时明
 * 时间：2020/3/5-11:15
 * 作用：
 */
package com.novel.admin.config;

import com.novel.admin.security.bean.JWTAccessDeniedHandler;
import com.novel.admin.security.bean.JWTAuthenticationEntryPoint;
import com.novel.admin.security.filter.JWTAuthenticationFilter;
import com.novel.admin.security.filter.JWTAuthorizationFilter;
import com.novel.admin.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * 密码编码器
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService createUserDetailsService()
    {
        return userDetailsServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        // 设置自定义的userDetailsService以及密码编码器
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // 出现乱码，解决方案
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.cors().and()
                // 禁用 CSRF
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                // 指定路径下的资源需要验证了的用户才能访问
                .antMatchers("/admin/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
                // 其他都放行了
                .anyRequest().permitAll()
                .and()
                //添加自定义Filter
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session（不创建会话）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 授权异常处理
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                .accessDeniedHandler(new JWTAccessDeniedHandler());
        // 防止H2 web 页面的Frame 被拦截
        http.headers().frameOptions().disable();
    }
}
