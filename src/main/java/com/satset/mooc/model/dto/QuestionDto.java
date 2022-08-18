package com.satset.mooc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    private String question;

    private String opt_true;

    private List<String> opt = new LinkedList<>();

}
