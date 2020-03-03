package com.piotreee.game.objects.client;

import com.piotreee.game.objects.server.ServerGameObject;
import com.piotreee.pixengine.objects.Transform;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Renderable;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Sprite;
import com.piotreee.pixengine.text.Font;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class ClientGameObject extends ServerGameObject implements Renderable {
    private static final Font FONT = Resources.getFont("font");
    private Sprite sprite;

    public ClientGameObject(int id, Transform transform, Vector2f velocity, Sprite sprite) {
        super(id, transform, velocity, "");
        this.sprite = sprite;
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        sprite.render(shader, camera, view);
        FONT.drawText("id: " + id, transform.position.x - 0.5f, transform.position.y - 1, shader, camera, view);
    }
}
