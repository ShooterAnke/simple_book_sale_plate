package net.anke.books_sales_platform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@TableName("orders")
public class Order {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer count;
    private Float allPay;
    private Date createTime;
    private Integer isSend;
    private Integer isGet;
    private Integer isDelete;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private List<OrderBook> orderBooks;
}
