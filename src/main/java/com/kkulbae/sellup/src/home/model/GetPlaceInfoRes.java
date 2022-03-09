package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
@AllArgsConstructor
public class GetPlaceInfoRes {
    private List<Place> candidates;
    private String status;

    public GetPlaceInfoRes() {

    }

//    private String status;
//    private Candidate[] candidates;


//    static class Candidate {
//        public String formatted_address;
//        public Json geometry;
//        public Json viewport;
//        public String name;
//        public int rating;
//    }
}

