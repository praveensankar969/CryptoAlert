package api.end.point;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaveToExcel {

	public static void SaveDataToExcel(List<ApiTemplate> data) throws IOException {

		FileInputStream fis = new FileInputStream("C:\\Users\\sanka\\workspace\\ChangeRetrival\\data\\CryptoData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yy HH-mm");
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

	public static void SaveDataToExcelTickerAPI(List<ApiTemplateTicker> data) throws IOException {

		FileInputStream fis = new FileInputStream("C:\\Users\\sanka\\workspace\\ChangeRetrival\\data\\CryptoData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yy HH-mm");
		XSSFSheet sheet = workbook.createSheet("Data-Ticker-API");

		XSSFRow row;
		row = sheet.createRow(0);
		row.createCell(0).setCellValue("Base Market");
		row.createCell(1).setCellValue("Quote Market");
		row.createCell(2).setCellValue("Initial Price");

		int index=1;
		for(int i=0;i<data.size();i++) {

			if(data.get(i).getQuote_unit().equals("usdt")) {
				row = sheet.createRow(index++);
				row.createCell(0).setCellValue(data.get(i).getBase_unit());
				row.createCell(1).setCellValue(data.get(i).getQuote_unit());
				row.createCell(2).setCellValue(data.get(i).getLast());
			}

		}

		FileOutputStream fos = new FileOutputStream("C:\\Users\\sanka\\workspace\\ChangeRetrival\\data\\CryptoData.xlsx");
		workbook.write(fos);
		fos.close();fis.close();workbook.close();

	}

	public static Map<String, Float> GetDataToExcelTickerAPI() throws IOException {

		FileInputStream fis = new FileInputStream("C:\\Users\\sanka\\workspace\\ChangeRetrival\\data\\CryptoData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Data-Ticker-API");

		XSSFRow row;
		String coin;
		float priceCoin;

		Map<String, Float> price = new HashMap<String, Float>();

		for(int i=1;i<=sheet.getLastRowNum();i++) {

			row = sheet.getRow(i);
			coin = row.getCell(0).getStringCellValue();
			priceCoin = (float) row.getCell(2).getNumericCellValue();
			price.put(coin, priceCoin);

		}

		fis.close();workbook.close();

		return price;

	}

}
