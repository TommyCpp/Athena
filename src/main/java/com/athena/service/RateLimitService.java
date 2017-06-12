package com.athena.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by Tommy on 2017/6/12.
 */
@Service
public class RateLimitService {
    private int expiredTime;
    private int timeRemain;
    private Calendar calendar;

    public RateLimitService(@Value("${search.limit.expiredtime}") int expiredTime) {
        this.expiredTime = expiredTime;
        this.timeRemain = expiredTime;
        this.calendar = Calendar.getInstance();
    }

    @Scheduled(cron = "0/5 * * * *")
    public void updateExpiredTimeForCache() {
        int currentMinute = calendar.get(Calendar.SECOND);
//        TODO: update the timeRemain

    }

    public int increaseLimit(String userInfo) {

        return 0;
//        TODO: finish
    }

}
