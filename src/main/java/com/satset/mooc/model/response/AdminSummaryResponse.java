package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminSummaryResponse {

    private String name;
    private long amount;

}
