package com.WHY.lease.web.app.vo.infomation;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "加好友")
@Data
@AllArgsConstructor
public class findFriendVo {
    @Schema(description = "发人")
    private Long fromId;

    @Schema(description = "收人")
    private Long toId;

    @Schema(description = "状态")
    private int status;
}
