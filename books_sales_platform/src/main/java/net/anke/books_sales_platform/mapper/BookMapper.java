package net.anke.books_sales_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.anke.books_sales_platform.model.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    public int update_book_count(@Param("book_id")int book_id,@Param("count")int count);
    public Book select_book_for_order(@Param("book_id")int book_id);
}
