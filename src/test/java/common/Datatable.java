package common;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import driver.DriverScript;

public class Datatable extends DriverScript{
	/*************************************
	 * method Name	: getExcelData()
	 * purpose		: to read the test data from the excel file
	 * 
	 *********************************/
	public Map<String, String> getExcelData(String filePath, String sheetName, String logicalName){
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		int rowNum = 0;
		Map<String, String> objData = null;
		String strValue = null;
		Calendar cal = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			objData = new HashMap<String, String>();
			
			if(sh==null) {
				report.writeReport(null, "Fail", "Failed to find the sheet '"+sheetName+"'");
				return null;
			}
			
			//Find the rowNumber in which the logical name present
			int row = sh.getPhysicalNumberOfRows();
			for(int r=0; r<row; r++) {
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if(cell1.getStringCellValue().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			
			if(rowNum > 0) {
				row1 = sh.getRow(0);
				row2 = sh.getRow(rowNum);
				int columns = row1.getPhysicalNumberOfCells();
				for(int c=0; c<columns; c++) {
					cell1 = row1.getCell(c);
					cell2 = row2.getCell(c);
					//format and read the cell data
					switch(cell2.getCellType()) {
						case BLANK:
							strValue = "";
							break;
						case STRING:
							strValue = cell2.getStringCellValue();
							break;
						case BOOLEAN:
							strValue = String.valueOf(cell2.getBooleanCellValue());
							break;
						case NUMERIC:
							if(DateUtil.isCellDateFormatted(cell2) == true) {
								double dt = cell2.getNumericCellValue();
								cal = Calendar.getInstance();
								cal.setTime(DateUtil.getJavaDate(dt));
								
								//If day is <10, then prefix zero
								if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
									sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
								}else {
									sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
								}
								
								//If month is <10, then prefix zero
								if((cal.get(Calendar.MONTH)+1) < 10) {
									sMonth = "0" + (cal.get(Calendar.MONTH)+1);
								}else {
									sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
								}
								
								sYear = String.valueOf(cal.get(Calendar.YEAR));
								strValue = sDay +"-" + sMonth + "-" + sYear;
							}else {
								strValue = String.valueOf(cell2.getNumericCellValue());
							}
							break;
						default:
								
					}
					
					objData.put(cell1.getStringCellValue(), strValue);
				}
				return objData;
			}else {
				report.writeReport(null, "Fail", "Failed to find the '"+logicalName+"' logical Name in the excel");
				return null;
			}
			
		}catch(Exception e) {
			report.writeReport(null, "Exception", "Exception in 'getExcelData()' method. " + e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				wb.close();
				wb = null;
				cal = null;
				objData = null;
				strValue = null;
				sDay = null;
				sMonth = null;
				sYear = null;
			}catch(Exception e) {
				report.writeReport(null, "Exception", "Exception in 'getExcelData()' method. " + e);
			}
		}
	}
	
	
	/*************************************
	 * method Name	: getRowCount()
	 * purpose		: to read the rowcount from the excel file
	 * 
	 *********************************/
	public int getRowCount(String filePath, String sheetName) {
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh==null) {
				report.writeReport(null, "Fail", "Failed to find the sheet '"+sheetName+"'");
				return -1;
			}
			return sh.getPhysicalNumberOfRows()-1;
		}catch(Exception e) {
			report.writeReport(null, "Exception", "Exception in 'getRowCount()' method. " + e);
			return -1;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e) {
				report.writeReport(null, "Exception", "Exception in 'getRowCount()' method. " + e);
			}
		}
	}
	
	
	
	/*************************************
	 * method Name	: getCellData()
	 * purpose		: to read the cell data from the excel file
	 * 
	 *********************************/
	public String getCellData(String filePath, String sheetName, String columnName, int rowNum) {
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		String cellData = null;
		int colNum = 0;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		Calendar cal = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh==null) {
				report.writeReport(null, "Exception", "Failed to find the sheet '"+sheetName+"'");
				return null;
			}
			
			//find the column number based on the column  name
			row = sh.getRow(0);
			int columns = row.getPhysicalNumberOfCells();
			for(int c=0; c<columns; c++) {
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(columnName)) {
					colNum = c;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			if(row.getCell(colNum)==null) {
				cell = row.createCell(colNum);
			}else {
				cell = row.getCell(colNum);
			}
			
			if(cell==null || cell.getCellType()==CellType.BLANK) {
				cellData = "";
			}else if(cell.getCellType()==CellType.BOOLEAN) {
				cellData = String.valueOf(cell.getBooleanCellValue());
			}else if(cell.getCellType()==CellType.STRING) {
				cellData = cell.getStringCellValue();
			}else if(cell.getCellType()==CellType.NUMERIC) {
				if(DateUtil.isCellDateFormatted(cell)==true) {
					double dt = cell.getNumericCellValue();
					cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(dt));
					
					//Prefix zero if day is <10
					if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
						sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
					}else {
						sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					}
					
					//Prefix zero if month is <10
					if((cal.get(Calendar.MONTH)+1) < 10) {
						sMonth = "0" + (cal.get(Calendar.MONTH)+1);
					}else {
						sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
					}
					
					sYear = String.valueOf(cal.get(Calendar.YEAR));
					cellData = sDay+"-"+sMonth+"-"+sYear;
				}else {
					cellData = String.valueOf(cell.getNumericCellValue());
				}
			}
			return cellData;
		}catch(Exception e) {
			report.writeReport(null, "Exception", "Exception in 'getCellData()' method. " + e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
				cal = null;
				sDay = null;
				sMonth = null;
				sYear = null;
			}catch(Exception e) {
			}
		}
	}
}
