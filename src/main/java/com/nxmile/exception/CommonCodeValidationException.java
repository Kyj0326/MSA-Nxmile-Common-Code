package com.nxmile.exception;


public class CommonCodeValidationException extends RuntimeException  {

    public CommonCodeValidationException(String field, String var) {
        super("It is biz error. field : " + field + ", var : " + var);
    }
}
