package net.anke.books_sales_platform.service.Impl;

import ch.qos.logback.core.util.TimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.anke.books_sales_platform.mapper.MenuMapper;
import net.anke.books_sales_platform.mapper.UserMapper;
import net.anke.books_sales_platform.mapper.UserResponseMapper;
import net.anke.books_sales_platform.model.entity.Menu;
import net.anke.books_sales_platform.model.entity.User;
import net.anke.books_sales_platform.model.request.LoginRequest;
import net.anke.books_sales_platform.model.request.PasswordRequest;
import net.anke.books_sales_platform.model.request.RegisterRequest;
import net.anke.books_sales_platform.model.response.UserResponse;
import net.anke.books_sales_platform.service.UserService;
import net.anke.books_sales_platform.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    UserResponseMapper userResponseMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public User find_by_username(String username) {
        User user=null;
        if(redisTemplate.hasKey("user:"+username)){
            user=(User) redisTemplate.opsForValue().get("user:"+username);
        }
        else{
            user=userMapper.selectOne(new QueryWrapper<User>().eq("username",username).eq("isdeleted",0));
            if(user!=null) {
                redisTemplate.opsForValue().set("user:" + username, user, 10, TimeUnit.MINUTES);
            }

        }
        return user;
    }

    @Override
    public int login(LoginRequest loginRequest) {
        String username=loginRequest.getUsername();
        String password= loginRequest.getPassword();
        User user=find_by_username(username);
        if(user!=null&&user.getPassword().equals(CommonUtils.MD5(password)))
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }

    @Override
    public List<Menu> get_menus(String username) {
        User user=find_by_username(username);
        int power=user.getPower();
        List<Menu> list=null;
        if(redisTemplate.hasKey("menu:power_"+power)){
            list=(List<Menu>) redisTemplate.opsForValue().get("menu:power_"+power);
        }
        else
        {
            list=menuMapper.list_menus_by_power(power);
            redisTemplate.opsForValue().set("menu:power_"+power,list,60,TimeUnit.MINUTES);
        }
        return list;
    }

    @Override
    public int register(RegisterRequest registerRequest) {
        User user=new User();
        user.setUsername(registerRequest.getUsername());user.setPassword(CommonUtils.MD5(registerRequest.getPassword()));
        user.setPower(1);user.setIsdeleted(0);user.setCreateTime(new Date());user.setUpdateTime(new Date());
        user.setHeadImage("http://shooteranke.site:4001/head_image.png");
        user.setPhone(registerRequest.getPhone());
        int code=userMapper.insert(user);
        return code;
    }

    @Override
    public IPage<UserResponse> get_page_data(int cur, int limit) {
        IPage<UserResponse> page=new Page<>(cur,limit);
        IPage<UserResponse> Ipage= userResponseMapper.selectPage(page,new QueryWrapper<UserResponse>().eq("isdeleted",0));
        return Ipage;
    }

    @Override
    public int delete_by_username(String username) {
        int code=userMapper.delete_by_username(username);
        return code;
    }

    @Override
    public int update_by_username(UserResponse userResponse) {
        int code= userResponseMapper.update_by_username(userResponse,new Date());
        return code;

    }

    @Override
    public IPage<UserResponse> search_page_data1(int cur, int limit, String username) {
        IPage<UserResponse> page=new Page<>(cur,limit);
        IPage<UserResponse> res=userResponseMapper.selectPage(page,new QueryWrapper<UserResponse>().eq("username",username).eq("isdeleted",0));
        return res;
    }

    @Override
    public IPage<UserResponse> search_page_data2(int cur, int limit, String username) {
        IPage<UserResponse> page=new Page<>(cur,limit);
        IPage<UserResponse> res=userResponseMapper.selectPage(page,new QueryWrapper<UserResponse>().like("username","%"+username+"%").eq("isdeleted",0));
        return res;
    }

    @Override
    public int update_password(PasswordRequest passwordRequest) {
        int code=userMapper.update_password(passwordRequest.getUsername(), CommonUtils.MD5(passwordRequest.getPassword()),new Date());
        return code;
    }

    @Override
    public int update_phone(String username, String phone) {
        int code=userMapper.update_phone(username,phone,new Date());
        return code;
    }

    @Override
    public int update_name(String username, String name) {
        int code=userMapper.update_name(username,name,new Date());
        return code;
    }

    @Override
    public int update_address(String username, String address) {
        int code=userMapper.update_address(username,address,new Date());
        return code;
    }

    @Override
    public int update_head_image(String username, String url) {
    int code=userMapper.update_head_image(username,url,new Date());
    return code;
    }
}
