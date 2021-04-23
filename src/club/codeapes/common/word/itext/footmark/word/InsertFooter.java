package club.codeapes.common.word.itext.footmark.word;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.rtf.field.RtfPageNumber;
import com.lowagie.text.rtf.field.RtfTotalPageNumber;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooterGroup;
import com.lowagie.text.rtf.style.RtfFont;
/*
 * 插入页脚类
 */
public class InsertFooter {
	private static RtfFont footerFont = new RtfFont("宋体", 9, Font.NORMAL, Color.BLACK);
	/*
	 * 插入字符串形式的页脚，用来指示页码
	 * @param document 文档对象
	 */
	public static void insertFooterPageAndTotal(Document document) throws DocumentException, IOException{
		
		Paragraph parafooter = new Paragraph();
		parafooter.setFont(new Font(footerFont));
		parafooter.add(new Phrase("第"));
		parafooter.add(new RtfPageNumber());
		parafooter.add(new Phrase("页 / 共"));
		parafooter.add(new RtfTotalPageNumber());
		parafooter.add(new Phrase("页"));

		HeaderFooter footer = new RtfHeaderFooter(parafooter);
		footer.setAlignment(HeaderFooter.ALIGN_RIGHT);
		
		
		RtfHeaderFooterGroup s = new RtfHeaderFooterGroup();
		s.setHeaderFooter(footer, 0);
		document.setFooter(footer);
		
//		HeaderFooter footerIndex = new HeaderFooter(new Phrase("s"),false); 
//		
//		Paragraph parafooter = new Paragraph();
//		parafooter.setFont(new Font(footerFont));
//		parafooter.add(new Phrase("第"));
//		parafooter.add(new RtfPageNumber());
//		parafooter.add(new Phrase("页 / 共"));
//		parafooter.add(new RtfTotalPageNumber());
//		parafooter.add(new Phrase("页"));
//
//		HeaderFooter footerAll = new HeaderFooter(parafooter,true);
//		
//		HeaderFooter footer = new HeaderFooter(new Phrase(" 第"), new Phrase(" 页")); 
//		footer.setAlignment(HeaderFooter.ALIGN_RIGHT);
//		
//		
//		RtfHeaderFooterGroup rg = new RtfHeaderFooterGroup();
//		rg.setPageNumber(999);
//		rg.setHeaderFooter(footerAll, 3);
//		rg.setHeaderFooter(footerIndex, 0);
//		document.setFooter(rg);
		
	}
	
	public static void insertFooterPage(Document document) throws DocumentException, IOException{
		Paragraph parafooter = new Paragraph();
		parafooter.setFont(new Font(footerFont));
		parafooter.add(new Phrase("第"));
		parafooter.add(new RtfPageNumber());
		parafooter.add(new Phrase("页"));
		HeaderFooter footer = new RtfHeaderFooter(parafooter);
		footer.setAlignment(Element.ALIGN_CENTER);
		footer.setBorder(Rectangle.NO_BORDER);
		document.setFooter(footer);
	}
}
