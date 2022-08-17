package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyNewUserResponse {

    private String date;
    private long student;
    private long instructor;
}
