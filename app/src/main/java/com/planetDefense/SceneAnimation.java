package com.planetDefense;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class SceneAnimation {
	private Vector<GameButton> Buttons;
	private Vector<GameImage> Images;
	private Vector<LabelLine> Labels;
	private Vector<GL_Line> Lines;
	private Integer StateNumber = 0;
	private Vector2D Velocity;
	private Boolean Start;
	
	public SceneAnimation() {
		Buttons = new Vector<GameButton>();
		Images = new Vector<GameImage>();
		Labels = new Vector<LabelLine>();
		Lines = new Vector<GL_Line>();
		Start = false;
	}
	
	public SceneAnimation(int i) {
		Buttons = new Vector<GameButton>();
		Images = new Vector<GameImage>();
		Labels = new Vector<LabelLine>();
		Lines = new Vector<GL_Line>();
		
		StateNumber = i;
		Start = false;
	}
	
	public Boolean isRunning() {
		return Start;
	}
	
	public void setState(int i) {
		StateNumber = i;
		Start = false;
	}
	
	public void setupAnimation(Vector<Object> sprites) {
		for(Object obj : sprites) {
			if(obj instanceof GameImage) {
				GL_Image img = (GL_Image)((GameImage)obj).getRawObject();
				
				if(img.isVisible()) {
					Images.add((GameImage)obj);
				}
				
			} else if(obj instanceof GameButton) {
				GL_Image img = (GL_Image)((GameButton)obj).getImage();
				
				if(img.isVisible()) {
					Buttons.add((GameButton)obj);
				}
				
			} else if(obj instanceof LabelLine) {
				if(((GL_Text)((LabelLine)obj).GetRawGL()).isVisible()) {
					Labels.add((LabelLine)obj);
				}
			} else if(obj instanceof GL_Line) {
				Lines.add((GL_Line)obj);
			}
		} 
	}
	
	public void setVelocity(Vector2D vector) {
		this.Velocity = vector;
	}
	
	public void update() {
		if(Start) {
			Integer ElementsToMove = 0;
			Integer ElementsMoved = 0;	
			
			if(Velocity.x() > 0) {
				for(GameImage sprite : Images) {
					sprite.translate(Velocity.x(), Velocity.y());
					if(sprite.getPosition().x() > 1280) {
						++ElementsMoved;
					}
				}
				
				for(GL_Line lines : Lines) {
					lines.translate(Velocity.x(), Velocity.y());
					++ElementsMoved;
				}
		
				for(GameButton button : Buttons) {
					button.Translate(Velocity.x(), Velocity.y());
					if(button.getPosition().x() > 1280) {
						++ElementsMoved;
					}
				}
				
				for(LabelLine label : Labels) {
					label.Translate(label.getPosition().x() + Velocity.x(), label.getPosition().y() + Velocity.y());
					if(label.getPosition().x() > 1280) {
						++ElementsMoved;
					}
				}
			} else {
				for(GameImage sprite : Images) {
					sprite.translate(Velocity.x(), Velocity.y());
					if(sprite.getPosition().x() + sprite.getSize().x() < -100) {
						++ElementsMoved;
					}
				}
				
				for(GL_Line lines : Lines) {
					lines.translate(Velocity.x(), Velocity.y());
					++ElementsMoved;
				}
		
				for(GameButton button : Buttons) {
					button.Translate(Velocity.x(), Velocity.y());
					if(button.getPosition().x() + button.getSize().x() < -100) {
						++ElementsMoved;
					}
				}
				
				for(LabelLine label : Labels) {
					label.Translate(label.getPosition().x() + Velocity.x(), label.getPosition().y() + Velocity.y());
					if(label.getPosition().x() + label.GetWidth() < -100) {
						++ElementsMoved;
					}
				}
			}
			
			ElementsToMove += Lines.size();			
			ElementsToMove += Buttons.size();
			ElementsToMove += Images.size();
			ElementsToMove += Labels.size();

			if(ElementsMoved >= ElementsToMove) {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						for(GameImage sprite : Images) {
							sprite.reset();
						}
						
						for(GameButton button : Buttons) {
							button.Reset();
						}
					
						for(LabelLine label : Labels) {
							label.Reset();
						}
						
						for(GL_Line line : Lines) {
							line.reset();
						}
						
						Buttons.clear();
						Images.clear();
						Labels.clear();
						Lines.clear();
						
						Velocity.Set(0f, 0f);
						Start = false;
					}
					
				}, 33);
				
				SceneManager.get().SwitchTo(StateNumber);
				TaloniteGame.EnableInput();
			}
		}
	}
	
	public void beginAnimation() {
		TaloniteGame.DisableInput();
		if(Velocity == null) {
			Velocity = new Vector2D(10, 0);
		} Start = true;
	}
}
