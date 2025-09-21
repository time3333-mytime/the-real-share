package com.WHY.lease.web.app.vo.message;

import com.WHY.lease.model.entity.UserInfo;
import com.WHY.lease.model.entity.WorryInfo;
import com.WHY.lease.web.app.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WorryInfoVo extends WorryInfo {
    @Schema(description = "发帖人详情")
    private UserInfo userInfo;
    @Schema(description = "图片列表")
    private List<GraphVo> graphVoList;
    @Schema(description = "发评论时间")
    private Date sendTime;
}
