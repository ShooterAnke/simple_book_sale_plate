package net.anke.books_sales_platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.anke.books_sales_platform.model.entity.Book;
import net.anke.books_sales_platform.model.entity.BookInfoImage;
import net.anke.books_sales_platform.model.entity.Category;

import java.util.List;

public interface BookService {
    public int add_book(Book book);
    public int add_book_info_image(List<BookInfoImage> list);
    public IPage<Book> get_page_data(int cur, int limit);
    public int delete_book_by_id(int id);
    public Book get_book_by_id(int id);
    public List<BookInfoImage> get_book_info_image_by_id(int id);
    public int update_all_data_by_id(Book book);
    public int add_category(String name);
    public List<Category> get_category_list();
    public IPage<Book> get_search_page_data(int option,String search,int cur,int limit);
    public int update_book_count(int book_id,int count);
}
