package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCelebRes {
    private int clbIdx;
    private String name;
    private String description;
    private String job;
    private String profileImage;
}
