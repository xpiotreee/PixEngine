package com.piotreee.pixengine.util;

import com.piotreee.pixengine.objects.Sheet;
import com.piotreee.pixengine.render.Animation;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Texture;
import com.piotreee.pixengine.text.Font;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

public class Resources {
    private static HashMap<String, Texture> textures = new HashMap<>();
    private static HashMap<String, Sheet<Texture>> textureSheets = new HashMap<>();
    private static HashMap<String, Animation> animations = new HashMap<>();
    private static HashMap<String, Shader> shaders = new HashMap<>();
    private static HashMap<String, Font> fonts = new HashMap<>();

    public static void init() {
        try {
            URI uri = Resources.class.getResource("/assets").toURI();
            Path path;
            if (uri.getScheme().equalsIgnoreCase("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                path = fileSystem.getPath("/assets");
            } else {
                path = Paths.get(uri);
            }

            Stream<Path> walk = Files.walk(path);
            for (Iterator<Path> it = walk.iterator(); it.hasNext(); ) {
                String resPath = it.next().toString();
                System.out.println(resPath);
                if (resPath.contains("/textures/") && !resPath.endsWith("/textures/")) {
                    System.out.println("texture path " + resPath);
                    String[] split = resPath.split("/textures/");
                    String filename = split[split.length - 1];
                    textures.put(filename.split("\\.")[0], FileUtil.getTexture(filename));
                } else if (resPath.contains("/shaders/") && !resPath.endsWith("/shaders/")) {
                    String[] split = resPath.split("/shaders/");
                    String filename = split[split.length - 1];
                    String shaderName = filename.split("\\.")[0].replace("vs", "").replace("fs", "");
                    if (!shaders.containsKey(shaderName)) {
                        shaders.put(shaderName, new Shader(shaderName));
                    }
                } else if (resPath.contains("/animations/") && resPath.endsWith(".json")) {
                    String[] split = resPath.split("/animations/");
                    String animationName = split[split.length - 1].split("\\.")[0];
                    animations.put(animationName, FileUtil.getAnimation(animationName));
                } else if (resPath.contains("/textureSheets/") && resPath.endsWith("json")) {
                    String[] split = resPath.split("/textureSheets/");
                    String sheetName = split[split.length - 1].split("\\.")[0];
                    textureSheets.put(sheetName, FileUtil.getTextureSheet(sheetName));
                } else if (resPath.contains("/fonts/") && resPath.endsWith("json")) {
                    String[] split = resPath.split("/fonts/");
                    String fontName = split[split.length - 1].split("\\.")[0];
                    fonts.put(fontName, FileUtil.getFont(fontName));
                }
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTexture(String name, Texture texture) {
        textures.put(name, texture);
    }

    public static void addTextureSheet(String name, Sheet<Texture> textureSheet) {
        textureSheets.put(name, textureSheet);
    }

    public static void addAnimation(String name, Animation animation) {
        animations.put(name, animation);
    }

    public static void addShader(String name, Shader shader) {
        shaders.put(name, shader);
    }

    public static void addFont(String name, Font font) {
        fonts.put(name, font);
    }

    public static Texture getTexture(String name) {
        return textures.get(name);
    }

    public static Sheet<Texture> getTextureSheet(String name) {
        return textureSheets.get(name);
    }

    public static Animation getAnimation(String name) {
        return animations.get(name);
    }

    public static Shader getShader(String name) {
        return shaders.get(name);
    }

    public static Font getFont(String name) {
        return fonts.get(name);
    }
}