package com.codewithniteesh.blog.exceptions;

public class DuplicateEntryException extends RuntimeException{
    private String fieldName;
    private String fieldValue;

    public DuplicateEntryException(String fieldName, String fieldValue) {
        super(String.format("%s: %s already Exist in Database, Please use another %s", fieldName, fieldValue, fieldName));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
