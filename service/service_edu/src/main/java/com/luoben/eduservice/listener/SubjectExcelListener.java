package com.luoben.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luoben.eduservice.entity.EduSubject;
import com.luoben.eduservice.entity.excel.SubjectData;
import com.luoben.eduservice.service.EduSubjectService;
import com.luoben.servicebase.exceptionhandler.MyException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


    public EduSubjectService subjectService;

    public SubjectExcelListener(){}

    public SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService=subjectService;
    }

    //读取excel内容，一行一行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new MyException(20001,"文件数据为空");
        }

        //每行读取有两个值，第一个是一级分类，第二个是二级分类
        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(existOneSubject==null){ //没有相同的一级分类，进行添加
            existOneSubject=new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }


        //获取一级分类Id
        String pid=existOneSubject.getId();
        //判断二级分类是否重复
        EduSubject existTowSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
        if(existTowSubject==null){ //没有相同的一级分类，进行添加
            existTowSubject=new EduSubject();
            existTowSubject.setParentId(pid);
            existTowSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTowSubject);
        }

    }

    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
}
