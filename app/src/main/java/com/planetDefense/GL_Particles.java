package  com.planetDefense;

import java.util.Random;
import static android.opengl.GLES20.*;

/**
 * 
 * @author B0023_000
 *
 */
public class GL_Particles {
	/**	*/
	private final String VS = "shaders/fire_vs.glsl";
	private final String FS = "shaders/fire_fs.glsl";
	
	/**	*/
	private GL_Buffer buffer = new GL_Buffer();
	private Matrix matrix = new Matrix();
	
	private GL_Program program;
	private Integer number;
	private Float time;
	
	/**	*/
	private Vector2D translation = new Vector2D();
	private Vector2D destination = new Vector2D();
	private Vector2D position = new Vector2D();
	
	/** */
	public GL_Particles() {
		program = GL_ShaderManager.get().getShader(VS, FS);
		number = 0;
		time = 10f;
	}
	
	/**
	 * 
	 * @param number
	 * @param x
	 * @param y
	 */
	public void initialiseParticles(int number, float x, float y) {
		float[] pos = new float[2 + (number * 2)];
		pos[0] = x;	
		pos[1] = y;
		
		Random rand = new Random();
		rand.setSeed(rand.nextInt(100));
		
		this.number = number;
		for(int i = 0; i < number; i++) {
			pos[i + 2] = (float)rand.nextInt(20000)/20000;
			pos[i + 3] = (float)rand.nextInt(20000)/20000;
			pos[i + 2] *= rand.nextInt(10) + 1; 
			pos[i + 3] *= rand.nextInt(10) + 1; 
					
		} 

		position.Set(x, y);
		buffer.insert(pos);
	}
	
	/**	*/
	public void SetPosition(float x, float y) {
		translation.Set(x, y);
	}
	
	/**	*/
	public void Reset() {
		time = 0.0f;
	}

	/**	*/
	public void Update() {
		time += 0.02f;
	}
	
	/**	*/
	public void Destination(float x, float y) {
		destination.Set(x, y);
	}
	
	
	static int DestinationID;
	static int DirectionID;
	static int ProjectionID;
	static int MatrixID;
	static int ColourID;
	static int TimeID;
	
	static int TranslateID;
	static int PositionID;
	
	
	public void startRender() {
		program.startProgram();
		
		if(DestinationID == 0) {
			DestinationID = program.getUniform("destination");
			DirectionID = program.getUniform("Translation");
			ProjectionID = program.getUniform("Projection");
			MatrixID = program.getUniform("ModelView");
			ColourID = program.getUniform("Colour");
			TimeID = program.getUniform("Time");
			
			TranslateID = program.getAttribute("Translate");
			PositionID = program.getAttribute("Position");
		}
		
		glEnableVertexAttribArray(TranslateID);
		glEnableVertexAttribArray(PositionID);
	}
	
	public void render() {
		
		
		if(time < 10.0f) {
			buffer.bindBuffer();			
			glVertexAttribPointer(TranslateID, 2, GL_FLOAT, false, 8, 8);
			glVertexAttribPointer(PositionID, 2, GL_FLOAT, false, 0, 0);
			glUniform2f(DirectionID, translation.x(), translation.y());
			
			glUniformMatrix4fv(ProjectionID, 1, false, matrix.GetProjection(), 0);
			glUniformMatrix4fv(MatrixID, 1, false, matrix.GetModelView(), 0);
			glUniform4f(ColourID, 1.0f, 0.5f, 0.0f, 1.0f);
			glUniform2f(DestinationID, destination.x(), destination.y());
			glUniform1f(TimeID, time);
			
			glDrawArrays(GL_POINTS, 0, number);
		
			buffer.unbind();
		}
	}
	
	public void endRender() {
		glDisableVertexAttribArray(TranslateID);
		glDisableVertexAttribArray(PositionID);
		
		program.endProgram();
	}
}
