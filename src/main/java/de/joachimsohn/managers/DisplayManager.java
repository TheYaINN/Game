package de.joachimsohn.managers;

import de.joachimsohn.math.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class DisplayManager {

    private long WINDOW;
    private int WIDTH = 1920;
    private int HEIGHT = 1080;
    int[] windowPosX = new int[1];
    int[] windowPosY = new int[1];

    private boolean isResized;
    private boolean isFullscreen;


    private InputManager inputManager;

    private Vector3f background = new Vector3f(1f, 0f, 0f);
    private float backgroundalpha;

    GLFWWindowSizeCallback windowSizeCallback;



    public DisplayManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public DisplayManager createDisplay() {
        //Init GLFW
        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        //Create Window
        WINDOW = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "My first game", isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0); //set monitor to GLFW.getprimaryMonitor() for fullscreen
        if (WINDOW == 0) throw new RuntimeException("Failed to create the GLFW window");
        setBackgroundColor(background.getX(), background.getY(), background.getZ(), backgroundalpha);

        //configure GLFW
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        windowPosX[0] = ((videoMode.width() - WIDTH) / 2);
        windowPosY[0] = ((videoMode.height() - HEIGHT) / 2);
        GLFW.glfwSetWindowPos(getWINDOW(), windowPosX[0], windowPosY[0]);
        GLFW.glfwMakeContextCurrent(WINDOW);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        createCallbacks();

        //Show window
        GLFW.glfwShowWindow(WINDOW);

        GLFW.glfwSwapInterval(1);
        return this;
    }

    public void updateDisplay() {
        if (isResized) {
            GL11.glViewport(0, 0, WIDTH, HEIGHT);
            isResized = false;
        }
        GL11.glClearColor(background.getX(), background.getY(), background.getZ(), backgroundalpha);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
        if (inputManager.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println(String.format("X: %s | Y: %s", inputManager.getMouseX(), inputManager.getMouseY()));
        }
    }

    private void createCallbacks() {
        windowSizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int width, int height) {
                WIDTH = width;
                HEIGHT = height;
                WINDOW = window;
                isResized = true;
            }
        };
        inputManager.addCallback(windowSizeCallback);

        GLFW.glfwSetKeyCallback(WINDOW, inputManager.getKeyboardCallback());
        GLFW.glfwSetMouseButtonCallback(WINDOW, inputManager.getMouseButtonsCallback());
        GLFW.glfwSetCursorPosCallback(WINDOW, inputManager.getMousePosCallback());
        GLFW.glfwSetScrollCallback(WINDOW, inputManager.getMouseScrollCallback());
        GLFW.glfwSetWindowSizeCallback(WINDOW, windowSizeCallback);
    }

    public void setBackgroundColor(float r, float g, float b, float alpha) {
        background.setVector(r, g, b);
        backgroundalpha = alpha;
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(WINDOW);
    }

    public boolean windowShouldClose() {
        return GLFW.glfwWindowShouldClose(WINDOW);
    }

    public long getWINDOW() {
        return WINDOW;
    }

    public void closeWindow() {
        GLFW.glfwDestroyWindow(WINDOW);
    }

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
        isResized = true;
        if (isFullscreen) {
            GLFW.glfwGetWindowPos(WINDOW, windowPosX, windowPosY);
            GLFW.glfwSetWindowMonitor(WINDOW, GLFW.glfwGetPrimaryMonitor(), 0, 0, WIDTH, HEIGHT, 0);
        } else {
            GLFW.glfwSetWindowMonitor(WINDOW, 0, windowPosX[0], windowPosY[0], WIDTH, HEIGHT, 0);
        }
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }
}
