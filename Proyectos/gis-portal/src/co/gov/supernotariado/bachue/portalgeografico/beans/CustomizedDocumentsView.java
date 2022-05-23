package co.gov.supernotariado.bachue.portalgeografico.beans;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IGisParametrosDao;
import co.gov.supernotariado.bachue.portalgeografico.model.AttributeDataTable;

/**
 * Esta clase define la exportacion y estilo de la informacion de la tabla de
 * atributos
 * 
 * @author datatools
 */
@ManagedBean
@RequestScoped
public class CustomizedDocumentsView implements Serializable {
	private static final long serialVersionUID = -8292534644366105331L;
	private static final Logger log = LoggerFactory.getLogger(CustomizedDocumentsView.class);

	@EJB
	private IGisParametrosDao gisParametrosDao;

	/**
	 * Metodo constructor que inicializa la vista.
	 */
	@PostConstruct
	public void init() {
	}

	/**
	 * Metodo que exporta datos de tabla a excel
	 * 
	 * @param dt El parámetro dt define una lista de AttributeDataTable que contiene
	 *           los datos actuales de la tabla.
	 */
	public void preProcessXLS(List<AttributeDataTable> dt) throws FileNotFoundException, IOException,
			ClassNotFoundException, URISyntaxException, InvalidFormatException {

		log.warn("Inicio de Creacion del archivo excel");
		Date date = new Date();
		int rows;

		log.warn("se lee archivo excel");

		byte[] b = gisParametrosDao.getBlob(Constants.PLANTILLA_EXCEL);

		ByteArrayInputStream bin = new ByteArrayInputStream(b);
		XSSFWorkbook wb = new XSSFWorkbook(bin);

		XSSFSheet sheet1 = wb.getSheet(Constants.HOJA_EXCEL_TABLA_ATRIBUTOS);
		XSSFRow row = sheet1.getRow(8);
		XSSFCell cell = row.getCell(1);
		cell.setCellValue(date.toString());
		XSSFCell cell2;
		XSSFCell cell3;
		XSSFCell cell4;
		XSSFCell cell5;
		XSSFCell cell6;
		XSSFCell cell7;
		XSSFCell cell8;
		XSSFCell cell9;
		XSSFCell cell10;
		XSSFCell cell11;
		XSSFCellStyle style3 = wb.createCellStyle();
		style3.setBorderBottom(BorderStyle.MEDIUM);
		style3.setBorderTop(BorderStyle.MEDIUM);
		style3.setBorderRight(BorderStyle.MEDIUM);
		style3.setBorderLeft(BorderStyle.MEDIUM);
		int x = 12;
		for (int i = 0; i < dt.size(); i++) {
			sheet1.createRow(x);
			row = sheet1.getRow(x);
			cell = row.createCell(0);
			cell = row.getCell(0);
			cell2 = row.getCell(1);
			cell2 = row.createCell(1);
			cell3 = row.getCell(2);
			cell3 = row.createCell(2);
			cell4 = row.getCell(3);
			cell4 = row.createCell(3);
			cell5 = row.getCell(4);
			cell5 = row.createCell(4);
			cell6 = row.getCell(5);
			cell6 = row.createCell(5);
			cell7 = row.getCell(6);
			cell7 = row.createCell(6);
			cell8 = row.getCell(7);
			cell8 = row.createCell(7);
			cell9 = row.getCell(8);
			cell9 = row.createCell(8);
			cell10 = row.getCell(9);
			cell10 = row.createCell(9);
			cell11 = row.getCell(10);
			cell11 = row.createCell(10);

			cell.setCellValue(dt.get(i).getNumber());
			cell2.setCellValue(dt.get(i).getNupre());
			cell3.setCellValue(dt.get(i).getEnrollment());
			cell4.setCellValue(dt.get(i).getCode());
			cell5.setCellValue(dt.get(i).getDepto());
			cell6.setCellValue(dt.get(i).getMunicipality());
			cell7.setCellValue(dt.get(i).getGroup());
			cell8.setCellValue(dt.get(i).getAddress());
			cell9.setCellValue(dt.get(i).getId());
			cell10.setCellValue(dt.get(i).getIdentification());
			cell11.setCellValue(dt.get(i).getName());

			rows = sheet1.getLastRowNum();
			x = x + 1;
			sheet1.shiftRows(x, rows, 1);

		}

		row = sheet1.getRow(sheet1.getLastRowNum() - 9);
		cell = row.getCell(2);
		cell.setCellValue(x - 12);

		byte[] bytes = gisParametrosDao.getBlob(Constants.LOGO_EXCEL_1);
		byte[] bytes2 = gisParametrosDao.getBlob(Constants.LOGO_EXCEL_2);

		int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		CreationHelper helper = wb.getCreationHelper();
		int pictureIdx2 = wb.addPicture(bytes2, Workbook.PICTURE_TYPE_PNG);

		Drawing<?> drawing = sheet1.createDrawingPatriarch();
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setDx1(0);
		anchor.setDy1(0);
		anchor.setDx2(1023);
		anchor.setDy2(0);
		anchor.setCol1(0);
		anchor.setRow1(sheet1.getLastRowNum() - 6);
		anchor.setCol2(0);
		anchor.setRow2(sheet1.getLastRowNum() - 6);
		ClientAnchor anchor2 = helper.createClientAnchor();
		anchor2.setDx1(0);
		anchor2.setDy1(0);
		anchor2.setDx2(1023);
		anchor2.setDy2(0);
		anchor2.setCol1(8);
		anchor2.setRow1(sheet1.getLastRowNum() - 6);
		anchor2.setCol2(8);
		anchor2.setRow2(sheet1.getLastRowNum() - 6);
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		Picture pict2 = drawing.createPicture(anchor2, pictureIdx2);
		pict.resize();
		pict2.resize();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);
		bos.close();
		wb.close();

