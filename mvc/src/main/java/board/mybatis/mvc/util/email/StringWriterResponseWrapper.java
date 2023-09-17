package board.mybatis.mvc.util.email;

import java.io.PrintWriter;
import java.io.StringWriter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class StringWriterResponseWrapper extends HttpServletResponseWrapper {

    private final StringWriter stringWriter;
    private final PrintWriter writer;

    public StringWriterResponseWrapper(StringWriter stringWriter, HttpServletResponse response) {
        super(response);
        this.stringWriter = stringWriter;
        this.writer = new PrintWriter(stringWriter);
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    public String getResult() {
        return stringWriter.toString();
    }
}
