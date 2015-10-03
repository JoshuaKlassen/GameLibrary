package jgame.util;

public class Utility {

	//TODO Design this better so users can see
	public enum OS {
		WINDOWS,
		MAC,
		LINUX,
		OTHER ("?.?.?.?", "Unkown OS");
		
		OS(){
			version = System.getProperty("os.version");
			name = System.getProperty("os.name");
		}
		
		OS(String ver, String name){
			version = ver;
			this.name = name;
		}
		private String version;
		private String name;
		public String toString() { return "OS: " + name + " (" + version + ")"; }	
	}

	
	public static OS getOperatingSystem(){		
		OS result = null;
		String os = System.getProperty("os.name").toLowerCase();
		
		if(os.contains("win")) result = OS.WINDOWS;
		else if(os.contains("mac")) result = OS.MAC;
		else if(os.contains("nix") || os.contains("nux") || os.contains("aix")) result = OS.LINUX;
		else result = OS.OTHER;
		
		return result;
	}
	
}
