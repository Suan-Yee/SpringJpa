package code.dao.impl;

import code.dao.StudentDao;
import code.entity.Student;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final StudentDao studentDao;

    public JasperPrint exportReport(HttpServletResponse response) throws JRException, IOException {
        List<Student> studentList = studentDao.findAllStudent();

        InputStream templateStream = getClass().getResourceAsStream("/students.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentList);
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("created","student");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);

        /*JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());*/
        return jasperPrint;
    }
}
