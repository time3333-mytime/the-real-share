package com.WHY.lease.web.app.vo.infomation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Schema(description = "基本信息")
@Data
@AllArgsConstructor
public class FriendsInfoVo {
    private List<NowUserInfoVo> userInfoVos;
}
