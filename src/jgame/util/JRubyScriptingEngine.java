package jgame.util;

import java.util.ArrayList;
import java.util.List;

import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

public final class JRubyScriptingEngine {

	private static ScriptingContainer scriptingContainer;
	
	private static List<String> loadPaths = new ArrayList<String>();
	
	public static void addScript(String scriptPath){
		loadPaths.add(scriptPath);
		getScriptingContainer().setLoadPaths(loadPaths);
	}
	
	public static void clearScripts(){
		loadPaths.clear();
		getScriptingContainer().setLoadPaths(loadPaths);
	}
	
	public static void initialize(){
		getScriptingContainer().runScriptlet("");
	}
	
	public static void runScript(String scriptPath){
		scriptingContainer.runScriptlet(PathType.ABSOLUTE, scriptPath);
	}
	
	public static void runScript(String scriptPath, String ... args){
		scriptingContainer.setArgv(args);
		scriptingContainer.runScriptlet(PathType.ABSOLUTE, scriptPath);
	}
	
	public static ScriptingContainer getScriptingContainer(){
		if(scriptingContainer == null){
			scriptingContainer = new ScriptingContainer();
		}
		return scriptingContainer;
	}
}