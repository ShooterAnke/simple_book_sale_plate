package net.anke.books_sales_platform.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.anke.books_sales_platform.mapper.BookMapper;
import net.anke.books_sales_platform.mapper.CartMapper;
import net.anke.books_sales_platform.mapper.OrderBookMapper;
import net.anke.books_sales_platform.mapper.OrderMapper;
import net.anke.books_sales_platform.model.entity.Book;
import net.anke.books_sales_platform.model.entity.Cart;
import net.anke.books_sales_platform.model.entity.Order;
import net.anke.books_sales_platform.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderBookMapper orderBookMapper;
    @Override
    public List<Cart> get_cart_by_userId(int userId) {
        List<Cart> list=cartMapper.selectList(new QueryWrapper<Cart>().eq("user_id",userId));
        for(Cart cart:list){
            cart.setBook(bookMapper.selectById(cart.getBookId()));
        }
        return list;
    }

    @Override
    public int add_cart(Cart cart) {
        int code= cartMapper.insert(cart);
        return code;
    }

    @Override
    public Cart find_cart_by_book_id(int bookId) {
        Cart cart=cartMapper.selectOne(new QueryWrapper<Cart>().eq("book_id",bookId));
        return cart;
    }

    @Override
    public int delete_cart(int id) {
        int code=cartMapper.delete(new QueryWrapper<Cart>().eq("id",id));
        return code;
    }

    @Override
    public int update_cart(Cart cart) {
        int code=cartMapper.update_cart(cart);
        return code;
    }

    @Override
    public int clean_cart(int user_id) {
        int code=cartMapper.delete(new QueryWrapper<Cart>().eq("user_id",user_id));
        return code;
    }


}
