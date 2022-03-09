package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Bounds {
    private LatLngLiteral northeast;
    private LatLngLiteral southwest;

    public Bounds() {

    }
}
