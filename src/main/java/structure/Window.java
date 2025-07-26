package structure;

import global.Universal;

import javax.swing.*;

public class Window extends JFrame  {
    /*canvas*/
    Screen sc;

    public Window(){
        sc = new Screen();
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Universal.GAME_WIDTH, Universal.GAME_HEIGHT);
        setResizable(true);
        add(sc);
        setLocationRelativeTo(null); // Centraliza a janela
        setVisible(true); // Mostra tudo
        sc.initGame();
    }
}
