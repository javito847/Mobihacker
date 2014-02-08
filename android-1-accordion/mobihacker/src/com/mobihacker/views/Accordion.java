package com.mobihacker.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mobihacker.R;
import com.mobihacker.utils.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Accordion extends LinearLayout
{
	// Constructor
	public Accordion(final Context context, JSONArray items) throws JSONException 
	{
		// Call father constructor
		super(context);
		
		// Set orientation 
		setOrientation(LinearLayout.VERTICAL);
		
		// Foreach jsonArray
		for(int i=0; i < items.length(); i++)
		{
			JSONObject item = items.getJSONObject(i);
			
			// Init params
			LinearLayout.LayoutParams params = Utils.initParams();
			
			// Create all elements on the accordion
			LinearLayout row = Utils.createLayout(context);
			Button title = Utils.createButton(context, i, item.getString("title"));
			TextView content = Utils.createContent(context, i, item.getString("content"));
			
			// Add title and content to the view
			row.addView(title, new LayoutParams(LayoutParams.MATCH_PARENT, 50));
			row.addView(content, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			addView(row, params);
		    
		    // Add event click
		    title.setOnClickListener(new View.OnClickListener() {
		    	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Button title = (Button) v;

					// Get parent layout
					LinearLayout parent = (LinearLayout) v.getParent().getParent();
					
					// Get content view
					TextView content = (TextView) ((View) parent).findViewById(v.getId()+1);

					if(content.getVisibility() == 0){
						title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up, 0);
						content.setVisibility(View.GONE);
					}else{
						
						for(int i=0; i< parent.getChildCount(); i++){
							
							LinearLayout child = (LinearLayout) parent.getChildAt(i);					
							
							for (int j=1; j < child.getChildCount() ; j++){
								
								TextView contentChild = (TextView) ((View) child).findViewById(child.getChildAt(j).getId());
								
								// Check if any child is displayed
								if (contentChild.getVisibility() == 0){
									Button titleChild = (Button) ((View) child).findViewById(child.getChildAt(j-1).getId());

									titleChild.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up, 0);
									contentChild.setVisibility(View.GONE);					
								}					
							}	
						}

						title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down, 0);			
						content.setVisibility(View.VISIBLE);
					}
				}
	        });
		}
    }
}
