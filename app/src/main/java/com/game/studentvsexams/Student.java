package com.game.studentvsexams;

import android.content.Context;

public class Student extends Body{
    public Student(Context context) {
        bitmapId = R.drawable.graduated;
        size = 2.5f;
        x = 7;
        y =  GameView.maxY - size - 1;
        speed = 0.3f;

        init(context);
    }

    @Override
    void update() {
        if(MainActivity.isLeftPressed && x >= 0){
            x -= speed;
        }
        if(MainActivity.isRightPressed && x <= GameView.maxX - 5){
            x += speed;
        }
    }
}
