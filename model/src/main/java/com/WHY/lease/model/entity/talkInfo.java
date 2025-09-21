package com.WHY.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "评论信息表")
@TableName(value = "talk_info")
@Data
public class talkInfo extends BaseEntity{
    @Schema(description = "评论")
    @TableField(value = "talk")
    private String talk;

    @Schema(description = "所属帖子")
    @TableField(value = "mid")
    private Long mid;

    @Schema(description = "状态")
    @TableField(value = "status")
    private int status;

    @Schema(description = "收到评论人")
    @TableField(value = "user_id")
    private Long userid;
    @Schema(description = "发送评论人")
    @TableField(value = "send_user_id")
    private Long senduserid;
}
