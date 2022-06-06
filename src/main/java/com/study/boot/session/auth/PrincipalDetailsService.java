package com.study.boot.session.auth;

import com.study.boot.session.domain.member.Member;
import com.study.boot.session.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member memberEntity = memberRepository.findByUsername(username);

        if (memberEntity != null) {
            return new PrincipalDetails(memberEntity);
        }

        return null;
    }
}