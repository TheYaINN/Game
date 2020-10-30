package de.joachimsohn.engine.graphics;

import de.joachimsohn.engine.math.Vector3f;
import lombok.Getter;

@Getter
public class Vertex {

    private Vector3f position;
    private Vector3f color;


    public Vertex(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

}
