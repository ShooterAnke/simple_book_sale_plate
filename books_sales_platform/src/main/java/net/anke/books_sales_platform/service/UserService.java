package net.anke.books_sales_platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.anke.books_sales_platform.model.entity.Menu;
import net.anke.books_sales_platform.model.entity.User;
import net.anke.books_sales_platform.model.request.LoginRequest;
import net.anke.books_sales_platform.model.request.PasswordRequest;
import net.anke.books_sales_platform.model.request.RegisterRequest;
import net.anke.books_sales_platform.model.response.UserResponse;

import java.util.List;

public interface UserService {
    User find_by_username(String username);
   int login(LoginRequest loginRequest);
    List<Menu> get_menus(String username);
    int register(RegisterRequest registerRequest);
    IPage<UserResponse> get_page_data(int cur, int limit);

    int delete_by_username(String username);
    int update_by_username(UserResponse userResponse);
    IPage<UserResponse> search_page_data1(int cur,int limit,String username);
    IPage<UserResponse> search_page_data2(int cur,int limit,String username);
    int update_password(PasswordRequest passwordRequest);
    int update_phone(String username,String password);
    int update_name(String username,String name);
    int update_address(String username,String address);
    int update_head_image(String username,String url);
}
