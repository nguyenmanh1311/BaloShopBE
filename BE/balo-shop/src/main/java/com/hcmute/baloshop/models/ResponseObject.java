package com.hcmute.baloshop.models;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseObject {
  private HttpStatus status;
  private String message;
  private Object data;

  public ResponseObject(HttpStatus status, String message, Object data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public ResponseObject(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public ResponseObject() {
  }

}
