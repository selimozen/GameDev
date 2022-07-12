package com.example.spaceway;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SpaceWay extends ApplicationAdapter {
	//Batch denen bir kavramı silmiştik.
	SpriteBatch batch;
	Texture background;
	Texture ship;
	Texture meteor1;
	Texture meteor2;
	Texture meteor3;
	Texture meteor4;
	float shipX = 0;
	float shipY = 0;
	int gameState = 0;
	float velocity = 0f;
	float gravity = 0.1f;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	//Konumların rastgele olması random sınıfından obje çağırıyoruz.
	Random random;

	Circle shipCircle;


	float enemyVelocity = 2;

	//Sürekli bir meteor gelmesi için ve ekrandan çıkan meteor tekrar ekrana gelsin diye, dizileri ve döngüleri kullanacağız.
	int numberOfenemy = 4;
	float [] enemyX = new float[numberOfenemy];
	float distance = 0;
	//Rastgele y konumları bu dizeler içerinde yer alacak.
	float [] enemyOffSet = new float[numberOfenemy];
	float [] enemyOffSet2 = new float[numberOfenemy];
	float [] enemyOffSet3 = new float[numberOfenemy];
	float [] enemyOffSet4 = new float[numberOfenemy];


	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	Circle[] enemyCircles4;

	
	@Override
	public void create () {
		//Uygulama başlatıldığında başlayacak herşeyi buraya almaktayız.
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		ship = new Texture("spaceship.png");
		meteor1 = new Texture("enemy.png");
		meteor2 = new Texture("enemy2.png");
		meteor3 = new Texture("enemy3.png");
		meteor4 = new Texture("enemy4.png");

		//meteorlar arası mesafeyi ayarlıyoruz.
		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();

		shipX = Gdx.graphics.getWidth() / 2 - ship.getHeight() / 2;
		shipY = Gdx.graphics.getHeight() / 3;

		shipCircle = new Circle();
		enemyCircles = new Circle[numberOfenemy];
		enemyCircles2 = new Circle[numberOfenemy];
		enemyCircles3 = new Circle[numberOfenemy];
		enemyCircles4 = new Circle[numberOfenemy];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.BLUE);
		font2.getData().setScale(6);


		for(int i = 0; i < numberOfenemy; i++){

			enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet4[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - meteor1.getWidth() / 2 + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
			enemyCircles4[i] = new Circle();

		}


	}

	@Override
	public void render () {
		//Uygulama başladıktan sonra olabilecek herşey ise render altından yapılır. Her hareket, sürekli devam edecek herşey.
		batch.begin();
		//İlk önce texture kullanarak tanımladık. batch kullanrak ise çizdirmeyi başardık.
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState ==1){

			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - ship.getHeight() / 2){
				score++;
				if(scoredEnemy < numberOfenemy - 1){
					scoredEnemy++;
				}else{
					scoredEnemy = 0;
				}
			}

			if(Gdx.input.justTouched()){
				velocity = -(float) (Gdx.graphics.getHeight() * 0.003);
				//velocity = -5;
			}
			for(int i = 0; i < numberOfenemy; i++ ){
				if(enemyX[i] < -meteor1.getWidth()){
					enemyX[i] = enemyX[i] + numberOfenemy * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet4[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


				}else{
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				batch.draw(meteor1, enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(meteor2, enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(meteor3, enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(meteor4, enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet4[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);


				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				enemyCircles4[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet4[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

			}






			//Burası en önemli noktalardan. Burada bir yer çekimi yaratmalıyız. Bunun içinde y eksenini değiştirmeliyiz.
			//Düşme işlemini gerçekleştirmek için y ekseninden birşeyler çıkarmalıyız. Mesela bu 1 olabilir ama öyle yapmayacağız.
			if(shipY > 0) {
				velocity = velocity + gravity;
				shipY = shipY - velocity;
			}else{
				gameState = 2;
			}

		}else if (gameState == 0){
			if(Gdx.input.justTouched()){
				//gameState1 ie oyunu başlatıyoruz değilse bu noktada 1 olmasını istiyoruz.
				gameState = 1;
			}
		}else if (gameState == 2){
			font2.draw(batch, "Game Over! Tap to Play Again!", 400, Gdx.graphics.getHeight() / 2);
			if(Gdx.input.justTouched()) {
				//gameState1 ie oyunu başlatıyoruz değilse bu noktada 1 olmasını istiyoruz.
				gameState = 1;
				}

				shipY = Gdx.graphics.getHeight() / 3;
				for(int i = 0; i < numberOfenemy; i++){

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet4[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - meteor1.getWidth() / 2 + i * distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
					enemyCircles4[i] = new Circle();

				}
				velocity = 0;
				score = 0;
				scoredEnemy = 0;


		}



		//Aslında bu bir seçenek ama bir böyle kullanmayacağız. Y sürekli değişeceği için x ve y için 2 farklı dğişken yaratacağız. Bu yolla ile istediğimiz zaman bu değişkenleri değiştirebiliriz.
		//batch.draw(ship, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 15);
		batch.draw(ship, shipX, shipY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

		font.draw(batch,String.valueOf(score), 100,200);

		batch.end();
		shipCircle.set(shipX + Gdx.graphics.getWidth() / 30, shipY + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

		for(int i = 0; i < numberOfenemy; i++){
			if(Intersector.overlaps(shipCircle, enemyCircles[i]) || Intersector.overlaps(shipCircle, enemyCircles2[i]) || Intersector.overlaps(shipCircle, enemyCircles3[i]) || Intersector.overlaps(shipCircle, enemyCircles4[i])){
				gameState = 2;
			}
		}

	}
	
	@Override
	public void dispose() {

	}
}
//Üst üste gelemsini engellemek için enemy velicityle oynamayı dene.
//numberofenemyi arttırmayı dene.