package com.piotreee.game.objects;

import com.piotreee.game.packets.AddGameObjectPacket;
import com.piotreee.game.packets.InputPacket;
import com.piotreee.pixengine.math.Collider;
import com.piotreee.pixengine.objects.Rigidbody;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Renderable;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Sprite;
import com.piotreee.pixengine.text.Font;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Optional;

public class Player extends TestGameObject implements Renderable {
    private static final Font FONT = Resources.getFont("font");
    private static final float SPEED = 5f;//0.015f;
    private InputPacket input;
    private Collider collider;
    private Rigidbody rigidbody;
    private Renderable renderable;

    public Player(AddGameObjectPacket p) {
        super(p);
    }

    public Player(int id) {
        super(id, 2);
        this.rigidbody = new Rigidbody();
        this.input = new InputPacket((byte) 0, (byte) 0);
//        this.collider = new Rectangle(transform.position.x - 0.9f, transform.position.y - 0.9f, 0.8f, 0.8f);
        this.collider = new Collider(transform.position, transform.scale);
    }

    @Override
    public void update(float delta) {
        float fixedSpeed = SPEED * delta;
        if (input.getMoveHorizontally() > 0) {
            rigidbody.addVelocity(fixedSpeed, 0);
        } else if (input.getMoveHorizontally() < 0) {
            rigidbody.addVelocity(-fixedSpeed, 0);
        }

        if (input.getMoveVertically() > 0) {
            rigidbody.addVelocity(0, fixedSpeed);
        } else if (input.getMoveVertically() < 0) {
            rigidbody.addVelocity(0, -fixedSpeed);
        }

//        System.out.println("player pos: x: " + transform.position.x + " y: " + transform.position.y);

        rigidbody.move(this, 3f, delta);
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        renderable.render(shader, camera, view);
        Vector3f scale = view.getScale(new Vector3f());
        FONT.drawText("id: " + id, transform.position.x - 32 / scale.x, transform.position.y - 32 / scale.y, shader, camera, view);
    }

    @Override
    protected void createFromPacket(AddGameObjectPacket p) {
        this.rigidbody = new Rigidbody();
//        this.collider = new Rectangle(transform.position.x - 0.9f, transform.position.y - 0.9f, 0.8f, 0.8f);
        this.collider = new Collider(transform.position, transform.scale);
        this.input = new InputPacket((byte) 0, (byte) 0);
        super.createFromPacket(p);
        this.renderable = new Sprite(transform, Resources.getTexture("test"));
    }

    public void setInput(InputPacket input) {
        this.input = input;
    }

    @Override
    public Optional<Rigidbody> getRigidbody() {
        return Optional.of(rigidbody);
    }

    @Override
    public Optional<Collider> getCollider() {
        return Optional.ofNullable(collider);
    }
}
