package board.mybatis.mvc.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;
import board.mybatis.mvc.service.EmailService;
import board.mybatis.mvc.util.email.JSPToEmailService;
import lombok.extern.log4j.Log4j2;

/**
 * 이메일 서비스 구현 클래스.
 * 이메일 회원가입 인증, 회원탈퇴 이메일을 발송합니다.
 */
@Log4j2
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String email;

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage simpleMailMessage;
    private final JSPToEmailService jspToEmailService;

    /**
     * EmailServiceImpl 생성자.
     * 
     * @param javaMailSender    이메일 전송을 위한 JavaMailSender 인스턴스
     * @param simpleMailMessage 이메일의 내용 및 구성을 위한 SimpleMailMessage 인스턴스
     */
    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, SimpleMailMessage simpleMailMessage,
            JSPToEmailService jspToEmailService) {
        log.info("Inject JavaMailSender");
        this.javaMailSender = javaMailSender;
        this.simpleMailMessage = simpleMailMessage;
        this.jspToEmailService = jspToEmailService;

    }

    /**
     * 사용자에게 회원가입 인증 이메일을 발송합니다.
     * 인증 이메일에는 주어진 사용자의 이메일 주소를 인증하기 위한 링크가 포함됩니다.
     *
     * @param toMember 이메일을 받을 사용자의 이메일 주소입니다.
     */
    @Override
    @Transactional(readOnly = true)
    public void sendCreateMail(String toMember) {
        log.info("Is Running SendCreateMail Email ServiceImpl");
        Map<String, Object> model = new HashMap<>();
        model.put("verificationUrl", "http://localhost:8084/spring/member/verify?email=" + toMember);
        String htmlContent = jspToEmailService
                .getRenderedHTMLString("/WEB-INF/spring/email/verfiyemail.jsp", model);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(email);
            messageHelper.setTo(toMember);
            messageHelper.setSubject("회원가입 인증 이메일입니다.");
            messageHelper.setText(htmlContent, true);
        };
        javaMailSender.send(messagePreparator);
    }

    /**
     * 사용자에게 회원탈퇴 이메일을 발송합니다.
     * 
     * @param toMember 이메일을 받을 사용자의 이메일의 주소입니다.
     */
    @Override
    @Transactional(readOnly = true)
    public void sendDeleteMail(String toMember) {
        log.info("Is Running SnedDeleteMail Email ServiceImpl");
        Map<String, Object> model = new HashMap<>();
        String htmlContent = jspToEmailService
                .getRenderedHTMLString("/WEB-INF/spring/email/deleteemail.jsp", model);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(email);
            messageHelper.setTo(toMember);
            messageHelper.setSubject("회원탈퇴 이메일입니다.");
            messageHelper.setText(htmlContent, true);
        };
        javaMailSender.send(messagePreparator);
    }
}