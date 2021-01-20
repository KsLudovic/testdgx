package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter {
	private Texture background1, background2, spaceship;
	float yMax, yCoordBg1, yCoordBg2;
	final int BACKGROUND_MOVE_SPEED = 100;
	private Viewport viewport;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private ShapeRenderer sr;
	private Vector3 pos;
	private FreeTypeFontGenerator fontGenerator;
	private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
	private BitmapFont font;
	private int tap=0;
	private Music music;
	@Override
	public void create () {
		sr= new ShapeRenderer();
		cam = new OrthographicCamera();
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport.setCamera(cam);
		batch = new SpriteBatch();
		Music music = Gdx.audio.newMusic(Gdx.files.internal("8-bit.wav"));
		music.setVolume(0.2f);
		music.setLooping(true);
		music.play();

		cam.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		pos= new Vector3(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,0);
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Bombing.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = 50;
		fontParameter.borderWidth =5;
		fontParameter.borderColor = Color.BLACK;
		fontParameter.color = Color.WHITE;
		font = fontGenerator.generateFont(fontParameter);
		background1 = new Texture(Gdx.files.internal("star.jpeg"));
		background2 = new Texture(Gdx.files.internal("star.jpeg")); // identical
		spaceship = new Texture(Gdx.files.internal(("spaceship.png")));
		yMax = 1280;
		yCoordBg1 = yMax*(-1); yCoordBg2 = 0;
	}

	@Override
	public void render () {

		//logic
		cam.update();

		if(Gdx.input.isTouched()){
			pos.set(Gdx.input.getX(),Gdx.input.getY(),0);
			cam.unproject(pos);
			tap++;

		}

		//DRAWING
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);

		//yCoordBg1 += BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
		//yCoordBg2 = yCoordBg1 + yMax;  // We move the background, not the camera
		//if (yCoordBg1 >= 0) {
		//	yCoordBg1 = yMax*(-1); yCoordBg2 = 0;
		//}
		batch.begin();

			batch.draw(background1, 0, 0, viewport.getWorldWidth(),viewport.getWorldHeight());
			//batch.draw(background2, 0, yCoordBg2);
			font.draw(batch,"score: "+tap, 50,Gdx.graphics.getHeight() - 50);
		//	batch.draw(spaceship,pos.x,pos.y);

		batch.end();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(Color.BLUE);
		sr.circle(pos.x,pos.y, 64);
		sr.end();

	}
	
	@Override
	public void dispose () {
		sr.dispose();
		batch.dispose();


	}
}
