package com.piotreee.game.objects;

import com.piotreee.game.packets.AddGameObjectPacket;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Renderable;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Sprite;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;

public class Papierz extends TestGameObject implements Renderable {
    private Renderable renderable;

    public Papierz(AddGameObjectPacket p) {
        super(p);
    }

    public Papierz(int id, float x, float y, float scaleX, float scaleY, float rotation) {
        this(id);
        transform.position.set(x, y);
        transform.scale.set(scaleX, scaleY);
        transform.setRotation(rotation);
    }

    public Papierz(int id) {
        super(id, 1);
    }

    @Override
    protected void createFromPacket(AddGameObjectPacket p) {
        super.createFromPacket(p);
        this.renderable = new Sprite(transform, Resources.getTexture("papierz"));
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        renderable.render(shader, camera, view);
    }
}
