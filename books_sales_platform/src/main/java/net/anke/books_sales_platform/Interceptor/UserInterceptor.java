package net.anke.books_sales_platform.Interceptor;

import io.jsonwebtoken.Claims;
import net.anke.books_sales_platform.utils.JWTUtil;
import net.anke.books_sales_platform.utils.JsonData;
import net.anke.books_sales_platform.utils.JsonUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("token");
        if(token==null){
            token=request.getParameter("token");
        }
        if(token!=null){
            Claims claims= JWTUtil.checkJWT(token);
            if(claims!=null)
            {
                request.setAttribute("username",claims.get("username"));

                return true;
            }
            else
            {
                sendJsonMessage(response, JsonData.buildError().msg("身份信息过期"));
                return false;
            }
        }
        else{
            sendJsonMessage(response,JsonData.buildError().msg("未登陆"));
            return false;
        }
    }
    public void sendJsonMessage(HttpServletResponse response, JsonData jsonData){
        try {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter printWriter=response.getWriter();
            printWriter.print(JsonUtil.objectToJson(jsonData));
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
