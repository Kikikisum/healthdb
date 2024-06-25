package com.example.healthdb.handler;


import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.utils.ResultUtils;
import io.micrometer.core.instrument.config.validate.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.healthdb.common.ErrorCode.*;

/**
 * 全局异常处理器
 *
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @InitBinder
    public void handleInitBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

    /**
     * 处理参数验证异常
     *
     * @param e exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(value = {
            BindException.class,
            ValidationException.class
    })
    public BaseResponse<String> handleParameterVerificationException(Exception e) {

        List<String> exceptionMsg = new ArrayList<>();

        log.error("Exception: {}", e.getMessage());

        if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            bindingResult.getAllErrors()
                    .forEach(a -> exceptionMsg.add(a.getDefaultMessage()));
        } else if (e instanceof ConstraintViolationException) {
            if (e.getMessage() != null) {
                exceptionMsg.add(e.getMessage());
            }
        } else {
            exceptionMsg.add("invalid parameter");
        }

        return ResultUtils.error(PARAMS_ERROR, String.join(",", exceptionMsg));
    }


    /**
     * 处理业务异常
     *
     * @param businessException business exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<BusinessException> processBusinessException(BusinessException businessException) {

        log.error("ResponseCode：{},Exception: {}", businessException.getCode(), businessException.getDescription());
        businessException.printStackTrace();
        if (businessException.getCode() == NOT_SET_PASSWORD.getCode()||businessException.getCode() == NOT_WX_REGISTER_ERROR.getCode()) {
            return ResultUtils.error(businessException.getCode(),businessException.getMessage(), businessException.getDescription());
        }
        return ResultUtils.error(PARAMS_ERROR, businessException.getDescription());
    }


    /**
     * 处理其他异常
     *
     * @param exception exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public BaseResponse<Exception> processException(Exception exception) {
        log.error("Exception: {}", exception.getLocalizedMessage());

        // 这里可以屏蔽掉后台的异常栈信息，直接返回"server error"
        return ResultUtils.error(PARAMS_ERROR, exception.getLocalizedMessage());
    }
}