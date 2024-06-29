package com.example.healthdb.model.request;

import lombok.Data;

/**
 * @author xwb
 */

@Data
public class AddServerTypeRequest {
    private String name;

    private Integer limit;
}
