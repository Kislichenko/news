package com.kislichenko.news.dto;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AppUserDTO {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
}
