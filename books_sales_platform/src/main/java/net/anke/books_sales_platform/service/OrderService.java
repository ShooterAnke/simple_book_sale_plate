package net.anke.books_sales_platform.service;

import net.anke.books_sales_platform.model.entity.Order;
import net.anke.books_sales_platform.model.entity.OrderBook;
import net.anke.books_sales_platform.model.entity.User;

import java.util.List;

public interface OrderService {
    public  int add_order(Order order);
    public int add_order_book(OrderBook orderBook);
    public List<Order> get_order_list(User user);
    public int confirm_order(int order_id);
    public int cancel_order(int order_id);
    public List<Order> get_finish_data(User user);
    public int back_book_count(List<OrderBook> list);
    public List<Order> get_card_data_by_option(int category_option,int search_option,String search);
    public int confirm_send(int order_id);
}
