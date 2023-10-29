package ru.chernykh.MySecondTestAppSpringBoot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.chernykh.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.chernykh.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.chernykh.MySecondTestAppSpringBoot.model.*;
import ru.chernykh.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.chernykh.MySecondTestAppSpringBoot.service.ValidationService;
import ru.chernykh.MySecondTestAppSpringBoot.util.DateTimeUtil;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;

    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService){
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }



    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid  @RequestBody Request request, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error("Ошибка валидации: {}", objectError.getDefaultMessage());
            });
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        log.info("request: {}",request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomerFormat().format(new Date()))
                .systemName(request.getSystemName()) // установка systemName
                .code(Codes.SUCCES)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("Response: {}", response);
        if ("123".equals(request.getUid())) {
            throw new UnsupportedCodeException("Ошибка: недопустимый uid. Код ошибки: 01");
        }
        try {
            validationService.isValid(bindingResult);
        }catch (ValidationFailedException e){
            log.error("Ошибка: ", e);
            log.info("Response: {}", response);
            response.setCode(Codes.FAILED);
            log.info("Response: {}", response);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            log.info("Response: {}", response);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Response: {}", response);
            return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Ошибка: ", e);
            log.info("Response: {}", response);
            response.setCode(Codes.FAILED);
            log.info("Response: {}", response);
            response.setErrorCode(ErrorCodes.UNKNOWNE_EXCEPTION);
            log.info("Response: {}", response);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Response: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        modifyResponseService.modify(response);




        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
