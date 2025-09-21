package com.WHY.lease.web.app.vo.message;

import com.WHY.lease.model.entity.UserInfo;
import com.WHY.lease.web.app.vo.graph.GraphVo;
import com.WHY.lease.model.entity.MessageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "帖子外面")
public class MessageOutVo extends MessageInfo {
    @Schema(description = "发帖时间")
    private Date sendTime;

    @Schema(description = "发帖人详情")
    private UserInfo userInfo;

    @Schema(description = "图片列表")
    private List<GraphVo> graphVoList;

}
