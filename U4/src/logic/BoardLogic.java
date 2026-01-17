package src.logic;
import java.awt.Point;
import src.logic.Enemy;
import static src.logic.Enemy.Type.*;
import src.render.EnemyRenderBox;
import java.util.Map;
import src.display.BoardDisplay;
import lib.Interval;
import lib.graphics.BasePanel;

/**
 * Handles gameloop for towers shooting and enemies moving.
 * @author alexcedw
*/
public class BoardLogic {
    private int waveCount = 0;
    private Enemy[] wave;
    private EnemyRenderBox[] waveRender;
    private int enemiesLaunched = 0;
    private int enemiesEnded = 0; // to keep track if wave is over or not
    private int launchDelay = 1000; // in ms
    private Interval launchInterval = new Interval(launchDelay);
    private boolean waveRunning = false;

    private Enemy.Type[][] wavePresets = new Enemy.Type[][]{ // To add a bit of progression to the start of the game. To implement later
        {TEST, TEST}, 
        {TEST, TEST, TEST},
        {TEST, TEST, TEST},
    };


    public BoardLogic(Point[] path){
        waveCount = 0;
        startWave(path);
    }

    public void startWave(Point[] path){
        waveCount++;
        generateWave(path);
        waveRunning = true;
    }
    
 
    /** MainLoop function of boardlogic. Handles execution timing
     */
    public void update(Point[] path, BasePanel board){
        if (!waveRunning) return; // we don't really want this to be active if there is not wave running for performance reasons



        // Check for moving enemies before adding a new one to remove # of checks
        moveEnemies(board, path);

        if (launchInterval.intervalPassed()){
            launchEnemy(board);
        }
    }


    // Generates enemies and their renderboxes. Not displayed on screen yet
    private void generateWave(Point[] path){
        enemiesLaunched = 0;
        int enemyCount = waveCount * 2;
        wave = new Enemy[enemyCount];
        waveRender = new EnemyRenderBox[enemyCount];
        
        for (int i = 0;i<enemyCount;i++){
            wave[i] = Enemy.getEnemyFromType(Enemy.types[(int)Math.floor(Math.random()*Enemy.types.length)], path);  
            waveRender[i] = new EnemyRenderBox(wave[i]);
        }
    }


    private void launchEnemy(BasePanel board){
        if (enemiesLaunched == wave.length) return; // If out of enemies to run then just stop
        
        // Add enemy display to board and reset its move timer
        board.add(waveRender[enemiesLaunched]);
        wave[enemiesLaunched].jumpInterval.resetTime();
        enemiesLaunched++;
    }


    /** Handles moving enemies.
     */
    private void moveEnemies(BasePanel board, Point[] path){
        

        for (int i = 0;i<enemiesLaunched;i++){ // we only want to move enemies which are currently on the board
            if (!wave[i].active) continue; // If not active then skip it
            if (!wave[i].jumpInterval.intervalPassed()) continue; // If the jump interval for the object has't passed skip it
            
            boolean atEnd = wave[i].jumpTile(path);
            
            if (atEnd){ // there is also the case that it dies but we can handle that when we add tower shooting
               // add player health modificaiton here
                wave[i].active = false;
                board.remove(waveRender[i]);
                continue;
            }

            waveRender[i].setLocationToRef(wave[i]);
            
        }
    }
}
