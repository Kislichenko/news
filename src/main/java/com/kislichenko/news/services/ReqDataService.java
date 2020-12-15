package com.kislichenko.news.services;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.dao.PriceRepository;
import com.kislichenko.news.dao.ReqDataRepository;
import com.kislichenko.news.dto.ReqDataDto;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.Price;
import com.kislichenko.news.entity.ReqData;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.form.InputTag;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReqDataService {
    ReqDataRepository reqDataRepository;
    AppUserRepository appUserRepository;
    PriceRepository priceRepository;

    ModelMapper modelMapper = new ModelMapper();

    public ReqDataService(ReqDataRepository reqDataRepository,
                          AppUserRepository appUserRepository,
                          PriceRepository priceRepository) {
        this.reqDataRepository = reqDataRepository;
        this.appUserRepository = appUserRepository;
        this.priceRepository = priceRepository;
    }

    public void deleteRequestById(Long id) {
        reqDataRepository.deleteById(id);
    }

    public ReqDataDto getRequestById(Long id) {
        if (reqDataRepository.findById(id).isPresent()) {
            ReqData reqData = reqDataRepository.findById(id).get();
            ReqDataDto reqDataDto = modelMapper.map(reqData, ReqDataDto.class);
            reqDataDto.setCreator(reqData.getCreator().getUsername());
            return reqDataDto;
        } else {
            return null;
        }
    }

    public void addNewRequest(ReqDataDto reqDataDto) {
        ReqData reqData = modelMapper.map(reqDataDto, ReqData.class);
        AppUser appUser = appUserRepository.findByUsername(reqDataDto.getCreator());
        reqData.setCreator(appUser);
        try {
            Price price = priceRepository.findPriceByType(reqData.getType());
            reqData.setCost(price.getCost());
            reqDataRepository.save(reqData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<ReqDataDto> getAllRequests() {
        List<ReqData> reqDatas = reqDataRepository.findAll();
        List<ReqDataDto> reqDataDtos = new ArrayList<>();
        for (ReqData reqData : reqDatas) {
            ReqDataDto reqDataDto = modelMapper.map(reqData, ReqDataDto.class);
            reqDataDto.setCreator(reqData.getCreator().getUsername());
            reqDataDtos.add(reqDataDto);
        }

        return reqDataDtos;
    }


}
