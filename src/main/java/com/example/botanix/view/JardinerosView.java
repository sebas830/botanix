package com.example.botanix.view;

import com.example.botanix.model.Jardineros;
import com.example.botanix.repository.JardinerosRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class JardinerosView
{
    @Autowired
    private JardinerosRepository jardinerosRepository;

    @GetMapping("/view/jardineros")
    public String lista(Model model)
    {
        model.addAttribute("jardineros", jardinerosRepository.findAll());
        return "jardineros/list";
    }

    @GetMapping("/view/jardineros/form")
    public String form(Model model)
    {
        model.addAttribute("jardineros", new Jardineros());
        return "jardineros/form";
    }

    @PostMapping("/view/jardineros/save")
    public String save(@ModelAttribute Jardineros jardineros, RedirectAttributes ra)
    {
        jardinerosRepository.save(jardineros);
        ra.addFlashAttribute("message", "Jardinero guardado con éxito");
        return "redirect:/view/jardineros";
    }

    @GetMapping("/view/jardineros/edit/{id}")
    public String edit(@PathVariable Long id, Model model)
    {
        Jardineros jardineros = jardinerosRepository.findById(id).orElse(null);
        model.addAttribute("jardineros", jardineros);
        return "jardineros/form";
    }

    @PostMapping("/view/jardineros/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra)
    {
        jardinerosRepository.deleteById(id);
        ra.addFlashAttribute("message", "Jardinero eliminado con éxito");
        return "redirect:/view/jardineros";
    }

    @GetMapping("/view/jardineros/pdf")
    public void exportarPDF(HttpServletResponse response) throws Exception
    {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Jardineros.pdf");

        List<Jardineros> jardinerosLista = jardinerosRepository.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Listado de Jardineros"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Columnas
        table.addCell("ID Jardinero");
        table.addCell("Nombre");
        table.addCell("Documento");
        table.addCell("Dirección");
        table.addCell("Rol");

        // Filas
        for (Jardineros j : jardinerosLista)
        {
            table.addCell(j.getId_jardinero().toString());
            table.addCell(j.getNombre());
            table.addCell(j.getDocumento());
            table.addCell(j.getDireccion());
            table.addCell(j.getRol());
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/view/jardineros/excel")
    public void exportarExcel(HttpServletResponse response) throws Exception
    {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Jardineros.xlsx");

        List<Jardineros> jardinerosLista = jardinerosRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Jardineros");

        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Jardinero");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Documento");
        headerRow.createCell(3).setCellValue("Dirección");
        headerRow.createCell(4).setCellValue("Rol");

        // Agregar datos
        int rowNum = 1;

        for (Jardineros j : jardinerosLista)
        {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(j.getId_jardinero());
            row.createCell(1).setCellValue(j.getNombre());
            row.createCell(2).setCellValue(j.getDocumento());
            row.createCell(3).setCellValue(j.getDireccion());
            row.createCell(4).setCellValue(j.getRol());
        }

        // Autoajustar columnas
        for (int i = 0; i < 5; i++)
        {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}

