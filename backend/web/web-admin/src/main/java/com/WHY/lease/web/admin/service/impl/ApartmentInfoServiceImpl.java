package com.WHY.lease.web.admin.service.impl;

import com.WHY.lease.common.exception.LeaseException;
import com.WHY.lease.common.result.ResultCodeEnum;
import com.WHY.lease.model.entity.*;
import com.WHY.lease.model.enums.ItemType;
import com.WHY.lease.web.admin.mapper.*;
import com.WHY.lease.web.admin.service.*;
import com.WHY.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.WHY.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.WHY.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.WHY.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.WHY.lease.web.admin.vo.fee.FeeValueVo;
import com.WHY.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private FeeValueMapper feeValueMapper;

    @Autowired
    private ApartmentInfoMapper mapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;

    @Autowired
    private ApartmentLabelService apartmentLabelService;
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;
    @Autowired
    private ProvinceInfoServiceImpl provinceInfoService;
    @Autowired
    private CityInfoServiceImpl cityInfoService;
    @Autowired
    private DistrictInfoServiceImpl districtInfoService;


    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate = apartmentSubmitVo.getId() != null;
        apartmentSubmitVo.setProvinceName(provinceInfoService.getById(apartmentSubmitVo.getProvinceId()).getName());
        apartmentSubmitVo.setCityName(cityInfoService.getById(apartmentSubmitVo.getCityId()).getName());
        apartmentSubmitVo.setDistrictName(districtInfoService.getById(apartmentSubmitVo.getDistrictId()).getName());
        super.saveOrUpdate(apartmentSubmitVo);
        if(isUpdate){
            LambdaQueryWrapper<GraphInfo>graphQueryWrapper=new LambdaQueryWrapper<>();
            graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            graphQueryWrapper.eq(GraphInfo::getItemId,apartmentSubmitVo.getId());
            graphInfoService.remove(graphQueryWrapper);

            LambdaQueryWrapper<ApartmentFacility>apartmentFacilityLambdaQueryWrapper=new LambdaQueryWrapper<>();
            apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,apartmentSubmitVo.getId());
            apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);

            LambdaQueryWrapper<ApartmentLabel>apartmentLabelLambdaQueryWrapper=new LambdaQueryWrapper<>();
            apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,apartmentSubmitVo.getId());
            apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);

            LambdaQueryWrapper<ApartmentFeeValue>apartmentFeeValueLambdaQueryWrapper=new LambdaQueryWrapper<>();
            apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId,apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);

        }


        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if(graphVoList!=null&&!graphVoList.isEmpty()) {
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }

        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
        if(facilityInfoIds!=null&&!facilityInfoIds.isEmpty()){
            ArrayList<ApartmentFacility> facilityInfos = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds) {
                ApartmentFacility apartmentFacility = new ApartmentFacility();
                apartmentFacility.setApartmentId(apartmentSubmitVo.getId());
                apartmentFacility.setFacilityId(facilityInfoId);
                facilityInfos.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(facilityInfos);
        }

        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if(labelIds!=null&&!labelIds.isEmpty()){
            ArrayList<ApartmentLabel> apartmentLabels = new ArrayList<ApartmentLabel>();
            for (Long labelId : labelIds) {
                ApartmentLabel apartmentLabel = ApartmentLabel.builder().labelId(labelId).apartmentId(apartmentSubmitVo.getId()).build();
                apartmentLabels.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabels);
        }

        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if(feeValueIds!=null&&!feeValueIds.isEmpty()){
            ArrayList<ApartmentFeeValue> apartmentFeeValues = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue build = ApartmentFeeValue.builder().apartmentId(apartmentSubmitVo.getId()).feeValueId(feeValueId).build();
                apartmentFeeValues.add(build);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValues);
        }
    }

    @Override
    public IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> page, ApartmentQueryVo queryVo) {
            return mapper.pageItem(page,queryVo);
    }

    @Override
    public ApartmentDetailVo getDetailById(Long id) {
        ApartmentInfo apartmentInfo = mapper.selectById(id);

        List<GraphVo>graphVoList=  graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT,id);

        List<LabelInfo> labelInfoList=labelInfoMapper.selectListByApartmentId(id);

        List<FacilityInfo> facilityInfoList=facilityInfoMapper.selectListByApartmentId(id);

        List<FeeValueVo> feeValueVoList=feeValueMapper.selectListByApartmentId(id);

        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
        BeanUtils.copyProperties(apartmentInfo,apartmentDetailVo);
        apartmentDetailVo.setGraphVoList(graphVoList);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        apartmentDetailVo.setFeeValueVoList(feeValueVoList);
        apartmentDetailVo.setLabelInfoList(labelInfoList);

        return apartmentDetailVo;
    }

    @Override
    public void removeByApartmentId(Long id) {
        LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId,id);

        long now=roomInfoMapper.selectCount(roomInfoLambdaQueryWrapper);
        if(now>0){
            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
        }
        else{
            super.removeById(id);

        LambdaQueryWrapper<GraphInfo>graphQueryWrapper=new LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
        graphQueryWrapper.eq(GraphInfo::getItemId,id);
        graphInfoService.remove(graphQueryWrapper);

        LambdaQueryWrapper<ApartmentFacility>apartmentFacilityLambdaQueryWrapper=new LambdaQueryWrapper<>();
        apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,id);
        apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);

        LambdaQueryWrapper<ApartmentLabel>apartmentLabelLambdaQueryWrapper=new LambdaQueryWrapper<>();
        apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,id);
        apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);

        LambdaQueryWrapper<ApartmentFeeValue>apartmentFeeValueLambdaQueryWrapper=new LambdaQueryWrapper<>();
        apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId,id);
        apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);
        }
    }
}




