package board.mybatis.mvc.util.email;

import java.util.Map;

import org.springframework.web.servlet.view.InternalResourceView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 내부 자원 뷰(InternalResourceView)를 확장하여 JSP를 렌더링하는 클래스입니다.
 * 기본 InternalResourceView와의 주된 차이점은 접근 제한자가 public으로 설정되어 있어 다른 패키지에서도 사용할 수
 * 있다는 점입니다.
 */
public class PublicInternalResourceView extends InternalResourceView {

    /**
     * 주어진 URL로 PublicInternalResourceView 인스턴스를 생성합니다.
     *
     * @param url 렌더링할 JSP의 경로
     */
    public PublicInternalResourceView(String url) {
        super(url);
    }

    /**
     * 주어진 모델, 요청 및 응답을 사용하여 뷰를 렌더링합니다.
     *
     * @param model    렌더링에 사용될 모델 데이터
     * @param request  현재 HTTP 요청
     * @param response 현재 HTTP 응답
     * @throws Exception 뷰 렌더링 중 발생할 수 있는 예외
     */
    @Override
    public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        super.renderMergedOutputModel(model, request, response);
    }
}
