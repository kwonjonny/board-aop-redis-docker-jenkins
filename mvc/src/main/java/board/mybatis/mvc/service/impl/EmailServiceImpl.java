package board.mybatis.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;
import board.mybatis.mvc.service.EmailService;
import lombok.extern.log4j.Log4j2;

/**
 * 이메일 서비스 구현 클래스.
 * 이메일 회원가입 인증, 회원탈퇴 이메일을 발송합니다.
 */
@Log4j2
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage simpleMailMessage;

    /**
     * EmailServiceImpl 생성자.
     * 
     * @param javaMailSender    이메일 전송을 위한 JavaMailSender 인스턴스
     * @param simpleMailMessage 이메일의 내용 및 구성을 위한 SimpleMailMessage 인스턴스
     */
    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, SimpleMailMessage simpleMailMessage) {
        log.info("Inject JavaMailSender");
        this.javaMailSender = javaMailSender;
        this.simpleMailMessage = simpleMailMessage;
    }

    /**
     * 주어진 이메일 주소로 회원가입 인증 이메일을 전송합니다.
     * 이 메서드는 주어진 이메일 주소에 인증 URL을 포함한 이메일을 전송합니다.
     * 사용자가 이 URL을 클릭하면 회원가입 인증이 완료됩니다.
     * 
     * @param toMember 회원가입 인증 이메일을 받을 사용자의 이메일 주소
     */
    @Override
    @Transactional(readOnly = true)
    public void sendCreateMail(String toMember) {
        log.info("Is Running SendCreateMail Email ServiceImpl");
        String verificationUrl = "http://localhost:8084/spring/member/verify?email=" + toMember;
        simpleMailMessage.setTo(toMember);
        simpleMailMessage.setSubject("회원가입 인증 이메일입니다.");
        simpleMailMessage.setText("클릭 후 회원가입 인증을 완료하세요: " + verificationUrl);
        javaMailSender.send(simpleMailMessage);
    }

}
