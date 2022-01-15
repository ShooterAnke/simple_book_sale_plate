package net.anke.books_sales_platform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_books")
public class OrderBook {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer bookId;
    private Integer count;
    private Float allPay;
    private Date createTime;
    private Integer orderId;
    private String coverImage;
    @TableField(exist = false)
    private Book book;
}
