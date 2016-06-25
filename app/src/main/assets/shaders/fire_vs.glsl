
// Simple 2D fire shader for OpenGL ES.

// Author : William Taylor

// Last Updated : 07/01/2014

attribute vec2 Position;
attribute vec2 Translate;

uniform vec2 destination;
uniform vec2 Translation;
uniform float Time;

uniform mat4 Projection;
uniform mat4 ModelView;

varying float TimePassed;

void main() 
{
	TimePassed = Time * 7.0;
	
	vec4 RealDestination = vec4(destination + Translate, 0.0, 1.0);
	vec4 FinalPosition = vec4(Position, 0.0, 1.0);
	vec4 addition = (FinalPosition - RealDestination) * TimePassed;
	vec4 Offset = vec4(Translate, 0.0, 1.0) * TimePassed;
	
	FinalPosition.x -= (addition.x - Translation.x);
	FinalPosition.y -= (addition.y - Translation.y);
	
	FinalPosition.x += Offset.x;
	FinalPosition.y += Offset.y;
	
	gl_PointSize = 4.0;
	gl_Position = Projection * ModelView * FinalPosition;
}