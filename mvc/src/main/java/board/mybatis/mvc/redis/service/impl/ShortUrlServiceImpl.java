package board.mybatis.mvc.redis.service.impl;

import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.redis.dto.ShortUrlResponseDTO;
import board.mybatis.mvc.redis.mappers.ShortUrlMapper;
import board.mybatis.mvc.redis.service.ShortUrlService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlMapper shortUrlMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public ShortUrlServiceImpl(ShortUrlMapper shortUrlMapper, RedisTemplate<String, String> redisTemplate) {
        log.info("Inject ShortUrlMapper");
        this.shortUrlMapper = shortUrlMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional
    public String createShortUrl(String orgUrl, String email) {
        log.info("Is Running Redis Create Short Url ServiceImpl");
        // 먼저 원래 URL에 대한 단축 URL이 있는지 Redis에서 확인
        String cachedShortUrl = redisTemplate.opsForValue().get(orgUrl);
        if (cachedShortUrl != null) {
            // 이미 단축된 URL이 있으면 그 URL을 반환
            return cachedShortUrl;
        }
        // Redis에 캐시되어 있지 않으면 데이터베이스에서 확인
        ShortUrlResponseDTO existingDto = shortUrlMapper.findByOrgUrl(orgUrl);
        if (existingDto != null) {
            // 이미 단축된 URL이 있으면 그 URL을 반환
            return existingDto.getShortUrl();
        }
        // 단축된 URL이 없는 경우, 새로운 단축 URL 생성
        String shortUrl = "shortened_" + orgUrl.hashCode();
        ShortUrlResponseDTO dto = ShortUrlResponseDTO.builder()
                .orgUrl(orgUrl)
                .shortUrl(shortUrl)
                .email(email)
                .build();
        shortUrlMapper.save(dto);
        // 원래 URL과 단축 URL을 Redis에 캐싱
        redisTemplate.opsForValue().set(orgUrl, shortUrl);
        redisTemplate.opsForValue().set(shortUrl, orgUrl); // 단축 URL로 원래 URL도 조회 가능하게 함
        return shortUrl;
    }

    @Override
    @Transactional
    public String getOriginalUrl(String shortUrl) {
        // 먼저 인메모리 (Redis)에서 원래 URL 조회
        String originalUrl = redisTemplate.opsForValue().get(shortUrl);
        // 인메모리에서 원래 URL을 찾지 못한 경우
        if (originalUrl == null) {
            ShortUrlResponseDTO dto = shortUrlMapper.findByOrgUrl(shortUrl);
            if (dto == null) {
                throw new DataNotFoundException("해당하는 url 이 없습니다.");
            }
            originalUrl = dto.getOrgUrl();
            // 원래 URL을 다시 인메모리에 저장 (캐싱)
            redisTemplate.opsForValue().set(shortUrl, originalUrl);
        }
        return originalUrl;
    }
}
