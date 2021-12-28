package com.game.studentvsexams;

import android.content.Context;

import java.util.Random;

public class Deadline extends Body{
    private final int radius = 2;
    private final float minSpeed = 0.2f;
    private final float maxSpeed = 0.5f;

    public Deadline(Context context) {
        Random random = new Random();

        bitmapId = R.drawable.deadline;
        y = -5;
        x = random.nextInt(GameView.maxX) - radius;
        size = radius * 1.5f;
        speed = minSpeed + (maxSpeed + minSpeed) * random.nextFloat();

        init(context);
    }

    @Override
    void update() {
        y += speed;
    }

    public boolean isCollision(float studentX, float studentY,float studentSize) {
        return !(((x+size) < studentX)||(x > (studentX+studentSize))||((y+size) < studentY)||(y > (studentY+studentSize)));
        /*
        ((x+size) < shipX) — корабль слева от астероида.
        (x > (shipX+shipSize)) — корабль справа от астероида.
        ((y+size) < shipY) — корабль сверху астероида.
        (y > (shipY+shipSize)) — корабль снизу астероида.
         */
    }
}
