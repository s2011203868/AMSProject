package com.purple.ams.ssm.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.StaticApplicationContext;

import com.purple.ams.ssm.pojo.ExceptionRecord;

import sun.swing.FilePane;
/**
 * 
 * @ClassName: DownLoadUtil 
 * @Description: 导出Excel工具类
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:53:32
 */
public class DownLoadUtil {
    
    private static String filePath;
    
    static {
        InputStream is = DownLoadUtil.class.getClassLoader().getResourceAsStream("tempfilepath.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            filePath = properties.getProperty("exceptionexporttempfilepath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static void downLoadExceptionRecordFile(HttpServletRequest request,HttpServletResponse response){
		
	    File file = new File(filePath);
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ServletOutputStream sos = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(sos);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String fileName = date +FunctionUtil.genImageName()+ ".xls";
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
			
			int len = 0;
			byte [] b = new byte [1024];
			while((len = bis.read(b))!=-1){
				bos.write(b, 0, len);
			}
			bos.flush();
			bos.close();
			sos.close();
			bis.close();
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void createExcel(List<ExceptionRecord> recordList) throws IOException{
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell  = null;
		String [] headerField = {"异常类型","异常原因","请求URI","Ip地址","操作人","操作时间"};
		for(int i = 0 ;i<6 ;i++){
			cell = row.createCell(i);
			cell.setCellValue(headerField[i]);
		}
		
		int rowNum = 1;
		for(ExceptionRecord record : recordList){
			row = sheet.createRow(rowNum);
			for(int i = 0 ; i< 6 ; i++){
				cell = row.createCell(i);
				switch (i) {
				case 0:
					cell.setCellValue(record.getExceptiontype());
					break;
				case 1:
					cell.setCellValue(record.getExceptioncause());
					break;
				case 2:
					cell.setCellValue(record.getUri());
					break;
				case 3:
					cell.setCellValue(record.getIpaddress());
					break;
				case 4:
					cell.setCellValue(record.getExecutor());
					break;
				default:
					cell.setCellValue(record.getDatetime());
					break;
				}
			}
			rowNum++;
		}
		workBook.write(new File(filePath));
	}
	
	public static void deleteFile(String files){
		File file = new File(files);
		if(file.exists()){
			file.delete();
		}
	}
}
