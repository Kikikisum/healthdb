package com.example.healthdb.model.request;

import lombok.Data;

/**
 * @author xwb
 */

@Data
public class UpdateOrdersRequest {
    private Integer id;

    private Integer status;
}
