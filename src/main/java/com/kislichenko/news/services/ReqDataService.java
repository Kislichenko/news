package com.kislichenko.news.services;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.dao.PriceRepository;
import com.kislichenko.news.dao.ReqDataRepository;
import com.kislichenko.news.dto.ReqDataDTO;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.Price;
import com.kislichenko.news.entity.ReqData;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public ReqDataDTO getRequestById(Long id) {
        if (reqDataRepository.findById(id).isPresent()) {
            ReqData reqData = reqDataRepository.findById(id).get();
            ReqDataDTO reqDataDto = modelMapper.map(reqData, ReqDataDTO.class);
            reqDataDto.setCreator(reqData.getCreator().getUsername());
            return reqDataDto;
        } else {
            return null;
        }
    }

    public void addNewRequest(ReqDataDTO reqDataDto) {
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

    public List<ReqDataDTO> getAllRequests() {
        List<ReqData> reqDatas = reqDataRepository.findAll();
        List<ReqDataDTO> reqDataDtos = new ArrayList<>();
        for (ReqData reqData : reqDatas) {
            ReqDataDTO reqDataDto = modelMapper.map(reqData, ReqDataDTO.class);
            reqDataDto.setCreator(reqData.getCreator().getUsername());
            reqDataDtos.add(reqDataDto);
        }

        return reqDataDtos;
    }
}
