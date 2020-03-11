package com.piotreee.pixengine.text;

import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Font {
    private String characters;
    private Letter[] letters;

    public Font(String characters, Letter[] letters) {
        this.characters = characters;
        this.letters = letters;
    }

    public void drawText(String text, float x, float y, Shader shader, Camera camera, Matrix4f view) {
        Vector3f scale = view.getScale(new Vector3f());
        float scaleX = scale.x;
        float scaleY = scale.y;
        shader.bind();
        shader.setUniform("sampler", 0);

        float startX = x;
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                x += 20 / scaleX;
                continue;
            } else if (chars[i] == '\n') {
                x = startX;
                y--;
                continue;
            }

            Letter letter = getLetter(chars[i]);
            Matrix4f projection = camera.getProjection();
            float width = letter.getTexture().getWidth() / scaleX;
            float height = letter.getTexture().getHeight() / scaleY;
            projection.mul(view);
            projection.translate(x, y, 0);
            projection.scale(width * 2, height * 1.5f, 1);
            letter.draw(shader, view, projection);
            x += width;
            if (i + 1 < chars.length && chars[i + 1] != ' ' && chars[i + 1] != '\n') {
                x += getLetter(chars[i + 1]).getTexture().getWidth() / scaleX;
            }
        }
    }

    private Letter getLetter(char character) {
        int index = characters.indexOf(character);
        if (index == -1) {
            index = characters.indexOf('?');
        }

        return letters[index];
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public Letter[] getLetters() {
        return letters;
    }

    public void setLetters(Letter[] letters) {
        this.letters = letters;
    }

    public int calcStringWidth(String string) {
        int width = 0;
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                width += 20;
            } else {
                width += getLetter(chars[i]).getTexture().getWidth();
            }

        }

        return width;
    }
}
