package board.mybatis.mvc.redis.service;

public interface ShortUrlService {
    String createShortUrl(String orgUrl, String email);

    String getOriginalUrl(String shortUrl);
}
