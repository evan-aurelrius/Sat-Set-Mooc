package com.satset.mooc.model.dto;

import lombok.Data;

@Data
public class LectureDto {

    private String title;
    private String link;

    public LectureDto(String title, String link) {
        this.title = title;
        this.link = link;
    }

}
