package com.kislichenko.news.services;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.dao.ReqDataRepository;
import com.kislichenko.news.dto.ReqDataDto;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.ReqData;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReqDataService {
    ReqDataRepository reqDataRepository;
    AppUserRepository appUserRepository;

    ModelMapper modelMapper = new ModelMapper();

    public ReqDataService(ReqDataRepository reqDataRepository,
                          AppUserRepository appUserRepository) {
        this.reqDataRepository = reqDataRepository;
        this.appUserRepository = appUserRepository;
    }

    public void addNewRequest(ReqDataDto reqDataDto) {
        ReqData reqData = modelMapper.map(reqDataDto, ReqData.class);
        AppUser appUser = appUserRepository.findByUsername(reqDataDto.getCreator());
        reqData.setCreator(appUser);

        reqDataRepository.save(reqData);
    }

    public List<ReqDataDto> getAllRequests() {
        List<ReqData> reqDatas = reqDataRepository.findAll();
        System.out.println(reqDatas.size());
        List<ReqDataDto> reqDataDtos = new ArrayList<>();
        for (ReqData reqData : reqDatas) {
            ReqDataDto reqDataDto = modelMapper.map(reqData, ReqDataDto.class);
            reqDataDto.setCreator(reqData.getCreator().getUsername());
            reqDataDtos.add(reqDataDto);
        }
        return reqDataDtos;
    }


}
