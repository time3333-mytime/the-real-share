package com.WHY.lease.web.app.vo.infomation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "用户基本信息")
@Data
@AllArgsConstructor
public class NowUserInfoVo {
    @Schema(description = "未读消息个数")
    private Long count;

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatarUrl;
}

