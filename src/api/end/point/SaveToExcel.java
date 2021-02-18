package api.end.point;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaveToExcel {

	public static void SaveDataToExcel(List<ApiTemplate> data) throws IOException {
		
		FileInputStream fis = new FileInputStream("C:\\Users\\sanka\\workspace\\ChangeRetrival\\data\\CryptoData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yy");
		XSSFSheet sheet = workbook.createSheet("Data"+ format.format(new Date()));
		
		XSSFRow row;
		row = sheet.createRow(0);
		row.createCell(0).setCellValue("Base Market");
		row.createCell(1).setCellValue("Quote Market");
		row.createCell(2).setCellValue("Initial Price");
		row.createCell(3).setCellValue("Highest Price");
		
		int index=1;
		for(int i=0;i<data.size();i++) {
			
			if(data.get(i).getQuoteMarket().equals("usdt")) {
				row = sheet.createRow(index++);
				row.createCell(0).setCellValue(data.get(i).getBaseMarket());
				row.createCell(1).setCellValue(data.get(i).getQuoteMarket());
				row.createCell(2).setCellValue(data.get(i).getLast());
				row.createCell(3).setCellValue(data.get(i).getHigh());
			}
			
		}
		
		FileOutputStream fos = new FileOutputStream("C:\\Users\\sanka\\workspace\\ChangeRetrival\\data\\CryptoData.xlsx");
		workbook.write(fos);
		fos.close();fis.close();workbook.close();
		
	}
	
	
}