package net.anke.books_sales_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.anke.books_sales_platform.model.entity.OrderBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderBookMapper extends BaseMapper<OrderBook> {
}
