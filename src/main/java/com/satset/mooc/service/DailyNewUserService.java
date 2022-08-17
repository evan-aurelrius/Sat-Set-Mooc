package com.satset.mooc.service;

import com.satset.mooc.model.response.DailyNewUserResponse;

import java.time.LocalDate;
import java.util.LinkedList;

public interface DailyNewUserService {
    LinkedList<DailyNewUserResponse> getDailyNewUser();

    Long countDaysDifferent(LocalDate latestData);
}
