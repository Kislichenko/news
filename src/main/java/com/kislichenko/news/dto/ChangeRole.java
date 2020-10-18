package com.kislichenko.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ChangeRole {
    private String username;
    private List<String> roles;
}
