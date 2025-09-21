package com.WHY.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "帖子表")
@TableName(value = "message_info")
@Data
public class MessageInfo extends BaseEntity{
    @Schema(description = "主要内容")
    @TableField(value = "subject")
    private String subject;

    @Schema(description = "具体内容")
    @TableField(value = "message")
    private String message;

    @Schema(description = "发帖人")
    @TableField(value = "user_id")
    private Long userid;
}
