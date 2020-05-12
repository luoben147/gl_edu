package com.luoben.order.client;

import com.luoben.commonutils.vo.CourseWebVoOrder;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {

    @GetMapping("/eduservice/courseApi/getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable("courseId") String courseId);
}
