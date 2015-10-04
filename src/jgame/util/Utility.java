package jgame.util;

public class Utility {

	public enum OS {
		WINDOWS,
		MAC,
		LINUX,
		OTHER ("?.?.?.?");
		
		OS(){
			version = System.getProperty("os.version");
		}
		
		OS(String ver){
			version = ver;
		}
		private String version;
		
		/**
		 * Returns a string representation of the operating system
		 * @return string representation of the operating system (name & version)
		 */
		public String toString() { return this.name() + " (" + version + ")"; }	
	}

	/**
	 * Returns the name and version of the 
	 * <br/>operating system as a OS enumeration.
	 * @return the corresponding OS enumeration
	 */
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
