package com.example.healthdb.model.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.PackagePrivate;

import java.util.Date;

/**
 * @author xwb
 */

@Data
public class AddOrdersRequest {
    private Integer uid;

    private Integer hid;

    private Integer pid;

    private String clinicTime;

    private String telephone;

    private String requirement;

    private Integer isFinished;
}
