package board.mybatis.mvc.security.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberDTO;
import board.mybatis.mvc.mappers.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * {@code CustomOAuth2UserService}는 카카오 로그인 사용자 정보를 처리하는 서비스 클래스입니다.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * OAuth2User 정보를 로드하여 처리합니다.
     *
     * @param userRequest OAuth2UserRequest 객체
     * @return OAuth2User 객체
     * @throws OAuth2AuthenticationException OAuth2 인증 예외
     */
    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Is Running Load By User Request");
        log.info(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = null;

        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(paramMap);
                break;
        }
        log.info("Email: " + email);

        // DB에 해당 사용자가있으면
        MemberConvertDTO memberReadDTO = memberMapper.selectOne(email);
        if (memberReadDTO == null) {

            MemberCreateDTO socialMember = MemberCreateDTO.builder()
                    .email(email)
                    .memberName("카카오 사용자")
                    .memberPw(passwordEncoder.encode("1111"))
                    .memberPhone("010-1111-1111")
                    .build();
            String memberRole = "USER";
            memberMapper.createJoinMemberRole(email, memberRole);
            memberMapper.joinMember(socialMember);
            // PW를 사용하지않는 자원 이미 카카오에서 인증이되었기 때문에 비워둔다.
            MemberDTO memberDTO = new MemberDTO(email, "", "카카오사용자", List.of("ADMIN"));
            return memberDTO;
        } else {
            MemberDTO memberDTO = new MemberDTO(
                    memberReadDTO.getEmail(),
                    memberReadDTO.getMemberPw(),
                    memberReadDTO.getMemberName(),
                    memberReadDTO.getRolenames());
            return memberDTO;
        }
    }

    private String getKakaoEmail(final Map<String, Object> paramMap) {
        Object value = paramMap.get("kakao_account");
        log.info("value: " + value);
        LinkedHashMap accountMap = (LinkedHashMap) value;
        String email = (String) accountMap.get("email");
        log.info("email: " + email);
        return email;
    }
}