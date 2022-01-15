package net.anke.books_sales_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.anke.books_sales_platform.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.Date;

@Mapper
public interface UserMapper  extends BaseMapper<User> {
    public int delete_by_username(@Param(value = "username") String username);
    public int update_password(@Param(value = "username")String username,
                               @Param(value="password") String password,
                               @Param(value = "date") Date date);
    public int update_phone(@Param(value = "username")String username,
                            @Param(value = "phone")String phone,
                            @Param(value = "date") Date date);
    public int update_name(@Param(value = "username")String username,
                           @Param(value = "name")String name,
                           @Param(value = "date") Date date
    );
    public int update_address(@Param(value = "username")String username,
                              @Param(value = "address")String address,@Param(value = "date") Date date);
    public int update_head_image(@Param(value = "username")String username,
                                 @Param(value = "url")String url,@Param(value = "date")Date date);
}
