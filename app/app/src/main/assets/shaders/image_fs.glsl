precision mediump float;

uniform sampler2D Texture;
uniform vec4 ColorShade;

varying vec2 TexCoord;
varying float cutoff;

void main()
{
	vec4 TextureColour = texture2D(Texture, TexCoord);
	vec4 FinalColour = TextureColour * ColorShade;
	
	gl_FragColor = FinalColour;
}
