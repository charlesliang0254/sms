package cn.edu.chd.sms.controller;

import cn.edu.chd.sms.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalController.class);
    @ExceptionHandler(RuntimeException.class)
    public JsonResult doHandlerRuntimeException(RuntimeException e) {
        e.printStackTrace();
        LOGGER.debug("调用GlobalController");
        return new JsonResult(e);
    }
}