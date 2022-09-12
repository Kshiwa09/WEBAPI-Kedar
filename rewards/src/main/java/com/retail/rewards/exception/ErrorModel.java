package com.retail.rewards.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorModel {
    private String message;
    private String errorCode;
    private String fieldName;

}