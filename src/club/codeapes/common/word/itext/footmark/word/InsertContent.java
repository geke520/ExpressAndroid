package club.codeapes.common.word.itext.footmark.word;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.rtf.style.RtfFont;

/*
 * 插入文档内容类
 */
public class InsertContent {
	private RtfFont contextFont = new RtfFont("宋体", 12, Font.NORMAL, Color.BLACK);
	/* 
	 * 默认为：行距1.5；左右边距1；段前断后距离1；段首空两个字符；左对齐
	 * @param document 文档对象
	 * @param context 内容 
	 * @throws DocumentException  
	 */  
	public void insertContext(Document document,String content) throws DocumentException, IOException{
		Paragraph paragraph = new Paragraph(content);
		//设置行距
		paragraph.setLeading(25);
		//设置整段的左边距
		paragraph.setIndentationLeft(1f);
		//设置整段的右边距
		paragraph.setIndentationRight(1f);
		//保持段落或者表格的连接不分开
		//paragraph.setKeepTogether(true);
		//段后距离
		paragraph.setSpacingAfter(1f);
		//段前距离
		paragraph.setSpacingBefore(1f);
		//设置第一行空的列数  
		paragraph.setFirstLineIndent(30f);  
		//对齐方式
		paragraph.setAlignment(Element.ALIGN_LEFT);  
		paragraph.setFont(contextFont);  
		document.add(paragraph);  
	}  
	
	
	public void insertContext(Document document,String content,float spacingBefore, float spacingAfter) throws DocumentException, IOException{
		Paragraph paragraph = new Paragraph();
		if(content.indexOf("&JunKs_7") != -1){
			paragraph.add(new Phrase(content.split("&JunKs_7")[0],new RtfFont("宋体", 12, Font.BOLD, Color.BLACK)));
			paragraph.add(new Phrase(content.split("&JunKs_7")[1]));
		}else{
			paragraph.add(content);
		}
		//设置行距
		paragraph.setLeading(25);
		//设置整段的左边距
		paragraph.setIndentationLeft(1f);
		//设置整段的右边距
		paragraph.setIndentationRight(1f);
		//保持段落或者表格的连接不分开
		//paragraph.setKeepTogether(true);
		//段后距离
		paragraph.setSpacingAfter(spacingAfter);
		//段前距离
		paragraph.setSpacingBefore(spacingBefore);
		//设置第一行空的列数  
		paragraph.setFirstLineIndent(30f);  
		//对齐方式
		paragraph.setAlignment(Element.ALIGN_LEFT);  
		paragraph.setFont(contextFont);  
		document.add(paragraph);  
	}  
	
	/**
	 * @param document 文档对象
	 * @param context 内容 
	 * @param fontsize 字体大小 
	 * @param fontStyle 字体样式 
	 * @param elementAlign 对齐方式 
	 * @param leading 行距
	 * @param spacingAfter  离下一段空的行数
	 * @param spacingBefore 离上一段空的行数
	 * @param lineIndent 段首空的列数
	 * @param indentationLeft 设置整段的左边距
	 * @param indentationRight 设置整段的右边距
	 * @param firstLineIndent 设置第一行空的列数 
	 * @param extraParagraphSpace 设置多余的段落空间
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void insertContext(Document document,Font contentFont,String content,int elementAlign,float leading,float indentationLeft,float indentationRight,
			float spacingAfter, float spacingBefore,float firstLineIndent) throws DocumentException, IOException{//,float extraParagraphSpace
			Paragraph paragraph = new Paragraph(content);
				//设置行距
				paragraph.setLeading(leading);
				//设置整段的左边距
				paragraph.setIndentationLeft(indentationLeft);
				//设置整段的右边距
				paragraph.setIndentationRight(indentationRight);
				//保持段落或者表格的连接不分开
				//paragraph.setKeepTogether(true);
				//段后距离
				paragraph.setSpacingAfter(spacingAfter);
				//段前距离
				paragraph.setSpacingBefore(spacingBefore);
				//设置第一行空的列数  
				paragraph.setFirstLineIndent(firstLineIndent);  
				//对齐方式
				paragraph.setAlignment(elementAlign);  
				paragraph.setFont(contentFont);
				//设置多余的段落空间
				//paragraph.setExtraParagraphSpace(extraParagraphSpace);
				
			document.add(paragraph);  
	}  
	
	
	/**
	 * @param document 文档对象
	 * @param context 内容 
	 * @param fontsize 字体大小 
	 * @param fontStyle 字体样式 
	 * @param elementAlign 对齐方式 
	 * @param leading 行距
	 * @param spacingAfter  离下一段空的行数
	 * @param spacingBefore 离上一段空的行数
	 * @param lineIndent 段首空的列数
	 * @param indentationLeft 设置整段的左边距
	 * @param indentationRight 设置整段的右边距
	 * @param firstLineIndent 设置第一行空的列数 
	 * @param extraParagraphSpace 设置多余的段落空间
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void insertContext(Document document,Font contentFont,String lableContent, String content,int elementAlign,float leading,float indentationLeft,float indentationRight,
			float spacingAfter, float spacingBefore,float firstLineIndent) throws DocumentException, IOException{//,float extraParagraphSpace
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Chunk(lableContent, new RtfFont("宋体", contentFont.getSize(), Font.NORMAL, Color.BLACK)));
			paragraph.add(new Chunk(content, contentFont));
				//设置行距
				paragraph.setLeading(leading);
				//设置整段的左边距
				paragraph.setIndentationLeft(indentationLeft);
				//设置整段的右边距
				paragraph.setIndentationRight(indentationRight);
				//保持段落或者表格的连接不分开
				//paragraph.setKeepTogether(true);
				//段后距离
				paragraph.setSpacingAfter(spacingAfter);
				//段前距离
				paragraph.setSpacingBefore(spacingBefore);
				//设置第一行空的列数  
				paragraph.setFirstLineIndent(firstLineIndent);  
				//对齐方式
				paragraph.setAlignment(elementAlign);  
				//paragraph.setFont(contentFont);
				//设置多余的段落空间
				//paragraph.setExtraParagraphSpace(extraParagraphSpace);
				
			document.add(paragraph);  
	} 

}
