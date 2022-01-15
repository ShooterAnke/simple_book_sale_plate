package net.anke.books_sales_platform.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import net.anke.books_sales_platform.utils.CommonUtils;
import net.anke.books_sales_platform.utils.JsonData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {
    @Autowired
    DefaultKaptcha captchaProducer;
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/pub/get_captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        String key=getKey(request);
        String text= captchaProducer.createText();
        redisTemplate.opsForValue().set(key,text,10, TimeUnit.MINUTES);
        BufferedImage bufferedImage=captchaProducer.createImage(text);
        ServletOutputStream stream=null;
        try {
            stream=response.getOutputStream();
            ImageIO.write(bufferedImage,"png",stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @GetMapping("/pub/check")
    public JsonData check(HttpServletRequest request, @RequestParam(value = "captcha") String captcha){
        String key=getKey(request);
        JsonData res=null;
        if(redisTemplate.hasKey(key)){
            if(redisTemplate.opsForValue().get(key).equals(captcha)){
                res=JsonData.buildSuccess().msg("验证成功");
                redisTemplate.delete(key);
            }
            else{
                res=JsonData.buildError().msg("验证码错误");
            }
        }
        else{
            res=JsonData.buildError().msg("验证码过期");
        }
        return res;
    }



    public String getKey(HttpServletRequest request){
        String ip = CommonUtils.getIpAddr(request);
        String userAgent=request.getHeader("userAgent");
        String key="captcha:"+CommonUtils.MD5(ip+userAgent);
        return key;
    }

}
