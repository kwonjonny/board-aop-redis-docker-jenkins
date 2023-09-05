package board.mybatis.mvc.redis.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import board.mybatis.mvc.redis.dto.ShortUrlResponseDTO;

@Mapper
public interface ShortUrlMapper {
    @Select("SELECT * FROM tbl_short_url WHERE orgUrl = #{orgUrl}")
    ShortUrlResponseDTO findByOrgUrl(@Param("orgUrl") String orgUrl);

    @Insert("INSERT INTO short_url_table(orgUrl, shortUrl, email) VALUES(#{orgUrl}, #{shortUrl}, #{email})")
    void save(ShortUrlResponseDTO shortUrlResponseDTO);

    @Update("UPDATE short_url_table SET shortUrl = #{shortUrl}, email = #{email} WHERE orgUrl = #{orgUrl}")
    void update(ShortUrlResponseDTO shortUrlResponseDTO);

    @Delete("DELETE FROM tbl_short_url WHERE orgUrl = #{orgUrl}")
    void delete(@Param("orgUrl") String orgUrl);
}