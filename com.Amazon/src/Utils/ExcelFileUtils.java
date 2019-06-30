package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtils {

	Workbook wb;
	
	public ExcelFileUtils() throws Throwable
	{
	FileInputStream fis=new FileInputStream("./TestInput/InputSheet.xlsx");
	wb=WorkbookFactory.create(fis);
	}
	
	public int rowCount(String Sheet)
	{
		int rc=wb.getSheet(Sheet).getLastRowNum();
		return rc;
	}
	
	public int colCount(String Sheet, int Row)
	{
		int cc=wb.getSheet(Sheet).getRow(Row).getLastCellNum();
		return cc;
	}
	
	public String getData(String Sheet, int Row, int col)
	{
		String data;
		if (wb.getSheet(Sheet).getRow(Row).getCell(col).getCellType()==Cell.CELL_TYPE_NUMERIC) {
			int Ndata=(int) wb.getSheet(Sheet).getRow(Row).getCell(col).getNumericCellValue();
			data=String.valueOf(Ndata);
		} else {
			data=wb.getSheet(Sheet).getRow(Row).getCell(col).getStringCellValue();
		}
		return data;
	}
	
	public void setData(String Sheet, int Row, int col, String status) throws Throwable
	{
		wb.getSheet(Sheet).getRow(Row).createCell(col).setCellValue(status);
		if (status.equalsIgnoreCase("Pass")) {
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			wb.getSheet(Sheet).getRow(Row).getCell(col).setCellStyle(style);
		}else if (status.equalsIgnoreCase("Fail")) {
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			wb.getSheet(Sheet).getRow(Row).getCell(col).setCellStyle(style);
		}else if (status.equalsIgnoreCase("NotExecuted")) {
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			wb.getSheet(Sheet).getRow(Row).getCell(col).setCellStyle(style);
		}
		FileOutputStream fos=new FileOutputStream("./TestOutput/OutputSheet.xlsx");
		wb.write(fos);
		//wb.close();
		fos.close();
	}
}
