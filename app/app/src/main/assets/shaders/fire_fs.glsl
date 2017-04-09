
// Simple 2D light shader for OpenGL ES.

// Author : William Taylor

// Last Updated : 07/01/2014

precision mediump float;

uniform vec4 Colour;

varying float TimePassed;

void main()
{
	vec4 ShadeColour = Colour;
	if(TimePassed >= 0.0) 
	{
		ShadeColour.xyz -= TimePassed / 4.0;
		ShadeColour.w  -= TimePassed / 5.0;
		gl_FragColor = ShadeColour;
	}
}