package net.anke.books_sales_platform.service;

import net.anke.books_sales_platform.model.entity.Cart;
import net.anke.books_sales_platform.model.entity.Order;

import java.util.List;

public interface CartService {
    public List<Cart> get_cart_by_userId(int userId);
    public int add_cart(Cart cart);
    public Cart find_cart_by_book_id(int bookId);
    public int delete_cart(int id);
    public int update_cart(Cart cart);
    public int clean_cart(int user_id);

}
