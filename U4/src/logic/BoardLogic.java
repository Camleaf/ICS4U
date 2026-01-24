package src.logic;
import java.awt.Point;
import src.logic.Enemy;
import static src.logic.Enemy.Type.*;
import src.render.EnemyRenderBox;
import java.util.Map;
import lib.Interval;
import lib.graphics.BasePanel;
import src.display.menu.WaveMenu;
import src.logic.Tile;
import java.util.ArrayList;

/**
 * Handles gameloop for towers shooting and enemies moving.
 * @author alexcedw
*/
public class BoardLogic {
    private int waveCount = 0;
    private Enemy[] wave;
    private EnemyRenderBox[] waveRender;
    private int enemiesLaunched;
    private int enemiesEnded; // to keep track if wave is over or not
    private int launchDelay = 1000; // in ms
    private Interval launchInterval = new Interval(launchDelay);
    private boolean waveRunning = false;
    public int playerHealth;
    private int attackCheckDelay = 250; // in ms. THis is so that we aren't doing the expensive attack calculations every frame
    private Interval attackCheckInterval = new Interval(attackCheckDelay);
    public int coins;
    private int moveEnemyCheckDelay = 50;
    private Interval moveEnemyCheckInterval = new Interval(moveEnemyCheckDelay);

    public BoardLogic(Point[] path){
        // init starting vars
        
        waveCount = 0;
        playerHealth = 20;
        coins = 100;
        
        enemiesLaunched = 0;
        enemiesEnded = 0;
    }

    public void startWave(Point[] path){
        if (waveRunning) {System.out.println("Attempted to start a wave during a wave.");return;}
        waveCount++;
        generateWave(path);
        waveRunning = true;
    }
    
 
    /** MainLoop function of boardlogic. Handles execution timing
     */
    public void update(Point[] path, BasePanel board, WaveMenu menu, Tile[][] tileArray){
        if (!waveRunning) return; // we don't really want this to be active if there is not wave running for performance reasons


        // Check for moving enemies before adding a new one to remove # of checks
        moveEnemies(board, path, menu);

        // launch enemies into board
        if (launchInterval.intervalPassed()){
            launchEnemy(board);
        }

        // check for end condition
        if (enemiesEnded >= wave.length){
            waveRunning = false;
            cleanUpRemnants(board);
            menu.endWave();
            return;
        }
        
        // attack enemies
        if (attackCheckInterval.intervalPassed()){
            launchAttacks(board, tileArray, menu);
        }
        
    }


    /**
     * Generates waves of enemies
     * @param Point[] path: the points on the board that make up the path
     */
    private void generateWave(Point[] path){
        enemiesLaunched = 0;
        int enemyCount = waveCount * 2;
        wave = new Enemy[enemyCount];
        waveRender = new EnemyRenderBox[enemyCount];
        
        for (int i = 0;i<enemyCount;i++){
            wave[i] = Enemy.getEnemyFromType(Enemy.types[(int)Math.floor(Math.random()*Enemy.types.length)], path, (int)Math.ceil(Math.random()*4));  
            waveRender[i] = new EnemyRenderBox(wave[i]);
            waveRender[i].setMovDelay(wave[i].jumpDelay);
            waveRender[i].setStepVector(wave[i].peekNextPath(path).x, wave[i].peekNextPath(path).y);
        }
    }

    /**
     * loads the enemies and sets up the wave
     * @param BasePanel board: the board the enemies are loaded onto
     */
    private void launchEnemy(BasePanel board){
        if (enemiesLaunched >= wave.length) return; // If out of enemies to run then just stop
        wave[enemiesLaunched].isLaunched = true;
        // Add enemy display to board and reset its move timer
        board.add(waveRender[enemiesLaunched]);
        wave[enemiesLaunched].jumpInterval.resetTime();
        enemiesLaunched++;
    }


    /** Handles moving enemies.
     * @param BasePanel board: the board the enemies are loaded onto
     * @param Point[] path: the points on the board that make up the path
     * @param WaveMenu waveMenu: the menu that controls the wave start
     */
    private void moveEnemies(BasePanel board, Point[] path, WaveMenu waveMenu){
        if (!moveEnemyCheckInterval.intervalPassed())return;

        for (int i = 0;i<enemiesLaunched;i++){ // we only want to move enemies which are currently on the board
            if (wave[i] == null) continue; // If not active then skip it
            
            waveRender[i].step();

            if (!wave[i].jumpInterval.intervalPassed()) continue; // If the jump interval for the object has't passed skip it
            
            boolean atEnd = wave[i].jumpTile(path);
            
            if (atEnd){ // there is also the case that it dies but we can handle that when we add tower shooting
               // add player health modificaiton here
                playerHealth -= wave[i].health;
                
                wave[i] = null;
                waveMenu.updateHP(playerHealth);
                enemiesEnded++;
                board.remove(waveRender[i]);
                continue;
            }
            Point nextPath = wave[i].peekNextPath(path);
            if (nextPath == null) nextPath = new Point(1,0);
            else {
                nextPath = new Point(nextPath.x, nextPath.y);
                nextPath.x -= wave[i].x;
                nextPath.y -= wave[i].y;
            }
            waveRender[i].setStepVector(nextPath.x, nextPath.y);
            waveRender[i].setLocationToRef(wave[i]); 
        }
    }

    /**
     * Controls the tower attacking enemies
     * @param BasePanel Board: the board the game is held on
     * @param Tile[][] tileArray: an array of all the tiles in the game
     * @param WaveMenu waveMenu: the menu that controls waves
     */
    private void launchAttacks(BasePanel board, Tile[][] tileArray, WaveMenu waveMenu){
        for (Tile[] row : tileArray){
            for (Tile tile : row){
                Tower tower = tile.getOccupier();
                if (tower == null) continue;
                if (!tower.attackInterval.peekIntervalPassed()) continue;
            

                ArrayList<Integer> indexesToAttack = tower.doAttack(wave, tile.getPosition());
                
                for (int enemyIndex : indexesToAttack){
                    Enemy enemy = wave[enemyIndex];
                    enemy.health -= tower.damage;
                    waveRender[enemyIndex].updateGraphicsToRef(enemy);

                    if (enemy.health<=0){
                        enemiesEnded++;
                        board.remove(waveRender[enemyIndex]);
                        coins += enemy.getReward();
                        waveMenu.updateCoins(coins);
                        wave[enemyIndex] = null;
                    }
                }
                tower.attackInterval.resetTime();
            }
        } 
    }



    /** A function to reset variables pertaining to a given wave and clean up graphics objects
     * @param BasePanel board: the board the game is held on
     */
    private void cleanUpRemnants(BasePanel board){
        for (EnemyRenderBox renderBox : waveRender){
            board.remove(renderBox);
        }

        // Free up all the objects
        wave = new Enemy[0];
        waveRender = new EnemyRenderBox[0];

        // reset vars
        enemiesLaunched = 0;
        enemiesEnded = 0;
    }
}
