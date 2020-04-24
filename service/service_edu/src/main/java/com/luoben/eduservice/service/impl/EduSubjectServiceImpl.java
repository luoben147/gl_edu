package com.luoben.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.eduservice.entity.EduSubject;
import com.luoben.eduservice.vo.ExcelSubjectDataVo;
import com.luoben.eduservice.vo.OneSubject;
import com.luoben.eduservice.vo.TwoSubject;
import com.luoben.eduservice.listener.SubjectExcelListener;
import com.luoben.eduservice.mapper.EduSubjectMapper;
import com.luoben.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        EasyExcel.read(is, ExcelSubjectDataVo.class, new SubjectExcelListener(subjectService)).sheet().doRead();
    }

    /**
     * 树形课程分类列表
     * @return
     */
    @Override
    public List<OneSubject> getAllOneAndTowSubject() {
        //查询所有一级分类 parentId =0
        QueryWrapper<EduSubject> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = super.baseMapper.selectList(wrapperOne);
        //查询所有二级分类 parentId !=0
        QueryWrapper<EduSubject> wrapperTwo=new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = super.baseMapper.selectList(wrapperTwo);

        //封装数据
        List<OneSubject> result=new ArrayList<>();
        for (int i = 0; i <oneSubjectList.size() ; i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            //一级分类
            OneSubject oneSubject=new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);

            //二级分类
            List<TwoSubject> twoFinalSubject=new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject two = twoSubjectList.get(j);
                if(two.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject=new TwoSubject();
                    BeanUtils.copyProperties(two,twoSubject);
                    twoFinalSubject.add(twoSubject);
                }
            }

            oneSubject.setChildren(twoFinalSubject);
            result.add(oneSubject);
        }

        return result;
    }
}
