package Instances;

import java.awt.*;

public abstract class GameObject {
    /*Super classe mãe de todos os elementos do jogo*/
    protected float x, y;
    protected int width, heigth;
    public abstract void render(Graphics2D g2d);
    public abstract void update(float deltaTime);

    /*Getters & Setters*/

    public int getWidth() {
        return width;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }
}
