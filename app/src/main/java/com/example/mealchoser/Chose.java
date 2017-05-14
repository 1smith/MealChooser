package com.example.mealchoser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Chose extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chose);
		setTitle("Chose A Meal");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
	
	public void dietStageLunch(View view){
		Intent intent = new Intent(this, DietaryRequirements.class);
		intent.putExtra("EXTRA_MESSAGE", 2);
    	startActivity(intent);
	}
	
	public void dietStageBreakfast(View view){
		Intent intent = new Intent(this, DietaryRequirements.class);
		intent.putExtra("EXTRA_MESSAGE", 1);
    	startActivity(intent);
	}
	public void dietStageDinner(View view){
		Intent intent = new Intent(this, DietaryRequirements.class);
		intent.putExtra("EXTRA_MESSAGE", 3);
    	startActivity(intent);
	}
	public void dietStageDessert(View view){
		Intent intent = new Intent(this, DietaryRequirements.class);
		intent.putExtra("EXTRA_MESSAGE", 4);
    	startActivity(intent);
	}
	
	public void needHelp(View view){
		Intent intent = new Intent(this, HelpScreen.class);
    	startActivity(intent);
	}
	
}
