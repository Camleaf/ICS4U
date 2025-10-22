import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class SwingDemoAlt {
    private JFrame fframe;

    public SwingDemoAlt() {
        fframe = new JFrame();
        fframe.setTitle("hello world");
        fframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fframe.setSize(800,500);
        fframe.setLocationRelativeTo(null);


    }

    public void show(){
        fframe.setVisible(true);
    }
}




public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingDemoAlt demo = new SwingDemoAlt();
                demo.show();
            }
        });
    }
}