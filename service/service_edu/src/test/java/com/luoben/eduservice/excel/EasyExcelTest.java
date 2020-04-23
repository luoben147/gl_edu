package com.luoben.eduservice.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class EasyExcelTest {

    //excel写操作
    @Test
    void excelWrite() {
        //1。设置写入文件夹地址和excel文件名
        String filename="E:\\write.xlsx";
        //2.调用easyexcel 方法实现写操作
        EasyExcel.write(filename,DemoData.class)
                .sheet("学生列表")
                .doWrite(getData());
    }

    //excel读
    @Test
    void excelRead(){
        String filename="E:\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }


    private List<DemoData> getData(){
        List<DemoData> list=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            DemoData data=new DemoData();
            data.setSno(i);
            data.setSname("Luck"+i);
            list.add(data);
        }
        return  list;
    }
}
