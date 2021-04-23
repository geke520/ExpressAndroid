package club.codeapes.common.path;


public class PathUtil {

	public static String getPath(){
		String os_name = System.getProperties().get("os.name").toString().toLowerCase();
		if(os_name.indexOf("windows")!=-1){
			return PathUtil.class.getClassLoader().getResource("\\").getPath();
		}else if(os_name.indexOf("linux")!=-1){
			return PathUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		}
		return null;
	}
	
}
