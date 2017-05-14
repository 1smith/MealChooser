package com.example.mealchoser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class HelpScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_screen);
		// Show the Up button in the action bar.
		//setupActionBar();
		setTitle("Help");
		String message = "Having difficulty using the Meal Chooser App? \n\n" +
				"It’s quite simple really, all you need to do is select the meal type you are looking for.\n\n" +
				"If you’ve just woken up and need a quick and easy meal, select the breakfast option.\n\n" +
				"If you’re feeling a bit peckish around the afternoon, select the lunch option.\n\n" +
				"If you’re home alone and hungry for a good feed, select the dinner option.\n\n" +
				"If you’ve got a sweet tooth (anytime is alright), select dessert option.\n\n\n" +
				"Once, you’ve made you’re select, select any dietary requirements you may have.\n\n" +
				"Whether you can’t digest any cow by-product, you don’t enjoy the smell of cooked meat (or uncooked)," +
				" your body can’t handle wheat, or if you just don’t like peanuts (or have an allergy, of course).\n\n" +
				"When you’ve made your selection(s), press the ‘Find your Recipe’ button and the app will search the " +
				"complex database and find you a meal fit for kings and queens. \n\n" +
				"If you don’t like what the app has found for you, swipe horiziontally and you’ll find a new recipe," +
				"that you may enjoy.";
		
		TextView textView = (TextView)findViewById(R.id.helpDisplay);
		textView.setText(message);

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
