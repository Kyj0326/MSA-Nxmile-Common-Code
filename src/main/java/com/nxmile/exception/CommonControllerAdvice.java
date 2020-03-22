package com.nxmile.exception;

import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class CommonControllerAdvice extends ResponseEntityExceptionHandler {

  private static final String LOGREF_NORMAL = "error";

  private static final String LOGREF_BIZ = "biz-error";

  @ExceptionHandler(CommonNotFoundException.class)
  public ResponseEntity<VndErrors> commonNotFoundException(CommonNotFoundException e) {
    return new ResponseEntity(new VndErrors(LOGREF_NORMAL, e.getMessage()),HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(GroupCodeNotFoundException.class)
  public ResponseEntity<VndErrors> groupNotFoundException(GroupCodeNotFoundException e) {
    return new ResponseEntity(new VndErrors(LOGREF_NORMAL, e.getMessage()),HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CommonCodeValidationException.class)
  public ResponseEntity<VndErrors> CommonCodeValidationException(CommonCodeValidationException e) {
    return new ResponseEntity(new VndErrors(LOGREF_BIZ, e.getMessage()),HttpStatus.BAD_REQUEST);
  }

}