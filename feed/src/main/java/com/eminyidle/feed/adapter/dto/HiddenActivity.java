package com.eminyidle.feed.adapter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HiddenActivity {
    private String placeId;
    private Integer tourDay;
    private String tourPlaceId;
    private String activity;
}
