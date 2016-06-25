package  com.planetDefense;

import java.util.Vector;

public class GL_ShaderManager {
	private static GL_ShaderManager instance;
	private Vector<GL_Program> programs;
	
	private GL_ShaderManager(){
		programs = new Vector<GL_Program>();
	}
	
	static public GL_ShaderManager get() {
		if(instance == null) {
			instance = new GL_ShaderManager();
		}
		return instance;
	}
	
	public void clear() {
		programs.clear();
		programs = new Vector<GL_Program>();
	}
	
	public GL_Program getShader(String vs, String fs) {
		GL_Program shader = null;
		Boolean found = false;
		
		for(GL_Program program : programs) {
			if(program.getFragFilename().equalsIgnoreCase(fs) &&
					program.getVertexFilename().equalsIgnoreCase(vs)) {
				shader = program;
				found = true;
				break;
			}
		}
		
		if(!found) {
			shader = new GL_Program();
			shader.PushShaders(vs, fs);
			programs.add(shader);
		}
		
		return shader;
	}
}
