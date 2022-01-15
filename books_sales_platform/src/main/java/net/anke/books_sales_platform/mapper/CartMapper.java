package net.anke.books_sales_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.anke.books_sales_platform.model.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
    public int update_cart(@Param(value = "cart") Cart cart);
}
