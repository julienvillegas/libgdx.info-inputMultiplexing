package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

/**
 * Created by julienvillegas on 17/01/2017.
 */
public class GameScreen implements Screen  {

    private Stage gameStage;
    private Stage UIStage;
    private Game game;
    private OrthographicCamera camera;

    private Slider slider;
    private InputMultiplexer multiplexer;


    public GameScreen(Game aGame) {
        game = aGame;
        gameStage = new Stage(new ScreenViewport());
        UIStage = new Stage(new ScreenViewport());

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(UIStage);
        multiplexer.addProcessor((gameStage));


        Image map = new Image(new Texture("map.jpg"));
        map.addListener(new ActorGestureListener(){
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);
                camera.position.x -= (deltaX*Gdx.graphics.getDensity());
                camera.position.y -= (deltaY*Gdx.graphics.getDensity());

            }
        });
        gameStage.addActor(map);

        Image ring = new Image((new Texture("ring.png")));
        ring.setPosition(1100,1225);
        gameStage.addActor(ring);

        Image magnifier = new Image(new Texture("magnifier.png"));
        magnifier.setPosition(Gdx.graphics.getWidth()/2-magnifier.getWidth()/4,Gdx.graphics.getHeight()/2-magnifier.getHeight()/2);
        UIStage.addActor(magnifier);

        slider = new Slider(1,2,0.01f,true, MyGdxGame.skin);
        slider.setAnimateInterpolation(Interpolation.smooth);
        //slider.setAnimateDuration(0.1f);
        slider.setHeight(Gdx.graphics.getHeight()*0.8f);
        slider.setPosition(Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/10);
        slider.setValue(1.55f);
        slider.addListener(new InputListener(){
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                camera.zoom = slider.getValue();
                camera.update();
                //Gdx.app.log("touchDragged","slider Value:"+slider.getValue());
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("up","slider Value:"+slider.getValue());

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log("down","slider Value:"+slider.getValue());

                return true;
            }
        });
        UIStage.addActor(slider);

        camera = (OrthographicCamera) gameStage.getViewport().getCamera();
        camera.translate(200,250);
        camera.zoom = 1.55f;
    }

    @Override
    public void show() {
        Gdx.app.log("MainScreen","show");
        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        UIStage.act();
        gameStage.act();


        gameStage.draw();
        UIStage.draw();

    }


    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {
        UIStage.dispose();
        gameStage.dispose();
    }


}
