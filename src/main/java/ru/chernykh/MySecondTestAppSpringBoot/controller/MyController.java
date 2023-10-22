package ru.chernykh.MySecondTestAppSpringBoot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.chernykh.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.chernykh.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.chernykh.MySecondTestAppSpringBoot.model.Request;
import ru.chernykh.MySecondTestAppSpringBoot.model.Response;
import ru.chernykh.MySecondTestAppSpringBoot.service.ValidationService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;

@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService){
        this.validationService = validationService;
    }



    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid  @RequestBody Request request, BindingResult bindingResult){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();
        if ("123".equals(request.getUid())) {
            throw new UnsupportedCodeException("Ошибка: недопустимый uid. Код ошибки: 01");
        }
        try {
            validationService.isValid(bindingResult);
        }catch (ValidationFailedException e){
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошлошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }




        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
