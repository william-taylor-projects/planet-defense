
// Simple 2D texture shader for OpenGL ES.

// Author : William Taylor

// Last Updated : 07/01/2014

attribute vec4 position;

uniform mat4 Projection;
uniform mat4 ModelView;

varying vec2 TexCoord;

void main() 
{
	vec2 positions = position.xy;
	
	mat4 MVP = Projection * ModelView;
	
	gl_Position = MVP * vec4(positions, 0.0, 1.0);
	
	TexCoord = position.zw;
}