package board.mybatis.mvc.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberDTO;
import board.mybatis.mvc.mappers.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// Custom User Details Servier Class
@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // 의존성 주입
    private final MemberMapper memberMapper;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /* 리턴타입이 UserDetails 이므로 Mapper를 통해 MemberDTO로 반환해야한다. */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Is Running LoadUserByUserName");
        log.info(username);

        MemberConvertDTO readDTO = memberMapper.selectOne(username);
        log.info("readDTO", readDTO);
        log.info(readDTO);

        MemberDTO memberDTO = new MemberDTO(username,
                readDTO.getMemberPw(),
                readDTO.getMemberName(),
                readDTO.getRolenames());
        return memberDTO;
    }
}