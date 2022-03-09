package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Place {
        private String formatted_address;
        private Geometry geometry;
        private String name;
        private Float rating;

        public Place() {

        }
}
