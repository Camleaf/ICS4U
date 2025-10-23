package pkg.display;

public class Camera {
    
    public int x;
    private int y;
    private int width;
    private int renderDist;
    
    public Camera(int width, int renderDist){
        x = 0;
        y = 0;

        this.width = width;
        this.renderDist = renderDist;

    }
}
