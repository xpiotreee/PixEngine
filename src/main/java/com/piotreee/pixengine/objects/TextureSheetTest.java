package com.piotreee.pixengine.objects;

import com.piotreee.pixengine.render.Model;
import com.piotreee.pixengine.render.Sprite;
import com.piotreee.pixengine.render.Texture;
import org.joml.Vector2i;

public class TextureSheetTest extends TestObject {
    private Sheet<Sprite> spriteSheet;
    private Vector2i currentSprite = new Vector2i(0, 0);

    public TextureSheetTest(Transform transform, Sheet<Texture> textureSheet) {
        super(transform, Model.RECTANGLE);
        this.spriteSheet = new Sheet<>(textureSheet.getWidth(), textureSheet.getHeight());
        for (int i = 0; i < textureSheet.getWidth(); i++) {
            for (int j = 0; j < textureSheet.getHeight(); j++) {
                spriteSheet.setElement(new Sprite(transform, textureSheet.getElement(i, j)), i, j);
            }
        }

        setCurrent(currentSprite);
    }

    public void setCurrent(int x, int y) {
        this.renderable = spriteSheet.getElement(x, y);
    }

    public void setCurrent(Vector2i position) {
        setCurrent(position.x, position.y);
    }

    public Vector2i getCurrentSprite() {
        return currentSprite;
    }
}
