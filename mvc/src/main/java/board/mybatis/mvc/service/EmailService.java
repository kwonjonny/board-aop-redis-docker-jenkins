package board.mybatis.mvc.service;

/*
 * 이메일 관련 인터페이스입니다.
 */
public interface EmailService {
    /**
     * 주어진 이메일 주소로 회원가입 인증 이메일을 전송합니다.
     * 
     * @param toMember 이메일을 받을 주소
     */
    void sendCreateMail(String toMember);

    /**
     * 주어진 이메일 주소로 회원 탈퇴 이메일을 전송합니다.
     * 
     * @param toMember 이메일을 받을 주소 
     */
    void sendDeleteMail(String toMember);
}
