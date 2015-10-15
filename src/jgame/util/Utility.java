package jgame.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

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
	
	/**
	 * Returns a randomly generated vector with coordinates in the given range.
	 * @param minX (minimum x coordinate).
	 * @param minY (minimum y coordinate).
	 * @param maxX (maximum x coordinate).
	 * @param maxY (maximum y coordinate).
	 * @return A vector within the given range.
	 */
	public static Vector2F generateVector(float minX, float minY, float maxX, float maxY){
		Random random = new Random();
		float x = random.nextFloat() * (maxX - minX) + minX;
		float y = random.nextFloat() * (maxY - minY) + minY;
		return new Vector2F(x, y);
	}

	/**
	 * Returns a randomly generated vector with coordinates in the given range.
	 * @param minX (minimum x coordinate).
	 * @param minY (minimum y coordinate).
	 * @param maxX (maximum x coordinate).
	 * @param maxY (maximum y coordinate).
	 * @return A vector within the given range.
	 */
	public static Vector2I generateVector(int minX, int minY, int maxX, int maxY){
		Random random = new Random();
		int x = random.nextInt(maxX - minX) + maxX;
		int y = random.nextInt(maxY - minY) + maxY;
		return new Vector2I(x, y);
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
