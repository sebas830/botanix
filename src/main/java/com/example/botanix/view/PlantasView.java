package com.example.botanix.view;

import com.example.botanix.model.Plantas;
import com.example.botanix.repository.PlantasRepository;
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
public class PlantasView
{
    @Autowired
    private PlantasRepository plantasRepository;

    @GetMapping("/view/plantas")
    public String lista(Model model)
    {
        model.addAttribute("plantas", plantasRepository.findAll());
        return "plantas/list";
    }

    @GetMapping("/view/plantas/form")
    public String form(Model model)
    {
        model.addAttribute("plantas", new Plantas());
        return "plantas/form";
    }

    @PostMapping("/view/plantas/save")
    public String save(@ModelAttribute Plantas plantas, RedirectAttributes ra)
    {
        plantasRepository.save(plantas);
        ra.addFlashAttribute("message", "Planta guardada con éxito");
        return "redirect:/view/plantas";
    }

    @GetMapping("/view/plantas/edit/{id}")
    public String edit(@PathVariable Long id, Model model)
    {
        Plantas plantas = plantasRepository.findById(id).orElse(null);
        model.addAttribute("plantas", plantas);
        return "plantas/form";
    }

    @PostMapping("/view/plantas/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra)
    {
        plantasRepository.deleteById(id);
        ra.addFlashAttribute("message", "Planta eliminada con éxito");
        return "redirect:/view/plantas";
    }

    @GetMapping("/view/plantas/pdf")
    public void exportarPDF(HttpServletResponse response) throws Exception
    {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","attachment; filename=Plantas.pdf");

        List<Plantas> plantasList = plantasRepository.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Listado de Plantas"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Columnas
        table.addCell("ID Planta");
        table.addCell("Código");
        table.addCell("Nombre");
        table.addCell("Nombre Científico");
        table.addCell("Especie");
        table.addCell("Cantidad");
        table.addCell("Precio");

        // Filas
        for (Plantas plantas : plantasList)
        {
            table.addCell(plantas.getId_planta().toString());
            table.addCell(plantas.getCodigo());
            table.addCell(plantas.getNombre());
            table.addCell(plantas.getNombre_cientifico());
            table.addCell(plantas.getEspecie());
            table.addCell(String.valueOf(plantas.getCantidad()));
            table.addCell(String.valueOf(plantas.getPrecio()));
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/view/plantas/excel")
    public void exportarExcel(HttpServletResponse response) throws Exception
    {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Plantas.xlsx");

        List<Plantas> plantasList = plantasRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Plantas");

        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Planta");
        headerRow.createCell(1).setCellValue("Código");
        headerRow.createCell(2).setCellValue("Nombre");
        headerRow.createCell(3).setCellValue("Nombre Científico");
        headerRow.createCell(4).setCellValue("Especie");
        headerRow.createCell(5).setCellValue("Cantidad");
        headerRow.createCell(6).setCellValue("Precio");

        // Agregar datos
        int rowNum = 1;

        for (Plantas p : plantasList)
        {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(p.getId_planta());
            row.createCell(1).setCellValue(p.getCodigo());
            row.createCell(2).setCellValue(p.getNombre());
            row.createCell(3).setCellValue(p.getNombre_cientifico());
            row.createCell(4).setCellValue(p.getEspecie());
            row.createCell(5).setCellValue(p.getCantidad());
            row.createCell(6).setCellValue(p.getPrecio());
        }

        // Autoajustar columnas
        for (int i = 0; i < 7; i++)
        {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
