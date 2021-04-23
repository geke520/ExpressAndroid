package club.codeapes.common.other;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 初始版本： 1.0.0.0
 * 完成日期： 2012-10-08 上午11:20:17
 * 加密工具
 */
public class EncryptUtil {
	
	private static byte[] naRand = new byte[]{25,12,27,2,15,9,17,8,19,0,11,7,17};

	/**
	 * MD5加密
	 * @param inputText 加密文本
	 * @return 密文
	 */
	public static String encryptMd5(String inputText) {
		return encryptText(inputText, "md5");
	}
	
	/**
	 * SHA加密
	 * @param inputText 加密文本
	 * @return 密文
	 */
	public static String encryptSha(String inputText) {
		return encryptText(inputText, "sha");
	}
	
	
	/**
	 * 加密算法
	 * @param inputText 加密文本
	 * @param algorithmName 加密方式  md5 sha
	 * @return
	 */
	private static String encryptText(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("无加密内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			return hexadecimal(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}
	
	/**
	 * 获取十六进制字符串
	 * @param arr 
	 * @return 十六进制字符串
	 */
	private static String hexadecimal(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
	
	/**
	 * 对字符串进行加密
	 * @param srcStr 源字符串
	 * @return 密文
	 */
	public static String encryptStrRe(String srcStr){
		String returnStr="";
		byte[] arrB = srcStr.getBytes();
		byte[] resB=new byte[arrB.length];
		int j=0;
		for(int i=0;i<arrB.length;i++){
			resB[i]=(byte) (arrB[i]+naRand[j++]);
			if(j==13)j=0;
		}
		returnStr=new String(resB);
		return returnStr;
	}

	/**
	 * 对字符串进行解密
	 * @param srcStr 源字符
	 * @return 明文
	 */
	public static String decryptStrRe(String srcStr){
		String returnStr="";
		byte[] arrB = srcStr.getBytes();
		byte[] resB=new byte[arrB.length];
		int j=0;
		for(int i=0;i<arrB.length;i++){
			resB[i]=(byte) (arrB[i]-naRand[j++]);
			if(j==13)j=0;
		}
		returnStr=new String(resB);
		return returnStr;
	}

	/**
	 * 替换方式的文件加密
	 * @param fileNameIn 源文件
	 * @param fileNameOut 加密后文件
	 * @return
	 */
	public static boolean encryptFileRe(String fileNameIn,String fileNameOut) {		
		  byte[] turn=new byte[13];
		  int length=0,i=0;
		  boolean bReturn=false;
		  try{
			  File objFile=new File(fileNameIn);
			  if(!objFile.exists()) return false;
			  DataInputStream myDataInputStream = new DataInputStream(new FileInputStream(fileNameIn));
			  DataOutputStream myDataOutputStream = new DataOutputStream(new FileOutputStream(fileNameOut));

			  if(myDataInputStream.available() !=0) {
			    while(true){
			      length=myDataInputStream.read(turn);

			      for(i=0;i<length;i++){
			         turn[i]+=naRand[i];
			      }
			      if(length==-1) break;
			      else  myDataOutputStream.write(turn,0,length);
			    }
			    bReturn =true;
			  }
			  myDataInputStream.close();
			  myDataOutputStream.close();		
		  }catch (Exception e) {
				System.out.println("encryptFileRe:" + e.getMessage());
		  }
		return bReturn;
	}
	
	/**
	 * 替换方式的文件解密
	 * @param fileNameIn 已加密文件
	 * @param fileNameOut 解密后文件
	 * @return
	 */
	public static boolean decryptFileRe(String fileNameIn,String fileNameOut) {		
		  byte[] turn=new byte[13];
		  int length=0,i=0;
		  boolean bReturn=false;
		  
		  try{
			  File objFile=new File(fileNameIn);
			  if(!objFile.exists()) return false;
			  DataInputStream myDataInputStream = new DataInputStream(new FileInputStream(fileNameIn));
			  DataOutputStream myDataOutputStream = new DataOutputStream(new FileOutputStream(fileNameOut));

			  if(myDataInputStream.available() !=0) {
			    while(true){
			      length=myDataInputStream.read(turn);

			      for(i=0;i<length;i++){
			         turn[i]-=naRand[i];
			      }
			      if(length==-1) break;
			      else  myDataOutputStream.write(turn,0,length);
			    }
			    bReturn =true;
			  }
			  myDataInputStream.close();
			  myDataOutputStream.close();		 
		  }catch (Exception e) {
				System.out.println("FileTools decrypt:" + e.getMessage());
		  }
		return bReturn;
	}
}
