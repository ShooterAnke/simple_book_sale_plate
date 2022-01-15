package net.anke.books_sales_platform.controller;

import net.anke.books_sales_platform.model.entity.Order;
import net.anke.books_sales_platform.model.entity.OrderBook;
import net.anke.books_sales_platform.model.entity.User;
import net.anke.books_sales_platform.service.OrderService;
import net.anke.books_sales_platform.service.UserService;
import net.anke.books_sales_platform.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/order/")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @GetMapping("/pri/getOrderList")
    public JsonData get_order_list(HttpServletRequest request)
    {
        User user=userService.find_by_username((String)request.getAttribute("username") );
        List<Order> list=orderService.get_order_list(user);
        return JsonData.buildSuccess().msg("查询成功").put("cardData",list);
    }
    @GetMapping("/pri/confirmOrder")
    public JsonData confirm_order(HttpServletRequest request, @RequestParam(value = "order_id")int order_id,@RequestParam(value = "user_id")int user_id){
        User user= userService.find_by_username((String) request.getAttribute("username"));
        if(user.getId()==user_id){
            int code=orderService.confirm_order(order_id);
            if(code>=1){
                return JsonData.buildSuccess().msg("确认成功");
            }
            else
            {
                return JsonData.buildError().msg("确认失败");
            }
        }else
        {
            return JsonData.buildError().msg("别改别人东西");
        }
    }
    @PostMapping("/pri/cancelOrder")
    public JsonData cancel_order(HttpServletRequest request, @RequestParam(value = "order_id")int order_id, @RequestParam(value = "user_id")int user_id,
                                 @RequestBody List<OrderBook> list){
        User user= userService.find_by_username((String) request.getAttribute("username"));
        if(user.getId()==user_id){
            int code=orderService.cancel_order(order_id);
            if(code>=1){
                orderService.back_book_count(list);
                return JsonData.buildSuccess().msg("取消成功");
            }else
            {
                return JsonData.buildError().msg("取消失败");
            }
        }else
        {
            return JsonData.buildError().msg("别改别人数据");
        }
    }
    @GetMapping("/pri/getFinishData")
    public JsonData get_finish_data(HttpServletRequest request){
        User user= userService.find_by_username((String) request.getAttribute("username"));
        List<Order> list=orderService.get_finish_data(user);
        return JsonData.buildSuccess().msg("查询成功").put("cardData",list);
    }

    @GetMapping("/pri/getCardDataByOption")
    public JsonData get_card_data_by_option(HttpServletRequest request,@RequestParam(value = "option1") int category_option,
                                            @RequestParam(value = "option2")int search_option,@RequestParam(value = "search")String search)
    {
        User user= userService.find_by_username((String) request.getAttribute("username"));
        if(user.getPower()==2||user.getPower()==3){
            List<Order> list=orderService.get_card_data_by_option(category_option,search_option,search);
            return JsonData.buildSuccess().msg("查询成功").put("cardData",list);
        }else
        {
            return JsonData.buildError().msg("没有权限");
        }
    }
    @GetMapping("/pri/confirmSend")
    public JsonData confirm_send(HttpServletRequest request,@RequestParam(value = "order_id")int order_id){
        User user=userService.find_by_username((String) request.getAttribute("username"));
        if(user.getPower()==2||user.getPower()==3){
            int code=orderService.confirm_send(order_id);
            if(code>=1){
                return JsonData.buildSuccess().msg("确认成功");
            }else
            {
                return JsonData.buildError().msg("确认失败");
            }
        }else
        {
            return JsonData.buildError().msg("没有权限");
        }
    }
}
