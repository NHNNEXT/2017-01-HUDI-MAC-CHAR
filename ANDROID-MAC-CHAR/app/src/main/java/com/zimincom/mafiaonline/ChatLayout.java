package com.zimincom.mafiaonline;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Zimincom on 2017. 5. 7..
 */

public class ChatLayout extends LinearLayout {
    ImageView profile;
    FrameLayout messageBubble;
    TextView name;
    TextView message;

    public ChatLayout(Context context) {
        super(context);
        initView();
    }

    public ChatLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public ChatLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.chatLayout);
        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.chatLayout, defStyle, 0);
        setTypeArray(typedArray);

    }

    public void setToMyMessage() {
        this.profile.setVisibility(GONE);
        this.name.setVisibility(GONE);
        this.messageBubble.setScaleX(-1f);
        this.message.setScaleX(-1f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        super.setLayoutParams(params);
    }

    public void setName(String userName) {
        this.name.setText(userName);
    }

    public void setMessage(String userMessage) {
        this.message.setText(userMessage);
    }

    private void setTypeArray(TypedArray typedArray) {

        int profileImgId = typedArray.getResourceId(R.styleable.chatLayout_profileImg, R.drawable.mafia);
        profile.setBackgroundResource(profileImgId);

        String name_string = typedArray.getString(R.styleable.chatLayout_name);
        name.setText(name_string);

        String message_string = typedArray.getString(R.styleable.chatLayout_message);
        message.setText(message_string);


        typedArray.recycle();

    }


    private void initView() {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.chat_item, this, false);
        addView(v);

        profile = (ImageView) findViewById(R.id.profile_image);
        name = (TextView) findViewById(R.id.name);
        message = (TextView) findViewById(R.id.message);
        messageBubble = (FrameLayout) findViewById(R.id.message_bubble);
    }


}
