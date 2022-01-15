package net.anke.books_sales_platform.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.anke.books_sales_platform.mapper.BookInfoImageMapper;
import net.anke.books_sales_platform.mapper.BookMapper;
import net.anke.books_sales_platform.mapper.CategoryMapper;
import net.anke.books_sales_platform.model.entity.Book;
import net.anke.books_sales_platform.model.entity.BookInfoImage;
import net.anke.books_sales_platform.model.entity.Category;
import net.anke.books_sales_platform.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookInfoImageMapper bookInfoImageMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public int add_book(Book book) {
        int code=bookMapper.insert(book);
        return code;
    }

    @Override
    public int add_book_info_image(List<BookInfoImage> list) {
        int code=0;
        for(BookInfoImage bookInfoImage : list){
            bookInfoImage.setCreateTime(new Date());
            code=bookInfoImageMapper.insert(bookInfoImage);
            if(code<=0)
            {
                break;
            }
        }
        return code;

    }

    @Override
    public IPage<Book> get_page_data(int cur, int limit) {
        IPage<Book> page=new Page<Book>(cur,limit);
        IPage<Book> Ipage=bookMapper.selectPage(page,new QueryWrapper<>());
        return Ipage;
    }

    @Override
    public int delete_book_by_id(int id) {
        int code=bookMapper.delete(new QueryWrapper<Book>().eq("id",id));
        int code2=bookInfoImageMapper.delete(new QueryWrapper<BookInfoImage>().eq("book_id",id));
        return code>=1&&code2>=0?1:0;
    }

    @Override
    public Book get_book_by_id(int id) {
        Book book=bookMapper.selectById(id);
        return book;
    }

    @Override
    public List<BookInfoImage> get_book_info_image_by_id(int id) {
        List<BookInfoImage> list=bookInfoImageMapper.selectList(new QueryWrapper<BookInfoImage>().eq("book_id",id));
        return list;
    }

    @Override
    public int update_all_data_by_id(Book book) {
        book.setUpdateTime(new Date());
        int code1=bookMapper.update(book,new QueryWrapper<Book>().eq("id",book.getId()));
        if(code1<=0)
        {
            return 0;
        }
        int code2=bookInfoImageMapper.delete(new QueryWrapper<BookInfoImage>().eq("book_id",book.getId()));
        if(code2<0)
            return 0;
        for(BookInfoImage bookInfoImage : book.getFileList())
        {
            bookInfoImageMapper.insert(bookInfoImage);
        }
        return 1;
    }

    @Override
    public int add_category(String name) {
        Category category=new Category();
        category.setName(name);
        int code=categoryMapper.insert(category);
        return code;
    }

    @Override
    public List<Category> get_category_list() {
        List<Category> list=categoryMapper.selectList(new QueryWrapper<Category>());
        return list;
    }

    @Override
    public IPage<Book> get_search_page_data(int option, String search, int cur, int limit) {
        IPage<Book> page=new Page<>(cur,limit);
        switch (option){
            case 1:return bookMapper.selectPage(page,new QueryWrapper<Book>().like("name","%"+search+"%"));
            case 2:return bookMapper.selectPage(page,new QueryWrapper<Book>().like("book_num","%"+search+"%"));
            default: break;
        }
        return null;
    }

    @Override
    public int update_book_count(int book_id, int count) {
        int code=bookMapper.update_book_count(book_id,count);
        return code;
    }
}
