package com.nxmile.exception;

import lombok.Getter;

@Getter
public class GroupCodeNotFoundException extends RuntimeException{

    private String groupCode;

    public GroupCodeNotFoundException(String groupCode) {
        super("GroupCode could not be found with groupcode : " + groupCode);
        this.groupCode = groupCode;
    }
}
