package board.mybatis.mvc.util.email;

import org.springframework.web.servlet.view.InternalResourceView;

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

@Log4j2
@Component
public class JSPToEmailService {

    private final WebApplicationContext webApplicationContext;

    @Autowired
    public JSPToEmailService(WebApplicationContext webApplicationContext) {
        log.info("Inject WebApplicationContext");
        this.webApplicationContext = webApplicationContext;
    }

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
            throw new RuntimeException("Failed to render JSP", e);
        }

        return stringWriter.toString();
    }
}
