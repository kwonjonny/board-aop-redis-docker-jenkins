package board.mybatis.mvc.util.email;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import board.mybatis.mvc.exception.JSPNotFoundException;

/**
 * JSP 페이지를 HTML 문자열로 변환하여 이메일의 내용으로 사용하는 서비스 클래스입니다.
 * 이 서비스를 통해 JSP 템플릿을 문자열로 렌더링하여 이메일로 사용할 수 있습니다.
 * 
 */
@Log4j2
@Component
public class JSPToEmailService {

    private final WebApplicationContext webApplicationContext;

    /**
     * 주어진 WebApplicationContext로 JSPToEmailService를 초기화하는 생성자입니다.
     *
     * @param webApplicationContext JSP 뷰를 렌더링하는 데 사용되는 WebApplicationContext
     */
    @Autowired
    public JSPToEmailService(WebApplicationContext webApplicationContext) {
        log.info("Inject WebApplicationContext");
        this.webApplicationContext = webApplicationContext;
    }

    /**
     * 주어진 JSP 경로와 모델을 사용하여 HTML 문자열을 렌더링합니다.
     * 
     * @param jspPath 렌더링할 JSP의 경로
     * @param model   JSP에 전달될 데이터
     * @return 렌더링된 HTML 문자열
     */
    public String getRenderedHTMLString(String jspPath, Map<String, Object> model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getResponse();
        ServletContext servletContext = webApplicationContext.getServletContext();

        PublicInternalResourceView view = new PublicInternalResourceView(jspPath);
        view.setApplicationContext(webApplicationContext);

        StringWriter stringWriter = new StringWriter();
        try {
            view.renderMergedOutputModel(model, request, new StringWriterResponseWrapper(stringWriter, response));
        } catch (Exception e) {
            throw new JSPNotFoundException("JSP페이지가 없습니다.", e);
        }
        return stringWriter.toString();
    }
}
