package com.kislichenko.news.dto;

import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.News;
import com.kislichenko.news.entity.ReportageStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReportageDTO {

    private Long id;

    @NotBlank(message = "Info manager is mandatory")
    private String infoManager;
    private String reporter;

    @NotBlank(message = "subject is mandatory")
    private Date startDate;
    private Date endDate;

    private String text;
    private String comment;
    private boolean confirm;

    private ReportageStatus status;
    private Long newsId;
    private String newsArticle;
}

