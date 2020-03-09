package com.piotreee.pixengine.objects;

import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.math.Collider;
import org.joml.Vector2f;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public void move(TestGameObject gameObject, float drag, float delta) {
        Vector2f position = gameObject.getTransform().position;
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
            x -= drag * delta;
        } else if (x < 0) {
            x += drag * delta;
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
            y -= drag * delta;
        } else if (y < 0) {
            y += drag * delta;
        }

//        velocity.set(x, y);
//        Vector2f fixedVelocity = velocity.mul(delta, new Vector2f());
//        Vector2f testPosition = gameObject.getTransform().position.add(fixedVelocity, new Vector2f());
//        gameObject.getCollider().ifPresent(collider -> {
//            int tileX = Math.round(testPosition.x);
//            int tileY = Math.round(testPosition.y);
//            Optional[] tiles = Game.INSTANCE.getTileMap().getTiles(tileX - 2, tileY - 2, tileX + 2, tileY + 2);
//            for (int i = 0; i < tiles.length; i++) {
//                ((Optional<Tile>) tiles[i]).flatMap(Tile::getCollider).ifPresent(tileCollider -> {
//                    if (tileCollider.overlaps(new Rectangle(testPosition.x - 0.9f, testPosition.y - 0.9f, collider.width, collider.height))) {
//                        testPosition.set(gameObject.getTransform().position);
//                    }
//                });
//            }
//        });

        velocity.set(x, y);
        Vector2f fixedVelocity = velocity.mul(delta, new Vector2f());
        Vector2f oldPosition = new Vector2f().set(position);
        position.add(fixedVelocity);

        gameObject.getCollider().ifPresent(collider -> {
            AtomicBoolean collides = new AtomicBoolean(false);
            int tileX = Math.round(position.x);
            int tileY = Math.round(position.y);
            Optional[] tiles = Game.INSTANCE.getTileMap().getTiles(tileX - 2, tileY - 2, tileX + 2, tileY + 2);

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
