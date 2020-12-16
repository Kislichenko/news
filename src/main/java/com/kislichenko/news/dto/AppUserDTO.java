package com.kislichenko.news.dto;

import lombok.*;

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
