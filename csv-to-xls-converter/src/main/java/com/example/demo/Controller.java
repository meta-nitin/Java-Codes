package com.example.demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("convert")
	public void convertCsvToXlsx() {

		String inputFileName = "src/main/resources/data.txt";
		String outputFileName = "src/main/resources/data.xlsx";
		String thisLine;

		// Load the file
		logger.info("Loading the file: " + inputFileName);
		FileInputStream file = null;
		try {
			file = new FileInputStream(inputFileName);
		} catch (FileNotFoundException e) {
			logger.error("File not found: " + e);
		}
		logger.info("File has been read");
		
		if (null != file) {

			// Reading the file
			logger.info("Reading the csv file and creating excel file simultaneously");
			BufferedReader myInput = new BufferedReader(new InputStreamReader(file));
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("new sheet");
			XSSFRow row = null;
			XSSFCell cell = null;
			String[] rowData = null;
			try {
				int rowNumber = 0;
				while ((thisLine = myInput.readLine()) != null) { // for each row
					row = sheet.createRow((short) 1 + rowNumber); // Left the 1st row in excel for Heading
					int colNumber = 0;
					rowData = thisLine.split(",");
					for (String record : rowData) { // for each column
						cell = row.createCell((short) colNumber);
						cell.setCellValue(record);
						++colNumber;
					}
					++rowNumber;
				}
				myInput.close();
				file.close();

				// Producing the File resource
				FileOutputStream fileOut = new FileOutputStream(outputFileName);
				workbook.write(fileOut);
				
				workbook.close();
				fileOut.close();
				logger.info("Excel file has been generated with the name: " + outputFileName + " and total rows: "
						+ sheet.getLastRowNum());
			} catch (IOException e) {
				logger.error("I/O Exception occured: " + e);
			} catch (Exception e) {
				logger.error("Exception occured during processing: " + e);
			}
		}
	}
	
//	
//	/**
//	 * Another way of conversion of csv to pdf
//	 */
//	@GetMapping("convert")
//	public void convertCsvToXls() {
//
//		String inputFileName = "src/main/resources/data.txt";
//		String outputFileName = "src/main/resources/data.xlsx";
//		List<List<String>> entireData = new ArrayList<>(); // to store entire sheet data
//		List<String> rowData = null;
//		String thisLine;
//
//		// Load the file
//		logger.info("Loading the file: " + inputFileName);
//		FileInputStream file = null;
//		try {
//			file = new FileInputStream(inputFileName);
//		} catch (FileNotFoundException e) {
//			logger.error("File not found: " + e);
//		}
//
//		if (null != file) {
//
//			// Reading the file
//			logger.info("Reading the file");
//			BufferedReader myInput = new BufferedReader(new InputStreamReader(file));
//			try {
//				while ((thisLine = myInput.readLine()) != null) {
//					rowData = new ArrayList<String>(); // to store each rows
//					String row[] = thisLine.split(",");
//					for (int j = 0; j < row.length; j++) {
//						rowData.add(row[j]);
//					}
//					entireData.add(rowData);
//				}
//				myInput.close();
//				file.close();
//			} catch (IOException e) {
//				logger.error("I/O Exception occured: " + e);
//			}
//			logger.info("Number of rows of data: " + entireData.size());
//			rowData.clear();
//
//			if (!entireData.isEmpty()) {
//				// creating an excel file
//				logger.debug("Creating an excel file");
//				try {
//					XSSFWorkbook hwb = new XSSFWorkbook();
//					XSSFSheet sheet = hwb.createSheet("new sheet");
//					for (int k = 0; k < entireData.size(); k++) {
//						rowData = entireData.get(k); // to retreive each rows
//						XSSFRow row = sheet.createRow((short) 1 + k); // Left the 1st row in excel for Heading
//						for (int p = 0; p < rowData.size(); p++) {
//							XSSFCell cell = row.createCell((short) p);
//							cell.setCellValue(rowData.get(p));
//						}
//					}
//					FileOutputStream fileOut = new FileOutputStream(outputFileName);
//					hwb.write(fileOut);
//					hwb.close();
//					fileOut.close();
//					logger.info("Excel file has been generated with the name: " + outputFileName);
//				} catch (Exception ex) {
//					logger.error("Error occured while creating an exccel file: " + ex);
//				}
//			}
//		}
//	}
	
}
