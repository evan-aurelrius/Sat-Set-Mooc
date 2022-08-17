package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AdminSummaryResponse {

    private String name;
    private long amount;

}
