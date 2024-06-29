package com.example.healthdb.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.healthdb.model.dto.HospitalCreateDTO;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HospitalListener extends AnalysisEventListener<HospitalCreateDTO> {

    private List<HospitalCreateDTO> hospitalCreateDTOs = new ArrayList<>();

    @Override
    public void invoke(HospitalCreateDTO data, AnalysisContext context) {
        hospitalCreateDTOs.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 所有数据读取完成后调用，可以在这里处理业务逻辑
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        // 发生异常时调用，可以进行异常处理
        exception.printStackTrace();
    }

}
