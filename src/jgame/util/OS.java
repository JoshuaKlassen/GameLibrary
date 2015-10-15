package jgame.util;

public enum OS {
	WINDOWS, MAC, LINUX, OTHER("?.?.?.?");

	OS() {
		version = System.getProperty("os.version");
	}

	OS(String ver) {
		version = ver;
	}

	private String version;

	/**
	 * Returns a string representation of the operating system
	 * 
	 * @return string representation of the operating system (name &
	 *         version)
	 */
	public String toString() {
		return this.name() + " (" + version + ")";
	}
}