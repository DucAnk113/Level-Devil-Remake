package main.java.com.leveldevil.remake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LevelDevilGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture playerTexture;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body playerBody;
    private float playerX = 12.5f; // Vị trí giữa bản đồ (12.5m)
    private float playerY = 1f;    // Đứng trên mặt đất (1m)
    private final float gravity = -9.81f; // Trọng lực thực tế (m/s^2)
    private final float speed = 5f;      // Tốc độ (m/s)
    private final float jumpForce = 10f; // Lực nhảy (m/s)
    private boolean isJumping;

    @Override
    public void create() {
        // Cấu hình camera và viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(800 / 32f, 600 / 32f, camera); // 25m x 18.75m
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Tải Tiled Map
        tiledMap = new TmxMapLoader().load("level.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 32f);

        // Khởi tạo Box2D World
        world = new World(new Vector2(0, gravity), true);
        debugRenderer = new Box2DDebugRenderer();

        // Tạo body cho player
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerX, playerY);
        playerBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f); // Kích thước player (0.5m x 0.5m)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        playerBody.createFixture(fixtureDef);
        shape.dispose();

        // Tạo body tĩnh cho ground (dựa trên layer "Ground" trong .tmx)
        createGroundBody();

        batch = new SpriteBatch();
        playerTexture = new Texture("player.png");
    }

    private void createGroundBody() {
        // Logic đơn giản để tạo body cho ground (cần mở rộng dựa trên layer trong .tmx)
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, 0); // Giả sử ground ở y = 0
        Body groundBody = world.createBody(groundDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(viewport.getWorldWidth() / 2, 1f); // Ground dài ngang màn hình, cao 1m
        FixtureDef groundFixture = new FixtureDef();
        groundFixture.shape = groundShape;
        groundBody.createFixture(groundFixture);
        groundShape.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Cập nhật World
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        // Cập nhật vị trí player từ Box2D
        playerX = playerBody.getPosition().x;
        playerY = playerBody.getPosition().y;

        // Xử lý input
        float velocityX = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) velocityX = -speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) velocityX = speed;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping) {
            playerBody.applyLinearImpulse(new Vector2(0, jumpForce), playerBody.getWorldCenter(), true);
            isJumping = true;
        }
        playerBody.setLinearVelocity(velocityX, playerBody.getLinearVelocity().y);

        // Cập nhật camera
        camera.update();
        tiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);

        // Vẽ
        tiledMapRenderer.render();
        batch.begin();
        batch.draw(playerTexture, playerX - 0.5f, playerY - 0.5f, 1f, 1f);
        batch.end();

        // Vẽ debug Box2D (tùy chọn)
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
