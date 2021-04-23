package club.codeapes.common.hardware;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

import com.sun.management.OperatingSystemMXBean;

public class HardWareUtils {

	public HardWareUtils() {
	}
	
	 /**
     * Return Opertaion System Name;
     *
     * @return os name.
     */
    public static String getOsName() {
        String os = "";
        os = System.getProperty("os.name");
        
        return os;
    }


	/**
	 * Linux不可用
	 * @return
	 */
	public static String getHdSerialInfo() {

		String line = "";
		String HdSerial = "";// 记录硬盘序列号
		try {
			Process proces = Runtime.getRuntime().exec("cmd /c dir c:");// 获取命令行参数
			BufferedReader buffreader = new BufferedReader(
					new InputStreamReader(proces.getInputStream()));
			while ((line = buffreader.readLine()) != null) {

				if (line.indexOf("卷的序列号是 ") != -1) { // 读取参数并获取硬盘序列号

					HdSerial = line.substring(line.indexOf("卷的序列号是 ")
							+ "卷的序列号是 ".length(), line.length());
					break;
					// System.out.println(HdSerial);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return HdSerial; // 返回硬盘序列号
	}

	public static String getHardDisk() {
		String str = "";
		File[] roots = File.listRoots();
		for (File _file : roots) {
			str += "<font style='font-weight: bold;'>" + _file.getPath()
					+ " 盘信息：</font>          总空间 = " + _file.getTotalSpace()
					/ 1024 / 1024 / 1000 + "G";
			str += " | 可用空间  = " + _file.getUsableSpace() / 1024 / 1024 / 1024
					+ "G";
			str += " | 已使用空间  = "
					+ (_file.getTotalSpace() - _file.getFreeSpace()) / 1024
					/ 1024 / 1024 + "G ";
			str += " | 自由空间 = " + _file.getFreeSpace() / 1024 / 1024 / 1024
					+ "G </br>";
		}
		return str;
	}

	// 获取内存使用率 
	public static String getTotalPhysicalMemorySize() {
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();// 总的物理内存+虚拟内存
		String str = osmxb.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024
				+ "G";
		return str;
	}

	// 获取内存使用率 
	public static String getUsedMemery() {
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();// 总的物理内存+虚拟内存
		long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();//  剩余的物理内存
		long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
		Double compare = (Double) (1 - freePhysicalMemorySize * 1.0
				/ totalvirtualMemory) * 100;
		String str = "内存已使用:" + compare.intValue() + "%";
		return str;
	}

	/**
	 * 获取主板序列号 WINDOWS
	 * @return
	 */
	public static String getMotherboardSNForWindows() {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n"
					+ "   (\"Select * from Win32_BaseBoard\") \n"
					+ "For Each objItem in colItems \n"
					+ "    Wscript.Echo objItem.SerialNumber \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

	/**
	 * 获取硬盘序列号
	 * 
	 * @param drive
	 *            盘符
	 * @return
	 */
	public static String getHardDiskSN(String drive) {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ drive
					+ "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber"; // see note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

	/**
	 * 获取CPU序列号 WINDOWS
	 * 
	 * @return
	 */
	public static String getCPUSerialForWindows() {
		String result = "";
		try {
			File file = File.createTempFile("tmp", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n"
					+ "   (\"Select * from Win32_Processor\") \n"
					+ "For Each objItem in colItems \n"
					+ "    Wscript.Echo objItem.ProcessorId \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
			file.delete();
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		if (result.trim().length() < 1 || result == null) {
			result = "无CPU_ID被读取";
		}
		return result.trim();
	}

	  
	/**
	 * Linux
	 * Returns the HD SerialNo. of the computer.
	 *
	 * @return the HD SerialNo.
	 */
	public static String getHDSerialNoForLinux() {
		String sn = "";
		String os = getOsName();
		if (os.startsWith("Linux")) {
			if (isSCSIorIDEHD() == "scsi") {
				// 注意如果是ubuntu等系统用户，本身没有root权限，请先：chmod 777 /dev/sda
				String command = "hdparm -i /dev/sda";
				Process p;
				try {
					p = Runtime.getRuntime().exec(command);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(p.getInputStream()));
					String line;
					while ((line = br.readLine()) != null) {
						if (line.contains("SerialNo")) {
							int index = line.indexOf("SerialNo")
									+ "SerialNo".length() + 1;
							sn = line.substring(index);
							break;
						}
					}
					br.close();
				} catch (IOException e) {
				}
			} else if (isSCSIorIDEHD() == "ide") {
				// 注意如果是ubuntu等系统用户，本身没有root权限，请先：chmod 777 /dev/sda
				String command = "hdparm -i /dev/hda";
				Process p;
				try {
					p = Runtime.getRuntime().exec(command);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(p.getInputStream()));
					String line;
					while ((line = br.readLine()) != null) {
						if (line.contains("SerialNo")) {
							int index = line.indexOf("SerialNo")
									+ "SerialNo".length() + 1;
							sn = line.substring(index);
							break;
						}
					}
					br.close();
				} catch (IOException e) {
				}
			} else {
				sn = "unknown";
			}

		}
		sn = sn.trim();
		return sn;
	}

	private static String isSCSIorIDEHD() {
		String os = getOsName();
		if (os.startsWith("Linux")) {
			// ubuntu系统下确定有root权限
			String command = "fdisk -l";
			Process p;
			try {
				p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.contains("sd")) {
						return "scsi";
					}
					if (line.contains("hd")) {
						return "ide";
					}
				}
				br.close();
			} catch (IOException e) {
			}
		}
		return "unkonwn"; // 未知类型
	}
	
	
	/****************************************************** MAC地址 *****************************************************/
	/** 
     * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取. 
     * 如果有特殊系统请继续扩充新的取mac地址方法. 
     *  
     * @return mac地址 
     */  
	private static String getUnixMACAddress() {  
        String mac = null;  
        BufferedReader bufferedReader = null;  
        Process process = null;  
        try {  
            // linux下的命令，一般取eth0作为本地主网卡  
            process = Runtime.getRuntime().exec("ifconfig eth0");  
            // 显示信息中包含有mac地址信息  
            bufferedReader = new BufferedReader(new InputStreamReader(  
                    process.getInputStream()));  
            String line = null;  
            int index = -1;  
            while ((line = bufferedReader.readLine()) != null) {  
                // 寻找标示字符串[hwaddr]  
                index = line.toLowerCase().indexOf("hwaddr");  
                if (index >= 0) {// 找到了  
                    // 取出mac地址并去除2边空格  
                    mac = line.substring(index + "hwaddr".length() + 1).trim();  
                    break;  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            bufferedReader = null;  
            process = null;  
        }  
        return mac;  
    }  
  
    /** 
     * 获取widnows网卡的mac地址. 
     *  
     * @return mac地址 
     */  
    private static String getWindowsMACAddress() {  
        String mac = null;  
        BufferedReader bufferedReader = null;  
        Process process = null;  
        try {  
            // windows下的命令，显示信息中包含有mac地址信息  
            process = Runtime.getRuntime().exec("ipconfig /all");  
            bufferedReader = new BufferedReader(new InputStreamReader(  
                    process.getInputStream()));  
            String line = null;  
            int index = -1;  
            while ((line = bufferedReader.readLine()) != null) {  
                System.out.println(line);  
                // 寻找标示字符串[physical  
                index = line.toLowerCase().indexOf("physical address");  
                  
                if (index >= 0) {// 找到了  
                    index = line.indexOf(":");// 寻找":"的位置  
                    if (index >= 0) {  
                        System.out.println(mac);  
                        // 取出mac地址并去除2边空格  
                        mac = line.substring(index + 1).trim();  
                    }  
                    break;  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            bufferedReader = null;  
            process = null;  
        }  
  
        return mac;  
    }  
  
    /** 
     * windows 7 专用 获取MAC地址 
     *  
     * @return 
     * @throws Exception 
     */  
    private static String getMACAddress() throws Exception {  
          
        // 获取本地IP对象  
        InetAddress ia = InetAddress.getLocalHost();  
        // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。  
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();  
  
        // 下面代码是把mac地址拼装成String  
        StringBuffer sb = new StringBuffer();  
  
        for (int i = 0; i < mac.length; i++) {  
            if (i != 0) {  
                sb.append("-");  
            }  
            // mac[i] & 0xFF 是为了把byte转化为正整数  
            String s = Integer.toHexString(mac[i] & 0xFF);  
            sb.append(s.length() == 1 ? 0 + s : s);  
        }  
  
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回  
        return sb.toString().toUpperCase();  
    }  
  
    /** 
     * 获取mac地址的方法
     * @param argc 运行参数. 
     * @throws Exception 
     */  
    public static String getMacForWinForLinux() throws Exception {  
        String os = getOsName();  
        System.out.println(os); 
        String mac = "";
        if (os.equals("windows 7")) {  
             mac = getMACAddress();  
            System.out.println(mac);  
        } else if (os.startsWith("windows")) {  
            // 本地是windows  
             mac = getWindowsMACAddress();  
            System.out.println(mac);  
        } else {  
            // 本地是非windows系统 一般就是unix  
             mac = getUnixMACAddress();  
            System.out.println(mac);  
        }
		return mac;  
    }  
    
    public static void main(String args[]) {
		System.out.println("获取硬件信息");
//		System.out.println("CPU  SN:" + HardWareUtils.getCPUSerial());
//		System.out.println("主板  SN:" + HardWareUtils.getMotherboardSN());
//		System.out.println("C盘   SN:" + HardWareUtils.getHardDiskSN("c"));
//		System.out.println("MAC  SN:" + HardWareUtils.getMac());
//		System.out.println("内存：" + getTotalPhysicalMemorySize());
//		System.out.println("硬盘大小：" + getHardDisk());
//		System.out.println("Windows硬盘序列号：" + getHdSerialInfo());
		
		System.out.println("Operation System=" + getOsName());
		System.out.println("HD SerialNo=" + getHDSerialNoForLinux());
	}
}