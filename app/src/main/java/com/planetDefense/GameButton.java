package  com.planetDefense;

public class GameButton {
	private class TextLoadingData {
		public String label;
	}
	
	private Vector2D position;
	private Vector2D size;
	
	private GL_Image Sprite;
	private GL_Text Text;
	
	private boolean spriteEnabled = false;
	private boolean textEnabled = false;
	private boolean loadText = false;
	
	private TextLoadingData data;
	
	public GameButton(LabelEngine e) {
		Sprite = new GL_Image();
		Text = new GL_Text(e);
		loadText = false;
	}
	
	public GameButton() {
		Sprite = new GL_Image();
	}
	
	public void SetTexture(String str) {
		if(spriteEnabled) {
			Sprite.load(str, str);
		}
	}
	
	public void Shade(float r, float g, float b, float a) {
		if(spriteEnabled) {
			Sprite.setShade(r, g, b, a);
		}
	}
	
	public void SetText(String string, float x, float y, float w, float h) {
		TaloniteGame.DisableInput();
		Text.Load(string, 0, 0);
		
		Float halfX = (float)(Text.GetWidth()/2);
		Text.SetInitialPosition(x - halfX, y);
		Text.Update(0);	
		
		textEnabled = true;
		if(!spriteEnabled) {
			Sprite.setPosition(x - halfX, y, w + halfX, h);
		}
		
		TaloniteGame.EnableInput();
	}
	
	public void SetText(String string, float x, float y) {
		Text.Load(string, 0, 0);	
		Text.SetInitialPosition(x - Text.GetWidth()/2, y);
		Text.Update(0);	
	
		textEnabled = true;
	}
	
	public void SetSprite(String string, float x, float y, float w, float h) {
		position = new Vector2D(x, y);
		size = new Vector2D(w, h);
		Sprite.load(string, string);
		Sprite.setPosition(x, y, w, h);
		spriteEnabled = true;
	}
	
	public void PushText(GL_Text e) {
		this.Text = e;
	}
	
	public void SetTextColour(float r, float g, float b, float a) {
		if(Text != null) {
			Text.SetColour(r, g, b, a);
		}
	}
	
	public Vector2D getPosition() {
		return Sprite.getPosition();
	}
	
	public void AddText(String str) {
		data = new TextLoadingData();
		data.label = str;
		loadText = true;
	}
	
	public void Reset() {
		Sprite.reset();
		Text.Reset();
	}
	
	public void AjustText(float x, float y) {
		Vector2D old = Text.getTranslate();
		Text.Translate(old.x()+x, old.y()+y);
	}
	
	public void Translate(float x, float y) {
		Vector2D vec = Text.getTranslate();
		
		if(spriteEnabled) {
			Sprite.translate(x, y);
		} 
		
		if(textEnabled) {
			Text.Translate(vec.x() + x, vec.y() + y);
		}
	}
	
	public void HideText() {
		textEnabled = false;
	}

	public void Update() {
		if(loadText) {
			textEnabled = true;
			Text.Load(data.label, 0, 0);			
			Text.SetColour(0, 0, 0, 1);
			
			float tx = Sprite.getPosition().x() + Sprite.getSize().x()/2 - Text.GetWidth()/2;
			float ty = Sprite.getPosition().y() + Sprite.getSize().y()/2 - Text.GetHeight()/2;
			float x = position.x() + size.x()/2 - Text.GetWidth()/2;
			float y = position.y() + size.y()/2 - Text.GetHeight()/2;
			
			Text.SetInitialPosition(x, y);	
			Text.Translate(tx, ty);	
			loadText = !loadText;
		} 
			
		if(spriteEnabled) {
			Sprite.update(0);
		} 
			
		if(textEnabled) {
			Text.Update(0);
		}
	}
	
	public Object GetRawObject() {
		return this.Sprite;
	}
	
	public Vector2D getSize() {
		return(Sprite.getSize());
	}

	public void Draw() {
		if(spriteEnabled && Sprite.isVisible()) {
			Sprite.render();
		} 
		
		if(textEnabled && Text.isVisible()) {
			Text.Draw();
		}
	}

	public String getFilename() {
		return Sprite.getFilename();
	}

	public GL_Image getImage() {
		if(!spriteEnabled) {
			return null;
		}
		return Sprite;
	}
	
	public GL_Text getText() {
		if(!textEnabled) {
			return null;
		}
		return this.Text;
	}
}
