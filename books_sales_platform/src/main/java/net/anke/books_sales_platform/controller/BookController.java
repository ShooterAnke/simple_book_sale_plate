package net.anke.books_sales_platform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.anke.books_sales_platform.model.entity.Book;
import net.anke.books_sales_platform.model.entity.BookInfoImage;
import net.anke.books_sales_platform.model.entity.Category;
import net.anke.books_sales_platform.model.entity.User;
import net.anke.books_sales_platform.model.request.ImageRequest;
import net.anke.books_sales_platform.service.BookService;
import net.anke.books_sales_platform.service.UserService;
import net.anke.books_sales_platform.utils.JsonData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book/")
public class BookController {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    RedisTemplate redisTemplate;
    @PostMapping("/pri/addBook")
    public JsonData add_book(HttpServletRequest request, @RequestBody Book book){
        User user= userService.find_by_username((String) request.getAttribute("username"));
        JsonData res=null;
        if(user.getPower()==2||user.getPower()==3){
            book.setCreateTime(new Date());
            book.setUpdateTime(new Date());
            int code= bookService.add_book(book);
            if(code>=1){
                res=JsonData.buildSuccess().msg("添加成功").put("id",book.getId());
            }
            else
            {
                res=JsonData.buildError().msg("添加失败");
            }
        }else {
            res=JsonData.buildError().msg("没有权利");
        }
        return res;
    }
    @PostMapping("/pri/uploadImage")
    public JsonData uploadImage(HttpServletRequest request,  ImageRequest imageRequest)
    {
        User user=userService.find_by_username((String) request.getAttribute("username"));
        if(user.getPower()==2||user.getPower()==3){
            MultipartFile file=imageRequest.getFile();
            String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1,file.getOriginalFilename().length());
            String savePath=UPLOAD_FOLDER+"/bookImageStore/";
            File savePathFile=new File(savePath);
            if(!savePathFile.exists()){
                savePathFile.mkdirs();
            }
            String filename= UUID.randomUUID().toString().replace("-","")+"."+suffix;
            try {
                file.transferTo(new File(savePath+filename));
            } catch (IOException e) {
                e.printStackTrace();
                return JsonData.buildError().msg("保存失败");
            }
            return JsonData.buildSuccess().put("url","http://localhost:8080/resource/bookImageStore/"+filename).msg("上传成功").put("name",filename);
        }
        else
        {
            return JsonData.buildError().msg("权限不够");
        }


    }
    @PostMapping("/pri/addBookInfoImage")
    public JsonData add_book_info_image(HttpServletRequest request,@RequestBody List<BookInfoImage> list){
            User user=userService.find_by_username((String) request.getAttribute("username"));
            if(list.size()==0){
                return JsonData.buildSuccess().msg("添加成功");
            }
            if(user.getPower()==2||user.getPower()==3){
                    int code= bookService.add_book_info_image(list);
                    if(code>=1){
                        return JsonData.buildSuccess().msg("添加成功");
                    }
                    else
                    {
                        return JsonData.buildError().msg("添加失败");
                    }

            }
            else
            {
                return JsonData.buildError().msg("没有权限");
            }
    }
    @GetMapping("/pub/getPageData")
    public JsonData get_page_data(HttpServletRequest request,@RequestParam(value = "cur")int cur,
                                  @RequestParam(value = "limit")int limit){
            IPage<Book> Ipage=bookService.get_page_data(cur,limit);
            return JsonData.buildSuccess().msg("查询成功").put("total",Ipage.getTotal()).put("tableData",Ipage.getRecords());
    }
    @GetMapping("/pri/deleteBookById")
    public JsonData delete_book_by_id(HttpServletRequest request,@RequestParam(value = "id") int id)
    {
        User user= userService.find_by_username((String) request.getAttribute("username"));
        if(user.getPower()==2||user.getPower()==3){
                int code=bookService.delete_book_by_id(id);
                if(code>=1){
                    return JsonData.buildSuccess().msg("删除成功");
                }
                else
                {
                    return JsonData.buildError().msg("删除失败");
                }
        }else
        {
            return JsonData.buildError().msg("没有权限");
        }
    }

    @GetMapping("/pub/getAllDataById")
    public JsonData get_all_data_by_id(HttpServletRequest request,@RequestParam(value = "id")int id){
                Book book=bookService.get_book_by_id(id);
                List<BookInfoImage> list=bookService.get_book_info_image_by_id(id);
                return JsonData.buildSuccess().msg("查询成功").put("formData",book).put("fileList",list);
    }

    @PostMapping("/pri/updateAllDataById")
    public JsonData update_all_data_by_id(HttpServletRequest request,@RequestBody Book book){
        User user=userService.find_by_username((String) request.getAttribute("username"));
        if(user.getPower()==2||user.getPower()==3){
            int code= bookService.update_all_data_by_id(book);
            if(code==1){
                return JsonData.buildSuccess().msg("更新成功");
            }
            else
            {
                return JsonData.buildError().msg("更新失败");
            }
        }
        else {
            return JsonData.buildError().msg("没有权限");
        }
    }

    @GetMapping("/pri/addCategory")
    public JsonData add_category(HttpServletRequest request,@RequestParam(value = "name") String name)
    {
        User user=userService.find_by_username((String) request.getAttribute("username"));
        if(user.getPower()==2||user.getPower()==3){
            int code=bookService.add_category(name);
            if(code>=1){
                redisTemplate.delete("book:categoryList");
                return JsonData.buildSuccess().msg("添加成功");

            }
            else
            {
                return JsonData.buildError().msg("添加失败");
            }
        }
        else
        {
            return JsonData.buildError().msg("没有权限");
        }

    }

    @GetMapping("/pri/getCategoryList")
    public JsonData get_category_list(HttpServletRequest request){

        User user=userService.find_by_username((String) request.getAttribute("username"));
        if(user.getPower()==2||user.getPower()==3){
            if(redisTemplate.hasKey("book:categoryList"))
            {
                return JsonData.buildSuccess().msg("获取分类列表成功").put("selectOptions",redisTemplate.opsForValue().get("book:categoryList"));
            }
            List<Category> list=bookService.get_category_list();
            redisTemplate.opsForValue().set("book:categoryList",list);
            return JsonData.buildSuccess().msg("获取分类列表成功").put("selectOptions",list);
        }
        else
        {
            return JsonData.buildError().msg("获取分类列表失败");
        }
    }

    @GetMapping("/pub/getSearchPageData")
    public JsonData get_search_page_data(HttpServletRequest request,@RequestParam(value = "option")int option,
                                         @RequestParam(value = "search")String search,@RequestParam(value = "cur")int cur,
                                         @RequestParam(value = "limit")int limit){
            IPage<Book> Ipage=bookService.get_search_page_data(option,search,cur,limit);
            if(Ipage==null){
                return JsonData.buildError().msg("出现意料之外的错误");
            }
            else
            {
                return JsonData.buildSuccess().msg("查询成功").put("total",Ipage.getTotal()).put("tableData",Ipage.getRecords());
            }
    }
}
