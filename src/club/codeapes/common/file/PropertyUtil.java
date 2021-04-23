package club.codeapes.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * 针对属性文件的读写操作工具类
 * @Description 针对属性文件的读写操作工具类
 * @version 1.0 
 * @date 2015年4月4日 下午9:26:56
 */
public class PropertyUtil {

	/*
	 * 存放属性文件键值的map对象
	 */
	private HashMap<String, String> map = new HashMap<String, String>();
	
	private Properties properties;
	
	private File file;
	/**
	 * 文件形式加载属性文件
	 * @param file
	 * @throws IOException
	 */
	public PropertyUtil(File file) throws IOException{
		this.file = file;
		FileInputStream fis = null;
		
		try{
			fis = new FileInputStream(file);
			load(fis);
		}finally{
			if(fis != null){
				fis.close();
			}
		}
		
	}
	
	public Properties getProperties(){
		return properties;
	}
	/**
	 * 文件全路径形式加载属性文件
	 * @param file
	 * @throws IOException
	 */
	public PropertyUtil(String file) throws IOException{
		this(new File(file));
	}
	/**
	 * 
	 * @throws IOException
	 */
	public void reload() throws IOException{
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			load(fis);
		}finally{
			if(fis != null){
				fis.close();
			}
		}
	}
	
	/**
	 * 加载属性文件
	 * @param is
	 * @throws IOException
	 */
	private void load(InputStream is)throws IOException{
		properties = new Properties();
		properties.load(is);
		Enumeration<?> pName = properties.propertyNames();
		while(pName.hasMoreElements()){
			String key = (String)pName.nextElement();
			String value = properties.getProperty(key);
			map.put(key, value);
		}
	}
	/**
	 * 通过key获取值
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		return map.get(key);
	}
	/**
	 * 通过key获取值
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key){
		if(map.containsKey(key)){
			return Integer.parseInt(map.get(key));
		}
		return null;
	}
	/**
	 * 通过key获取值
	 * @param key
	 * @return
	 */
	public Long getLong(String key){
		if(map.containsKey(key)){
			return Long.parseLong(map.get(key));
		}
		return null;
	}
	
	public Boolean getBoolean(String key){
		if(map.containsKey(key)){
			return Boolean.parseBoolean(map.get(key));
		}
		return null;
	}
	/**
	 * 设置新的属性
	 * @param key
	 * @param value
	 * @throws Exception 
	 */
	public void setValue(String key,String value) throws Exception {  
		FileOutputStream outputFile = null;
		FileInputStream inputFile = null;
        try {
        	properties.setProperty(key, value);  
            outputFile = new FileOutputStream(this.file.getAbsolutePath());   
            properties.store(outputFile, "");  
            outputFile.flush(); 
            //重新加载
            inputFile = new FileInputStream(this.file);
            load(inputFile);
        } catch (Exception e) {  
            throw e;  
        }finally{  
			try {
				if (outputFile != null) {
					outputFile.close();
				}
				if (inputFile != null) {
					inputFile.close();
				}
			} catch (Exception e) {
			}
        } 
    } 
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {
			PropertyUtil pu = new PropertyUtil("d:/a.txt");
			System.out.println(pu.getValue("a"));
			pu.setValue("b", "dddddd");
			System.out.println(pu.getValue("b"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
