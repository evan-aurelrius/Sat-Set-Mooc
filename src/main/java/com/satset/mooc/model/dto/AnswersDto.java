package com.satset.mooc.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class AnswersDto {
    private long user_id;

    @JsonProperty("answers")
    private List<String> answers = new LinkedList<>();

}