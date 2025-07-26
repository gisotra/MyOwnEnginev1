package gameloop;

import structure.Screen;

public class EngineThread implements Runnable{
    /*------------ ATRIBUTOS ------------*/

    private Screen sc = new Screen();
    private final double tempoPorFrame = 1_000_000_000.0 / 60;
    private double ultimoTempo = System.nanoTime();
    private long nextFrame = System.nanoTime() + (long) tempoPorFrame;
    private double threadSleep;
    private long threadSleepMS;
    private int threadSleepNano;

    private long timer = System.currentTimeMillis();
    private int frames = 0;
    private int updates = 0;

    /*------------ CONSTRUTOR ------------*/
    public EngineThread(Screen sc) {
        this.sc = sc;
    }

    /*------------ THREAD PRINCIPAL ------------*/
    @Override
    public void run() {
        final float fixedStep = 1.0f / 60.0f;
        float accumulator = 0.0f;

        while (true) {

            double agora = System.nanoTime();
            double frameTime = (agora - ultimoTempo) / 1_000_000_000.0;
            ultimoTempo = agora;

            // Evita explosão de updates em travadas longas
            if (frameTime > 0.25) {
                frameTime = 0.25;
            }

            accumulator += (float) frameTime;

            // Substeps: atualiza várias vezes se necessário
            while (accumulator >= fixedStep) {
                update(fixedStep);
                updates++;
                accumulator -= fixedStep;
            }

            // Renderiza uma vez por frame
            render();
            frames++;

            // Controla o tempo de sono com base na lógica de FPS
            threadSleep = (nextFrame - System.nanoTime()) / 1_000_000.0;
            if (threadSleep < 0) {
                threadSleep = 0;
            }

            threadSleepMS = (long) threadSleep;
            threadSleepNano = (int) ((threadSleep - threadSleepMS) * 1_000_000);

            try {
                Thread.sleep(threadSleepMS, threadSleepNano);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            nextFrame += (long) tempoPorFrame;

            // FPS / UPS debug
            if (System.currentTimeMillis() - timer >= 1000) {
                //System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                timer += 1000;
            }
        }
    }

    /*------------ MÉTODO UPDATE ------------*/
    public void update(float dT) {
        sc.update(dT);
    }

    /*------------ MÉTODO RENDER ------------*/
    public void render() {
        sc.render();
    }

    public void sleepEngine() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        nextFrame = System.nanoTime() + (long) tempoPorFrame;
        ultimoTempo = System.nanoTime();
    }
}

