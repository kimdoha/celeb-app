package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetThemeRes {
    private int thmIdx;
    private String name;
    private String title;
    private String themeUrl;
}
