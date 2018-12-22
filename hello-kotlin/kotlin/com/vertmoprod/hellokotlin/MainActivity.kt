package com.vertmoprod.hellokotlin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        var my_text = findViewById(R.id.my_text) as TextView;
        my_text.setText("Hello Kotlin !");
    }
}
