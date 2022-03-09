package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTotalPlaceInfoRes {
    private String address;
    private Double lat;
    private Double lng;
    private String name;
    private Float rating;
}
