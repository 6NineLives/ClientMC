package net.minecraft.client.gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.lax1dude.eaglercraft.v1_8.HString;
import net.lax1dude.eaglercraft.v1_8.IOUtils;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files (c) 2022-2024 lax1dude, ayunami2000. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class FontRenderer implements IResourceManagerReloadListener {
	protected static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
	/**+
	 * Array of width of all the characters in default.png
	 */
	protected int[] charWidth = new int[256];
	/**+
	 * the height in pixels of default text
	 */
	public int FONT_HEIGHT = 9;
	public EaglercraftRandom fontRandom = new EaglercraftRandom();
	/**+
	 * Array of the start/end column (in upper/lower nibble) for
	 * every glyph in the /font directory.
	 */
	protected byte[] glyphWidth = new byte[65536];
	/**+
	 * Array of RGB triplets defining the 16 standard chat colors
	 * followed by 16 darker version of the same colors for drop
	 * shadows.
	 */
	protected int[] colorCode = new int[32];
	protected final ResourceLocation locationFontTexture;
	protected final TextureManager renderEngine;
	protected float posX;
	protected float posY;
	protected boolean unicodeFlag;
	protected boolean bidiFlag;
	protected float red;
	protected float blue;
	protected float green;
	protected float alpha;
	protected int textColor;
	protected boolean randomStyle;
	protected boolean boldStyle;
	protected boolean italicStyle;
	protected boolean underlineStyle;
	protected boolean strikethroughStyle;
	public float size;

	public FontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn,
			boolean unicode) {
		this.locationFontTexture = location;
		this.renderEngine = textureManagerIn;
		this.unicodeFlag = unicode;
		textureManagerIn.bindTexture(this.locationFontTexture);

		for (int i = 0; i < 32; ++i) {
			int j = (i >> 3 & 1) * 85;
			int k = (i >> 2 & 1) * 170 + j;
			int l = (i >> 1 & 1) * 170 + j;
			int i1 = (i >> 0 & 1) * 170 + j;
			if (i == 6) {
				k += 85;
			}

			if (gameSettingsIn.anaglyph) {
				int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
				int k1 = (k * 30 + l * 70) / 100;
				int l1 = (k * 30 + i1 * 70) / 100;
				k = j1;
				l = k1;
				i1 = l1;
			}

			if (i >= 16) {
				k /= 4;
				l /= 4;
				i1 /= 4;
			}

			this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
		}

		this.readGlyphSizes();
	}

    public FontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn,
            boolean unicode, float size) {
        this(gameSettingsIn, location, textureManagerIn, unicode);
        this.size = size;
    }

	public void onResourceManagerReload(IResourceManager resourceManager) {
		this.readFontTexture();
	}

	private void readFontTexture() {
		ImageData bufferedimage;
		try {
			bufferedimage = TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager()
					.getResource(this.locationFontTexture).getInputStream());
		} catch (IOException ioexception) {
			throw new RuntimeException(ioexception);
		}

		int i = bufferedimage.width;
		int j = bufferedimage.height;
		int[] aint = bufferedimage.pixels;
		int k = j / 16;
		int l = i / 16;
		byte b0 = 1;
		float f = 8.0F / (float) l;
		if (this.locationFontTexture.toString().equalsIgnoreCase("minecraft:client/font/roboto/regular.png")) {
		    f = 5.0F / l;
		}
		else if (this.locationFontTexture.toString().equalsIgnoreCase("minecraft:client/font/play/regular.png")) {
            f = 5.0F / l;
        }
		else if (this.locationFontTexture.toString().equalsIgnoreCase("minecraft:client/font/roboto/bold.png")) {
            f = 5.0F / l;
        }
		else if (this.locationFontTexture.toString().equalsIgnoreCase("minecraft:client/font/ubuntu/medium.png")) {
            f = 5.0F / l;
        }
		else if (this.locationFontTexture.toString().equalsIgnoreCase("minecraft:client/font/play/bold.png")) {
            f = 5.0F / l;
        }

		for (int i1 = 0; i1 < 256; ++i1) {
			int j1 = i1 % 16;
			int k1 = i1 / 16;
			if (i1 == 32) {
				this.charWidth[i1] = 3 + b0;
			}

			int l1;
			for (l1 = l - 1; l1 >= 0; --l1) {
				int i2 = j1 * l + l1;
				boolean flag = true;

				for (int j2 = 0; j2 < k && flag; ++j2) {
					int k2 = (k1 * l + j2) * i;
					if ((aint[i2 + k2] >> 24 & 255) != 0) {
						flag = false;
					}
				}

				if (!flag) {
					break;
				}
			}

			++l1;
			this.charWidth[i1] = (int) (0.5D + (double) ((float) l1 * f)) + b0;
		}

	}

	private void readGlyphSizes() {
		InputStream inputstream = null;

		try {
			inputstream = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("font/glyph_sizes.bin")).getInputStream();
			inputstream.read(this.glyphWidth);
		} catch (IOException ioexception) {
			throw new RuntimeException(ioexception);
		} finally {
			IOUtils.closeQuietly(inputstream);
		}

	}

	private float func_181559_a(char parChar1, boolean parFlag) {
		if (parChar1 == 32) {
			return 4.0F;
		} else {
			int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000"
					.indexOf(parChar1);
			return i != -1 && !this.unicodeFlag ? this.renderDefaultChar(i, parFlag)
					: this.renderUnicodeChar(parChar1, parFlag);
		}
	}

	/**+
	 * Render a single character with the default.png font at
	 * current (posX,posY) location...
	 */
	private float renderDefaultChar(int parInt1, boolean parFlag) {
		int i = parInt1 % 16 * 8;
		int j = parInt1 / 16 * 8;
		int k = parFlag ? 1 : 0;
		this.renderEngine.bindTexture(this.locationFontTexture);
		int l = this.charWidth[parInt1];
		float f = (float) l - 0.01F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		worldrenderer.begin(Tessellator.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX);

		worldrenderer.pos(this.posX + (float) k, this.posY, 0.0F).tex((float) i / 128.0F, (float) j / 128.0F)
				.endVertex();

		worldrenderer.pos(this.posX - (float) k, this.posY + 7.99F, 0.0F)
				.tex((float) i / 128.0F, ((float) j + 7.99F) / 128.0F).endVertex();

		worldrenderer.pos(this.posX + f - 1.0F + (float) k, this.posY, 0.0F)
				.tex(((float) i + f - 1.0F) / 128.0F, (float) j / 128.0F).endVertex();

		worldrenderer.pos(this.posX + f - 1.0F - (float) k, this.posY + 7.99F, 0.0F)
				.tex(((float) i + f - 1.0F) / 128.0F, ((float) j + 7.99F) / 128.0F).endVertex();

		tessellator.draw();

		return (float) l;
	}

	private ResourceLocation getUnicodePageLocation(int parInt1) {
		if (unicodePageLocations[parInt1] == null) {
			unicodePageLocations[parInt1] = new ResourceLocation(
					HString.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(parInt1) }));
		}

		return unicodePageLocations[parInt1];
	}

	/**+
	 * Load one of the /font/glyph_XX.png into a new GL texture and
	 * store the texture ID in glyphTextureName array.
	 */
	private void loadGlyphTexture(int parInt1) {
		this.renderEngine.bindTexture(this.getUnicodePageLocation(parInt1));
	}

	/**+
	 * Render a single Unicode character at current (posX,posY)
	 * location using one of the /font/glyph_XX.png files...
	 */
	private float renderUnicodeChar(char parChar1, boolean parFlag) {
		if (this.glyphWidth[parChar1] == 0) {
			return 0.0F;
		} else {
			int i = parChar1 / 256;
			this.loadGlyphTexture(i);
			int j = this.glyphWidth[parChar1] >>> 4;
			int k = this.glyphWidth[parChar1] & 15;
			float f = (float) j;
			float f1 = (float) (k + 1);
			float f2 = (float) (parChar1 % 16 * 16) + f;
			float f3 = (float) ((parChar1 & 255) / 16 * 16);
			float f4 = f1 - f - 0.02F;
			float f5 = parFlag ? 1.0F : 0.0F;
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();

			worldrenderer.begin(Tessellator.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX);

			worldrenderer.pos(this.posX + f5, this.posY, 0.0F).tex(f2 / 256.0F, f3 / 256.0F).endVertex();

			worldrenderer.pos(this.posX - f5, this.posY + 7.99F, 0.0F).tex(f2 / 256.0F, (f3 + 15.98F) / 256.0F)
					.endVertex();

			worldrenderer.pos(this.posX + f4 / 2.0F + f5, this.posY, 0.0F).tex((f2 + f4) / 256.0F, f3 / 256.0F)
					.endVertex();

			worldrenderer.pos(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F)
					.tex((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F).endVertex();

			tessellator.draw();

			return (f1 - f) / 2.0F + 1.0F;
		}
	}

	/**+
	 * Draws the specified string with a shadow.
	 */
	public int drawStringWithShadow(String text, float x, float y, int color) {
		return this.drawString(text, x, y, color, true);
	}

	/**+
	 * Draws the specified string.
	 */
	public int drawString(String text, int x, int y, int color) {
		return this.drawString(text, (float) x, (float) y, color, false);
	}

    public int drawString(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, false);
    }

    public float drawCenteredString(String string, float x, float y, int color) {
        return this.drawString(string, x - (float)this.getStringWidth(string) / 2.0f, y, color, false);
    }

    public float drawCenteredStringWithShadow(String string, float x, float y, int color) {
        float f3 = this.drawString(string, (x - (float)this.getStringWidth(string) / 2.0f) + 1.0f, y + 1.0f, color, true);
        return this.drawString(string, x - (float)this.getStringWidth(string) / 2.0f, y, color);
    }

	/**+
	 * Draws the specified string.
	 */
	public int drawString(String text, float x, float y, int color, boolean dropShadow) {
		GlStateManager.enableAlpha();
		this.resetStyles();
		int i;
		if (dropShadow) {
			i = this.renderString(text, x + 1.0F, y + 1.0F, color, true);
			i = Math.max(i, this.renderString(text, x, y, color, false));
		} else {
			i = this.renderString(text, x, y, color, false);
		}

		return i;
	}

	/**+
	 * Apply Unicode Bidirectional Algorithm to string and return a
	 * new possibly reordered string for visual rendering.
	 */
	private String bidiReorder(String parString1) {
//		try {
//			Bidi bidi = new Bidi((new ArabicShaping(8)).shape(parString1), 127);
//			bidi.setReorderingMode(0);
//			return bidi.writeReordered(2);
//		} catch (ArabicShapingException var3) {
//			return parString1;
//		}
		return parString1;
	}

	/**+
	 * Reset all style flag fields in the class to false; called at
	 * the start of string rendering
	 */
	protected void resetStyles() {
		this.randomStyle = false;
		this.boldStyle = false;
		this.italicStyle = false;
		this.underlineStyle = false;
		this.strikethroughStyle = false;
	}

	/**+
	 * Render a single line string at the current (posX,posY) and
	 * update posX
	 */
	protected void renderStringAtPos(String parString1, boolean parFlag) {
		for (int i = 0; i < parString1.length(); ++i) {
			char c0 = parString1.charAt(i);
			if (c0 == 167 && i + 1 < parString1.length()) {
				int i1 = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(parString1.charAt(i + 1)));
				if (i1 < 16) {
					this.randomStyle = false;
					this.boldStyle = false;
					this.strikethroughStyle = false;
					this.underlineStyle = false;
					this.italicStyle = false;
					if (i1 < 0 || i1 > 15) {
						i1 = 15;
					}

					if (parFlag) {
						i1 += 16;
					}

					int j1 = this.colorCode[i1];
					this.textColor = j1;
					GlStateManager.color((float) (j1 >> 16) / 255.0F, (float) (j1 >> 8 & 255) / 255.0F,
							(float) (j1 & 255) / 255.0F, this.alpha);
				} else if (i1 == 16) {
					this.randomStyle = true;
				} else if (i1 == 17) {
					this.boldStyle = true;
				} else if (i1 == 18) {
					this.strikethroughStyle = true;
				} else if (i1 == 19) {
					this.underlineStyle = true;
				} else if (i1 == 20) {
					this.italicStyle = true;
				} else if (i1 == 21) {
					this.randomStyle = false;
					this.boldStyle = false;
					this.strikethroughStyle = false;
					this.underlineStyle = false;
					this.italicStyle = false;
					GlStateManager.color(this.red, this.blue, this.green, this.alpha);
				}

				++i;
			} else {
				int j = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000"
						.indexOf(c0);
				if (this.randomStyle && j != -1) {
					int k = this.getCharWidth(c0);

					char c1;
					while (true) {
						j = this.fontRandom.nextInt(
								"\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000"
										.length());
						c1 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000"
								.charAt(j);
						if (k == this.getCharWidth(c1)) {
							break;
						}
					}

					c0 = c1;
				}

				float f1 = this.unicodeFlag ? 0.5F : 1.0F;
				boolean flag = (c0 == 0 || j == -1 || this.unicodeFlag) && parFlag;
				if (flag) {
					this.posX -= f1;
					this.posY -= f1;
				}

				float f = this.func_181559_a(c0, this.italicStyle);
				if (flag) {
					this.posX += f1;
					this.posY += f1;
				}

				if (this.boldStyle) {
					this.posX += f1;
					if (flag) {
						this.posX -= f1;
						this.posY -= f1;
					}

					this.func_181559_a(c0, this.italicStyle);
					this.posX -= f1;
					if (flag) {
						this.posX += f1;
						this.posY += f1;
					}

					++f;
				}

				if (this.strikethroughStyle) {
					Tessellator tessellator = Tessellator.getInstance();
					WorldRenderer worldrenderer = tessellator.getWorldRenderer();
					GlStateManager.disableTexture2D();
					worldrenderer.begin(7, DefaultVertexFormats.POSITION);
					worldrenderer.pos((double) this.posX, (double) (this.posY + (float) (this.FONT_HEIGHT / 2)), 0.0D)
							.endVertex();
					worldrenderer
							.pos((double) (this.posX + f), (double) (this.posY + (float) (this.FONT_HEIGHT / 2)), 0.0D)
							.endVertex();
					worldrenderer.pos((double) (this.posX + f),
							(double) (this.posY + (float) (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
					worldrenderer
							.pos((double) this.posX, (double) (this.posY + (float) (this.FONT_HEIGHT / 2) - 1.0F), 0.0D)
							.endVertex();
					tessellator.draw();
					GlStateManager.enableTexture2D();
				}

				if (this.underlineStyle) {
					Tessellator tessellator1 = Tessellator.getInstance();
					WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
					GlStateManager.disableTexture2D();
					worldrenderer1.begin(7, DefaultVertexFormats.POSITION);
					int l = this.underlineStyle ? -1 : 0;
					worldrenderer1.pos((double) (this.posX + (float) l),
							(double) (this.posY + (float) this.FONT_HEIGHT), 0.0D).endVertex();
					worldrenderer1.pos((double) (this.posX + f), (double) (this.posY + (float) this.FONT_HEIGHT), 0.0D)
							.endVertex();
					worldrenderer1
							.pos((double) (this.posX + f), (double) (this.posY + (float) this.FONT_HEIGHT - 1.0F), 0.0D)
							.endVertex();
					worldrenderer1.pos((double) (this.posX + (float) l),
							(double) (this.posY + (float) this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
					tessellator1.draw();
					GlStateManager.enableTexture2D();
				}

				this.posX += (float) ((int) f);
			}
		}

	}

	/**+
	 * Render string either left or right aligned depending on
	 * bidiFlag
	 */
	private int renderStringAligned(String text, int x, int y, int wrapWidth, int color, boolean parFlag) {
		if (this.bidiFlag) {
			int i = this.getStringWidth(this.bidiReorder(text));
			x = x + wrapWidth - i;
		}

		return this.renderString(text, (float) x, (float) y, color, parFlag);
	}

    public float drawRightAlignedStringWithShadow(String text, float x, float y, int color) {
        return this.drawStringWithShadow(text, x - this.getStringWidth(text), y, color);
    }

	/**+
	 * Render single line string by setting GL color, current
	 * (posX,posY), and calling renderStringAtPos()
	 */
	private int renderString(String text, float x, float y, int color, boolean dropShadow) {
		if (text == null) {
			this.posX = x;
			this.posY = y;
		} else {
			if (this.bidiFlag) {
				text = this.bidiReorder(text);
			}

			if ((color & -67108864) == 0) {
				color |= -16777216;
			}

			if (dropShadow) {
				color = (color & 16579836) >> 2 | color & -16777216;
			}

			this.red = (float) (color >> 16 & 255) / 255.0F;
			this.blue = (float) (color >> 8 & 255) / 255.0F;
			this.green = (float) (color & 255) / 255.0F;
			this.alpha = (float) (color >> 24 & 255) / 255.0F;
			GlStateManager.color(this.red, this.blue, this.green, this.alpha);
			this.posX = x;
			this.posY = y;
			this.renderStringAtPos(text, dropShadow);
		}
		return (int) this.posX;
	}

	/**+
	 * Returns the width of this string. Equivalent of
	 * FontMetrics.stringWidth(String s).
	 */
	public int getStringWidth(String text) {
		if (text == null) {
			return 0;
		} else {
			int i = 0;
			boolean flag = false;

			for (int j = 0; j < text.length(); ++j) {
				char c0 = text.charAt(j);
				int k = this.getCharWidth(c0);
				if (k < 0 && j < text.length() - 1) {
					++j;
					c0 = text.charAt(j);
					if (c0 != 108 && c0 != 76) {
						if (c0 == 114 || c0 == 82) {
							flag = false;
						}
					} else {
						flag = true;
					}

					k = 0;
				}

				i += k;
				if (flag && k > 0) {
					++i;
				}
			}

			return i;
		}
	}

	/**+
	 * Returns the width of this character as rendered.
	 */
	public int getCharWidth(char character) {
		if (character == 167) {
			return -1;
		} else if (character == 32) {
			return 4;
		} else {
			int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000"
					.indexOf(character);
			if (character > 0 && i != -1 && !this.unicodeFlag) {
				return this.charWidth[i];
			} else if (this.glyphWidth[character] != 0) {
				int j = this.glyphWidth[character] >>> 4;
				int k = this.glyphWidth[character] & 15;
				if (k > 7) {
					k = 15;
					j = 0;
				}

				++k;
				return (k - j) / 2 + 1;
			} else {
				return 0;
			}
		}
	}

	/**+
	 * Trims a string to fit a specified Width.
	 */
	public String trimStringToWidth(String text, int width) {
		return this.trimStringToWidth(text, width, false);
	}

	/**+
	 * Trims a string to fit a specified Width.
	 */
	public String trimStringToWidth(String text, int width, boolean reverse) {
		StringBuilder stringbuilder = new StringBuilder();
		int i = 0;
		int j = reverse ? text.length() - 1 : 0;
		int k = reverse ? -1 : 1;
		boolean flag = false;
		boolean flag1 = false;

		for (int l = j; l >= 0 && l < text.length() && i < width; l += k) {
			char c0 = text.charAt(l);
			int i1 = this.getCharWidth(c0);
			if (flag) {
				flag = false;
				if (c0 != 108 && c0 != 76) {
					if (c0 == 114 || c0 == 82) {
						flag1 = false;
					}
				} else {
					flag1 = true;
				}
			} else if (i1 < 0) {
				flag = true;
			} else {
				i += i1;
				if (flag1) {
					++i;
				}
			}

			if (i > width) {
				break;
			}

			if (reverse) {
				stringbuilder.insert(0, c0);
			} else {
				stringbuilder.append(c0);
			}
		}

		return stringbuilder.toString();
	}

	/**+
	 * Remove all newline characters from the end of the string
	 */
	private String trimStringNewline(String text) {
		while (text != null && text.endsWith("\n")) {
			text = text.substring(0, text.length() - 1);
		}

		return text;
	}

	/**+
	 * Splits and draws a String with wordwrap (maximum length is
	 * parameter k)
	 */
	public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor) {
		this.resetStyles();
		if ((textColor & -67108864) == 0) {
			textColor |= -16777216;
		}
		this.textColor = textColor;
		str = this.trimStringNewline(str);
		this.renderSplitString(str, x, y, wrapWidth, false);
	}

	/**+
	 * Perform actual work of rendering a multi-line string with
	 * wordwrap and with darker drop shadow color if flag is set
	 */
	private void renderSplitString(String str, int x, int y, int wrapWidth, boolean addShadow) {
		List<String> lst = this.listFormattedStringToWidth(str, wrapWidth);
		for (int i = 0, l = lst.size(); i < l; ++i) {
			this.renderStringAligned(lst.get(i), x, y, wrapWidth, this.textColor, addShadow);
			y += this.FONT_HEIGHT;
		}

	}

	/**+
	 * Returns the width of the wordwrapped String (maximum length
	 * is parameter k)
	 */
	public int splitStringWidth(String parString1, int parInt1) {
		return this.FONT_HEIGHT * this.listFormattedStringToWidth(parString1, parInt1).size();
	}

	/**+
	 * Set unicodeFlag controlling whether strings should be
	 * rendered with Unicode fonts instead of the default.png font.
	 */
	public void setUnicodeFlag(boolean unicodeFlagIn) {
		this.unicodeFlag = unicodeFlagIn;
	}

	/**+
	 * Get unicodeFlag controlling whether strings should be
	 * rendered with Unicode fonts instead of the default.png font.
	 */
	public boolean getUnicodeFlag() {
		return this.unicodeFlag;
	}

	/**+
	 * Set bidiFlag to control if the Unicode Bidirectional
	 * Algorithm should be run before rendering any string.
	 */
	public void setBidiFlag(boolean bidiFlagIn) {
		this.bidiFlag = bidiFlagIn;
	}

    public int getHeight() {
        float scaleFactor = Ref.getInstanceCreator().createScaledResolution().getScaleFactor();
        float scale = (2.0f / scaleFactor);

        boolean followMinecraftScale = false;

        try {
            followMinecraftScale = ArchClient.getInstance().globalSettings.followMinecraftScale.<Boolean>value();
        } catch (Exception ignored) {

        }

        if (followMinecraftScale) {
            return (int) (((FONT_HEIGHT * scale) - 8) / 2);
        } else {
            return (FONT_HEIGHT - 8) / 2;
        }
    }

	/**+
	 * Breaks a string into a list of pieces that will fit a
	 * specified width.
	 */
	public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
		return Arrays.asList(this.wrapFormattedStringToWidth(str, wrapWidth, 0).split("\n"));
	}

	/**+
	 * Inserts newline and formatting into a string to wrap it
	 * within the specified width.
	 */
	String wrapFormattedStringToWidth(String str, int wrapWidth, int depthCheck) { // TODO: fix recursive
		if (depthCheck > 20) {
			return str;
		}
		int i = this.sizeStringToWidth(str, wrapWidth);
		if (str.length() <= i) {
			return str;
		} else {
			String s = str.substring(0, i);
			char c0 = str.charAt(i);
			boolean flag = c0 == 32 || c0 == 10;
			String s1 = getFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
			return s + "\n" + this.wrapFormattedStringToWidth(s1, wrapWidth, ++depthCheck);
		}
	}

	/**+
	 * Determines how many characters from the string will fit into
	 * the specified width.
	 */
	private int sizeStringToWidth(String str, int wrapWidth) {
		int i = str.length();
		int j = 0;
		int k = 0;
		int l = -1;

		for (boolean flag = false; k < i; ++k) {
			char c0 = str.charAt(k);
			switch (c0) {
			case '\n':
				--k;
				break;
			case ' ':
				l = k;
			default:
				j += this.getCharWidth(c0);
				if (flag) {
					++j;
				}
				break;
			case '\u00a7':
				if (k < i - 1) {
					++k;
					char c1 = str.charAt(k);
					if (c1 != 108 && c1 != 76) {
						if (c1 == 114 || c1 == 82 || isFormatColor(c1)) {
							flag = false;
						}
					} else {
						flag = true;
					}
				}
			}

			if (c0 == 10) {
				++k;
				l = k;
				break;
			}

			if (j > wrapWidth) {
				break;
			}
		}

		return k != i && l != -1 && l < k ? l : k;
	}

    public String setWrapWords(String string, double d) {
        return this.wrapWords(string, d, false);
    }

    public String wrapWords(String string, double d, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        float f = 0.0f;
        int n = bl ? string.length() - 1 : 0;
        int n2 = bl ? -1 : 1;
        boolean bl2 = false;
        boolean bl3 = false;
        for (int i = n; i >= 0 && i < string.length() && f < (float)d; i += n2) {
            char c = string.charAt(i);
            double d2 = this.getStringWidth(String.valueOf(c));
            if (bl2) {
                bl2 = false;
                if (c != 'l' && c != 'L') {
                    if (c == 'r' || c == 'R') {
                        bl3 = false;
                    }
                } else {
                    bl3 = true;
                }
            } else if (d2 < 0.0) {
                bl2 = true;
            } else {
                f = (float)((double)f + d2);
                if (bl3) {
                    f += 1.0f;
                }
            }
            if (f > (float)d) break;
            if (bl) {
                stringBuilder.insert(0, c);
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

	/**+
	 * Checks if the char code is a hexadecimal character, used to
	 * set colour.
	 */
	private static boolean isFormatColor(char colorChar) {
		return colorChar >= 48 && colorChar <= 57 || colorChar >= 97 && colorChar <= 102
				|| colorChar >= 65 && colorChar <= 70;
	}

	/**+
	 * Checks if the char code is O-K...lLrRk-o... used to set
	 * special formatting.
	 */
	private static boolean isFormatSpecial(char formatChar) {
		return formatChar >= 107 && formatChar <= 111 || formatChar >= 75 && formatChar <= 79 || formatChar == 114
				|| formatChar == 82;
	}

	/**+
	 * Digests a string for nonprinting formatting characters then
	 * returns a string containing only that formatting.
	 */
	public static String getFormatFromString(String text) {
		String s = "";
		int i = -1;
		int j = text.length();

		while ((i = text.indexOf(167, i + 1)) != -1) {
			if (i < j - 1) {
				char c0 = text.charAt(i + 1);
				if (isFormatColor(c0)) {
					s = "\u00a7" + c0;
				} else if (isFormatSpecial(c0)) {
					s = s + "\u00a7" + c0;
				}
			}
		}

		return s;
	}

	/**+
	 * Get bidiFlag that controls if the Unicode Bidirectional
	 * Algorithm should be run before rendering any string
	 */
	public boolean getBidiFlag() {
		return this.bidiFlag;
	}

	public int getColorCode(char character) {
		return this.colorCode["0123456789abcdef".indexOf(character)];
	}

    public int getColorForID(int id) {
        return this.colorCode[id];
    }
}