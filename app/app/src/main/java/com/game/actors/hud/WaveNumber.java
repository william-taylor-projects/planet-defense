package com.game.actors.hud;

import com.framework.graphics.Font;
import com.framework.graphics.Label;

public class WaveNumber {
	private Label waveText;
	private Integer waveNumber;
	private Float textAlpha;
	
	public WaveNumber() {
		waveText = new Label(Font.get("large"), "0");
		SetLevel(1);
	}
	
	public void SetLevel(int currentWave) {
		waveNumber = currentWave;
		textAlpha = 0f;
		
		waveText.text(asString());
		waveText.load(20, 0);
		waveText.setColour(1f, 1f, 1f, 0f);
	}
	
	public void Update() {
		waveText.setColour(1f, 1f, 1f, textAlpha);
		waveText.update();
		
		if(textAlpha < 1.0f) {
			textAlpha += 0.005f;
		}
	}
	
	public String asGrade() {
		String grade = "A++";
	
		if(waveNumber <= 3 && waveNumber >= 0) grade = "F";
		if(waveNumber <= 10 && waveNumber > 3) grade = "E";
		if(waveNumber <= 20 && waveNumber > 10) grade = "D";
		if(waveNumber <= 30 && waveNumber > 20) grade = "C";
		if(waveNumber <= 40 && waveNumber > 30) grade = "B";
		if(waveNumber <= 50 && waveNumber > 40) grade = "A";
			
		return grade;
	}
	
	public String asString() {
		return String.valueOf(waveNumber);
	}
	
	public String asColour() {
		String colour = "RED";
		
		if(waveNumber <= 10 && waveNumber >= 3) {
			colour = "ORANGE";
		} else if(waveNumber <= 20 && waveNumber >= 10) {
			colour = "YELLOW";
		} else if(waveNumber <= 30 && waveNumber >= 20) {
			colour = "BLUE";
		} else if(waveNumber <= 40 && waveNumber >= 30) {
			colour = "GREEN";
		} else if(waveNumber >= 40){
			colour = "GOLD";
		}
		
		return colour;
		
	}
	
	public void SetCenter() {
		waveText.setInitialPosition(350 - waveText.getWidth() / 2, 200);
	}
	
	public void SetLeft() {
		waveText.setInitialPosition(20, 0);
	}

	public void ResetObject() {
		SetLevel(0);
	}

    public Label getWaveText() {
        return this.waveText;
    }
}