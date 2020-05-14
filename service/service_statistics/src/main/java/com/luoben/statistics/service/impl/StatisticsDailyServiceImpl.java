package com.luoben.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.commonutils.R;
import com.luoben.statistics.client.UcenterClient;
import com.luoben.statistics.entity.StatisticsDaily;
import com.luoben.statistics.mapper.StatisticsDailyMapper;
import com.luoben.statistics.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author luoben
 * @since 2020-05-13
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 统计某一天的注册人数
     *
     * @param day
     */
    @Override
    public void createStatisticsByDay(String day) {

        //删除已存在的统计对象    保证每次数据都是最新的
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);

        //获取统计信息
        R registerCount = ucenterClient.registerCount(day);
        Integer registerNum = (Integer) registerCount.getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    /**
     * 获取图表数据
     *
     * @param begin
     * @param end
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated", type);//指定查询的字段
        wrapper.between("date_calculated", begin, end); //区间

        List<StatisticsDaily> dayList = baseMapper.selectList(wrapper);

        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        for (int i = 0; i < dayList.size(); i++) {
            StatisticsDaily daily = dayList.get(i);
            //封装日期list
            dateList.add(daily.getDateCalculated());

            //封装数量list
            switch (type) {
                case "register_num":
                    numList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("dateList", dateList);
        result.put("numList", numList);

        return result;
    }
}
