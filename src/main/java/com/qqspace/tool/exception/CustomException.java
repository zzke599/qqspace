package com.qqspace.tool.exception;
/**
* @author zzke
* @ClassName
* @Description  系统自定义异常类
*/
public class CustomException extends Exception {
	 //异常信息
    public String message;
    public CustomException(String message){
        super(message);
        this.message = message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }

}
