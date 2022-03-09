package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPlaceListRes {
    private int picIdx;
    private String address;
    private String name;
    private Float rating;
    private Double latitude;
    private Double longitude;
}
