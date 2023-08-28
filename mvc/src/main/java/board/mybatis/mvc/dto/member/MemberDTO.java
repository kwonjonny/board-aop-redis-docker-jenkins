package board.mybatis.mvc.dto.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;

// Spring Security Member DTO Class
@Getter
@Setter
public class MemberDTO extends User implements OAuth2User {

    private String email;
    private String memberName;
    private String memberPw;
    private List<String> roleNames = new ArrayList<>();

    public MemberDTO(String email, String memberPw, String memberName, List<String> roleNames) {
        super(email, memberPw,
                roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList()));
        this.memberName = memberName;
        this.email = email;
        this.memberPw = memberPw;
        this.roleNames = roleNames;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return this.email;
    }
}
