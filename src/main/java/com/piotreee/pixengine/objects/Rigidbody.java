package com.piotreee.pixengine.objects;

import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.scenes.Game;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.math.Collider;
import org.joml.Vector2f;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Rigidbody {
    private Vector2f velocity = new Vector2f();
    private float minVel = 0.05f;
    private float maxVel = 3f;

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f vel) {
        velocity.set(vel);
    }

    public void setVelocity(float velX, float velY) {
        velocity.set(velX, velY);
    }

    public void addVelocity(Vector2f vel) {
        velocity.add(vel);
    }

    public void addVelocity(float velX, float velY) {
        velocity.add(velX, velY);
    }

    public void move(TestGameObject gameObject, float delta, boolean isServer) {
        Vector2f position = gameObject.getTransform().position;
        TileMap tileMap;
        if (isServer) {
            tileMap = GameServer.INSTANCE.getTileMap();
        } else {
            tileMap = Game.INSTANCE.getTileMap();
        }

        AtomicReference<Float> fixedDrag = new AtomicReference<>(1f * delta);
        tileMap.getTile(position).ifPresent(tile -> fixedDrag.set(tile.getDrag() * delta));

        float x = velocity.x;
        float y = velocity.y;


        if (x > maxVel) {
            x = maxVel;
        } else if (x < -maxVel) {
            x = -maxVel;
        }

        if (x < minVel && x > -minVel) {
            x = 0;
        }

        if (x > 0) {
            x -= fixedDrag.get();
        } else if (x < 0) {
            x += fixedDrag.get();
        }

        if (y > maxVel) {
            y = maxVel;
        } else if (y < -maxVel) {
            y = -maxVel;
        }

        if (y < minVel && y > -minVel) {
            y = 0;
        }

        if (y > 0) {
            y -= fixedDrag.get();
        } else if (y < 0) {
            y += fixedDrag.get();
        }

        velocity.set(x, y);
        Vector2f fixedVelocity = velocity.mul(delta, new Vector2f());
        Vector2f oldPosition = new Vector2f().set(position);
        position.add(fixedVelocity);

        gameObject.getCollider().ifPresent(collider -> {
            AtomicBoolean collides = new AtomicBoolean(false);
            int tileX = Math.round(position.x);
            int tileY = Math.round(position.y);
            Optional[] tiles = tileMap.getTiles(tileX - 2, tileY - 2, tileX + 2, tileY + 2);
            for (int i = 0; i < tiles.length && !collides.get(); i++) {
                ((Optional<Tile>) tiles[i]).flatMap(Tile::getCollider).ifPresent(tileCollider -> {
                    if (collider.overlaps(tileCollider)) {
                        collides.set(true);
                        if (new Collider(oldPosition.add(fixedVelocity.x, 0, new Vector2f()), collider.size).overlaps(tileCollider)) {
                            velocity.set(0, velocity.y);
                        }

                        if (new Collider(oldPosition.add(0, fixedVelocity.y, new Vector2f()), collider.size).overlaps(tileCollider)) {
                            velocity.set(velocity.x, 0);
                        }
//
                        velocity.mul(delta, position).add(oldPosition);
                    }
                });
            }
        });
    }
}
