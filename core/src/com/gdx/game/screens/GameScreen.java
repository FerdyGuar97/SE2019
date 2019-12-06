package com.gdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.game.GdxGame;
import com.gdx.game.entities.Player;
import com.gdx.game.entities.bosses.DemoBoss;
import com.gdx.game.improvedcontact.ImprovedContactListener;
import com.gdx.game.movements.MovementSetFactory;
import net.dermetfan.gdx.physics.box2d.ContactMultiplexer;

/**
 *
 * @author Armando
 */
public class GameScreen implements Screen {

    private final Stage stage;
    private final GdxGame game;
    private final World world;
    private final Player player;
    private final Box2DDebugRenderer debugRenderer;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;

    public GameScreen(GdxGame aGame) {
        this.game = aGame;
        stage = new Stage(aGame.vp);
        world = new World(Vector2.Zero, true);
        world.setContactListener(new ContactMultiplexer(new ImprovedContactListener()));
        map = new TmxMapLoader().load("mappaTest/mappaTest.tmx");
        float pixelsPerUnit = 32f;
        float unitPerMeters = 5f;
        mapRenderer = new OrthogonalTiledMapRenderer(map, GdxGame.SCALE / (pixelsPerUnit * unitPerMeters));
        debugRenderer = new Box2DDebugRenderer();
        float playerWorldWidth = 16 / GdxGame.SCALE;
        float playerWorldHeight = 28 / GdxGame.SCALE;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        player = new Player("uajono", 100, world, playerWorldWidth, playerWorldHeight, new Vector2(15, 15 * (h / w)));
        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        OrthographicCamera cam = (OrthographicCamera) stage.getCamera();
        game.vp.setWorldSize(30, 30 * (h / w));
        cam.position.set(player.getPosition(), stage.getCamera().position.z);
        cam.update();
        stage.addActor(player);

        MovementSetFactory mvsf = MovementSetFactory.instanceOf();
        Vector2 v = player.getPosition().add(5, 5);
        //v is the player position to substitute when merge is complete
        DemoBoss db = new DemoBoss("Nameless King", 30, this.world, 20 / GdxGame.SCALE, 20 / GdxGame.SCALE, v, mvsf.build("Fast", "Square", false, v, 3));
        stage.addActor(db);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 6, 2);
        stage.act();
        mapRenderer.setView((OrthographicCamera) stage.getCamera());
//        decommentare per seguire il player
//        stage.getCamera().position.set(player.getPosition(), stage.getCamera().position.z);
        mapRenderer.render();
        stage.draw();
        debugRenderer.render(world, stage.getCamera().combined);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
