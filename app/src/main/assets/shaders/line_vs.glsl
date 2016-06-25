
attribute vec3 position;

uniform mat4 Projection;
uniform mat4 ModelView;

void main()
{
	mat4 MVP = Projection * ModelView;
	
	gl_Position = MVP * vec4(position, 1.0);
}