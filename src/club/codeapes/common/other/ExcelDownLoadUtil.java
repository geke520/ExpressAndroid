package club.codeapes.common.other;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 导出Excel文档工具类
 * */
public class ExcelDownLoadUtil {
 
    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list,String []keys,String columnNames[]) {
        // 创建excel
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        HSSFSheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为设第几列；第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
        	if(i==0){
        		sheet.setColumnWidth( i, (short) (35.7 * 40)); 
        	}else if(i==1){
        		sheet.setColumnWidth( i, (short) (35.7 * 120));
        	}else if(i==2){
        		sheet.setColumnWidth( i, (short) (35.7 * 120)); 
        	}else if(i==3){
        		sheet.setColumnWidth( i, (short) (35.7 * 150));
        	}else if(i==4){
        		sheet.setColumnWidth( i, (short) (35.7 * 80));
        	}else if(i==5){
        		sheet.setColumnWidth( i, (short) (35.7 * 80));
        	}else if(i==6){
        		sheet.setColumnWidth( i, (short) (35.7 * 80));
        	}else if(i==7){
        		sheet.setColumnWidth( i, (short) (35.7 * 120));
        	}else if(i==8){
        		sheet.setColumnWidth( i, (short) (35.7 * 100));
        	}else if(i==9){
        		sheet.setColumnWidth( i, (short) (35.7 * 100));
        	}else if(i==10){
        		sheet.setColumnWidth( i, (short) (35.7 * 80));
        	}else if(i==11){
        		sheet.setColumnWidth( i, (short) (35.7 * 80));
        	}else if(i==12){
        		sheet.setColumnWidth( i, (short) (35.7 * 100));
        	}
        }
 
        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();//标题
        CellStyle cs2 = wb.createCellStyle();//内容
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    // 创建第1行列标题
        Row row1 = sheet.createRow((short) 0);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row1.createCell(i);
            cell.setCellValue(columnNames[i]);
            row1.setHeightInPoints(23);
            cell.setCellStyle(cs);
        }
        
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row = sheet.createRow((short) (i));
            row.setHeightInPoints(23);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
       
        //创建表格尾部
