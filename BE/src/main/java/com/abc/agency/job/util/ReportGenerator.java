package com.abc.agency.job.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportGenerator {

    /**
     * This method use to generate PDF report from provided parameters.
     *
     * @param data
     * @param params
     * @param reportName
     * @return
     */
    public byte[] generatePDFReport(List<?> data, Map<String, Object> params, String reportName) {
        try {
            File file = ResourceUtils.getFile(Constants.REPORT_DIRECTORY + reportName);
            HashMap<String, Object> parameters = new HashMap<>();
            if (null != params) {
                parameters.putAll(params);
            }
            parameters.put(Constants.REPORT_PARAM_SUBREPORT_DIRECTORY, file.getParent());
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(data);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\kashy\\OneDrive\\Desktop\\abc\\New folder\\CV.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}
