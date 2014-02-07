package com.mobihacker.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mobihacker.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Accordion extends LinearLayout
{
	// Constructor
	public Accordion(Context context, JSONArray items) throws JSONException 
	{
		// Call father constructor
		super(context);
		
		// Set orientation 
		setOrientation(LinearLayout.VERTICAL);
		
		// Foreach jsonArray
		for(int i=0; i < items.length(); i++)
		{
			JSONObject item = items.getJSONObject(i);
			
			TextView content = new TextView(context);
			Button title = new Button(context);
			
			// title set-up
			title.setText(item.getString("title"));
			title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up, 0);
			title.setId(i);
			title.setTypeface(null, 1);
			
			// content set-up
			content.setText(item.getString("content"));
			content.setPadding(20, 10, 10, 10);
			content.setVisibility(View.GONE);
			content.setId(i+1);
			content.setTypeface(null, 1);
			
			// Add title and content to the view
			addView(title);
		    addView(content);
		    
		    // Add event click
		    title.setOnClickListener(new View.OnClickListener() {
		    	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Button title = (Button) v;

					// Get parent layout
					LinearLayout parent = (LinearLayout) v.getParent();
					
					// Get content view
					TextView content = (TextView) ((View) parent).findViewById(v.getId()+1);

					if(content.getVisibility() == 0){
						title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up, 0);
						content.setVisibility(View.GONE);
					}else{
						title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down, 0);
						content.setVisibility(View.VISIBLE);
					}
				}
	        });
		}
    }
}
