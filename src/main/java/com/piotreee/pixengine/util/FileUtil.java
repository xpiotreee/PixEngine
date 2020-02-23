package com.piotreee.pixengine.util;

import com.google.gson.Gson;
import com.piotreee.pixengine.configs.AnimationConfig;
import com.piotreee.pixengine.configs.FontConfig;
import com.piotreee.pixengine.configs.TextureSheetConfig;
import com.piotreee.pixengine.objects.Sheet;
import com.piotreee.pixengine.render.Animation;
import com.piotreee.pixengine.render.Texture;
import com.piotreee.pixengine.text.Font;
import com.piotreee.pixengine.text.Letter;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class FileUtil {
    private static Gson gson = new Gson();

    public static String getShader(String filename) {
        StringBuilder outputString = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream("/assets/shaders/" + filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                outputString.append(line);
                outputString.append("\n");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputString.toString();
    }

    public static Texture getTexture(String filename) {
        try {
            System.out.println("path " + "/assets/textures/" + filename);
            BufferedImage image = ImageIO.read(FileUtil.class.getResourceAsStream("/assets/textures/" + filename));
            return getTexture(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Texture getTexture(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixelsRaw = image.getRGB(0, 0, width, height, null, 0, height);

        ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

        try {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = pixelsRaw[i * height + j];
                    pixels.put((byte) ((pixel >> 16) & 0xFF));
                    pixels.put((byte) ((pixel >> 8) & 0xFF));
                    pixels.put((byte) (pixel & 0xFF));
                    pixels.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            pixels.put((byte) 0x88);
            pixels.put((byte) 0x88);
            pixels.put((byte) 0x88);
            pixels.put((byte) 0x00);
        }


        pixels.flip();

        System.out.println(pixels.toString());

        int textureObject = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureObject);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        return new Texture(textureObject, width, height);
    }

    public static Animation getAnimation(String name) {
        StringBuilder outputString = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream("/assets/animations/" + name + ".json")));
            String line;
            while ((line = reader.readLine()) != null) {
                outputString.append(line);
                outputString.append("\n");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnimationConfig config = gson.fromJson(outputString.toString(), AnimationConfig.class);
        return new Animation(getAnimationFrames(name, config.getAmount(), config.getWidth(), config.getHeight()), config.getFps());
    }

    public static Sheet<Texture> getTextureSheet(String name) {
        StringBuilder outputString = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream("/assets/textureSheets/" + name + ".json")));
            String line;
            while ((line = reader.readLine()) != null) {
                outputString.append(line);
                outputString.append("\n");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextureSheetConfig config = gson.fromJson(outputString.toString(), TextureSheetConfig.class);
        Sheet<Texture> sheet = new Sheet<>(config.getWidth(), config.getHeight());
        try {
            BufferedImage image = ImageIO.read(FileUtil.class.getResourceAsStream("/assets/textureSheets/" + name + ".png"));
            for (int i = 0; i < config.getWidth(); i++) {
                for (int j = 0; j < config.getHeight(); j++) {
                    sheet.setElement(getTexture(image.getSubimage(
                            i * config.getImgWidth(), j * config.getImgHeight(), config.getImgWidth(), config.getImgHeight()
                    )), i, j);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sheet;
    }

    public static Font getFont(String name) {
        StringBuilder outputString = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream("/assets/fonts/" + name + ".json")));
            String line;
            while ((line = reader.readLine()) != null) {
                outputString.append(line);
                outputString.append("\n");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FontConfig config = gson.fromJson(outputString.toString(), FontConfig.class);
        return new Font(config.getCharacters(), getLetters(name, config.getCharacters().length()));
    }

    private static Texture[] getAnimationFrames(String name, int size, int width, int height) {
        Texture[] frames = new Texture[size];
        try {
            BufferedImage image = ImageIO.read(FileUtil.class.getResourceAsStream("/assets/animations/" + name + ".png"));
            for (int i = 0; i < size; i++) {
                frames[i] = getTexture(image.getSubimage(i * width, 0, width, height));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frames;
    }

    private static Letter[] getLetters(String name, int length) {
        Letter[] letters = new Letter[length];
        try {
            BufferedImage image = ImageIO.read(FileUtil.class.getResourceAsStream("/assets/fonts/" + name + ".png"));
            int character = 0;
            int x = 0;

            for (int posX = 0; posX < image.getWidth(); posX++) {
                boolean endOfChar = false;
                for (int posY = 0; posY < image.getHeight(); posY++) {
                    endOfChar = ((image.getRGB(posX, posY) >> 24) & 0xFF) == 0x00;
                    if (!endOfChar) {
                        break;
                    }
                }

                if (endOfChar) {
                    letters[character] = new Letter(getTexture(image.getSubimage(x, 0, posX - x, image.getHeight())));
                    x = posX;
                    character++;
                }
            }

            System.out.println(character);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return letters;
    }
}
