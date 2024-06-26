package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.entity.Patient;
import com.example.healthdb.model.request.AddPatientRequest;
import com.example.healthdb.model.request.DeletePatientRequest;
import com.example.healthdb.service.PatientService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Resource
    private PatientService patientService;

    @PostMapping("/add")
    public BaseResponse<Void> addPatient(@RequestBody AddPatientRequest request)
    {
        patientService.addPatient(request);
        return ResultUtils.success(null);
    }

    @PostMapping("/delete")
    public BaseResponse<Void> deletePatient(@RequestBody DeletePatientRequest request)
    {
        patientService.deletePatient(request);
        return ResultUtils.success(null);
    }

    @GetMapping("/my/{id}")
    public BaseResponse<List<Patient>> queryByMy(@PathVariable Long id)
    {
        return ResultUtils.success(patientService.queryByMy(id));
    }
}
