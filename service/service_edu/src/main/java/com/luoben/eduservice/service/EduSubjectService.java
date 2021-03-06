package com.luoben.eduservice.service;

import com.luoben.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luoben.eduservice.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-04-23
 */
public interface EduSubjectService extends IService<EduSubject> {
    /**
     * 添加课程分类
     * @param file
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService) throws Exception;

    /**
     * 课程分类列表（树形）
     * @return
     */
    List<OneSubject> getAllOneAndTowSubject();
}
