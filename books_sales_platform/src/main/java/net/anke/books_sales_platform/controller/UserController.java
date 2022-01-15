package net.anke.books_sales_platform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.anke.books_sales_platform.mapper.UserMapper;
import net.anke.books_sales_platform.model.entity.Menu;
import net.anke.books_sales_platform.model.entity.User;
import net.anke.books_sales_platform.model.request.ImageRequest;
import net.anke.books_sales_platform.model.request.LoginRequest;
import net.anke.books_sales_platform.model.request.PasswordRequest;
import net.anke.books_sales_platform.model.request.RegisterRequest;
import net.anke.books_sales_platform.model.response.UserResponse;
import net.anke.books_sales_platform.service.UserService;
import net.anke.books_sales_platform.utils.JWTUtil;
import net.anke.books_sales_platform.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @GetMapping("/pri/findByUsername")
    public JsonData find_by_username(HttpServletRequest request){
        String username=(String) request.getAttribute("username");
            User user=userService.find_by_username(username);
            if(user!=null){
                return JsonData.buildSuccess().msg("查询成功").put("user", UserResponse.user_to_response(user));
            }
            else{
                return JsonData.buildError().msg("查询失败不存在用户");
            }
    }
    @PostMapping("/pub/login")
    public JsonData Login(@RequestBody LoginRequest loginRequest){
        int flag=userService.login(loginRequest);
        JsonData res=null;
        if(flag==0){
            List<Menu> list=userService.get_menus(loginRequest.getUsername());
            res=JsonData.buildSuccess().msg("登陆成功").put("token",JWTUtil.geneJsonWebToken(loginRequest.getUsername())).put("menus",list);

        }
        else
        {
            res=JsonData.buildError().msg("登陆失败");

        }
        return res;
    }
    @PostMapping("/pub/register")
    public JsonData Register(@RequestBody RegisterRequest registerRequest){
        String username=registerRequest.getUsername();
        String password=registerRequest.getPassword();
        JsonData res=null;
        if(username.length()>=5&&username.length()<=10&&password.length()>=5&&password.length()<=15){
            if(userService.find_by_username(username)!=null){
                res=JsonData.buildError().msg("用户已存在");
            }else{
                int code= userService.register(registerRequest);
                if(code==1)
                {
                    res=JsonData.buildSuccess().msg("用户创建成功");
                }
                else
                {
                    res=JsonData.buildError().msg("用户创建失败");
                }
            }

        }else
        {
            res=JsonData.buildError().msg("用户名密码不合规范");
        }
        return res;
    }
    @GetMapping("/pri/getPageData")
    public JsonData get_page_data(HttpServletRequest request,@RequestParam(value = "cur")int cur,@RequestParam(value = "limit")int limit){
        String username=(String) request.getAttribute("username");
        User user=userService.find_by_username(username);
        JsonData res=null;
        if(user.getPower()==3){
            IPage<UserResponse> Ipage=userService.get_page_data(cur,limit);
            res=JsonData.buildSuccess().msg("成功").put("tableData",Ipage.getRecords()).put("total",Ipage.getTotal());
        }else
        {
            res=JsonData.buildError().msg("没有权限");
        }
        return res;
    }
    @GetMapping("/pri/deleteByUsername")
    public JsonData delete_by_username(HttpServletRequest request,@RequestParam(value = "username")String username){
            User user=userService.find_by_username((String) request.getAttribute("username"));
            JsonData res=null;
            if(user.getPower()==3){
                int code=userService.delete_by_username(username);
                if(code>=1){
                    res=JsonData.buildSuccess().msg("删除成功");
                    if(redisTemplate.hasKey("user:"+username)) {
                        redisTemplate.delete("user:" + username);
                    }
                }
                else
                {
                    res=JsonData.buildError().msg("删除失败");
                }
            }
            else
            {
                res=JsonData.buildError().msg("没有权限");
            }
            return res;
    }
    @PostMapping("/pri/updateByUsername")
    public JsonData update_by_username(HttpServletRequest request,@RequestBody UserResponse userResponse){
        User user=userService.find_by_username((String) request.getAttribute("username"));
        JsonData res=null;
        if(user.getPower()==3){
            int code = userService.update_by_username(userResponse);
            if(code>=1){
                res=JsonData.buildSuccess().msg("修改成功");
                if(redisTemplate.hasKey("user:"+userResponse.getUsername()))
                {
                    redisTemplate.delete("user:"+userResponse.getUsername());
                }
            }
        }
        else
        {
            res=JsonData.buildError().msg("权限不够");
        }
        return res;
    }
    @GetMapping("/pri/searchPageData")
    public JsonData search_page_data(HttpServletRequest request,@RequestParam(value = "option") Integer option,
                                     @RequestParam(value = "username") String username,@RequestParam(value = "cur") Integer cur,
                                     @RequestParam(value = "limit") Integer limit)
    {
            User user=userService.find_by_username((String) request.getAttribute("username"));
            JsonData res=null;
            IPage<UserResponse> Ipage=null;
            if(user.getPower()==3){
                if(option==1){
                    Ipage=userService.search_page_data1(cur,limit,username);
                    res=JsonData.buildSuccess().msg("查询成功").put("tableData",Ipage.getRecords()).put("total",Ipage.getTotal());
                }
                if(option==2){
                    Ipage=userService.search_page_data2(cur,limit,username);
                    res=JsonData.buildSuccess().msg("查询成功").put("tableData",Ipage.getRecords()).put("total",Ipage.getTotal());
                }
            }
            else
            {
                res=JsonData.buildError().msg("没有权限");
            }
            return res;

    }

    @PostMapping("/pri/updatePassword")
    public JsonData update_password(HttpServletRequest request, @RequestBody PasswordRequest passwordRequest){
        User user=userService.find_by_username((String) request.getAttribute("username"));
        JsonData res=null;
        if(user.getPower()==3){
            int code= userService.update_password(passwordRequest);
            if(code>=1)
            {
                res=JsonData.buildSuccess().msg("修改成功");
            }
            else
            {
                res=JsonData.buildError().msg("修改失败");
            }
        }else
        {
            res=JsonData.buildError().msg("没有权限");
        }
        return res;
    }

    @GetMapping("/pri/updatePhone")
    public JsonData update_phone(HttpServletRequest request,@RequestParam(value = "username") String username,@RequestParam(value = "phone")String phone){
        JsonData res=null;
        if(username.equals((String) request.getAttribute("username"))){
            int code=userService.update_phone(username,phone);
            if(code>=1){
                res=JsonData.buildSuccess().msg("修改成功");
                if(redisTemplate.hasKey("user:"+username)){
                    redisTemplate.delete("user:"+username);
                }
            }
            else
            {
                res=JsonData.buildError().msg("修改失败");
            }
        }else
        {
            res=JsonData.buildError().msg("为什么要改别人的信息？");
        }
        return res;

    }
    @GetMapping("/pri/updateName")
    public JsonData update_name(HttpServletRequest request,@RequestParam(value = "username")String username,@RequestParam(value = "name")String name){
        JsonData res=null;
        if(username.equals((String)request.getAttribute("username")))
        {
            int code=userService.update_name(username,name);
            if(code>=1){
                res=JsonData.buildSuccess().msg("修改成功");
                if(redisTemplate.hasKey("user:"+username)){
                    redisTemplate.delete("user:"+username);
                }
            }
            else
            {
                res=JsonData.buildError().msg("修改失败");
            }
        }
        else
        {
            res=JsonData.buildError().msg("为什么要改别人信息？");
        }
        return res;
    }
    @GetMapping("/pri/updateAddress")
    public JsonData update_address(HttpServletRequest request,@RequestParam(value = "username")String username,@RequestParam(value = "address")String address){
        JsonData res=null;
        if(username.equals((String)request.getAttribute("username")))
        {
            int code=userService.update_address(username,address);
            if(code>=1){
                res=JsonData.buildSuccess().msg("修改成功");
                if(redisTemplate.hasKey("user:"+username)){
                    redisTemplate.delete("user:"+username);
                }
            }
            else
            {
                res=JsonData.buildError().msg("修改失败");
            }

        }else
        {
            res=JsonData.buildError().msg("为什么要改别人信息");
        }
        return res;
    }
    @PostMapping("/pri/uploadHeadImage")
    public JsonData upload_head_image(HttpServletRequest request,ImageRequest imageRequest){
        String username=(String) request.getAttribute("username");
        MultipartFile file=imageRequest.getFile();
        if(file==null)
        {
           return JsonData.buildError().msg("请选择文件上传");
        }
        String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1,file.getOriginalFilename().length());
        String savePath=UPLOAD_FOLDER+username+"/";
        File savePathFile=new File(savePath);
        if(!savePathFile.exists()){
            savePathFile.mkdirs();
        }
        String filename= UUID.randomUUID().toString().replace("-","")+"."+suffix;
        try {
            file.transferTo(new File(savePath+filename));
        } catch (IOException e) {
            e.printStackTrace();
            return JsonData.buildError().msg("上传失败");
        }

        String url="http://localhost:8080/resource/"+username+"/"+filename;
        int code=userService.update_head_image(username,url);
        if(code>=1){
            if(redisTemplate.hasKey("user:"+username))
            {
                redisTemplate.delete("user:"+username);
            }
            return JsonData.buildSuccess().put("filepath",url).msg("上传成功");
        }
        else
        {
            return JsonData.buildError().msg("上传失败");
        }


    }
}