		byte[] bt = bos.toByteArray();

		FacesContext ctx = FacesContext.getCurrentInstance();


		if (!ctx.getResponseComplete()) {
			String fileName = Constants.EXCEL_TABLA_ATRIBUTOS;
			String contentType = Constants.CONTENT_TYPE_EXCEL;
			HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
			response.setContentType(contentType);
			response.setHeader(Constants.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"");
			ServletOutputStream out = response.getOutputStream();

			out.write(bt, 0, bt.length);

			out.flush();
			out.close();
			ctx.responseComplete();
			bin.close();
		}
	}

	/**
	 * Metodo que exporta datos de tabla a pdf
	 * 
	 * @param dt El parámetro dt define una lista de AttributeDataTable que contiene
	 *           los datos actuales de la tabla.
	 */
	public void preProcessPDF(List<AttributeDataTable> dt)
			throws URISyntaxException, DocumentException, MalformedURLException, IOException {
		log.warn("Inicio de Creacion del archivo pdf");
		Color myColor = WebColors.getRGBColor(Constants.COLOR_CABECERA_PDF);
		
		//Document document = new Document(PageSize.A4.rotate());
		Document document = new Document();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, bos);

		log.warn("Se abre el documento para su modificacion");
		document.open();
		
		byte[] bytes = gisParametrosDao.getBlob(Constants.LOGO_PDF_1);
		byte[] bytes2 = gisParametrosDao.getBlob(Constants.LOGO_PDF_2);
		Image img = Image.getInstance(bytes);

		document.add(img);
		
		PdfPTable table = new PdfPTable(11);
		table.setSpacingBefore(50.0f);
		table.setWidthPercentage(100.0f);
		Font fuente = new Font();
		fuente.setColor(255, 255, 255);
		PdfPCell cell = new PdfPCell(new Phrase(Constants.CABECERA_NUMERO, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_NUPRE, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_MATRICULA, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_CHIP, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_DEPTO, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_MUNICIPIO, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_GRUPO, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_DIRECCION, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_TIPO_ID, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_IDENTIFICACION, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(Constants.CABECERA_NOMBRE, fuente));
		cell.setBackgroundColor(myColor);
		table.addCell(cell);
		for (AttributeDataTable nombre : dt) {
			table.addCell(nombre.getNumber());
			table.addCell(nombre.getNupre());
			table.addCell(nombre.getEnrollment());
			table.addCell(nombre.getCode());
			table.addCell(nombre.getDepto());
			table.addCell(nombre.getMunicipality());
			table.addCell(nombre.getGroup());
			table.addCell(nombre.getAddress());
			table.addCell(nombre.getId());
			table.addCell(nombre.getIdentification());
			table.addCell(nombre.getName());
		}
		
		table.setSpacingAfter(50.0f);
		
		document.add(table);
		
		Image img2 = Image.getInstance(bytes2);
		document.add(img2);
		document.close();

		bos.close();

		byte[] bt = bos.toByteArray();

		FacesContext ctx = FacesContext.getCurrentInstance();

		if (!ctx.getResponseComplete()) {

			String fileName = Constants.PDF_TABLA_ATRIBUTOS;
			String contentType = Constants.CONTENT_TYPE_PDF;
			HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();

			response.setContentType(contentType);

			response.setHeader(Constants.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"");

			ServletOutputStream out = response.getOutputStream();

			out.write(bt, 0, bt.length);

			out.flush();
			out.close();
			ctx.responseComplete();
		}
		log.warn("Se finaliza el proceso de export pdf");
	}

}
