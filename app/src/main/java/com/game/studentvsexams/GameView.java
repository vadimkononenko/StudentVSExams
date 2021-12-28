package com.game.studentvsexams;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    public static int maxX = 20; // размер по горизонтали
    public static int maxY = 28; // размер по вертикали
    public static float unitW = 0; // пикселей в юните по горизонтали
    public static float unitH = 0; // пикселей в юните по вертикали

    private ArrayList<Deadline> deadlines = new ArrayList<>(); // тут будут харанится дедлайны
    private final int DEADLINE_INTERVAL = 10; // время через которое появляются дедлайны (в итерациях)
    private int currentTime = 0;

    private boolean firstTime = true;
    private boolean gameRunning = true;
    private Student student;
    private Thread gameThread;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public GameView(Context context) {
        super(context);

        surfaceHolder = getHolder();
        paint = new Paint();

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameRunning) {
            update();
            draw();
            checkDeadlineCollision();
            checkIfNewDeadline();
            control();
        }
    }

    protected void update() {
        if(!firstTime) {
            student.update();
            for (Deadline deadline : deadlines) {
                deadline.update();
            }
        }
    }

    protected void draw() {
        if(surfaceHolder.getSurface().isValid()) {
            if(firstTime){ // инициализация при первом запуске
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width()/maxX; // вычисляем число пикселей в юните
                unitH = surfaceHolder.getSurfaceFrame().height()/maxY;

                student = new Student(getContext()); // добавляем студента
            }

            canvas = surfaceHolder.lockCanvas(); // закрываем canvas
            canvas.drawColor(Color.RED); // заполняем фон красным

            student.drow(paint, canvas); // рисуем студента

            for (Deadline deadline: deadlines) { // рисуем дедлайны
                deadline.drow(paint, canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas); // открываем canvas
        }
    }

    protected void control() {
        try {
            gameThread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void checkDeadlineCollision() {
        for (Deadline deadline : deadlines) {
            if(deadline.isCollision(student.x, student.y, student.size)){
                // игрок проиграл
                gameRunning = false; // останавливаем игру
            }
        }
    }

    protected void checkIfNewDeadline() {
        if(currentTime >= DEADLINE_INTERVAL){
            Deadline deadline = new Deadline(getContext());
            deadlines.add(deadline);
            currentTime = 0;
        }else{
            currentTime++;
        }
    }
}