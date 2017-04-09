precision mediump float;

uniform sampler2D Texture;
uniform vec4 Shade;

varying vec2 TexCoord;

void main()
{
	gl_FragColor = vec4(Shade.r, Shade.g, Shade.b, texture2D(Texture, TexCoord).a) * Shade.a;
}
