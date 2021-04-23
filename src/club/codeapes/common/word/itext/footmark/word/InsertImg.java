package club.codeapes.common.word.itext.footmark.word;

import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
/*
 * 插入图片类
 */
public class InsertImg {

	/**
	 * 插入图片如果在这里修改长和宽，图片的大小还是原来的大小，所以最好先设置图片合适的大小，再插入
	 * @param document 文档对象
	 * @param imgUrl 图片路径 
	 * @param imageAlign 显示位置 
	 * @param height 显示高度 
	 * @param weight 显示宽度 
	 * @throws MalformedURLException 
	 * @throws IOException 
	 * @throws DocumentException 
	 */  
	public void insertImgByAlign(Document document,String imgUrl,int imageAlign,int height,
			int weight,int percent,int heightPercent,int weightPercent,int rotation) throws MalformedURLException, IOException, DocumentException{  
		Image img = Image.getInstance(imgUrl);
		if(img==null)  
			return;  
		img.setAlignment(imageAlign);  
		//img.scaleAbsolute(height, weight);  
		img.scalePercent(percent);  
		img.scalePercent( weightPercent,heightPercent);  
		//img.setRotation(rotation);  
		document.add(img);  
	}  

}
