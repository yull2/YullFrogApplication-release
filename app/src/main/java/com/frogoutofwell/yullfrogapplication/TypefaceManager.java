package com.frogoutofwell.yullfrogapplication;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Tacademy on 2016-06-09.
 */
public class TypefaceManager {
    private static final TypefaceManager instance = new TypefaceManager();

    public static TypefaceManager getInstance(){
        return instance;
    }

    private TypefaceManager(){

    }

    public static final String FONT_NAME_NANUM = "nanumgothic";
    private Typeface nanumgothic;


    public static final String FONT_NAME_LIGHT = "light";
    private Typeface light;


    public static final String FONT_NAME_REGULAR = "regular";
    private Typeface regular;

    public Typeface getTypeface(Context context, String fontName){
        if (FONT_NAME_NANUM.equals(fontName)){
            if (nanumgothic == null){
                nanumgothic = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothic.ttf");
            }
            return nanumgothic;
        }
        if (FONT_NAME_LIGHT.equals(fontName)){
            if (light == null){
                light = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
            }
            return light;
        }
        if (FONT_NAME_REGULAR.equals(fontName)){
            if (regular == null){
                regular = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
            }
            return regular;
        }
        return null;
    }

}
