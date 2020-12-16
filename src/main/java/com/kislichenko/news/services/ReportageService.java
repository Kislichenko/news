package com.kislichenko.news.services;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.dao.ReportageRepository;
import com.kislichenko.news.dto.ReportageDTO;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.News;
import com.kislichenko.news.entity.Reportage;
import com.kislichenko.news.entity.ReportageStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportageService {

    ReportageRepository reportageRepository;
    AppUserRepository appUserRepository;
    ModelMapper modelMapper = new ModelMapper();

    public ReportageService(ReportageRepository reportageRepository,
                            AppUserRepository appUserRepository) {
        this.reportageRepository = reportageRepository;
        this.appUserRepository = appUserRepository;
    }

    public void deleteReportageById(Long id) {
        reportageRepository.deleteById(id);
    }

    public ReportageDTO getReportageById(Long id) {
        if (reportageRepository.findById(id).isPresent()) {
            Reportage reportage = reportageRepository.findById(id).get();
            ReportageDTO reporatgeDTO = modelMapper.map(reportage, ReportageDTO.class);
            reporatgeDTO.setInfoManager(reportage.getInfoManager().getUsername());
            if (reportage.getReporter() != null) {
                reporatgeDTO.setReporter(reportage.getReporter().getUsername());
            }
            return reporatgeDTO;
        } else {
            return null;
        }
    }

    public void addNewReportage(ReportageDTO reporatgeDTO) {
        Reportage reportage = modelMapper.map(reporatgeDTO, Reportage.class);
        AppUser infoManager = appUserRepository.findByUsername(reporatgeDTO.getInfoManager());
        AppUser reporter = appUserRepository.findByUsername(reporatgeDTO.getReporter());
        reportage.setInfoManager(infoManager);
        reportage.setReporter(reporter);
        try {
            reportageRepository.save(reportage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ReportageDTO> getAllReportages() {

        List<Reportage> reportageList = reportageRepository.findAll();
        List<ReportageDTO> reportageDTOS = new ArrayList<>();
        for (Reportage reportage : reportageList) {
            ReportageDTO reportageDTO = modelMapper.map(reportage, ReportageDTO.class);
            reportageDTO.setInfoManager(reportage.getInfoManager().getUsername());
            if (reportage.getReporter() != null) {
                reportageDTO.setReporter(reportage.getReporter().getUsername());
            }
            reportageDTO.setNewsId(reportage.getNews().getId());
            reportageDTO.setNewsArticle(reportage.getNews().getArticle());
            reportageDTOS.add(reportageDTO);
        }
        return reportageDTOS;


    }

    public void createReportageByNews(News news) {
        Reportage reportage = new Reportage();
        reportage.setInfoManager(news.getInfoManager());
        reportage.setNews(news);
        reportage.setStartDate(new Date());
        reportage.setStatus(ReportageStatus.Created);
//        reportage.setText("NaN");
        reportage.setComment("-");
        reportageRepository.save(reportage);
    }
}
