package jgame.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//TODO Document
//TODO Serializables

public final class Utility {
	
	private Utility(){}
	
	/**
	 * Returns the name and version of the <br/>
	 * operating system as a OS enumeration.
	 * 
	 * @return the corresponding OS enumeration
	 */
	public static OS getOperatingSystem() {
		OS result = null;
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win"))
			result = OS.WINDOWS;
		else if (os.contains("mac"))
			result = OS.MAC;
		else if (os.contains("nix") || os.contains("nux") || os.contains("aix"))
			result = OS.LINUX;
		else
			result = OS.OTHER;

		return result;
	}
	
	//TODO Document
	public static BufferedImage loadImage(String filePath) {
		try {
			return ImageIO.read(Utility.class
					.getResourceAsStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String[] readFromFile(String filePath){
		ArrayList<String> result = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Utility.class.getResourceAsStream(filePath)));
			String line = "";

			while((line = reader.readLine()) != null)
				result.add(line);
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (String[])result.toArray();
	}
	
	public static boolean writeToFile(String[] output, String filePath){
		
		boolean success = false;
		
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
			
			for(String line : output){
				writer.write(line);
				writer.newLine();
			}
			
			writer.flush();
			writer.close();
		
			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	public static Object readObject(String filepath){
		Object result = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filepath);
			ois = new ObjectInputStream(fis);
			result = ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean writeObject(Serializable object, String filepath){
		File file = new File(filepath);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		boolean result = true;
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
			fos = new FileOutputStream(filepath);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.close();
			fos.close();
		} catch(IOException e){
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * Checks to see if the given directory exists
	 * @param directory - the file directory which will be checked
	 * @return true if and only if the file exists; false otherwise
	 */
	public static boolean directoryExists(String directory){
		return new File(directory).exists();
	}
	
	/**
	 * Creates the given directory
	 * @param directory - the file directory that will be created
	 * @return true if and only if the directory was created; false otherwise
	 */
	public static boolean makeDirectory(String directory){
		return new File(directory).mkdirs();
	}
	
	/**
	 * Deletes the given directory
	 * @param directory - the file directory that will be deleted
	 * @return true if and only if the directory was deleted; false otherwise
	 */
	public static boolean deleteDirectory(String directory){
		return new File(directory).delete();
	}
	
	/**
	 * Creates a data folder within:
	 * <br>appdata/roaming/ (Windows)
	 * <br>Library/Application Support/ (Mac)
	 * <br>~/. (Unix)
	 * <br>and returns the file path. 
	 * @param folderName - the name of the folder
	 * @return the absolute file path of the data file path
	 */
	public static String createDataFolder(String folderName){
		String home = System.getProperty("user.home");
	
		OS os = getOperatingSystem();
		
		if(os == OS.WINDOWS){
			home = System.getenv("appdata");
		}else if(os == OS.MAC){
			home += "/Library/Application Support/.";
		}else if(os == OS.LINUX){
			home += "~/.";
		}
		
		File dir = new File(home);
		dir = new File(dir, folderName);
		
		if(!directoryExists(dir.getAbsolutePath())){
			makeDirectory(dir.getAbsolutePath());
		}
		return dir.getAbsolutePath();
	}
	
}
