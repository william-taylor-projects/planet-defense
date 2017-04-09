
attribute vec4 position;
attribute vec2 Spacing;

uniform mat4 Projection;
uniform mat4 ModelView;

varying vec2 TexCoord;

void main() 
{
	vec2 positions = position.xy + Spacing;
	
	mat4 MVP = Projection * ModelView;
	
	gl_Position = (MVP * vec4(positions, 0.0, 1.0));

	TexCoord = position.zw;
}