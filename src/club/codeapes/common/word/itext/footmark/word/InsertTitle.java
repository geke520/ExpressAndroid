package club.codeapes.common.word.itext.footmark.word;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.style.RtfFont;

/**
 * 插入word文档标题类 
 */
public class InsertTitle {
	private RtfFont titleFont = new RtfFont("宋体", 16, Font.BOLD, Color.BLACK);
	/**
	 * 插入主标题，三号黑体加粗，居中
	 * @param document 文档对象
	 * @param title 标题内容
	 */
	public void insertMainTitle(Document document,String title) throws DocumentException, IOException{
		Paragraph paragraph = new Paragraph(title);
		paragraph.setAlignment(Element.ALIGN_CENTER);  
		paragraph.setFont(this.titleFont);  
		document.add(paragraph); 
	}
	/**
	 * 根据用户提供的参数插入主标题
	 * @param title 标题内容
	 * @param fontSize 字体大小
	 * @param fontStyle 字体样式
	 * @param elementAlign 标题位置
	 * @param leading 行距
	 */
	public void insertMainTitle(Document document,String title, Font font,int elementAlign,float leading,float indentationLeft) throws DocumentException, IOException{
		Paragraph paragraph = new Paragraph(title);
		paragraph.setIndentationLeft(indentationLeft);
		paragraph.setLeading(leading);
		paragraph.setAlignment(elementAlign);  
		paragraph.setFont(font);  
		document.add(paragraph); 
	}

}
