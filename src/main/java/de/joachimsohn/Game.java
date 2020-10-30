package de.joachimsohn;

import de.joachimsohn.engine.graphics.Mesh;
import de.joachimsohn.engine.graphics.Renderer;
import de.joachimsohn.engine.graphics.Shader;
import de.joachimsohn.engine.graphics.Vertex;
import de.joachimsohn.engine.managers.DisplayManager;
import de.joachimsohn.engine.managers.InputManager;
import de.joachimsohn.engine.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Game implements Runnable {

    private Thread game;

    private DisplayManager displayManager;
    private InputManager inputManager;

    private Renderer renderer;

    Mesh mesh = new Mesh(new Vertex[]{
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f), new Vector3f(1.0f,1.0f,1.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.0f), new Vector3f(0.0f,1.0f,1.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector3f(1.0f,0.0f,1.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(1.0f,1.0f,0.0f))
    }, new int[]{
            0, 1, 2,
            0, 3, 2
    });

    Shader shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");


    public void init() {
        inputManager = new InputManager();
        displayManager = new DisplayManager(inputManager).createDisplay();
        mesh.create();
        shader.create();
    }

    public void start() {
        game = new Thread(this, "game");
        renderer = new Renderer(shader);
        game.start();
    }

    public void loop() {
        displayManager.updateDisplay();
        renderer.renderMesh(mesh);
        displayManager.swapBuffers();
    }

    @Override
    public void run() {
        init();
        while (!displayManager.windowShouldClose() && !inputManager.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            loop();
            if (inputManager.isKeyDown(GLFW.GLFW_KEY_F11)) displayManager.setFullscreen(!displayManager.isFullscreen());
        }
       close();
    }

    private void close(){
        displayManager.closeWindow();
        mesh.destroy();
        shader.destroy();
    }

}
