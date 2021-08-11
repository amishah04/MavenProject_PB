package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
// class name
public class ExcelFileUtil {

	// interface - declare object for workbook - 
	Workbook wb;
	// constructor name - to invoke methods within the class and outside the class
	// we can create object and use them anywhere
	// constructor for reading excel path	
	public ExcelFileUtil(String excelpath) throws Throwable
	
	{	
	FileInputStream fi = new FileInputStream(excelpath);
	wb = WorkbookFactory.create(fi);	
		
	}
	// count no of rows in a sheet
	
	public int rowCount (String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
		
	}
	// get data from cell  - conver string type to int type
	
	public String getCellData (String sheetname, int row, int column)
	{
	String data = "";
	if (wb.getSheet(sheetname).getRow(row).getCell(column).getCellType() == Cell.CELL_TYPE_NUMERIC )
	{
		
		int celldata =(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		data = String.valueOf(celldata);
	}
	else
	{
		data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		
	}
	
	return data;
	
	}
	// set cell data 
	
	public void setCellData(String sheetname, int row, int column, String status, String writeexcel) throws Throwable
	{
	// get sheet from wb
		
	Sheet ws = wb.getSheet(sheetname);
	
	// get row from sheet
	
	Row rowNum = ws.getRow(row);
	
	// create cell 
	Cell cell = rowNum.createCell(column);
	
	//write status
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("Pass"))
	{
		CellStyle style = wb.createCellStyle();
		// create font
		Font font = wb.createFont();
		// set colour
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
		
	}
	else if (status.equalsIgnoreCase("Blocked"))
	{
		CellStyle style = wb.createCellStyle();
		// create font
		Font font = wb.createFont();
		// set colour
		font.setColor(IndexedColors.BLUE.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);	
	}
	else if (status.equalsIgnoreCase("Fail"))
	{
		CellStyle style = wb.createCellStyle();
		// create font
		Font font = wb.createFont();
		// set colour
		font.setColor(IndexedColors.RED.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
	}
	
	FileOutputStream fo = new FileOutputStream(writeexcel);
	
	wb.write(fo);
	}
	
	
}
