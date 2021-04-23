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
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import com.lowagie.text.rtf.style.RtfFont;
/*
 * 插入页眉类
 */
public class InsertHeader {
	private static RtfFont headerFont = new RtfFont("宋体", 9, Font.NORMAL, Color.BLACK);		//页面下划线的另一种方式  Font.UNDERLINE
	/*
	 * 插入字符串形式的页眉，用来描述文档
	 * @param document 文档对象
	 */
	public static void insertHeader(Document document,String headerStr) throws DocumentException, IOException{
		//下滑线  
//		Phrase paraHeader = new Phrase(headerStr, headerFont); 
//		HeaderFooter header = new RtfHeaderFooter(paraHeader);
//		header.setAlignment(HeaderFooter.ALIGN_BOTTOM);//(paraHeader.ALIGN_BOTTOM);
		System.out.println(headerStr.split("&JunKs_7")[0] + "--------" +headerStr.split("&JunKs_7")[1]);
		 Table table=new Table(2,1);
		 table.setPadding(0);
		 table.setBorder(0);
		 table.setWidth(100);
		 Paragraph pr = new Paragraph();
		 pr.add(new Paragraph());
		 pr.add(new Phrase(headerStr.split("&JunKs_7")[0], headerFont));
		 Cell cell = new Cell(pr);
		 cell.setUseBorderPadding(true);
		 cell.setBorderWidthBottom(1);
		 cell.setBorderColorBottom(new Color(0,0,0));
         table.addCell(cell);
         
         Paragraph pr2 = new Paragraph();
		 pr2.add(new Paragraph());
		 pr2.add(new Phrase(headerStr.split("&JunKs_7")[1], headerFont));
		 Cell cell2 = new Cell(pr2);
		 cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		 cell2.setUseBorderPadding(true);
		 cell2.setBorderWidthBottom(1);
		 cell2.setBorderColorBottom(new Color(0,0,0));
         table.addCell(cell2);
        
		//Phrase paraHeader = new Phrase(headerStr, headerFont); 
		HeaderFooter header = new RtfHeaderFooter(table);
		document.setHeader(header);
	}
	/*
	 * 插入图片形式的页眉，由参数定义
	 * @param document 文档对象
	 * @param img 图片对象
	 */
	public static void insertHeader(Document document,String imgUrl,int height) throws BadElementException, MalformedURLException, IOException{
		Image img = Image.getInstance(imgUrl);
		img.setAlignment(Element.ALIGN_CENTER);
		img.scaleAbsolute(document.getPageSize().getWidth()*88/100, document.getPageSize().getHeight()*height/100);
		RtfHeaderFooter header = new RtfHeaderFooter(img);
		document.setHeader(header);
	}
}
