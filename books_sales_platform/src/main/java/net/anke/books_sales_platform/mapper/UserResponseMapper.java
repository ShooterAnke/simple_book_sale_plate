package net.anke.books_sales_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.anke.books_sales_platform.model.response.UserResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserResponseMapper extends BaseMapper<UserResponse> {
    public int update_by_username(@Param("user")UserResponse userResponse,@Param(value = "date") Date date);
}
