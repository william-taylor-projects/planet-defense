package  com.planetDefense;


import static android.opengl.GLES20.*;

/**
 * 
 * @author B0023_000
 *
 */
public class GL_Image {	
	/**	*/
	private final String VS = "shaders/image_vs.glsl";
	private final String FS = "shaders/image_fs.glsl";
	
	/**	*/
	private GL_TextureManager.Sprite Sprite;
	private float[] Color = new float[4];
	
	/**	*/
	private Vector2D realPosition = new Vector2D();
	private Vector2D translation = new Vector2D();
	private Vector2D position = new Vector2D();
	private Vector2D scale = new Vector2D(1,1);
	private Vector2D size = new Vector2D();
	
	private GL_Buffer buffer = new GL_Buffer();
	private Matrix matrix = new Matrix();
	
	private GL_Program program;
	private String filename;
	
	public GL_TextureManager.Sprite getSprite() {
		return Sprite;
	}
	
	public void setSprite(GL_TextureManager.Sprite s) {
		Sprite = s;
	}
	/**
	 * 
	 */
	public GL_Image() {		
		program = GL_ShaderManager.get().getShader(VS, FS);
		
		setShade(1f, 1f, 1f, 1f);
	}
	
	public String getFilename() {
		return filename;
	}
	
	/**
	 * 
	 */
	public void reset() {
		translation.Set(0, 0);
	}
	
	/**
	 * 
	 * @param filename
	 * @param name
	 */
	public void load(String filename, String name) {
		Sprite = GL_TextureManager.get().LoadTexture(filename, name);	
		this.filename = filename;
	}
	
	public boolean isVisible() {
		realPosition.Set(position);
		realPosition.x(translation.x());
		realPosition.y(translation.y());
		
		if(realPosition.y() + size.y() >= 0 && realPosition.y() <= 800) {
			return true;
		} return false;
	}
	
	static int  ProjectionID = 0;
	static int PositionID = 0;
	static int MatrixID = 0;
	static int ColorID = 0;
	
	/**
	 * 
	 */
	public void render() {	
		program.startProgram();
		buffer.bindBuffer();
		
		glBindTexture(GL_TEXTURE_2D, Sprite.TexID[0]);
		
		if(ProjectionID == 0 && PositionID == 0 && MatrixID == 0 && ColorID == 0)
		{
			ProjectionID = program.getUniform("Projection");
			PositionID = program.getAttribute("position");
			MatrixID = program.getUniform("ModelView");
			ColorID = program.getUniform("ColorShade");
		}
	
		glEnableVertexAttribArray(PositionID);
		
		glVertexAttribPointer(PositionID, 4,  GL_FLOAT, false, 0, 0);
		glUniformMatrix4fv(ProjectionID, 1, false, matrix.GetProjection(), 0);
		glUniformMatrix4fv(MatrixID, 1, false, matrix.GetModelView(), 0);
		glUniform4fv(ColorID, 1, Color, 0);
		
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
		glDisableVertexAttribArray(PositionID);
		program.endProgram();
		buffer.unbind();
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param s
	 */
	public void translateTo(float x, float y, float s) {
		Vector2D position = getPosition();
		
		if(x != position.x() || y != position.y()) {
			Vector2D vec = new Vector2D();
			
			float tx = x - position.x();
			float ty = 0.0f;
	
			if(y != position.y() && y <= position.y()) {
				ty = y - position.y();
			} else if(y != position.y() && y > position.y()) {
				ty = y - position.y();
			}
			
			vec.Set(tx, ty);
			vec.normalise();
			
			translation.x(vec.x() * s);
			translation.y(vec.y() * s);
		}
	}
	
	
	/**
	 * 
	 * @param angle
	 */
	public void update(int angle) {
		matrix.LoadIdentity();
		matrix.Translate(translation.x(), translation.y());
		matrix.Rotate(angle, position.x() + (size.x()/2), position.y() + (size.y()/2));
		matrix.Scale(scale.x(), scale.y());
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param div
	 */
	public void shift(float x, float y, int div) {
		if(x != 0.0f && y != 0.0f) {
			translation.x((int)(x - (position.x() + translation.x()))/div);
			translation.y((int)(y - (position.y() + translation.y()))/div);
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void translate(float x, float y) {
		translation.x(x);
		translation.y(y);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void translateOnce(float x, float y) {
		translation.Set(0, 0);
		translation.x(x);
		translation.y(y);
	}
	
	
	public void setPosition(float x, float y, float w, float h, float t, float z, float b, float c) {
		float[] pos = new float[16];	

		pos[0] = x;		pos[1] = y;		pos[2] = t;	pos[3] = c;
		pos[4] = x;		pos[5] = y+h;	pos[6] = t;	pos[7] = z;
		pos[8]= x+w;	pos[9] = y;		pos[10]= b;	pos[11]= c;
		pos[12]= x+w;	pos[13]= y+h;	pos[14]= b;	pos[15]= z;
		
		position.Set(x, y);
		size.Set(w, h);
		
		buffer.insert(pos);
	}
	
	/**	*/
	public void setScale(float x, float y) {
		scale.Set(x, y);
	}
	
	public void setShade(float r, float g, float b, float a) {
		Color[0] = r;	Color[1] = g;
		Color[2] = b;	Color[3] = a;
	}
	
	public void setPosition(float x, float y, float w, float h) {
		setPosition(x, y, w, h, 0, 0, 1, 1);
	}
	

	public Vector2D getPosition() { 
		realPosition.Set(position);
		realPosition.x(translation.x());
		realPosition.y(translation.y());
		return realPosition;
	}
	
	public float getRotation() {
		return(matrix.GetRotation());
	}

	public Vector2D getSize() { 
		return(size);
	}
	
	public Vector2D getScale() {
		return(scale);
	}

	public void beginProgram() {
		program.startProgram();
		
		if(ProjectionID == 0 && PositionID == 0 && MatrixID == 0 && ColorID == 0)
		{
			ProjectionID = program.getUniform("Projection");
			PositionID = program.getAttribute("position");
			MatrixID = program.getUniform("ModelView");
			ColorID = program.getUniform("ColorShade");
		}
		
		glEnableVertexAttribArray(PositionID);
	}
	
	public void startRender() {
		buffer.bindBuffer();
		
		glBindTexture(GL_TEXTURE_2D, Sprite.TexID[0]);
		
		glVertexAttribPointer(PositionID, 4,  GL_FLOAT, false, 0, 0);
		glUniformMatrix4fv(ProjectionID, 1, false, matrix.GetProjection(), 0);
		glUniformMatrix4fv(MatrixID, 1, false, matrix.GetModelView(), 0);
		glUniform4fv(ColorID, 1, Color,  0);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
	}
	
	public void endProgram() {
		glDisableVertexAttribArray(PositionID);
		program.endProgram();
	}
}
