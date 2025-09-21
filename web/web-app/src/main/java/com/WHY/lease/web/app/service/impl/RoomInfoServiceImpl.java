package com.WHY.lease.web.app.service.impl;

import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.model.entity.*;
import com.WHY.lease.model.enums.ItemType;
import com.WHY.lease.model.enums.LeaseStatus;
import com.WHY.lease.web.app.mapper.*;
import com.WHY.lease.web.app.service.*;
import com.WHY.lease.web.app.vo.apartment.ApartmentItemVo;
import com.WHY.lease.web.app.vo.attr.AttrValueVo;
import com.WHY.lease.web.app.vo.fee.FeeValueVo;
import com.WHY.lease.web.app.vo.graph.GraphVo;
import com.WHY.lease.web.app.vo.message.TalkInfoVo;
import com.WHY.lease.web.app.vo.room.RoomDetailVo;
import com.WHY.lease.web.app.vo.room.RoomItemVo;
import com.WHY.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {


    @Autowired
    private  RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private TalkInfoMapper talkInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;
    @Autowired
    private LeaseAgreementService service;

    @Autowired
    private BrowsingHistoryService browsingHistoryService;

    @Override
    public IPage<RoomItemVo> pageItem(Page<RoomItemVo> roomItemVoPage, RoomQueryVo queryVo) {
        return roomInfoMapper.pageItem(roomItemVoPage,queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {
        RoomInfo roomInfo = roomInfoMapper.selectById(id);
        if (roomInfo == null) {
            return null;
        }

        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, id);

        List<LeaseTerm> leaseTermList = leaseTermMapper.selectListByRoomId(id);

        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByRoomId(id);

        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByRoomId(id);

        List<PaymentType> paymentTypeList = paymentTypeMapper.seclectById(id);

        List<AttrValueVo> attrValueVoList = attrValueMapper.selectListByRoomId(id);

        List<FeeValueVo> feeValueVoList = feeValueMapper.selectListByApartmentId(roomInfo.getApartmentId());

        ApartmentItemVo apartmentItemVo = apartmentInfoService.selectApartmentItemVoById(roomInfo.getApartmentId());

        RoomDetailVo roomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo, roomDetailVo);

        roomDetailVo.setApartmentItemVo(apartmentItemVo);
        roomDetailVo.setGraphVoList(graphVoList);
        roomDetailVo.setAttrValueVoList(attrValueVoList);
        roomDetailVo.setFacilityInfoList(facilityInfoList);
        roomDetailVo.setLabelInfoList(labelInfoList);
        roomDetailVo.setPaymentTypeList(paymentTypeList);
        roomDetailVo.setFeeValueVoList(feeValueVoList);
        roomDetailVo.setLeaseTermList(leaseTermList);


        browsingHistoryService.saveHistory(LoginUserHolder.getLoginUser().getUserId(),id);

        return roomDetailVo;
    }

    @Override
    public IPage<RoomItemVo> pageItemByApartmentId(Page<RoomItemVo> roomItemVoPage, Long id) {
        return roomInfoMapper.pageItemByApartmentId(roomItemVoPage,id);
    }

    @Override
    public List<TalkInfoVo> getTalkDetailByid(Long id) {
        LambdaUpdateWrapper<talkInfo> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(talkInfo::getMid,id);
        List<talkInfo> list = talkInfoMapper.selectList(lambdaUpdateWrapper);
        List<TalkInfoVo>List=new ArrayList<>();
        for (talkInfo talkInfo : list) {
            TalkInfoVo talkInfoVo=new TalkInfoVo();
            BeanUtils.copyProperties(talkInfo,talkInfoVo);
            java.util.List<GraphVo> graphVos = graphInfoMapper.selectListByItemTypeAndId(ItemType.CONTENT, talkInfo.getId());
            talkInfoVo.setGraphVoList(graphVos);
            talkInfoVo.setSendTime(talkInfo.getCreateTime());
            UserInfo userInfo1=userInfoMapper.selectById(talkInfo.getSenduserid());
            talkInfoVo.setUserInfo(userInfo1);
            List.add(talkInfoVo);
        }
        return  List;
    }

    @Override
    public IPage<RoomItemVo> pageItem2(Page<RoomItemVo> roomItemVoPage, Long userId) {
        return roomInfoMapper.pageItem2(roomItemVoPage,userId);
    }

    @Override
    public Boolean getIsOut(Long id) {
        LambdaUpdateWrapper<LeaseAgreement> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(LeaseAgreement::getRoomId,id);
        List<LeaseAgreement> list = service.list(lambdaUpdateWrapper);
        if(list!=null&&!list.isEmpty()){
            for(LeaseAgreement leaseAgreement:list){
                if(leaseAgreement.getStatus()== LeaseStatus.SIGNED){
                    return true;
                }
            }
        }
        return false;
    }
}




