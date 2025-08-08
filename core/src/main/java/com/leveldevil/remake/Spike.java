package main.java.com.leveldevil.remake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Spike {
    private Texture texture;
    private Rectangle bounds;

    public Spike(float x, float y) {
        texture = new Texture("spike.png");
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() { return bounds; }
}
