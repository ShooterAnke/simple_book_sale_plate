package net.anke.books_sales_platform.model.response;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.anke.books_sales_platform.model.entity.User;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class UserResponse {
    String username;
    String phone;
    String address;
     Date createTime;
    Date updateTime;
    String headImage;
    String name;
    Integer power;

    public static UserResponse user_to_response(User user){
        return new UserResponse(user.getUsername(),user.getPhone(),user.getAddress(),user.getCreateTime(),user.getUpdateTime()
        ,user.getHeadImage(),user.getName(),user.getPower());
    }


}
