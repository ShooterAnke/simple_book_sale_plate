package net.anke.books_sales_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.anke.books_sales_platform.model.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    public List<Menu> list_menus_by_power(@Param(value = "power") int power);
}
