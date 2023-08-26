package board.mybatis.mvc.aop;

import java.util.Enumeration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

// Service Layer 의 모든 로직 점검을 위한 Aop Logging Class
@Aspect
@Component
public class LoggingAOP {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 포인트컷 설정 - board.file.service 패키지의 모든 메소드를 대상으로 함.
    @Pointcut("execution(* board.file.service..*.*(..))")
    public void applicationPackagePointcut() {
    }

    // 포인트컷 메소드가 실행되기 전(Before)에 로깅을 하도록 설정함.
    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        // Request 정보를 가져오기 위해 사용.
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String logMessage = buildLogMessage(request); // 로그 메시지 생성
        String argumentList = buildArgumentList(joinPoint); // 메소드 인자값 목록 생성

        // 로그를 INFO 레벨로 출력.
        log.info(
                "Enter: {}.{}() with argument[s] = {}\n from URL: {}, Details: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                argumentList,
                request.getRequestURI(),
                logMessage);
    }

    // 포인트컷 메소드가 정상적으로 종료된 후(AfterReturning)에 로깅을 하도록 설정함.
    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        // 메소드의 반환값을 로그로 출력.
        log.info(
                "Exit: {}.{}() with result = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    // Helper method to build the log message
    private String buildLogMessage(HttpServletRequest request) {
        // 각 요청 정보를 가져옴
        String remoteIP = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String headers = getHeadersAsString(request);
        String parameters = request.getQueryString() != null ? request.getQueryString() : "No parameters";

        // 포맷에 맞게 로그 메시지 생성.
        return String.format(
                "\n Request Details:\n Remote IP: %s\n Headers: %s\n Method: %s\n URI: %s\n Parameters: %s",
                remoteIP, headers, method, uri, parameters);
    }

    // Helper method to get headers as a string
    private String getHeadersAsString(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();

        // 모든 헤더 정보를 가져와 문자열로 변환.
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }

        return headers.toString();
    }

    // Helper method to build the argument list
    private String buildArgumentList(JoinPoint joinPoint) {
        StringBuilder argumentList = new StringBuilder();
        Object[] args = joinPoint.getArgs();

        // 메소드의 모든 인자값을 가져와 문자열로 변환.
        for (Object arg : args) {
            argumentList.append(arg.toString()).append("\n");
        }

        return argumentList.toString();
    }
}