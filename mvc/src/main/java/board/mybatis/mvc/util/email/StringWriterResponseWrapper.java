package board.mybatis.mvc.util.email;

import java.io.PrintWriter;
import java.io.StringWriter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

/**
 * StringWriter로 응답을 캡처하기 위한 HttpServletResponseWrapper 확장 클래스입니다.
 * 이 클래스를 사용하면 JSP 렌더링 결과를 문자열로 캡처할 수 있습니다.
 */
public class StringWriterResponseWrapper extends HttpServletResponseWrapper {

    private final StringWriter stringWriter;
    private final PrintWriter writer;

    /**
     * 주어진 StringWriter와 HttpServletResponse로 StringWriterResponseWrapper 인스턴스를
     * 생성합니다.
     *
     * @param stringWriter JSP 렌더링 결과를 저장할 StringWriter
     * @param response     원래의 HttpServletResponse
     */
    public StringWriterResponseWrapper(StringWriter stringWriter, HttpServletResponse response) {
        super(response);
        this.stringWriter = stringWriter;
        this.writer = new PrintWriter(stringWriter);
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    /**
     * StringWriter에서 렌더링된 결과를 문자열로 반환합니다.
     *
     * @return 렌더링된 HTML 문자열
     */
    public String getResult() {
        return stringWriter.toString();
    }
}
