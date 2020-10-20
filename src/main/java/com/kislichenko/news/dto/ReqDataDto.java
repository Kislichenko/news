package com.kislichenko.news.dto;

import com.kislichenko.news.entity.AdBlockTypes;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.ReqData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReqDataDto {
    @NotBlank(message = "subject is mandatory")
    private String subject;
    private Date startDate;
    private Date endDate;

    @NotBlank(message = "legal data is mandatory")
    private String legalData;
    private Date creationDate;
    private AdBlockTypes type;

    @NotBlank(message = "creatir username is mandatory")
    private String creator;
}

