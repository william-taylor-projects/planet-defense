package  com.planetDefense;


/**
 * This is hardly a class that does much. It just hides
 * the GL_Image functionality. This class also forms
 * the base to a greater sprite library that will be devoloped
 * when needed.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class GameImage {
	/**	The sprite image current implementation used opengl es 2.0 */
	private GL_Image Sprite;
	private String filename;
	
	/**
	 * A simple Constructor that loads the image.
	 * @param ID Filename location.
	 */
	public GameImage(String ID) {
		this.filename = ID;
		Sprite = new GL_Image();
		Sprite.load(ID, ID);
	}
	
	/**
	 * A method that allows people that know how the GL_Image
	 * class words the opertunity to access it directly.
	 * 
	 * @return This function returns the raw class 
	 * 		   that is used for operations.
	 */
	public Object getRawObject() {
		return this.Sprite;
	}
	
	/**	Basic set position functioin */
	public void setPosition(int x, int y, int w, int h) {
		Sprite.setPosition(x, y, w, h);
	}
	
	public String getFilename() {
		return filename;
	}
	
	public Vector2D getPosition() {
		return(Sprite.getPosition());
	}
	
	public Vector2D getSize() {
		return(Sprite.getSize());
	}

	/** Basic Update Function	*/
	public void update() {
		Sprite.update(0);
	}
	
	public void setTexture(String str) {
		Sprite.load(str, str);
		this.filename = str;
	}
	
	public void reset() {
		Sprite.reset();
	}
	
	/**	Set the textures shade */
	public void shade(float r, float g, float b, float a) {
		Sprite.setShade(r, g, b, a);
	}
	
	/**							*/
	public void translate(float x, float y) {
		Sprite.translate(x, y);
	}

	/** Basic Draw function	*/
	public void draw() {
		if(Sprite.isVisible()) {
			Sprite.render();
		}
	}
}
