package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // spring security filter가 spring filter Chain에 등록이 된다
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화 , preAuthorize, postAuthorize 어노테이션 활성화
public class
SecurityConfig extends WebSecurityConfigurerAdapter {
    //

    //password 암호화
    @Bean  // 해당 메서드의 리턴되는 object를 IoC에 등록해준다
    public BCryptPasswordEncoder encodePwd() {
        //
        return new BCryptPasswordEncoder();
    }

    // spring security 규칙
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests() //보호된 리소스 url에 접근할 수 있는 권한을 설정 (요청에 대한 권한 지정)
                // 인증만 되면 들어갈 수 있는 주소
                .antMatchers("/user/**").authenticated()
                // 특정 권한을 가지는 사용자만 접근 가능
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm") // 접근권한 없는 곳 접근하면 login페이지로 이동하도록
                .loginProcessingUrl("/login")// login주소가 호출되면 시큐리티가 낚아채서 대신 로그인해줌.
                .defaultSuccessUrl("/"); //로그인 성공시
    }
}
