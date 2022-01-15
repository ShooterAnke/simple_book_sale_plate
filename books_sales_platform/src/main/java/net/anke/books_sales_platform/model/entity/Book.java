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

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("books")
public class Book {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String category;
    private Integer count;
    private String publisher;
    private Float price;
    private String coverImage;
    private Date createTime;
    private Date updateTime;
    private String bookNum;
    private String detail;
    @TableField(exist = false)
    private List<BookInfoImage> fileList;

}
