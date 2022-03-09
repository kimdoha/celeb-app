package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Geometry {
    private LatLngLiteral location;
    private Bounds viewport;

    public Geometry() {

    }
}
