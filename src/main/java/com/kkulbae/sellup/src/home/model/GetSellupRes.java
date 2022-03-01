package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSellupRes {
    private int slpIdx;
    private String name;
    private String description;
    private String job;
    private String profileImage;
}
