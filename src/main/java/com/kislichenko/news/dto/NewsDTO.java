package com.kislichenko.news.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewsDTO {
    private Long id;

    @NotBlank(message = "article is mandatory")
    private String article;

    private String body;
    private boolean bad;
    private boolean realization;
    private String infoManager;
}
