package com.mobihacker.utils;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;

import com.mobihacker.R;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Utils {
	
	// Read json file
	public static JSONArray readJson(Context context) throws JSONException, IOException
	{
		InputStream in = context.getAssets().open("accordion.json");
	    byte[] buffer = new byte[in.available()];
	    in.read(buffer);
	    in.close();
	    return new JSONArray(new String(buffer, "UTF-8"));	   
	}
	
	// Create linear layout
	public static LinearLayout createLayout(Context context)
	{
		LinearLayout row = new LinearLayout(context);
		LayoutTransition lt = new LayoutTransition();
		lt.enableTransitionType(LayoutTransition.CHANGING);
		lt.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);
		row.setLayoutTransition(lt);
		row.setBackgroundColor(Color.WHITE);
		row.setOrientation(LinearLayout.VERTICAL);
		row.setBackground(context.getResources().getDrawable(R.drawable.border));
		return row;
	}
	
	// Create linear button accordion
	public static Button createButton(Context context, int i, String text)
	{
		Button title = new Button(context);
		title.setText(text);
		title.setTextSize(13);
		title.setTextColor(Color.BLUE);
		title.setGravity(Gravity.LEFT | Gravity.CENTER);
		title.setBackgroundColor(Color.WHITE);
		title.setBackground(context.getResources().getDrawable(R.drawable.border));
		title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up, 0);
		title.setId(i);
		return title;
	}
	
	// Create linear content accordion
	public static TextView createContent(Context context, int i, String text)
	{
		TextView content = new TextView(context);
		content.setText(text);
		content.setTextSize(13);
		content.setGravity(Gravity.LEFT);
		content.setPadding(20, 10, 10, 10);
		content.setVisibility(View.GONE);
		content.setId(i+1);
		return content;
	}
	
	// Create params linear layout
	public static LinearLayout.LayoutParams initParams ()
	{
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 0, 5);
		return params;
	}
}
