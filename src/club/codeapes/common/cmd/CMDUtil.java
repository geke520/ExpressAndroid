package club.codeapes.common.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

public class CMDUtil {

	public static void main(String[] args) {
//		exec("omp -u admin -w admin --xml='<create_target><name>xxxx</name><hosts>192.168.118.72</hosts></create_target>'");
	
//		System.out.println(str.replaceAll("\\s{1,}", ","));
//	
		System.out.println("xxx\ndfsdfsdf".split("[\n]").length);
	
	}
	
	public static String exec(String command){
		return exec(command,"UTF-8");
	}
	public static String exec(String[] command){
		return exec(command,"UTF-8");
	}
	/** 
     * 读取控制命令的输出结果 
     * 
     * @param cmd                命令 
     * @param isPrettify 返回的结果是否进行美化（换行），美化意味着换行，默认不进行美化,当此参数为null时也不美化， 
     * @return 控制命令的输出结果 
     */ 
	public static String exec(String command,String encoding){
		String rs = null;
		Process process = execProcess(command);
		InputStream is = null;
		try{
			is = process.getInputStream();
			rs = IOUtils.toString(is, encoding);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    IOUtils.closeQuietly(is);
		}
		return rs; 
	}
	
	public static String exec(String[] command,String encoding){
		String rs = null;
		Process process = execProcess(command);
		InputStream is = null;
		try{
			is = process.getInputStream();
			rs = IOUtils.toString(is, encoding);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    IOUtils.closeQuietly(is);
		}
		return rs; 
	}
	public static Process execProcess(String[] command) {
		try {
			return Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 执行进程
	 * @param command
	 * @return
	 */
	public static Process execProcess(String command) {
		try {
			return Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 执行某操作系统命令,返回成功否
	 * @param cmd
	 * @param path
	 * @return
	 */
	public static boolean exeCmd(String cmd, File path) {
		Process proc;
		java.io.BufferedReader procin;
		boolean bRet = false;
		String[] cmdA = { "/bin/sh", "-c", cmd };
		try {
			if (path != null)
				proc = Runtime.getRuntime().exec(cmdA, null, path);
			else
				proc = Runtime.getRuntime().exec(cmdA);

			procin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String str;
			while ((str = procin.readLine()) != null) {
				System.out.println(str);
			}
			procin.close();
			proc.destroy();
			bRet = true;
		} catch (Exception e0) {
			System.out.println("exeCmd () error:" + e0.getMessage());
		}
		return bRet;
	}
}
