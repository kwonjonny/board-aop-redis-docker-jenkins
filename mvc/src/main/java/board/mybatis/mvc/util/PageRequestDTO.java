package board.mybatis.mvc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    // 검색 조건
    private String searchType; // 검색 타입
    private String keyword; // 검색 키워드
    private String link; // 검색 조건 , 페이지, 사이즈 통합
    private boolean replyLast;

    private LocalDate startDate;
    private LocalDate endDate;

    // if page 요청이 0 보다 작으면 강제 1 page
    public void setPage(int page) {
        if (page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }
    }

    // if size 요청이 0보다 작고 100 보다 크면 강제 10
    public void setSize(int size) {
        if (size <= 0 || size > 100) {
            this.size = 10;
        } else {
            this.size = size;
        }
    }

    public int getSkip() {
        return (this.page - 1) * this.size;
    }

    public int getEnd() {
        return this.page * this.size;
    }

    // 한번에 가져오는 list 101
    public int getCountEnd() {
        int temp = (int) (Math.ceil(this.page / 10.0) * (10 * size));
        return temp + 1;
    }

    // type 배열로 반환 처리
    // T C W 로 들어오는 검색 조건을 Split 해서 My Batis 검색
    public String[] getTypes() {
        if (this.searchType == null || this.searchType.isEmpty()) {
            return null;
        }
        return this.searchType.split("");
    }

    // link
    public String getLink() {

        if (link == null) {
            // 문자열 합치기
            StringBuilder strBuilder = new StringBuilder();

            // 페이지,사이즈 append
            strBuilder.append("page=" + this.page);
            strBuilder.append("&size=" + this.size);

            // 검색타입
            if (searchType != null && searchType.length() > 0) {
                strBuilder.append("&type=" + this.searchType);
            }
            // 검색어
            if (keyword != null) {
                try {
                    strBuilder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            // startDate
            if (startDate != null) {
                strBuilder.append("&startDate=" + startDate.toString()); // LocalDate의 경우 ISO 8601 날짜 형식으로 변환됩니다.
            }

            // endDate
            if (endDate != null) {
                strBuilder.append("&endDate=" + endDate.toString());
            }

            // toString으로 String전달
            link = strBuilder.toString();
        }
        return link;
    }
}
