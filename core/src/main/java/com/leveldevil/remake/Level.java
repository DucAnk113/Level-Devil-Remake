package main.java.com.leveldevil.remake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public class Level {
    private Texture groundTexture;
    private Rectangle groundBounds;
    private ArrayList<Spike> spikes;

    public Level() {
        groundTexture = new Texture("ground.png");
        groundBounds = new Rectangle(0, 0, 800, 50);
        spikes = new ArrayList<>();
        spikes.add(new Spike(200, 50)); // Gai tại vị trí (200, 50)
        spikes.add(new Spike(300, 50)); // Gai tại vị trí (300, 50)
    }

    public void render(SpriteBatch batch) {
        batch.draw(groundTexture, groundBounds.x, groundBounds.y);
        for (Spike spike : spikes) {
            spike.render(batch);
        }
    }

    public void dispose() {
        groundTexture.dispose();
        for (Spike spike : spikes) {
            spike.dispose();
        }
    }

    public Rectangle getGroundBounds() { return groundBounds; }
    public ArrayList<Spike> getSpikes() { return spikes; }
}
