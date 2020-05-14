package com.luoben.statistics.schedule;

import com.luoben.statistics.service.StatisticsDailyService;
import com.luoben.statistics.utls.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 */
@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService dailyService;

    /**
     * 测试
     * 每天隔5秒 执行一次
     */
/*    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("**************task1执行了");
    }*/

    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.createStatisticsByDay(day);
    }
}
