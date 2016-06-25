package com.planetDefense;

import static android.opengl.GLES20.*;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * 
 * @author B0023_000
 *
 */
public class GL_TextureManager {
	/** */
	public class Sprite {
		public String TexName;
		public int[] TexID;	
		public int Height;
		public int Width;
	}
	
	public void clear() {
		Textures.clear();
		Textures = new Vector<Sprite>();
	}
	
	private static GL_TextureManager instance;
	
	/** */
	private Vector<Sprite> Textures;
	/** */
	public static Integer Count = 0;
	
	/**
	 * 
	 */
	private GL_TextureManager() {
		Textures = new Vector<Sprite>();
		glActiveTexture(GL_TEXTURE0);
	}
	
	public static GL_TextureManager get() {
		if(instance == null) {
			instance = new GL_TextureManager();
		} return instance;
	}
	
	/**
	 * 
	 * @param TexName
	 * @return
	 */
	public Sprite GetSprite(String TexName) {
		for(int i = 0; i < Textures.size(); i++) {
			if(Textures.get(i).TexName.equals(TexName)) {
				return Textures.get(i);
			}
		} return null;
	}
	
	/**
	 * 
	 * @param filename
	 * @param name
	 * @return
	 */
	public Sprite LoadTexture(String filename, String name) {
		int Duplicate = CheckForDuplicate(name);
		
		if(Duplicate == -1) {
			Sprite sprite = new Sprite();
			sprite.TexID = new int[1];
			sprite.TexName = name;
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false;
			
			ResourceManager resc = ResourceManager.Get();
			
			Bitmap Sprite = BitmapFactory.decodeStream(resc.GetResource(filename));
			
			glGenTextures(1, sprite.TexID , 0);
			glBindTexture(GL_TEXTURE_2D, sprite.TexID [0]);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			

			if(Sprite == null) {
				Log.e("Error with : ", filename);
			}
			
			GLUtils.texImage2D(GL_TEXTURE_2D, 0, Sprite, 0);	
			sprite.Height = Sprite.getHeight();
			sprite.Width = Sprite.getWidth();
			Textures.add(sprite);
			Sprite.recycle();
			Count++;
			return sprite;
		} else {
			return Textures.get(Duplicate);
		}
	}
	
	/**
	 * 
	 */
	public void ReleaseTextures() {
		for(Sprite sprite : Textures) {
			glDeleteTextures(1, sprite.TexID, 0);
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private int CheckForDuplicate(String name) {
		int ID = -1;
		for(int i = 0; i < Textures.size(); i++) {
			if(Textures.get(i).TexName.equals(name)) {
				ID = i;
				break;
			}
		}
		return ID;
	}
}
