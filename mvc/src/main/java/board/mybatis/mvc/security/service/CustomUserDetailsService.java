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

/**
 * {@code CustomUserDetailsService}는 사용자의 인증 정보를 처리하는 서비스 클래스입니다.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 사용자명을 기반으로 UserDetails 정보를 로드하여 반환합니다.
     *
     * @param username 사용자명
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 사용자명을 찾을 수 없을 때 발생하는 예외
     */
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