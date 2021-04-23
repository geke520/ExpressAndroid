package club.codeapes.common.word.itext.footmark.word;

import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.style.RtfParagraphStyle;

/*
 * 插入大纲类，方便在word中生成目录
 */
public class InsertOutlineTitle{
	
	/**
	 * 插入大纲标题，3号黑体加粗，居左
	 * @param document 文档对象
	 * @param outlineTitle 大纲内容
	 * @param outline 大纲级别，取值为1、2、3之一
	 */
	public void insertOutlineTitle(Document document,String outlineTitle,int outline) throws DocumentException, IOException{
		RtfParagraphStyle rtfGsBt = null;
		Paragraph paragraph = null;
		
		if(outline==1){
			rtfGsBt = RtfParagraphStyle.STYLE_HEADING_1;
			rtfGsBt.setSize(16f);
		}else if(outline==2){
			rtfGsBt = RtfParagraphStyle.STYLE_HEADING_2;
//			outlineTitle = "  "+outlineTitle;
			rtfGsBt.setSize(15f);
		}else if(outline==3){
			rtfGsBt = RtfParagraphStyle.STYLE_HEADING_3;
//			outlineTitle = "    "+outlineTitle;
			rtfGsBt.setSize(14f);
		}
		rtfGsBt.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt.setStyle(Font.BOLD);
		rtfGsBt.setSpacingBefore(20);	//距离上一行的距离
		rtfGsBt.setSpacingAfter(0);		//距离下一行的距离
		rtfGsBt.setLineLeading(6);
		paragraph = new Paragraph(outlineTitle,rtfGsBt);
		document.add(paragraph); 
	}
	
	
	public void insertOutlineTitle(Document document,String outlineTitle,int outline,Font font) throws DocumentException, IOException{
		RtfParagraphStyle rtfGsBt = null;
		Paragraph paragraph = null;
		
		if(outline==1){
			rtfGsBt = RtfParagraphStyle.STYLE_HEADING_1;
		}else if(outline==2){
			rtfGsBt = RtfParagraphStyle.STYLE_HEADING_2;
			outlineTitle = "  "+outlineTitle;
		}else if(outline==3){
			rtfGsBt = RtfParagraphStyle.STYLE_HEADING_3;
			outlineTitle = "    "+outlineTitle;
		}
		rtfGsBt.setAlignment(Element.ALIGN_LEFT);
        rtfGsBt.setStyle(font.getStyle());
		rtfGsBt.setSpacingBefore(6);
		rtfGsBt.setSpacingAfter(6);
		rtfGsBt.setSize(14f);
		rtfGsBt.setLineLeading(5);
		paragraph = new Paragraph(outlineTitle,rtfGsBt);
		document.add(paragraph); 
	}
	
	/**
	 * 插入大纲标题，标题样式由参数定义
	 * @param document 文档对象
	 * @param outlineTitle 大纲内容
	 * @param outline 大纲级别，取值为1、2、3之一
	 * @param fontSize 字体大小
	 * @param fontStyle 字体样式
	 * @param elementAlign 标题位置
	 */
	public void insertOutlineTitle(Document document,String outlineTitle,int outline,int fontSize,int fontStyle,int elementAlign) throws DocumentException, IOException{
		RtfParagraphStyle rtfGsBt = null;
		if(outline==1){
			rtfGsBt = RtfParagraphStyle.STYLE_HEADING_1;
		}else if(outline==2){
			rtfGsBt = RtfParagraphStyle.STYLE_HEADING_2;
		}else if(outline==3){
			rtfGsBt = RtfParagraphStyle.STYLE_HEADING_3;
		}
		rtfGsBt.setAlignment(elementAlign); 
		rtfGsBt.setStyle(fontStyle);
		rtfGsBt.setSize(fontSize);
		rtfGsBt.setSpacingBefore(6);
		rtfGsBt.setSpacingAfter(6);
		Paragraph paragraph = new Paragraph(outlineTitle,rtfGsBt);
		document.add(paragraph); 
	}
	

}
