package ru.chernykh.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.chernykh.MySecondTestAppSpringBoot.model.Response;


@Service
public interface ModifyResponseService {

    Response modify(Response response);
}
