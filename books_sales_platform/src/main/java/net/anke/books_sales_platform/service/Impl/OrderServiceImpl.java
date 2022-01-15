package net.anke.books_sales_platform.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import net.anke.books_sales_platform.mapper.BookMapper;
import net.anke.books_sales_platform.mapper.OrderBookMapper;
import net.anke.books_sales_platform.mapper.OrderMapper;
import net.anke.books_sales_platform.mapper.UserMapper;
import net.anke.books_sales_platform.model.entity.Book;
import net.anke.books_sales_platform.model.entity.Order;
import net.anke.books_sales_platform.model.entity.OrderBook;
import net.anke.books_sales_platform.model.entity.User;
import net.anke.books_sales_platform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderBookMapper orderBookMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public int add_order(Order order) {
        int code=orderMapper.insert(order);
        return code;
    }

    @Override
    public int add_order_book(OrderBook orderBook) {
        int code=orderBookMapper.insert(orderBook);
        return code;
    }

    @Override
    public List<Order> get_order_list(User user) {
        List<Order> list=orderMapper.selectList(new QueryWrapper<Order>().eq("user_id",user.getId()).eq("is_delete",0).eq("is_get",0) );
        for(Order order:list){
            User newUser=new User();
            newUser.setHeadImage(user.getHeadImage());newUser.setPhone(user.getPhone());
            newUser.setAddress(user.getAddress());newUser.setName(user.getName());newUser.setUsername(user.getUsername());
            order.setUser(newUser);
            List<OrderBook> bookList=orderBookMapper.selectList(new QueryWrapper<OrderBook>().eq("order_id",order.getId())) ;
            for(OrderBook orderBook:bookList){
                orderBook.setBook(bookMapper.select_book_for_order(orderBook.getBookId()));
            }
            order.setOrderBooks(bookList);
        }
        return list;
    }

    @Override
    public int confirm_order(int order_id) {
        int code=orderMapper.confirm_order(order_id);
        return code;
    }

    @Override
    public int cancel_order(int order_id) {
        int code=orderMapper.cancel_order(order_id);

        return code;
    }

    @Override
    public List<Order> get_finish_data(User user) {
        List<Order> list=orderMapper.selectList(new QueryWrapper<Order>().eq("user_id",user.getId()).eq("is_delete",0).eq("is_get",1) );
        for(Order order:list){
            User newUser=new User();
            newUser.setHeadImage(user.getHeadImage());newUser.setPhone(user.getPhone());
            newUser.setAddress(user.getAddress());newUser.setName(user.getName());newUser.setUsername(user.getUsername());
            order.setUser(newUser);
            List<OrderBook> bookList=orderBookMapper.selectList(new QueryWrapper<OrderBook>().eq("order_id",order.getId())) ;
            for(OrderBook orderBook:bookList){
                orderBook.setBook(bookMapper.select_book_for_order(orderBook.getBookId()));
            }
            order.setOrderBooks(bookList);
        }
        return list;
    }

    @Override
    public int back_book_count(List<OrderBook> list) {
        for(OrderBook orderBook:list){
            int  count =orderBook.getCount()+orderBook.getBook().getCount();
            bookMapper.update_book_count(orderBook.getBookId(),count);
        }
        return 1;
    }

    @Override
    public List<Order> get_card_data_by_option(int category_option, int search_option, String search) {
      List<Order> list=null;
        if(category_option==1){
          if(search_option==1){
              list=orderMapper.search_card_data_by_username(0,0,search);
          }
          else {
              list=orderMapper.search_card_data_by_order_id(0,0,Integer.valueOf(search));
          }
      }else
        {
            if(category_option==2){
                if(search_option==1){
                    list=orderMapper.search_card_data_by_username(1,0,search);
                }
                else{
                list=orderMapper.search_card_data_by_order_id(1,0,Integer.valueOf(search));
                }
            }
            else
            {
                if(search_option==1){
                    list=orderMapper.search_card_data_by_username(1,1,search);
                }
                else
                {
                    list=orderMapper.search_card_data_by_order_id(1,1,Integer.valueOf(search));
                }
            }
        }
        return list;
    }

    @Override
    public int confirm_send(int order_id) {
        int code=orderMapper.confirm_send(order_id);
        return code;
    }
}
