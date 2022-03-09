package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostPlaceReq {
    private String address;
    private String name;
    private Float rating;
    private Double lat;
    private Double lng;
}
