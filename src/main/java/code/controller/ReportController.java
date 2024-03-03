package code.controller;

import code.service.impl.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/generateReport")
    public void generateReport(HttpServletResponse response) {
        try {
            JasperPrint jasperPrint = reportService.exportReport();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=myReport.pdf");

            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@PostMapping("/generateReportExcel")
    public void generateReportExcel(HttpServletResponse response) throws IOException {
        try {

            JasperPrint jasperPrint = reportService.exportReport(response);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "myReport.xls");


            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            exporter.exportReport();

            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to generate Excel report");
        }
    }*/
}
