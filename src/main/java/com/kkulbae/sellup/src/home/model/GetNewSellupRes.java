package com.kkulbae.sellup.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetNewSellupRes {
    private String kind;
    private String videoId;
    private String title;
    private String thumbnails;
    private String url;
    private String description;

}
