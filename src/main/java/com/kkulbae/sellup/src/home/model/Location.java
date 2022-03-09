package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Location {
    private Double lat;
    private Double lng;

    public Location() {

    }
}
