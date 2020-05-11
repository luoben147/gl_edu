package com.luoben.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luoben.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-04-21
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String,Object> pageListWeb(Page<EduTeacher> pageParam);
}
