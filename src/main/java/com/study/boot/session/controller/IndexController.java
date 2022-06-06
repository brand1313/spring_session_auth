package com.study.boot.session.controller;

import com.study.boot.session.auth.PrincipalDetails;
import com.study.boot.session.domain.member.Member;
import com.study.boot.session.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class IndexController {

    private final HttpSession httpSession;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"", "/"})
    public String hello() {
        return "hello";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "login 하세요";
    }

    @PostMapping("/join")
    public void join(@RequestBody Member member, HttpServletResponse response) throws IOException {

        String rawPassword = member.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        member.setRole("ROLE_USER");
        member.setPassword(encPassword);

        memberRepository.save(member);

        response.sendRedirect("/loginForm");
    }

    @GetMapping("/userInfo")
    public String userInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletResponse response) throws IOException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getName();

        return principalDetails.getUsername();
    }

}
