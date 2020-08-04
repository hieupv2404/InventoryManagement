package inventory.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import inventory.model.InvoiceTemp;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import inventory.model.Invoice;
import inventory.util.Constant;
import inventory.util.DateUtil;

public class GoodsReceiptReport extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String fileName = "invoice-export-"+System.currentTimeMillis()+".xlsx";
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");


		Sheet sheet = workbook.createSheet("data");
		Row title = sheet.createRow(0);
//			HSSFCell cell = (HSSFCell) row.createCell(rownum, CellType.FORMULA);
//		// Sét công thức.
//			cell.setCellFormula("SUM(D2:D4)");
		title.createCell(1).setCellValue(" Invoice Export");

		Row header = sheet.createRow(1);
		header.createCell(0).setCellValue("#");
		header.createCell(1).setCellValue("Code");
		header.createCell(2).setCellValue("Qty");
		header.createCell(3).setCellValue("Price");
		header.createCell(4).setCellValue("Product");
		header.createCell(5).setCellValue("Update date");
		header.createCell(6).setCellValue("Supplier");
		header.createCell(7).setCellValue("User");

		List<InvoiceTemp> invoiceTempList =(List<InvoiceTemp>) model.get(Constant.KEY_GOODS_RECEIPT_REPORT);
//		List<Invoice> invoices =(List<Invoice>) model.get(Constant.KEY_GOODS_RECEIPT_REPORT);

		int rownum=2;
		for(InvoiceTemp invoiceTemp :invoiceTempList) {
			Row row = sheet.createRow(rownum++);
			row.createCell(0).setCellValue(rownum-2);
			row.createCell(1).setCellValue(invoiceTemp.getCode());
			row.createCell(2).setCellValue(invoiceTemp.getQty());
			row.createCell(3).setCellValue(Float.parseFloat(invoiceTemp.getPrice().toString()));
			row.createCell(4).setCellValue(invoiceTemp.getProductName());
			row.createCell(5).setCellValue(DateUtil.dateToString(invoiceTemp.getUpdateDate()));
			row.createCell(6).setCellValue(invoiceTemp.getSupplierName());
			row.createCell(7).setCellValue(invoiceTemp.getUserName());
		}
//		for(Invoice invoice :invoices) {
//			Row row = sheet.createRow(rownum++);
//			row.createCell(0).setCellValue(rownum-1);
//			row.createCell(1).setCellValue(invoice.getCode());
//			row.createCell(2).setCellValue(invoice.getQty());
//			row.createCell(3).setCellValue(Float.parseFloat(invoice.getPrice().toString()));
//			row.createCell(4).setCellValue(invoice.getProductInfo().getName());
//			row.createCell(5).setCellValue(DateUtil.dateToString(invoice.getUpdateDate()));
//		}
			Row row = sheet.createRow(rownum++);
//			HSSFCell cell = (HSSFCell) row.createCell(rownum, CellType.FORMULA);
//		// Sét công thức.
//			cell.setCellFormula("SUM(D2:D4)");
			row.createCell(1).setCellValue("Total: ");
		row.createCell(2).setCellValue("=SUM(C3:C"+String.valueOf(rownum-1)+")");
			row.createCell(3).setCellValue("=SUM(D3:D"+String.valueOf(rownum-1)+")");
	}

}
