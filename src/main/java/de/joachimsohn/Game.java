package de.joachimsohn;

import de.joachimsohn.graphics.Mesh;
import de.joachimsohn.graphics.Renderer;
import de.joachimsohn.graphics.Vertex;
import de.joachimsohn.managers.DisplayManager;
import de.joachimsohn.managers.InputManager;
import de.joachimsohn.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Game implements Runnable {

    private Thread game;

    private DisplayManager displayManager;
    private InputManager inputManager;

    private Renderer renderer = new Renderer();

    Mesh mesh = new Mesh(new Vertex[]{
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f))
    }, new int[]{
            0, 1, 2,
            0, 3, 2
    });


    public void init() {
        inputManager = new InputManager();
        displayManager = new DisplayManager(inputManager).createDisplay();
        mesh.create();
    }

    public void start() {
        game = new Thread(this, "game");
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
        while (!displayManager.windowShouldClose()) {
            loop();
            if (inputManager.isKeyDown(GLFW.GLFW_KEY_F11)) displayManager.setFullscreen(!displayManager.isFullscreen());
        }
        displayManager.closeWindow();
    }

}
