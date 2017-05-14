package com.example.mealchoser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class DietaryRequirements extends Activity {
		 
	public String yourChoice;
	public int choice;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_dietary_requirements);
	        setTitle("Enter Dietary Requirements");
	        
	        yourChoice = "You have chosen ";
	        Intent intent = getIntent();
	        choice = intent.getIntExtra("EXTRA_MESSAGE", 0);
	        if(choice == 0 ){
	        	yourChoice = "I am Error";
	        }
	        else {
	        	switch(choice){
	        	case 1: yourChoice += "Breakfast"; break;
	        	case 2:yourChoice += "Lunch"; break;
	        	case 3:yourChoice += "Dinner"; break;
	        	case 4:yourChoice += "Dessert"; break;
	        	}
	        	
	        }
	        TextView textView1 = (TextView) findViewById(R.id.textView1);
	        textView1.setText(yourChoice);
	    }


	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			//getMenuInflater().inflate(R.menu.main, menu);
			return false;
		}
	    
	    public void presentMeal(View view){
	    	Intent intent = new Intent(this, Recipe.class);
	    	
	    	//get check boxes
	    	CheckBox nut = (CheckBox) findViewById(R.id.peanut);
	    	CheckBox veg = (CheckBox) findViewById(R.id.vegie);
	    	CheckBox lact = (CheckBox) findViewById(R.id.lactose);
	    	CheckBox glut = (CheckBox) findViewById(R.id.gluten);
	    	
	    	//build boolean array and fill with choices
	    	boolean diet[] = new boolean[8];
	    	diet[0] = (choice == 1);
	    	diet[1] = (choice == 2);
	    	diet[2] = (choice == 3);
	    	diet[3] = (choice == 4);
	    	diet[4] = lact.isChecked();
	    	diet[5] = veg.isChecked();
	    	diet[6] = glut.isChecked();
	    	diet[7] = nut.isChecked();
	    		    	
	    	intent.putExtra("diet", diet);
	    	startActivity(intent);
	    }
}
