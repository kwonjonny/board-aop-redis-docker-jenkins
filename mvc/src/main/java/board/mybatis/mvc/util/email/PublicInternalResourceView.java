package board.mybatis.mvc.util.email;

import java.util.Map;

import org.springframework.web.servlet.view.InternalResourceView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PublicInternalResourceView extends InternalResourceView {
    public PublicInternalResourceView(String url) {
        super(url);
    }

    @Override
    public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        super.renderMergedOutputModel(model, request, response);
    }
}
