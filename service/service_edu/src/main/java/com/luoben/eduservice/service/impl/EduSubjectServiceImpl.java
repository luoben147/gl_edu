package com.luoben.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.eduservice.entity.EduSubject;
import com.luoben.eduservice.entity.excel.SubjectData;
import com.luoben.eduservice.listener.SubjectExcelListener;
import com.luoben.eduservice.mapper.EduSubjectMapper;
import com.luoben.eduservice.service.EduSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author luoben
 * @since 2020-04-23
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     *
     * @param file
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) throws Exception {
        InputStream is = file.getInputStream();
        EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
    }
}
