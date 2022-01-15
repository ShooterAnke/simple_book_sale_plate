package net.anke.books_sales_platform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("menus")
public class Menu {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer power;
    private Integer motherId;
    private String label;
    private String icon;
    private String component;
    private String path;
    private String name;

    private List<Menu> children;
}
