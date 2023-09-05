package board.mybatis.mvc.util.redis;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import board.mybatis.mvc.util.security.CurrentMember;

@Component("customKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return CurrentMember.getCurrentUserEmail() + "_" + params[0];
    }
}