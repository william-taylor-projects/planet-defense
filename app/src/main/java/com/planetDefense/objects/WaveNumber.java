package com.planetDefense.objects;

/**
 * Copyright (c) 2014 - William Taylor <wi11berto@yahoo.co.uk>
 *
 *	This software is provided 'as-is', without any express or implied warranty. 
 *  In no event will the authors be held liable for any damages arising from 
 *  the use of this software. Permission is granted to anyone to use this 
 *  software for any purpose, including commercial applications, and to 
 *  alter it and redistribute it freely, subject to the following 
 *  restrictions:
 *
 *	1. The origin of this software must not be misrepresented; 
 *     you must not claim that you wrote the original software. 
 *	   If you use this software in a product, an acknowledgment 
 *     in the product documentation would be appreciated 
 *     but is not required.
 *
 *  2. Altered source versions must be plainly marked as such, 
 *     and must not be misrepresented as being the original 
 *     software.
 *  
 *  3. This notice may not be removed or altered 
 *     from any source distribution.
 *     
 */

public class WaveNumber {
	public LabelLine Text;
	private Integer Label;
	private Float Alpha;
	
	public WaveNumber() {
		Text = new LabelLine(LabelEngine.Get("large"), "0");
		SetLevel(1);
	}
	
	public void SetLevel(int currentWave) {
		Label = currentWave;
		Alpha = 0f;
		
		Text.Text(asString());
		Text.Load(20, 0);
		Text.SetColour(1f, 1f, 1f, 0f);
	}
	
	public void Update() {
		Text.SetColour(1f, 1f, 1f, Alpha);
		Text.Update();
		
		if(Alpha < 1.0f) {
			Alpha += 0.005f;
		}
	}
	
	public String asGrade() {
		String grade = "A++";
	
		if(Label <= 3 && Label >= 0) grade = "F";
		if(Label <= 10 && Label > 3) grade = "E";
		if(Label <= 20 && Label > 10) grade = "D"; 
		if(Label <= 30 && Label > 20) grade = "C";
		if(Label <= 40 && Label > 30) grade = "B";
		if(Label <= 50 && Label > 40) grade = "A";
			
		return grade;
	}
	
	public String asString() {
		return String.valueOf(Label);
	}
	
	public String asColour() {
		String colour = "RED";
		
		if(Label <= 10 && Label >= 3) {
			colour = "ORANGE";
		} else if(Label <= 20 && Label >= 10) {
			colour = "YELLOW";
		} else if(Label <= 30 && Label >= 20) {
			colour = "BLUE";
		} else if(Label <= 40 && Label >= 30) {
			colour = "GREEN";
		} else if(Label >= 40){
			colour = "GOLD";
		}
		
		return colour;
		
	}
	
	public void SetCenter() {
		Text.SetInitialPosition(350 - Text.GetWidth()/2, 200);
	}
	
	public void SetLeft() {
		Text.SetInitialPosition(20, 0);
	}

	public void ResetObject() {
		SetLevel(0);
	}
}