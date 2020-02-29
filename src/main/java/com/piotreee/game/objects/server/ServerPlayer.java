package com.piotreee.game.objects.server;

import com.piotreee.game.packets.InputPacket;
import com.piotreee.pixengine.objects.Transform;
import org.joml.Vector2f;

public class ServerPlayer extends ServerGameObject {
    private float speed;
    private InputPacket input;

    public ServerPlayer(int id, Transform transform, Vector2f velocity, String spriteName) {
        super(id, transform, velocity, spriteName);
        this.speed = 0.015f;
        this.input = new InputPacket((byte) 0, (byte) 0);
    }

    @Override
    public void update(double delta) {
        float fixedSpeed = this.speed * (float) delta;
        if (input.getMoveHorizontally() > 0) {
            velocity.add(fixedSpeed, 0);
        } else if (input.getMoveHorizontally() < 0) {
            velocity.add(-fixedSpeed, 0);
        }

        if (input.getMoveVertically() > 0) {
            velocity.add(0, fixedSpeed);
        } else if (input.getMoveVertically() < 0) {
            velocity.add(0, -fixedSpeed);
        }

        super.update(delta);
        System.out.println(input.toString());
        System.out.println(velocity.toString());
    }

    public ServerPlayer setInput(InputPacket input) {
        this.input = input;
        return this;
    }
}
