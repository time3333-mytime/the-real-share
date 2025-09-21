package com.WHY.lease.web.app.vo.infomation;

import com.WHY.lease.model.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "信息")
@TableName(value = "information_vo")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class InformationVo extends BaseEntity {
    @Schema(description = "消息")
    @TableField(value = "text")
    private String text;

    @Schema(description = "发消息人")
    @TableField(value = "from_id")
    private Long fromId;

    @Schema(description = "收消息人")
    @TableField(value = "to_id")
    private Long toId;

    @Schema(description = "状态")
    @TableField(value = "status")//0已读 1未读
    private int status;
}
