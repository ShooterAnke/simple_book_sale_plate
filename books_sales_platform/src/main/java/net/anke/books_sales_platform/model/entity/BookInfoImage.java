package net.anke.books_sales_platform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("book_info_images")
public class BookInfoImage {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer Id;
    private String name;
    private String url;
    private Integer bookId;
    private Date createTime;
}