//        ExcelDownLoadUtil.createFoot(wb,sheet,columnNames.length,(list.size()));
        return wb;
    }
    
    /**
     * 创建表格的尾部
     * @param sheet
     * @param wb
     * @param startRow
     */
    @SuppressWarnings({ "deprecation", "unused" })
	private static void createFoot(HSSFWorkbook wb,HSSFSheet sheet,int rowCells,int startRow){
    	
		for(int i=0; i<2; i++){
			
			Row row = sheet.createRow((startRow+i));
			for(int j=0;j<rowCells;j++){
	            Cell cell = row.createCell(j);
	            cell.setCellValue("");
	            row.setHeightInPoints(25);
	        }
		    sheet.addMergedRegion(new CellRangeAddress((startRow+i), (startRow+i), 0, rowCells-1));//合并单元格
		    if(i==0){
		    	row.getCell(0).setCellValue("注：1.付款方式：现金、转账、支付宝"); //设置表格标题
		    }else if(i==1){
		    	row.getCell(0).setCellValue("2.结余金额(D) = 预付金额(A)-累计消费(C)"); 
		    }
		   
	        
	        
		}
 
		
    }
    
    
    
    
    /**
     * 创建通讯录头部
     * @param sheet
     * @param
     */
    private static void createHeader(HSSFWorkbook wb, HSSFSheet sheet,int rowCells){
    	
    	// 创建样式1
    	HSSFCellStyle cs1 = wb.createCellStyle();  
    	HSSFFont font1 = wb.createFont();
    	
        font1.setFontHeightInPoints((short) 16);
        font1.setFontName("宋体");
        font1.setColor(IndexedColors.BLACK.getIndex());
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);

        cs1.setFont(font1);
        cs1.setBorderLeft(CellStyle.BORDER_THIN);
        cs1.setBorderRight(CellStyle.BORDER_THIN);
        cs1.setBorderTop(CellStyle.BORDER_THIN);
        cs1.setBorderBottom(CellStyle.BORDER_THIN);
        cs1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
		cs1.setAlignment(CellStyle.ALIGN_CENTER);// 水平
		
		//创建样式2
		HSSFCellStyle cs2 = wb.createCellStyle();  
    	HSSFFont font2 = wb.createFont();
    	
        font2.setFontHeightInPoints((short) 14);
        font2.setFontName("宋体");
        font2.setColor(IndexedColors.BLACK.getIndex());
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
      
        cs2.setFont(font2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
		cs2.setAlignment(CellStyle.ALIGN_LEFT);// 水平
		
    	
    	// 创建第0行列
	    Row row0 = sheet.createRow((short)0);
	    for(int i=0;i<rowCells;i++){
            Cell cell = row0.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(cs1);
            row0.setHeightInPoints(30);
        }
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, rowCells-1));//合并单元格
        row0.getCell(0).setCellValue("福建六壬网安股份有限公司通讯录"); //设置表格标题
       // row0.getCell(0).setCellStyle(cs1);
	
        // 创建第1行列
	    Row row1 = sheet.createRow((short)1);
	    for(int i=0;i<rowCells;i++){
            Cell cell = row1.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(cs2);
            row1.setHeightInPoints(30);
        }
	    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, rowCells-1));//合并单元格
        row1.getCell(0).setCellValue("公司电话: 0591-87957158,87585599,86397979, 87858631,87888635"); //设置公司电话
        //row1.getCell(0).setCellStyle(cs2);
       
        // 创建第2行列 
	    Row row2 = sheet.createRow((short)2);
	    for(int i=0;i<rowCells;i++){
            Cell cell = row2.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(cs2);
            row2.setHeightInPoints(30);
        }
	    sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, rowCells-1));//合并单元格
        row2.getCell(0).setCellValue("公司传真: 0591-22856555-8008"); //设置公司传真
       // row2.getCell(0).setCellStyle(cs2);
    }
    
    
     /**
      * 创建Excel记录
      * @param contectList 通讯录List
      * @param keys  字段
      * @return
      */
  	 public static List<Map<String, Object>> createExcelRecord(List<Map<String,Object>> contectList,String[] keys) {
  	        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
  	        Map<String, Object> map = new HashMap<String, Object>();
  	        map.put("sheetName", "sheet1");
  	        listmap.add(map);
  	        for (int j = 0; j < contectList.size(); j++) {
  	            Map<String, Object> mapValue = new HashMap<String, Object>();
  	            for(int k=0; k<keys.length;k++){
  	            	mapValue.put(keys[k], contectList.get(j).get(keys[k]));
  	            }
  	            listmap.add(mapValue);
  	        }
  	        return listmap;
  	    }
 
  	 
  	/**
  	 * 合并某行部门中空格和职位单元格
  	 * @param sheet 要合并单元格的excel 的sheet
  	 * @param startCell 要合并行的开始列
  	 * @param endCell  要合并行的结束列
  	 * @param startRow 要合并行的开始行
  	 * @param endRow  要合并行的结束行
  	 * @param workBook workBook
  	 */
  	private static void addRowMergedRegion(HSSFSheet sheet,
			int startCell, int endCell, int startRow, int endRow, HSSFWorkbook workBook) { 
  		
		for(int i = startRow; i <= endRow; i++){
			HSSFRow row = sheet.getRow(i);
			int index = startCell;  //开始合并的cell
			for(int j = startCell ;j < endCell; j++){
				HSSFCell cell = row.getCell(j);
				if(!cell.getStringCellValue().equals(" ")){  
					index ++;
				}else{
					break;
				}
			}
			String tempString = row.getCell(endCell).getStringCellValue();
			sheet.addMergedRegion(new CellRangeAddress(i, i, index, endCell));//合并部门及职位的单元格
	        row.getCell(index).setCellValue(tempString); //合并列并设置新值
	       
		}
		
		
  	
  	}
  	 
  	/**  
      * 合并某列下值相同的单元格  
      * @param sheet 要合并单元格的excel 的sheet
      * @param cellLine  要合并的列  
      * @param startRow  要合并列的开始行  
      * @param endRow    要合并列的结束行  
      * @param workBook workBook
      */  
	private static void addColumnMergedRegion(HSSFSheet sheet, int cellLine,
			int startRow, int endRow, HSSFWorkbook workBook) {

		HSSFCellStyle style = workBook.createCellStyle(); // 样式对象
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		
		Font font = workBook.createFont(); //设置字体样式
		if(cellLine==0){
			font.setFontHeightInPoints((short) 15);
			font.setFontName("宋体");
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		}else{
			font.setFontHeightInPoints((short) 12);
			font.setFontName("宋体");
		}
		style.setFont(font);
		
		style.setWrapText(true);//单元格中文字自动换行

		// 获取第startRow行的cellLine列数据,以便后面进行比较
		String s_will =  "";
		int count = 0; // 计数参数
		boolean flag = false; // 单元格的值是否是相同
		for (int i = startRow; i <= endRow; i++) {
			String s_current = sheet.getRow(i).getCell(cellLine)
					.getStringCellValue();
			if (s_will.equals(s_current)) {  
				s_will = s_current;
				if (flag) {
					sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
					HSSFRow row = sheet.getRow(startRow - count);
					String cellValueTemp = sheet.getRow(startRow - count)
							.getCell(cellLine).getStringCellValue();
					HSSFCell cell = row.createCell(cellLine);
					cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
					cell.setCellStyle(style); // 样式
					count = 0;
					flag = false;
				}
				startRow = i;
				count++;
			}else if(s_will.equals("")){
				flag = false; 
				s_will = s_current;
			}else {
				flag = true;
				s_will = s_current;
			}
			// 由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
			if (i == endRow && count > 0) {
				sheet.addMergedRegion(new CellRangeAddress(endRow - count,endRow, cellLine, cellLine));
				String cellValueTemp = sheet.getRow(startRow - count)
						.getCell(cellLine).getStringCellValue();
				HSSFRow row = sheet.getRow(startRow - count);
				HSSFCell cell = row.createCell(cellLine);
				cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
				cell.setCellStyle(style); // 样式
			}
		}
	}
  	 
 /**
  * 将部门拼接字符串拆分成字符串数组
  * @param deptStr  部门的拼接字符串
  * @return  部门数组
  */
	public static String[] parseDept(String deptStr){
		String depts = deptStr;
		String[] oldDept = depts.split(",");  //通过“,”拆分
		String[] newDept = new String[3];
		for(int i=0;i<oldDept.length;i++){
			newDept[i] = oldDept[i];
		}
		return newDept;
	}
	
	
	 /**
     * 重置PersonList数据
     * @param oldPersonList  原来的personList
     * @return 新的personList
     */
	public static List<Map<String, Object>> resetPersonList(
			List<Map<String, Object>> oldPersonList) {
		
		for (Map<String, Object> oldPerson : oldPersonList) {
			String deptStr = oldPerson.get("dept_name").toString();
			String[] depts = ExcelDownLoadUtil.parseDept(deptStr);
			for (int i=0; i<depts.length; i++) {
				oldPerson.put("dept"+(i+1), depts[i]);
			}
		}
		return oldPersonList;
	}
	
	public static void downExcel(HttpServletResponse response,ByteArrayOutputStream os,String fileName) throws Exception{
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			throw e1;
		}
		ServletOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
}


