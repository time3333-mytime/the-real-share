package com.WHY.lease.web.admin.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduleTasksTest {
    @Autowired
    private  ScheduleTasks scheduleTasks;
    @Test
    public  void test(){
        scheduleTasks.checkLeaseStatus();
    }
}