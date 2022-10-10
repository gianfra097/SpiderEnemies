package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.SpiderEnemies;

public class GameCamera {
    
    public OrthographicCamera camera;
    public ExtendViewport viewport;

    public GameCamera (int width, int height){
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT, camera);
        camera.position.set(SpiderEnemies.WIDTH/2, SpiderEnemies.HEIGHT/2, 0); 
        camera.update();
    }
    
    public Matrix4 combined(){
        return camera.combined;
    }
    
    public void update (int width, int height){
        viewport.update(width, height);
    }
    
    public Vector2 getInputInGameWorld (){
        Vector3 inputScreen = new Vector3(Gdx.graphics.getWidth() - Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
        Vector3 unprojected = camera.unproject(inputScreen);
        return new Vector2(unprojected.x, unprojected.y);
    }
    
}
