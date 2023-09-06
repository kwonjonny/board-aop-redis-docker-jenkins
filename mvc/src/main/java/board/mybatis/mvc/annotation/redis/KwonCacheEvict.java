package board.mybatis.mvc.annotation.redis;
            
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KwonCacheEvict {
    String value();
    String key() default "";
}
