package com.WHY.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "好友表")
@TableName(value = "friends_info")
@AllArgsConstructor
@Data
public class FriendsInfo extends BaseEntity{
    @Schema(description = "用户1的id")
    @TableField(value = "first_id")
    private Long firstId;

    @Schema(description = "用户2的id")
    @TableField(value = "second_id")
    private Long secondId;

    @Schema(description = "状态")
    @TableField(value = "status")
    private Integer status;
}
