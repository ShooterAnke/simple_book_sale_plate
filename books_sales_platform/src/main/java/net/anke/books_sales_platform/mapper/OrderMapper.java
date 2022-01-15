package net.anke.books_sales_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.anke.books_sales_platform.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    public int confirm_order(@Param("order_id")int order_id);
    public int cancel_order(@Param("order_id")int order_id);
    public List<Order> search_card_data_by_username(@Param("is_send")int send,@Param("is_get")int get,@Param("username")String username);
    public List<Order> search_card_data_by_order_id(@Param("is_send")int send,@Param("is_get")int get,@Param("order_id")int orderId);
    public int confirm_send(@Param("order_id")int order_id);
}
