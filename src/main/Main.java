package main;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) throws Exception {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D RPG");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // Causes window to be sized to fit

        window.setLocationRelativeTo(null); // Start on center of screen
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
