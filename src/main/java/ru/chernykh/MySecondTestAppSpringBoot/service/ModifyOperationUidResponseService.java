package ru.chernykh.MySecondTestAppSpringBoot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.chernykh.MySecondTestAppSpringBoot.model.Response;

import java.util.UUID;
@Slf4j
@Service
@Qualifier("ModifyOperationUidResponseService")
public class ModifyOperationUidResponseService  implements ModifyResponseService{

    @Override
    public Response modify(Response response){
        log.info("request: {}",response);
        UUID uuid = UUID.randomUUID();

        response.setOperationUid(uuid.toString());

        return response;
    }


}
