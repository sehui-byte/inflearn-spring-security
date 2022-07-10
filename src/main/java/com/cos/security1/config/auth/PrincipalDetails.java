package com.cos.security1.config.auth;

// security가 /login 주소요청이 오면 낚아채서 login진행시킨다
// login 진행이 완료가 되면 security session을 만들어준다 (Security ContextHolder) : 이 key값에 session정보를 저장
// 이 세션에 들어갈 정보(object)는 Authentication type의 객체
// 이 Authentication 안에 user정보(UserDetails type 객체) 가 있어야 한다.

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Security session <- Authentication <- UserDetails(PrincipalDetails)
public class PrincipalDetails implements UserDetails {
    private User user; // compostion

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 user의 권한을 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorityCollection = new ArrayList<>();
        authorityCollection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().toString();
            }
        });
        return authorityCollection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 1년동안 회원이 로그인 안했을 경우 휴면계정으로 만든다고 하는 경우 사용할 수 있는 메서드
         return true;
    }
}
