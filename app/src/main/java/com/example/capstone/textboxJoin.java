package com.example.capstone;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Type;

public class textboxJoin extends LinearLayout  {

    LinearLayout LayoutLine;
    EditText editText;
    ImageButton delBtn;
    TextView Title, ConfirmBtn, GuideTxt;

    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;

    public textboxJoin(Context context){
        super(context);
        initView();
    }

    public textboxJoin(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public textboxJoin(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void setTitleVisible(int visible){
        Title.setVisibility(visible);
    }

    private void setDelBtnVisible(int visible){
        delBtn.setVisibility(visible);
    }

    private void setConfirmBtnVisible(int visible){
        ConfirmBtn.setVisibility(visible);
    }

    private void initView() {

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.textbox_join, this, false);
        addView(v);

        Title = (TextView)findViewById(R.id.Title);
        LayoutLine = (LinearLayout) findViewById(R.id.LayoutLine);
        editText = (EditText) findViewById(R.id.editText);
        delBtn = (ImageButton) findViewById(R.id.delBtn);
        ConfirmBtn = (TextView) findViewById(R.id.ConfirmBtn);
        GuideTxt = (TextView) findViewById(R.id.GuideTxt);

        setDelBtnVisible(2);
        setConfirmBtnVisible(2);
        setTitleVisible(0);

    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.textboxJoin);
        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.textboxJoin, defStyle, 0);
        setTypeArray(typedArray);
    }


    private void setTypeArray(TypedArray typedArray) {

        String TitleID = typedArray.getString(R.styleable.textboxJoin_title);
        Title.setText(TitleID);

        boolean TitleVisibleID = typedArray.getBoolean(R.styleable.textboxJoin_titleVisible, false);
        if(TitleVisibleID){
            Title.setVisibility(VISIBLE);
        }else{
            Title.setVisibility(GONE);
        }

        int LayoutID = typedArray.getResourceId(R.styleable.textboxJoin_Layout, R.drawable.roundborder_g4);
        LayoutLine.setBackgroundResource(LayoutID);

        boolean delBtnVisibleID = typedArray.getBoolean(R.styleable.textboxJoin_delBtnVisible, false);
        if(delBtnVisibleID){
            delBtn.setVisibility(VISIBLE);
        }else{
            delBtn.setVisibility(GONE);
        }

        int ConfirmBgID = typedArray.getResourceId(R.styleable.textboxJoin_ConfirmBg, R.drawable.roundborder_g4);
        ConfirmBtn.setBackgroundResource(ConfirmBgID);

        String ConfirmTitleID = typedArray.getString(R.styleable.textboxJoin_ConfirmTitle);
        ConfirmBtn.setText(ConfirmTitleID);

        boolean ConfirmVisibleID = typedArray.getBoolean(R.styleable.textboxJoin_confirmBtnVisible, false);
        if(ConfirmVisibleID){
            ConfirmBtn.setVisibility(VISIBLE);
        }else{
            ConfirmBtn.setVisibility(GONE);
        }

        String hintID = typedArray.getString(R.styleable.textboxJoin_hint);
        editText.setHint(hintID);

        int inputType = typedArray.getInt(R.styleable.textboxJoin_android_inputType, EditorInfo.TYPE_CLASS_TEXT);
        editText.setInputType(inputType);

        String GuideTxtID = typedArray.getString(R.styleable.textboxJoin_GuideTxt);
        GuideTxt.setText(GuideTxtID);

        boolean GuidTxtVisible = typedArray.getBoolean(R.styleable.textboxJoin_GuidTxtVisible, false);
        if (GuidTxtVisible){
            GuideTxt.setVisibility(VISIBLE);
        }else{
            GuideTxt.setVisibility(GONE);
        }

        typedArray.recycle();
    }

    void setTitle(int TitleID){
        Title.setText(TitleID);
    }

    void setTitle(boolean ConfirmVisibleID){
        if(ConfirmVisibleID){
            Title.setVisibility(VISIBLE);
        }else{
            Title.setVisibility(GONE);
        }
    }


    void setLayoutLine(int LayoutID){
        LayoutLine.setBackgroundResource(LayoutID);
    }

    void setDelBtn(boolean delBtnVisibleID){
        if(delBtnVisibleID){
            delBtn.setVisibility(VISIBLE);
        }else{
            delBtn.setVisibility(GONE);
        }
    }

    void setConfirmBtn(boolean ConfirmVisibleID){

        if(ConfirmVisibleID){
            ConfirmBtn.setVisibility(VISIBLE);
        }else{
            ConfirmBtn.setVisibility(GONE);
        }
    }

    void setConfirmBtn(int ConfirmBgID){
        ConfirmBtn.setBackgroundResource(ConfirmBgID);
    }

    void setConfirmBtn(String ConfirmTitleID){
        ConfirmBtn.setText(ConfirmTitleID);
    }

    void setEditText(String hintID){
        editText.setHint(hintID);
    }

    void setEditText(int inputType){
        editText.setInputType(inputType);
    }

    void setGuideTxt(String GuideTxtID){
        GuideTxt.setText(GuideTxtID);
    }


}
