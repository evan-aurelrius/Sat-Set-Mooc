package com.satset.mooc.service;

import com.satset.mooc.model.DailyNewUser;
import com.satset.mooc.model.response.DailyNewUserResponse;
import com.satset.mooc.repository.DailyNewUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

@Service
public class DailyNewUserServiceImpl implements DailyNewUserService{

    @Autowired
    DailyNewUserRepository dailyNewUserRepository;

    @Override
    public LinkedList<DailyNewUserResponse> getDailyNewUser() {
        LinkedList<DailyNewUserResponse> lst = new LinkedList<>();
        LinkedList<DailyNewUser> rawLst;
        DailyNewUser latestDailyNewUser = dailyNewUserRepository.findFirstByOrderByDateDesc();

        long difference = countDaysDifferent(latestDailyNewUser.getDate().toLocalDate());
        rawLst = dailyNewUserRepository.findAllByOrderByDateAsc();

        if(difference==0) {                     // latest is today
            for(DailyNewUser d:rawLst)
                lst.add(new DailyNewUserResponse(getString(d.getDate()),d.getNew_student(),d.getNew_instructor()));

        } else {
            Date date;
            DailyNewUser temp;
            LinkedList<DailyNewUser> newLst = new LinkedList<>();
            int i;
            for (i=0; i<difference; i++) {
                date = new Date(System.currentTimeMillis()- (long) i * 24 * 60 * 60 * 1000);
                lst.addFirst(new DailyNewUserResponse(getString(date),0L,0L));
                newLst.addFirst(new DailyNewUser(date));
                rawLst.pollFirst();
            }
            while(i<7) {
                temp = rawLst.pollLast();
                assert temp != null;
                lst.addFirst(new DailyNewUserResponse(getString(temp.getDate()),temp.getNew_student(), temp.getNew_instructor()));
                newLst.addFirst(temp);
                i++;
            }
            saveAllChanges(newLst);
        }

        return lst;
    }

    @Override
    public Long countDaysDifferent(LocalDate latestData) {
        return ChronoUnit.DAYS.between(latestData, LocalDate.now());
    }

    @Override
    public void setDailyNewUser(Boolean isStudent, Boolean isIncrement) {
        DailyNewUser latestDailyNewUser = dailyNewUserRepository.findFirstByOrderByDateDesc();
        long difference = countDaysDifferent(latestDailyNewUser.getDate().toLocalDate());
        if(difference==0) {
            if(Boolean.TRUE.equals(isStudent))
                latestDailyNewUser.setNew_student(latestDailyNewUser.getNew_student()+1);
            else if(Boolean.TRUE.equals(isIncrement))
                latestDailyNewUser.setNew_instructor(latestDailyNewUser.getNew_instructor()+1);
            else
                latestDailyNewUser.setNew_instructor(latestDailyNewUser.getNew_instructor()-1);
            dailyNewUserRepository.save(latestDailyNewUser);
        } else {
            Date date;
            DailyNewUser temp;
            LinkedList<DailyNewUser> rawLst = dailyNewUserRepository.findAllByOrderByDateAsc();
            LinkedList<DailyNewUser> newLst = new LinkedList<>();
            int i;
            for (i=0; i<difference; i++) {
                date = new Date(System.currentTimeMillis()- (long) i * 24 * 60 * 60 * 1000);
                newLst.addFirst(new DailyNewUser(date));
                rawLst.pollFirst();
            }
            while(i<7) {
                temp = rawLst.pollLast();
                assert temp != null;
                newLst.addFirst(temp);
                i++;
            }
            if(Boolean.TRUE.equals(isStudent))
                newLst.getLast().setNew_student(newLst.getLast().getNew_student()+1);
            else if(Boolean.TRUE.equals(isIncrement))
                newLst.getLast().setNew_instructor(newLst.getLast().getNew_instructor()+1);
            else
                newLst.getLast().setNew_instructor(newLst.getLast().getNew_instructor()+1);
            saveAllChanges(newLst);
        }

    }

    String getString(Date date){
        String tmp = date.toString();
        tmp = tmp.substring(8)+tmp.substring(4,8)+tmp.substring(0,4);
        return tmp;
    }

    void saveAllChanges(LinkedList<DailyNewUser> newList) {
        dailyNewUserRepository.deleteAll();
        dailyNewUserRepository.saveAll(newList);
    }

}
