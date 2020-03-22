package com.nxmile.exception;

import lombok.Getter;

@Getter
public class CommonNotFoundException extends RuntimeException {

  private String commonCode;
  private String arg;

  public CommonNotFoundException(String commonCode, String arg) {
    super("Common code could not be found with commonCode: " + commonCode + " arg : " + arg);
    this.commonCode = commonCode;
    this.arg = arg;
  }

}