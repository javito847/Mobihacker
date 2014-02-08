package com.mobihacker;

import java.io.IOException;

import org.json.JSONException;

import com.mobihacker.utils.Utils;
import com.mobihacker.views.Accordion;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		LinearLayout accordionLayout = (LinearLayout) findViewById(R.id.accordion);
		accordionLayout.setPadding(15, 15, 15, 15);
		
		try {
			Accordion accordion = new Accordion(this, Utils.readJson(this));
			accordionLayout.addView(accordion, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
