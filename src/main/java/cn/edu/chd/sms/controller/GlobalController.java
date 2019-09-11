package cn.edu.chd.sms.controller;

import cn.edu.chd.sms.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalController {
    @ExceptionHandler(RuntimeException.class)
    public JsonResult doHandlerRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return new JsonResult(e);
    }
}