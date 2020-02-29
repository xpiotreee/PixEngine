package com.piotreee.game.client;

import com.piotreee.game.objects.client.ClientGameObject;
import com.piotreee.game.packets.AddGameObjectPacket;
import com.piotreee.game.packets.TestPacket;
import com.piotreee.game.packets.UpdateGameObjectPacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.Client;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.objects.Transform;
import com.piotreee.pixengine.objects.Updatable;
import com.piotreee.pixengine.render.Sprite;
import com.piotreee.pixengine.util.Resources;
import io.netty.channel.ChannelHandlerContext;
import org.joml.Vector2f;

import java.util.List;

public class GameClient extends Client {
    private Game game;

    public GameClient(String host, int port, Game game) {
        super(host, port);
        this.game = game;
        addListeners(new PacketListener<TestPacket>(TestPacket.class) {
            @Override
            public void on(ChannelHandlerContext ctx, TestPacket testPacket) {
                game.getConnectedCount().setText(testPacket.getTest() + "");
            }
        }, new PacketListener<UpdateGameObjectPacket>(UpdateGameObjectPacket.class) {
            @Override
            public void on(ChannelHandlerContext ctx, UpdateGameObjectPacket packet) {
                update(packet);
            }
        }, new PacketListener<AddGameObjectPacket>(AddGameObjectPacket.class) {
            @Override
            public void on(ChannelHandlerContext ctx, AddGameObjectPacket packet) {
                addGameObject(packet);
            }
        });
    }

    private void addGameObject(AddGameObjectPacket packet) {
        Transform transform = new Transform(
                new Vector2f(packet.getPosX(), packet.getPosY()),
                new Vector2f(packet.getScaleX(), packet.getScaleY()),
                packet.getRotation());

        game.add(
                new ClientGameObject(packet.getGameObjectId(), transform,
                        new Vector2f(packet.getVelX(), packet.getVelY()),
                        new Sprite(transform, Resources.getTexture(packet.getSpriteName()))));
    }

    private void update(UpdateGameObjectPacket packet) {
        List<Updatable> updatables = game.getUpdatables();
        int size = game.getUpdatablesSize();
        for (int i = 0; i < size; i++) {
            Updatable updatable = updatables.get(i);
            if (updatable instanceof ClientGameObject) {
                ClientGameObject gameObject = (ClientGameObject) updatable;
                System.out.println("gameobject id: " + gameObject.getId());
                System.out.println("packet gameobject id: " + packet.getGameObjectId());
                if (gameObject.getId() == packet.getGameObjectId()) {
                    gameObject.getTransform().position.set(packet.getPosX(), packet.getPosY());
                    gameObject.getTransform().setRotation(packet.getRotation());
                    gameObject.setVelocity(packet.getVelX(), packet.getVelY());
                }
            }
        }
    }
}
