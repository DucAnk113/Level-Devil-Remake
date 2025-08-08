package main.java.com.leveldevil.remake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Texture texture;
    private Rectangle bounds;
    private float x, y;
    private float speed = 200; // Tốc độ di chuyển
    private float jumpForce = 400; // Lực nhảy
    private float velocityY = 0; // Vận tốc theo trục Y
    private boolean isJumping = false;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        texture = new Texture("player.png"); // Đặt ảnh nhân vật
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
        // Cập nhật vị trí
        bounds.setPosition(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void dispose() {
        texture.dispose();
    }

    // Getter và setter
    public Rectangle getBounds() { return bounds; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public float getX() { return x; }
    public float getY() { return y; }
    public void setVelocityY(float velocityY) { this.velocityY = velocityY; }
    public float getVelocityY() { return velocityY; }
    public float getSpeed() { return speed; }
    public float getJumpForce() { return jumpForce; }
    public boolean isJumping() { return isJumping; }
    public void setJumping(boolean jumping) { isJumping = jumping; }
}
