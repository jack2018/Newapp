package com.ylz.newapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {
    @BindView(R.id.s)
    TextView s;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Typeface fontFace = Typeface.createFromAsset(getAssets(),
                "fonts/qnhg.ttf");
        s.setTypeface(fontFace);
        textView.setTypeface(fontFace);

    }


    @OnClick(R.id.s)
    public void onViewClicked() {
        s.setText("ssssssss");
    }
}
