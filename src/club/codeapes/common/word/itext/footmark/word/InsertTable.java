package club.codeapes.common.word.itext.footmark.word;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.style.RtfFont;

public class InsertTable {
	
	private RtfFont tableFont = new RtfFont("宋体", 11, Font.COURIER, Color.BLACK);
	/**
	 * 创建表格的方法
	 * @param ruwsNum 行数
	 * @param colsNum 列数
	 * @param colsWidth 列宽
	 * @param fontSize 字体大小
	 * @param fontStyle 字体样式
	 * @param fullPage 是否横向布满页面
	 * @return Table
	 */
	public Table insertTable(Document document,int rowsNum,int colsNum,int[] colsWidth,boolean fullPage) throws DocumentException, IOException{  
		Table table = new Table(colsNum,rowsNum);
		table.setWidths(colsWidth);// 设置每列所占比例 
		if(fullPage)
			table.setWidth(100); // 占页面宽度100%  
		table.setAlignment(Element.ALIGN_CENTER);// 居中显示  
		table.setAlignment(Element.ALIGN_MIDDLE);// 纵向居中显示  
		table.setAutoFillEmptyCells(true); // 自动填满  
		table.setPadding(12);// 单元格内间距，即文字与单元格边的距离
		table.setSpacing(0);// 单元格之间的间距 
		return table;
		//表格操作开始
//		addCell(table, "第1个", 2, 5, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
//		addCell(table, "第2个", 1, 1, Element.ALIGN_LEFT, Element.ALIGN_TOP);
//		addCell(table, "第3个", 1, 1, Element.ALIGN_LEFT, Element.ALIGN_TOP);
//		addCell(table, "第4个", 3, 2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
//		addCell(table,"第5个");addCell(table,"第6个");addCell(table,"第7个");addCell(table,"第8个");
//		addCell(table, "第9个", 3, 1, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
//		addCell(table, "第10个", 2, 3, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
//		addCell(table,"第11个");
//		addCell(table,"第12个");
//		addCell(table,"第13个");
//		addCell(table, "第14个", 2, 3, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
//		addCell(table,"第15个");
//		addCell(table,"第16个");
//		//addCell(table, "第17个", 3, 2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
//		addImageCell(table, 3, 2, "d://me.jpg", 100f,50f);
//		addCell(table, "第17个", 2, 3, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
//		addCell(table, "第18个", 1, 3, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
		
	}
	/**
	 * 插入单元格和内容，默认内容居中
	 * @param table
	 * @param content
	 * @throws BadElementException
	 */
	public void addCell(Table table,String content) throws BadElementException{
		Cell cell = new Cell(new Phrase(content, tableFont));  
		cell.setVerticalAlignment(Element.ALIGN_CENTER);  
		cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);  
		cell.setRowspan(1); 
		cell.setColspan(1);
		table.addCell(cell);
	}
	/**
	 * 插入单元格和内容，可以设置单元格占的列数和行数
	 * @param table
	 * @param content
	 * @param rowspanNum
	 * @param colspanNum
	 * @param align
	 * @param vlign
	 * @throws BadElementException
	 */
	public void addCell(Table table,String content,int rowspanNum,int colspanNum,int align,int vlign,Color color,int isHeader) throws BadElementException{
		Cell cell = new Cell(new Phrase(content, tableFont));  
		cell.setVerticalAlignment(vlign);  
		cell.setHorizontalAlignment(align);  
		cell.setRowspan(rowspanNum); 
		cell.setColspan(colspanNum);
		if(color != null)
			cell.setBackgroundColor(color);  
		if(isHeader == 1)
			cell.setHeader(true);
		table.addCell(cell);
		if(isHeader == 1)
			table.endHeaders(); //为表格添加表头信息时，要留意的是一旦表头信息添加完了之后，必须调用 endHeaders()方法，否则当表格跨页后，表头信息不会再显示。
	}
	/**
	 * 插入图片如果在这里修改长和宽，图片的大小还是原来的大小，所以最好先设置图片合适的大小，再插入
	 * @param table
	 * @param rowspanNum
	 * @param colspanNum
	 * @param imgUrl
	 * @param width
	 * @param height
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void addImageCell(Table table,int rowspanNum,int colspanNum,String imgUrl,float width,float height) throws BadElementException, MalformedURLException, IOException{
		Cell cell = new Cell();
		cell.setRowspan(rowspanNum); 
		cell.setColspan(colspanNum);
		Image img = Image.getInstance(imgUrl);
		img.scaleAbsolute(width, height);
		cell.add(img);
		table.addCell(cell);
	}
	
}
