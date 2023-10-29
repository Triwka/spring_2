package ru.chernykh.MySecondTestAppSpringBoot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.chernykh.MySecondTestAppSpringBoot.model.Response;
import ru.chernykh.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;
@Slf4j
@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService  implements  ModifyResponseService{

    @Override
    public Response modify(Response response){
        log.info("request: {}",response);
        response.setSystemTime(DateTimeUtil.getCustomerFormat()
                .format(new Date()));

        return response;
    }

}
