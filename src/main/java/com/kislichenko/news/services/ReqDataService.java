package com.kislichenko.news.services;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.dao.ReqDataRepository;
import com.kislichenko.news.dto.ReqDataDto;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.ReqData;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReqDataService {
    ReqDataRepository reqDataRepository;
    AppUserRepository appUserRepository;

    public ReqDataService(ReqDataRepository reqDataRepository,
                          AppUserRepository appUserRepository){
        this.reqDataRepository = reqDataRepository;
        this.appUserRepository = appUserRepository;
    }

    public void addNewRequest(ReqDataDto reqDataDto){
        System.out.println(reqDataDto.toString());
        ModelMapper modelMapper = new ModelMapper();

        ReqData reqData = modelMapper.map(reqDataDto, ReqData.class);
        AppUser appUser = appUserRepository.findByUsername(reqDataDto.getCreator());
        reqData.setCreator(appUser);

        System.out.println(reqData.toString());
        try {
            reqDataRepository.save(reqData);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
