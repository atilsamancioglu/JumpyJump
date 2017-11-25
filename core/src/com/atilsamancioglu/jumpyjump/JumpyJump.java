package com.atilsamancioglu.jumpyjump;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.Random;

public class JumpyJump extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	float birdY = 0;
	float birdX = 0;
	float velocity = 0;
	int gameState = 0;
	float gravity = 0.1f;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float maximumOffset;
	Random random;
	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	float enemyVelocity = 2;

	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	float[] enemyOffset = new float[numberOfEnemies];
	float[] enemyOffset2 = new float[numberOfEnemies];
	float[] enemyOffset3 = new float[numberOfEnemies];
	float distanceBtwEnemies;

	Circle[] enemyCircles;
	Circle [] enemyCircles2;
	Circle [] enemyCircles3;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		birdY = Gdx.graphics.getHeight() / 3;
		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;

		Gdx.input.setInputProcessor(this);

		enemy1 = new Texture("1.png");
		enemy2 = new Texture("1.png");
		enemy3 = new Texture("1.png");

		maximumOffset = Gdx.graphics.getHeight() / 2 - 200;

		random = new Random();

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(5);

		shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		distanceBtwEnemies = Gdx.graphics.getWidth() / 2;

		for (int i = 0; i < numberOfEnemies; i++) {

			enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);
			enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);

			enemyX[i] = Gdx.graphics.getWidth()  - enemy1.getWidth() / 2 + i * distanceBtwEnemies;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3 [i] = new Circle();


		}

	}

	@Override
	public void render () {


		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState == 1) {

			//check to see if enemies passed the bird or not
			if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2) {
				score++;
				if (scoredEnemy < numberOfEnemies - 1) {
					scoredEnemy++;

					System.out.println("score" + String.valueOf(score));
				} else {
					scoredEnemy = 0;
				}

			}

			if (Gdx.input.justTouched()) {
				velocity = -6;

				//nextFloat creates a number btw 0 - 1

			}

			for (int i = 0; i < numberOfEnemies; i++) {

				if (enemyX[i] < -enemy1.getWidth()) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distanceBtwEnemies;

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);


				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;


				}

				batch.draw(enemy1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(enemy2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(enemy3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffset[i] + Gdx.graphics.getHeight() / 20 ,Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);



			}
			/*if (birdY > 0 || velocity < 0 ) {
				velocity = velocity + gravity;
				birdY -= velocity;
			}*/

			if (birdY > 0  ) {
				velocity = velocity + gravity;
				birdY -= velocity;
			} else {
				gameState = 2;
			}

		} else if (gameState == 0) {

			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {
			font2.draw(batch,"Game Over! Tap To Play Again",50, Gdx.graphics.getHeight() / 2);

			if (Gdx.input.justTouched()) {
				gameState = 1;
				birdY = Gdx.graphics.getHeight() / 3;

				for (int i = 0; i < numberOfEnemies; i++) {

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);

					enemyX[i] = Gdx.graphics.getWidth()  - enemy1.getWidth() / 2 + i * distanceBtwEnemies;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3 [i] = new Circle();


				}
				score = 0;
				scoredEnemy = 0;
				velocity = 0;
			}



		}


		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

		font.draw(batch, String.valueOf(score),100,200);

		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLUE);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);


		for (int i = 0; i < numberOfEnemies; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffset[i] + Gdx.graphics.getHeight() / 20 ,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 20 ,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 20 ,Gdx.graphics.getWidth() / 30);


			if (Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])) {
				gameState = 2;
			}


		}

		//shapeRenderer.end();


	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
