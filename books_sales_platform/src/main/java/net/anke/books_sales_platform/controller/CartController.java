package net.anke.books_sales_platform.controller;

import net.anke.books_sales_platform.mapper.BookMapper;
import net.anke.books_sales_platform.mapper.OrderMapper;
import net.anke.books_sales_platform.model.entity.Cart;
import net.anke.books_sales_platform.model.entity.Order;
import net.anke.books_sales_platform.model.entity.OrderBook;
import net.anke.books_sales_platform.model.entity.User;
import net.anke.books_sales_platform.service.BookService;
import net.anke.books_sales_platform.service.CartService;
import net.anke.books_sales_platform.service.OrderService;
import net.anke.books_sales_platform.service.UserService;
import net.anke.books_sales_platform.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cart/")
public class CartController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    BookService bookService;
    @GetMapping("/pri/getCartByUserId")
    public JsonData get_cart_by_userId(HttpServletRequest request){
        User user=userService.find_by_username((String) request.getAttribute("username"));
            List<Cart> res=cartService.get_cart_by_userId(user.getId());
            return JsonData.buildSuccess().msg("查询成功").put("cardData",res);
    }
    @PostMapping("/pri/addCart")
    public JsonData add_cart(HttpServletRequest request,@RequestBody Cart cart){
        User user=userService.find_by_username((String) request.getAttribute("username"));
        cart.setUserId(user.getId());cart.setCreateTime(new Date());
        if(cartService.find_cart_by_book_id(cart.getBookId())==null){
            int code=cartService.add_cart(cart);
            if(code>=1){
                return JsonData.buildSuccess().msg("添加成功");
            }else
            {
                return JsonData.buildError().msg("添加失败");
            }
        }else

        {
            return JsonData.buildError().msg("已经在购物车中,请在购物车中修改数目");
        }

    }
    @GetMapping("/pri/deleteCart")
    public JsonData delete_cart(HttpServletRequest request,@RequestParam(value = "id")int id){
        User user=userService.find_by_username((String) request.getAttribute("username"));
        int code=cartService.delete_cart(id);
        if(code>=1){
            return JsonData.buildSuccess().msg("删除成功");
        }else
        {
            return JsonData.buildError().msg("删除失败");
        }
    }
    @PostMapping("/pri/updateCart")
    public JsonData update_cart(HttpServletRequest request,@RequestBody Cart cart){
        User user=userService.find_by_username((String) request.getAttribute("username"));
        if(user.getId()==cart.getUserId()){
            int code=cartService.update_cart(cart);
            if(code>=1){
                return JsonData.buildSuccess().msg("修改成功");
            }
            else
            {
                return JsonData.buildError().msg("修改失败");
            }
        }else
        {
            return JsonData.buildError().msg("不要修改别人信息");
        }

    }
    @PostMapping("/pri/stockCheck")
    public JsonData stock_check(HttpServletRequest request,@RequestBody List<Cart> list){
        for(Cart cart:list){
            int count=bookService.get_book_by_id(cart.getBookId()).getCount();
            if(cart.getCount()>count){
                return JsonData.buildError().msg("库存不足，请仔细查看警告，调整数目");
            }
        }
        return JsonData.buildSuccess().msg("库存足够");
    }
    @GetMapping("/pri/cleanCart")
    public JsonData clean_cart(HttpServletRequest request){
        User user=userService.find_by_username((String) request.getAttribute("username"));
        int code=cartService.clean_cart(user.getId());
        if(code>=1){
            return JsonData.buildSuccess().msg("清空购物车成功");
        }else
        {
            return JsonData.buildError().msg("清空购物车失败");
        }
    }
    @PostMapping("/pri/addOrder")
    public JsonData add_order(HttpServletRequest request,@RequestBody List<Cart> list){
        User user=userService.find_by_username((String) request.getAttribute("username"));
        Order order=new Order();order.setCount(list.size());order.setCreateTime(new Date());order.setIsDelete(0);order.setIsGet(0);
        order.setIsSend(0);order.setUserId(user.getId());
        float sum=0;
        for(Cart cart:list){
            sum+=cart.getAllPay();
        }
        order.setAllPay(sum);
        int code=orderService.add_order(order);
        if(code>=1){
            for(Cart cart:list){
                OrderBook orderBook=new OrderBook();
                orderBook.setBookId(cart.getBookId());orderBook.setOrderId(order.getId());orderBook.setCount(cart.getCount());
                orderBook.setCreateTime(new Date());orderBook.setAllPay(cart.getAllPay());orderBook.setCoverImage(cart.getBook().getCoverImage());
                orderService.add_order_book(orderBook);
                bookService.update_book_count(cart.getBookId(),cart.getBook().getCount()-cart.getCount());
            }
            return JsonData.buildSuccess().msg("添加成功");

        }else
        {
            return JsonData.buildError().msg("添加失败");
        }
    }
}
