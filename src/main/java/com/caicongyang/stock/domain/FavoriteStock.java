package com.caicongyang.stock.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_favorite_stock")
public class FavoriteStock {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String symbol;      // 股票代码
    private String name;        // 股票名称
    private String type;        // 类型：stock-个股，concept-概念
    private Double price;       // 当前价格
    
    @TableField("`change`")    // 使用反引号包裹关键字
    private Double change;      // 涨跌幅
    
    private String volume;      // 成交量
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;  // 加入时间
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;  // 更新时间
} 