package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.model.vo.Role;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // view를 리턴하겠다
public class IndexController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"", "/"})
    public String index() {
        //
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        //
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        //
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        //
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        //
        return "loginForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        //
        System.out.println(user);
        user.setRole(Role.ROLE_USER);

        //pw가 암호화되어야 security로 로그인 가능
        String rawPassword = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(rawPassword));
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinProc() {
        //
        return "joinForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        //
        return "개인정보";
    }

    // 이 data() 메서드가 실행되기 전에 PreAuthorize가 실행된다
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    // PostAuthorize()도 있음.
    @GetMapping("/data")
    public @ResponseBody String data() {
        //
        return "데이터정보";
    }

}
