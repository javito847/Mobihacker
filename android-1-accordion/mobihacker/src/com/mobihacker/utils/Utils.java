package com.mobihacker.utils;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;

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
}
