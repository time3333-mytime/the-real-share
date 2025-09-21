package com.WHY.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName(value = "landload_room_info")
public class LandloadRoom extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Schema(description = "房间id")
    @TableField(value = "room_id")
    private Long roomId;
    @Schema(description = "房间id")
    @TableField(value = "landlord_id")
    private Long landLordId;
}
