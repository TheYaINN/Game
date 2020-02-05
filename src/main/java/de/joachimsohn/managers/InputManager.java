package de.joachimsohn.managers;

import org.lwjgl.glfw.*;
import org.lwjgl.system.Callback;

import java.util.ArrayList;
import java.util.List;

public class InputManager {

    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private double mouseX;
    private double mouseY;
    private double mouseScrollX;
    private double mouseScrollY;

    private GLFWKeyCallback keyboardCallback;
    private GLFWCursorPosCallback mousePosCallback;
    private GLFWMouseButtonCallback mouseButtonsCallback;
    private GLFWScrollCallback mouseScrollCallback;

    private List<Callback> callbacks = new ArrayList();

    public InputManager() {
        keyboardCallback = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };
        addCallback(keyboardCallback);
        mousePosCallback = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };
        addCallback(mousePosCallback);
        mouseButtonsCallback = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                mouseButtons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };
        addCallback(mouseButtonsCallback);

        mouseScrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                mouseScrollX += xoffset;
                mouseScrollY += yoffset;
            }
        };
        addCallback(mouseScrollCallback);
    }

    public void destroy() {
        callbacks.forEach(Callback::free);
    }

    public void addCallback(Callback callback) {
        callbacks.add(callback);
    }


    public boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

    public boolean isMouseDown(int mouseButton) {
        return mouseButtons[mouseButton];
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboardCallback;
    }

    public GLFWCursorPosCallback getMousePosCallback() {
        return mousePosCallback;
    }

    public GLFWMouseButtonCallback getMouseButtonsCallback() {
        return mouseButtonsCallback;
    }

    public GLFWScrollCallback getMouseScrollCallback() {
        return mouseScrollCallback;
    }
}
