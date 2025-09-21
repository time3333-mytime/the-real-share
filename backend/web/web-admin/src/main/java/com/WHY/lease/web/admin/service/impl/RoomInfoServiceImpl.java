package com.WHY.lease.web.admin.service.impl;

import com.WHY.lease.model.entity.*;
import com.WHY.lease.model.enums.ItemType;
import com.WHY.lease.web.admin.mapper.*;
import com.WHY.lease.web.admin.service.*;
import com.WHY.lease.web.admin.vo.attr.AttrValueVo;
import com.WHY.lease.web.admin.vo.graph.GraphVo;
import com.WHY.lease.web.admin.vo.room.RoomDetailVo;
import com.WHY.lease.web.admin.vo.room.RoomItemVo;
import com.WHY.lease.web.admin.vo.room.RoomQueryVo;
import com.WHY.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private RoomInfoMapper mapper;

    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private RoomFacilityService roomFacilityService;
    @Autowired
    private RoomLabelService roomLabelService;
    @Autowired
    private RoomAttrValueService roomAttrValueService;

    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;

    @Autowired
    private RoomLeaseTermService roomLeaseTermService;

    @Autowired
    private GraphInfoMapper graphInfoMapper;


    @Override
    public void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo) {
        boolean isUpdate = roomSubmitVo.getId() != null;
        super.saveOrUpdate(roomSubmitVo);
        if(isUpdate){
            LambdaQueryWrapper<GraphInfo> graphQueryWrapper=new LambdaQueryWrapper<>();
            graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
            graphQueryWrapper.eq(GraphInfo::getItemId,roomSubmitVo.getId());
            graphInfoService.remove(graphQueryWrapper);

            LambdaQueryWrapper<RoomFacility>apartmentFacilityLambdaQueryWrapper=new LambdaQueryWrapper<>();
            apartmentFacilityLambdaQueryWrapper.eq(RoomFacility::getRoomId,roomSubmitVo.getId());
            roomFacilityService.remove(apartmentFacilityLambdaQueryWrapper);

            LambdaQueryWrapper<RoomLabel>apartmentLabelLambdaQueryWrapper=new LambdaQueryWrapper<>();
            apartmentLabelLambdaQueryWrapper.eq(RoomLabel::getRoomId,roomSubmitVo.getId());
            roomLabelService.remove(apartmentLabelLambdaQueryWrapper);

            LambdaQueryWrapper<RoomAttrValue> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RoomAttrValue::getRoomId,roomSubmitVo.getId());
            roomAttrValueService.remove(lambdaQueryWrapper);

            LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeLambdaQueryWrapper=new LambdaQueryWrapper<>();
            roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId,roomSubmitVo.getId());
            roomPaymentTypeService.remove(roomPaymentTypeLambdaQueryWrapper);

            LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper=new LambdaQueryWrapper<>();
            roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId,roomSubmitVo.getId());
            roomLeaseTermService.remove(roomLeaseTermLambdaQueryWrapper);
        }

        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();
        if(graphVoList!=null&&!graphVoList.isEmpty()) {
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.ROOM);
                graphInfo.setItemId(roomSubmitVo.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
        if(facilityInfoIds!=null&&!facilityInfoIds.isEmpty()){
            ArrayList<RoomFacility> facilityInfos = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds) {
                RoomFacility roomFacility = RoomFacility.builder().roomId(roomSubmitVo.getId()).facilityId(facilityInfoId).build();
                facilityInfos.add(roomFacility);
            }
            roomFacilityService.saveBatch(facilityInfos);
        }


        List<Long> labelIds = roomSubmitVo.getLabelInfoIds();
        if(labelIds!=null&&!labelIds.isEmpty()){
            ArrayList<RoomLabel> apartmentLabels = new ArrayList<>();
            for (Long labelId : labelIds) {
                RoomLabel roomLabel = RoomLabel.builder().labelId(labelId).roomId(roomSubmitVo.getId()).build();
                apartmentLabels.add(roomLabel);
            }
            roomLabelService.saveBatch(apartmentLabels);
        }

        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();
        if(attrValueIds!=null&&!attrValueIds.isEmpty()){
            ArrayList<RoomAttrValue> roomAttrValues= new ArrayList<>();
            for (Long attrValueId : attrValueIds) {
                RoomAttrValue roomAttrValue=RoomAttrValue.builder().roomId(roomSubmitVo.getId()).attrValueId(attrValueId).build();
                roomAttrValues.add(roomAttrValue);
            }
            roomAttrValueService.saveBatch(roomAttrValues);
        }
        //5.保存新的paymentTypeList
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
        if (!CollectionUtils.isEmpty(paymentTypeIds)) {
            ArrayList<RoomPaymentType> roomPaymentTypeList = new ArrayList<>();
            for (Long paymentTypeId : paymentTypeIds) {
                RoomPaymentType roomPaymentType = RoomPaymentType.builder().roomId(roomSubmitVo.getId()).paymentTypeId(paymentTypeId).build();
                roomPaymentTypeList.add(roomPaymentType);
            }
            roomPaymentTypeService.saveBatch(roomPaymentTypeList);
        }


        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
        if(leaseTermIds!=null&&!leaseTermIds.isEmpty()) {
            ArrayList<RoomLeaseTerm> roomLeaseTerms = new ArrayList<>();
            for (Long leaseTermId : leaseTermIds) {
                RoomLeaseTerm roomLeaseTerm=RoomLeaseTerm.builder().roomId(roomSubmitVo.getId()).leaseTermId(leaseTermId).build();
                roomLeaseTerms.add(roomLeaseTerm);
            }
            roomLeaseTermService.saveBatch(roomLeaseTerms);
        }
    }

    @Override
    public IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo) {
        return mapper.pageItem(page,queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {
        RoomInfo roomInfo = mapper.selectById(id);

        ApartmentInfo apartmentInfo=apartmentInfoMapper.selectById(roomInfo.getApartmentId());

        List<GraphVo>graphVoList=  graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM,id);

        List<LabelInfo> labelInfoList=labelInfoMapper.selectListByRoomId(id);

        List<FacilityInfo> facilityInfoList=facilityInfoMapper.selectListByRoomId(id);

        List<LeaseTerm> leaseTermList=leaseTermMapper.selectListByApartmentId(id);

        List<AttrValueVo> attrValueVoList=attrValueMapper.selectListByApartmentId(id);

        List<PaymentType> paymentTypeList = paymentTypeMapper.selectListByRoomId(id);

        RoomDetailVo adminRoomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo, adminRoomDetailVo);

        adminRoomDetailVo.setApartmentInfo(apartmentInfo);
        adminRoomDetailVo.setGraphVoList(graphVoList);
        adminRoomDetailVo.setAttrValueVoList(attrValueVoList);
        adminRoomDetailVo.setFacilityInfoList(facilityInfoList);
        adminRoomDetailVo.setLabelInfoList(labelInfoList);
        adminRoomDetailVo.setPaymentTypeList(paymentTypeList);
        adminRoomDetailVo.setLeaseTermList(leaseTermList);

        return adminRoomDetailVo;
    }

    @Override
    public void removeByRoomId(Long id) {
        super.removeById(id);

        LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
        graphQueryWrapper.eq(GraphInfo::getItemId, id);
        graphInfoService.remove(graphQueryWrapper);

        LambdaQueryWrapper<RoomAttrValue> attrQueryWrapper = new LambdaQueryWrapper<>();
        attrQueryWrapper.eq(RoomAttrValue::getRoomId, id);
        roomAttrValueService.remove(attrQueryWrapper);

        LambdaQueryWrapper<RoomFacility> facilityQueryWrapper = new LambdaQueryWrapper<>();
        facilityQueryWrapper.eq(RoomFacility::getRoomId, id);
        roomFacilityService.remove(facilityQueryWrapper);

        LambdaQueryWrapper<RoomLabel> labelQueryWrapper = new LambdaQueryWrapper<>();
        labelQueryWrapper.eq(RoomLabel::getRoomId, id);
        roomLabelService.remove(labelQueryWrapper);

        LambdaQueryWrapper<RoomPaymentType> paymentQueryWrapper = new LambdaQueryWrapper<>();
        paymentQueryWrapper.eq(RoomPaymentType::getRoomId, id);
        roomPaymentTypeService.remove(paymentQueryWrapper);

        LambdaQueryWrapper<RoomLeaseTerm> termQueryWrapper = new LambdaQueryWrapper<>();
        termQueryWrapper.eq(RoomLeaseTerm::getRoomId, id);
        roomLeaseTermService.remove(termQueryWrapper);
    }


}




