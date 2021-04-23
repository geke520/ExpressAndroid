package club.codeapes.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import club.codeapes.common.lang.Array;

/**
 * 
 * @Description
 * @version 1.0
 * @date 2015年4月4日 下午9:45:03
 */
public class FileUtil {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(FileUtil.class);

	/**
	 * 写文件
	 * 
	 * @param content
	 *            文件内容
	 * @param filePath
	 *            文件路径（包括文件名）
	 * @param append
	 *            是否追加内容
	 * @param encoding
	 *            字符编码
	 * @throws IOException
	 */
	public static void write(String content, String filePath, boolean append,
			String encoding) throws IOException {
		File parent = new File(new File(filePath).getParent()); // 得到父文件夹
		if (!parent.exists()) {
			parent.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(filePath).getAbsolutePath(), append);
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(fos, encoding));
		pw.print(content);
		pw.close();
		fos.close();
	}

	/**
	 * 写文件（默认不追加内容及编码为UTF-8）
	 * 
	 * @param content
	 * @param filePath
	 * @throws IOException
	 */
	public static void write(String content, String filePath)
			throws IOException {
		write(content, filePath, false, "UTF-8");
	}

	/**
	 * 追加内容
	 * 
	 * @param content
	 * @param filePath
	 * @throws IOException
	 */
	public static void append(String content, String filePath)
			throws IOException {
		write(content, filePath, true, "UTF-8");
	}

	/**
	 * 写文件
	 * 
	 * @param data
	 * @param fullPath
	 * @throws IOException
	 */
	public static void write(byte[] data, String fullPath) throws IOException {
		File parent = new File(new File(fullPath).getParent()); // 得到父文件夹
		if (!parent.exists()) {
			parent.mkdirs();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fullPath);
			fos.write(data);
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	/**
	 * 移动文件或者文件夹,如从e:/aa.txt到e:/tmp/aa.txt, 从e:/aa到e:/bb/aa
	 * 
	 * @param input
	 *            String
	 * @param output
	 *            String
	 */
	public static void move(String source, String target) {
		File sourceFile = new File(source);
		File targetFile = new File(target);
		try {
			sourceFile.renameTo(targetFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copy(String source, String target) throws IOException {
		final int BUFSIZE = 65536;
		File f = new File(source);
		File f2 = new File(target);
		if (!f.exists()) {
			return;
		}
		if (!f2.exists()) {
			File parent = new File(f2.getParent()); // 得到父文件夹
			if (!parent.exists()) {
				parent.mkdirs();
			}
			f2.createNewFile();
		}
		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(f));
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(target));
		byte[] buf = new byte[BUFSIZE];
		int len = 0;
		while ((len = bis.read(buf)) != -1) {
			bos.write(buf, 0, len);
		}
		bos.close();
		bis.close();
	}

	/**
	 * 复制文件夹及其子文件夹
	 * 
	 * @param source
	 *            String 源文件夹,如: d:/tmp
	 * @param dest
	 *            String 目标文件夹,如: e:/tmp
	 * @throws IOException
	 */
	public static void copyFolder(String source, String target)
			throws IOException {

		File f1 = new File(source);
		File f2 = new File(target);
		if (!f1.exists()) {
			return;
		}
		if ((!f2.exists()) && (f1.isDirectory())) {
			f2.mkdirs();
		}
		String[] fileList = f1.list();
		if (fileList == null) {
			return;
		}
		for (String file : fileList) {
			String newSource = f1.getAbsolutePath() + File.separator + file;
			String newTarget = f2.getAbsolutePath() + File.separator + file;
			if (new File(newSource).isDirectory()) {
				logger.info("正在复制文件夹从" + newSource + "到" + newTarget + "。。。");
				copyFolder(newSource, newTarget);
			} else {
				logger.info("正在复制文件从" + newSource + "到" + newTarget + "。。。");
				copy(newSource, newTarget);
			}
		}
	}

	/**
	 * 删除某个文件夹下的所有文件和所有子文件夹, 不包括它自己
	 * 
	 * @param folder
	 *            String
	 * @param delFolder
	 *            boolean 是否连文件夹一起删除
	 * @throws IOException
	 */
	public static void deleteSubFiles(String folder, boolean delFolder)
			throws IOException {
		File f1 = new File(folder);

		if (!f1.exists()) {
			return;
		}
		String[] fileList = f1.list();
		if (fileList == null) { // 空文件夹
			if (delFolder) {
				logger.info("正在删除文件夹" + f1.getAbsolutePath() + "。。。");
				f1.delete(); // 删除文件夹
			}
			return;
		}
		for (String file : fileList) {
			String newSource = f1.getAbsolutePath() + File.separator + file;
			if (new File(newSource).isDirectory()) {
				deleteSubFiles(newSource, delFolder);
				if (delFolder) {
					logger.info("正在删除文件夹" + newSource + "。。。");
					new File(newSource).delete(); // 删除文件夹
				}
			} else {
				logger.info("正在删除文件" + newSource + "。。。");
				new File(newSource).delete();
			}
		}// for
	}

	/**
	 * 删除整个文件夹，包括它本身
	 * 
	 * @param dir
	 *            String
	 * @throws IOException
	 */
	public static void deleteFolder(String dir) throws IOException {
		deleteSubFiles(dir, true);
		File f = new File(dir);
		logger.info("正在删除文件夹" + f.getAbsolutePath() + "...");
		f.delete();
	}

	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String readText(Path path) throws IOException {
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
		}
		return sb.toString();
	}

	public static String read(String path, String separator,String encoding){
		return read(new File(path),separator,encoding);
	}
	
	
	public static String read(File file, String separator,String encoding){
		String _separator = separator == null ? "" : separator;
		Array array = new Array();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));
			String data = br.readLine();// 一次读入一行，直到读入null为文件结束
			array.push(data);
			while (data != null) {
				data = br.readLine(); // 接着读下一行
				array.push(data);
			}
			return array.join(_separator);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static String read(String path, String separator)  {
		return read(new File(path),separator,"utf-8");
	}

	public static String read(File file, String separator) {
		return read(file,separator,"utf-8");
	}

	public static byte[] readStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = is.read(b, 0, b.length)) != -1) {
			baos.write(b, 0, len);
		}
		return baos.toByteArray();
	}

	public static String readReader(Reader reader) throws IOException {
		BufferedReader bufReader = new BufferedReader(reader);
		StringWriter writer = new StringWriter();
		String line = null;
		while ((line = bufReader.readLine()) != null) {
			writer.append(line);
		}

		return writer.toString();
	}

	public static byte[] readFile(URL url) throws IOException,
			URISyntaxException {
		String protocol = url.getProtocol();
		if (protocol.equals("file")) {
			return Files.readAllBytes(Paths.get(url.toURI()));
		} else if (protocol.equals("jar")) {
			JarURLConnection conn = null;
			InputStream is = null;
			try {
				conn = (JarURLConnection) url.openConnection();
				conn.setDoInput(true);
				is = conn.getInputStream();

				return readStream(is);
			} finally {
				if (is != null) {
					is.close();
				}
			}
		} else if (protocol.equals("http")) {
			HttpURLConnection conn = null;
			InputStream is = null;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				is = conn.getInputStream();
				return readStream(is);
			} finally {
				if (is != null) {
					is.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			}
		} else if (protocol.equals("https")) {
			HttpsURLConnection conn = null;
			InputStream is = null;
			try {
				conn = (HttpsURLConnection) url.openConnection();
				conn.setDoInput(true);
				is = conn.getInputStream();
				return readStream(is);
			} finally {
				if (is != null) {
					is.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			}
		}
		return null;
	}

	/**
	 * 判断一个文件是否为文本文件 顺序地读出这个文件的每一个字节，如果文件里有一个字节的值等于0，那么这个文件就不是文本文件；
	 * 反之，如果这个文件中没有一个字节的值是0的话，就可以判定这个文件是文本文件了。
	 * 
	 * @param file
	 *            String 文件名,包括路径
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean isTextFile(String file) throws IOException {
		boolean isText = true;
		FileInputStream fis = new FileInputStream(file);
		int data;
		while ((data = fis.read()) != -1) {
			if (data == 0) {
				isText = false;
				break;
			}// if
		}// while
		fis.close();
		return isText;
	}

	public static String formatFileSize(long fileSize) {
		StringBuffer sb = new StringBuffer();
		if (fileSize < 1024) {
			sb.append(fileSize);
			sb.append("B");
		}
		if (1024 * 1024 > fileSize && fileSize >= 1024) {
			String num = String.valueOf((double) (fileSize / 1024));
			sb.append(num.substring(0, num.indexOf(".") + 2));
			sb.append("KB");
		}
		if (1024 * 1024 * 1024 > fileSize && fileSize >= 1024 * 1024) {
			String num = String.valueOf((double) (fileSize / (1024 * 1024)));
			sb.append(num.substring(0, num.indexOf(".") + 2));
			sb.append("MB");
		}
		if (1024 * 1024 * 1024 * 1024 >= fileSize
				&& fileSize >= 1024 * 1024 * 1024) {
			String num = String
					.valueOf((double) (fileSize / (1024 * 1024 * 1024)));
			sb.append(num.substring(0, num.indexOf(".") + 2));
			sb.append("GB");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("E:/api/1.txt");
		System.out.println(FileUtil.read(file, null,"gbk"));
	}

	public static String getFileEncoding(String path) throws Exception {
		return getFileEncoding(new File(path));
	}
	
	public static String getFileEncoding(File file){
		BufferedInputStream bin = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(file));
			int p = (bin.read() << 8) + bin.read();
			String code = null;
			switch (p) {
			case 0xefbb:
				code = "UTF-8";
				break;
			case 0xfffe:
				code = "Unicode";
				break;
			case 0xfeff:
				code = "UTF-16BE";
				break;
			default:
				code = "GBK";
			}
			return code;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bin != null){
				try {
					bin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取XML文件内标签的值
	 * 
	 * @param fileName
	 *            文件路径
	 * @param tag
	 *            标签名
	 * @return 标签值
	 */
	public static String getValueByTag(String fileName, String tag) throws Exception {
		String strReturn = "";
		java.io.File objFile = new java.io.File(fileName);
		String dataStr = "", aLine = "";// 得到文件中的内容
		int pos1 = -1, pos2 = -1;

		if (objFile.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			aLine = reader.readLine();
			while (aLine != null) {
				dataStr += aLine;
				aLine = reader.readLine();
				pos1 = dataStr.indexOf("<" + tag + ">");
				pos2 = dataStr.indexOf("</" + tag + ">");
				if ((pos1 >= 0) && (pos2 > 0)) {
					strReturn = dataStr.substring(pos1 + tag.length() + 2, pos2);
					break;
				}
			}
			reader.close();
		}
		objFile = null;
		return strReturn;
	}
	
	 /**
	  * 设置XML文件内标签的值
	 * @param fileName 文件路径
	 * @param tag 标签名
	 * @param value 标签值
	 * @return 成功否
	 */
	public static boolean setValueByTag(String fileName, String tag,String value){
		boolean bReturn=false;
			try{
				java.io.File objFile = new java.io.File(fileName);  
			    String aLine="";
			    StringBuffer dataBuf= new StringBuffer() ;
			    int pos1=-1,pos2=-1;
			    
			    if(objFile.exists()){		 	         
			        BufferedReader reader=new BufferedReader(new FileReader(fileName));
			        aLine = reader.readLine();		        	        
			        while(aLine!=null){		        			        				        	
			        	dataBuf.append(aLine);
			        	aLine = reader.readLine();	
			        	if (!bReturn){
				        	pos1 =dataBuf.indexOf("<"+tag+">");
				        	pos2 =dataBuf.indexOf("</"+tag+">");
				        	if ((pos1>=0)&&(pos2>0)){
				        		dataBuf = new StringBuffer(dataBuf.substring(0,pos1)+"<"+tag+">"+value+dataBuf.substring(pos2));
				        		bReturn = true;
				        	}
			        	}
			        }
			        if (!bReturn){	
			        	dataBuf.append("\r\n<"+tag+">"+value+"</"+tag+">");			        				        	
			        }
			        reader.close();	 
			        //替换原文件内容
			        FileUtil.copy(fileName,fileName+"bak");			        
			        objFile.createNewFile();
			        FileWriter write=new FileWriter(objFile);
			        write.write(dataBuf.toString());
			        write.close();
			        bReturn = true;
			        objFile = new java.io.File(fileName+"bak");  
			        if (objFile.exists()) objFile.delete();
			    }
			    objFile=null;		    
			}catch (Exception e) {
				bReturn = false;
				System.out.println("FileTools setValueByTag:" + e.getMessage());
		    }
			return bReturn;
	}
	/**
	 * 下载
	 * @param response
	 * @param fileName
	 * @param filePath
	 * @throws Exception
	 */
	public static void FileDown(HttpServletResponse response,String fileName,String filePath) throws Exception {
		// 设置响应头，控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename="
				+ URLEncoder.encode(fileName, "UTF-8"));
		
		// 读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(filePath);
		// 创建输出流
		OutputStream out = response.getOutputStream();
		// 创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		// 循环将输入流中的内容读取到缓冲区当中
		while ((len = in.read(buffer)) > 0) {
			// 输出缓冲区的内容到浏览器，实现文件下载
			out.write(buffer, 0, len);
		}
		// 关闭文件输入流
		in.close();
		// 关闭输出流
		out.close();
	}
	
	 /**
     * 递归删除文件夹及文件夹下的文件
     * @param path
     */
    public static void deleteAllFilesOfDir(File path) {  
	    if (!path.exists())  
	        return;  
	    if (path.isFile()) {  
	        path.delete();  
	        return;  
	    }  
	    File[] files = path.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        deleteAllFilesOfDir(files[i]);  
	    }  
	    path.delete();  
	}
}
