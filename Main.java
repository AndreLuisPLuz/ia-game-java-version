import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_ESCAPE)
                    return;
                
                System.exit(0);
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        Screen screen = new Screen();

        frame.add(screen);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        frame.setVisible(true);

        screen.setSize(frame.getSize());


<<<<<<< HEAD
        screen.AddPlayer(Massacration.class);
        screen.AddPlayer(TreviPlayer.class);
        screen.AddPlayer(DriftKing.class);
        screen.AddPlayer(Joelma.class);
        // screen.AddPlayer(DriftKing.class);
        // screen.AddPlayer(Joelma.class);
        // screen.AddPlayer(Atom.class);
        // screen.AddPlayer(MoranguinhoPlayer.class);
        // screen.AddPlayer(CamperPlayer.class);
        // screen.AddPlayer(Massacration.class);
=======
        screen.AddPlayer(DriftKing.class);
        screen.AddPlayer(Joelma.class);
        screen.AddPlayer(CamperPlayer.class);
        screen.AddPlayer(Massacration2.class);
        screen.AddPlayer(MoranguinhoPlayer.class);
        screen.AddPlayer(Atom.class);
>>>>>>> 39a178aef155f316e5118e8a08cb44aede1ed7ec
    }
}