package structure;

import gameloop.EngineThread;
import global.Universal;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Screen extends Canvas {

    private Thread t;
    private EngineThread et;

    /*Construtor*/
    public Screen(){
        setPreferredSize(new Dimension(200, 200));
        setFocusable(true);
        requestFocus();
    }

    /*Render*/
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBuffer();
            return;
        }
        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        try{
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            drawGrid(g2d);
        } finally {
            g2d.dispose(); //libero todos os desenhos feitos pelo pincél de uma vez
        }
        bs.show();
        Toolkit.getDefaultToolkit().sync();
    }

    /*Update*/
    public void update(float deltaTime){

    }

    public void drawGrid(Graphics2D g2d) {
        g2d.setColor(new Color(11, 23, 152)); // Cor preta para o grid
        for (int x = 0; x < Universal.GAME_WIDTH; x += Universal.TILES_SIZE) {
            for (int y = 0; y < Universal.GAME_HEIGHT; y += Universal.TILES_SIZE) {
                // Desenha o contorno de cada tile
                g2d.drawRect(x, y, Universal.TILES_SIZE, Universal.TILES_SIZE);
            }
        }
    }

    public void createBuffer(){
        createBufferStrategy(3);
    }

    public void initGame(){
        this.et = new EngineThread(this);
        this.t = new Thread(et);
        t.start();
    }
}
