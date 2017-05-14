package com.example.mealchoser;


import java.util.Random;
import android.database.DatabaseUtils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Recipe extends Activity{
	
	String test = "";
	DBAdapter mealdb;
	GestureDetector myGestureDetector;
	String name = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		openDB();
		setContentView(R.layout.activity_recipe);
		setTitle("Your Meal");
		Intent intent = getIntent();
		boolean t[] = intent.getBooleanArrayExtra("diet");
		if(t != null){
			test += "        Meal      \n";
			test += "Breakfast:     " + t[0]+"\n";
			test += "Lunch:         " + t[1]+"\n";
			test += "Dinner:        " + t[2]+"\n";
			test += "Dessert:       " + t[3]+"\n";
			test += "        Dietary      \n";
			test += "Lactose:       " + t[4]+"\n";
			test += "Vegie:         " + t[5]+"\n";
			test += "Glutenfree:    " + t[6]+"\n";
			test += "Peanut:        " + t[7]+"\n";
		}
		
	    myGestureDetector = new GestureDetector(this, new OnGestureListener(){

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				if(Math.abs(e1.getX() - e2.getX()) > 100){
					Recipe.this.recreate();
					//System.out.println("Swipe");
					return true;
				}
				return false;
			}
	    	
	    }
	    );
        
		String message = "";
		
		/*cursor=mealdb.Query(
				mealdb.DATABASE_TABLE,
				new int[] {DBAdapter.COL_ROWID, 
				DBAdapter.COL_Title, 
				DBAdapter.COL_Difficulty, 
				DBAdapter.COL_Recipe,}, 
				COL_Breakfast = t[0] + 
				COL_Lunch = t[1]
				COL_Dinner = t[2]
				COL_Dessert = t[3]
				COL_Lactose = t[4]
				COL_Vegetarian = t[5]
				COL_Gluten = t[6]
				COL_Peanut = t[7]);*/
		int breakfast = (t[0]) ? 1 : 0;
		int lunch = (t[1]) ? 1 : 0;
		int dinner = (t[2]) ? 1 : 0;
		int dessert = (t[3]) ? 1 : 0;
		int lactose = (t[4]) ? 1 : 0;
		int vege = (t[5]) ? 1 : 0;
		int gluten = (t[6]) ? 1 : 0;
		int peanut = (t[7]) ? 1 : 0;

		Cursor cursor = mealdb.findRecipe(String.valueOf(breakfast), String.valueOf(lunch), String.valueOf(dinner), String.valueOf(dessert), String.valueOf(lactose), String.valueOf(vege), String.valueOf(gluten), String.valueOf(peanut));
		//message += "Number of Values Found: " + cursor.getCount() + "\n";
		//message += mealdb.s + "\n";
		String messageTitle = "";
		String messageDiffculty = "";
		
		if(cursor.getCount() == 0) {
			messageTitle += "No Results Found \n";
			messageDiffculty = "";
			message = "";
		}
		else{
			Random number = new Random();
			int random = number.nextInt(cursor.getCount());
			
			if(cursor.moveToFirst()){
				cursor.moveToPosition(random);
				int id = cursor.getInt(0);
				name = cursor.getString(1);
				int difficulty = cursor.getInt(2);
				String recipe = cursor.getString(3);
				
				messageTitle += name;
				messageDiffculty += "Diffculty: " +difficulty;
				message += "Instructions:\n" + recipe;
						//"ID = "+ id + "\n"
				
				
				String uri = "@drawable/image" +id;
				int imageResource = getResources().getIdentifier(uri, null, getPackageName());

				ImageView img = (ImageView)findViewById(R.id.imageView1);
				Drawable res = getResources().getDrawable(imageResource);
				img.setImageDrawable(res);
			}
		}
		cursor.close();
		
		TextView textView = (TextView)findViewById(R.id.RecipeTitle);
		textView.setText(messageTitle);
		TextView textView1 = (TextView)findViewById(R.id.RecipeDiffculty);
		textView1.setText(messageDiffculty);
		displayText(message);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}
	
	private void openDB(){
		mealdb = new DBAdapter(this);
		mealdb.open();
		Cursor mCursor = mealdb.checkSize();
		Boolean rowExists;

		if (mCursor.moveToFirst())
		{
		   // DO SOMETHING WITH CURSOR
		  rowExists = true;

		} else
		{
		   // I AM EMPTY
		   rowExists = false;
		}
		if(!rowExists)
			insertRows();
	}
	
	private void closeDB(){
		mealdb.close();
	}

	private void displayText(String message) {
		TextView textView = (TextView)findViewById(R.id.RecipeDisplay);
		textView.setText(message);
		
	}
	
	private void insertRows(){
		mealdb.insertRow(true, false, false, false, true, false, false, false, "Dairy Free Apple Muffins", 2, "Preheat the oven to 180 degrees C/350 degrees F/gas 4. Line a 12-hole muffin tray with paper cases." 
				+ "Beat the margarine and sugar in a large bowl until pale and fluffy. Beat the egg in another bowl, then stir it into the margarine mixture. Sieve in the flour, baking powder and cinnamon, then fold through (don't mix too much at this stage). Stir in the ground almonds."
				+ "Roughly chop the hazelnuts, then halve, core and chop the apple into rough 1cm chunks. Add the apple to the bowl along with half the hazelnuts and the vanilla extract, then stir briefly to combine. \n \n"
				+ "Divide the mixture between the paper cases and sprinkle over the remaining hazelnuts. Place on the middle shelf of the hot oven for around 20 minutes, or until golden and an inserted skewer comes out clean. Leave to cool completely on a wire rack, then tuck in.");
			mealdb.insertRow(false, false, true, false, false, false, false, false, "Classic Tomato Spaghetti", 1, "1. Pick the basil leaves onto a chopping board (reserving a few baby leaves to garnish), then roughly chop the remaining leaves and finely chop the stalks. \n \n"
				+"2. Peel and finely slice the onion and garlic. If using fresh, cut the tomatoes in half, then roughly chop them or carefully open the tins of tomatoes. \n \n"
				+"3. Put a saucepan on a medium heat and add 1 tablespoon of olive oil and the onion, then cook for around 7 minutes, or until soft and lightly golden. \n \n"
				+"4. Stir in the garlic and basil stalks for a few minutes, then add the fresh or tinned tomatoes and the vinegar. \n \n"
				+"5. Season with a tiny pinch of salt and pepper, then continue cooking for around 15 minutes, stirring occasionally. \n \n"
				+"6. Stir in the chopped basil leaves, then reduce to low and leave to tick away. Meanwhile… \n \n"
				+"7. Carefully fill a large pot three-quarters of the way up with boiling water, add a tiny pinch of salt and bring back to the boil. \n \n"
				+"8. Add the spaghetti and cook according to packet instructions – you want to cook your pasta until it is al dente. This translates as 'to the tooth' and means that it should be soft enough to eat, but still have a bit of a bite and firmness to it. Use the timings on the packet instructions as a guide, but try some just before the time is up to make sure it's perfectly cooked. \n \n"
				+"9. Once the pasta is done, ladle out and reserve a cup of the cooking water and keep it to one side, then drain in a colander over the sink and tip the spaghetti back into the pot. \n \n"
				+"10. Stir the spaghetti into the sauce, adding a splash of the pasta water to loosen, if needed. \n \n"
				+"11. Serve with the reserved basil leaves sprinkled over the top and use a microplane to finely grate the Parmesan cheese, then sprinkle over.");
			mealdb.insertRow(false, false, false, true, false, false, false, false, "Strawberry Slushie", 1, "1. Pick the top leafy bits off the strawberries. \n \n"
				+"2. Pick the mint leaves, discarding the stalk. \n"
				+"3. Add the strawberries and mint leaves to a liquidiser along with 100g of ice cubes. \n \n"
				+"4. On a chopping board, cut the lemon in half. \n \n"
				+"5. Squeeze half the juice into the liquidiser, using your fingers to catch any pips. \n \n"
				+"6. Add enough cold water to just cover the strawberries (roughly 350ml), pop the lid on and whiz until smooth. \n \n"
				+"7. Fill a large jug halfway up with ice cubes. \n \n"
				+"8. Taste the slushie and sweeten with a little sugar, if needed.");
			mealdb.insertRow(false, true, false, false, false, false, false, false, "Griddle-pan Waffles", 1, "Crack the eggs into a bowl, add the milk and whisk to combine. Sift in the flour, baking powder and salt then whisk until fully combined. Add the cooled melted butter and gradually stir it through the mixture. It's important not to stir the mixture any more after this or your waffles may be tough."
				+"Place your griddle pan over a high heat, add the extra teaspoon of butter and as soon as it's melted pour in the waffle batter and spread it around to fill the pan. You could also make smaller waffles, if you prefer – you'll need to do 2 at a time."
				+"Lower the heat to medium-low and cook the waffles for around 6 minutes, or until lightly golden on the bottom. Flip over and continue to cook for around 6 minutes, until golden and cooked through. (It can be tricky to flip a whole waffle, but be bold and go for it – if it breaks, don't worry, you can rock the rustic look.)"
				+"Give the waffle an extra couple of minutes on each side to crisp up, then serve them with your toppings. I like mine with bacon, egg and maple syrup, but you can serve them with berries and yoghurt, or whatever you like. ");
            mealdb.insertRow(true, false, false, false, false, false, false, true, "The Full English", 2, "Preheat the grill to high. Set the oven to its lowest heat and pop 2 plates in it to keep warm. Put the sausages, mushrooms and tomatoes, scored-side up, on to a large grill pan and place under the grill, about 5cm from the heat. Cook for about 10 minutes, turning the sausages once or twice."
                +"Add the bacon and black pudding to the pan and grill for 5 minutes, turning halfway through cooking, until they are cooked and crispy. Put the baked beans in a saucepan and warm gently for 2 to 3 minutes, stirring occasionally."
                +"Place a non-stick saucepan over a low heat. Melt 1 knob of butter in the pan, add the eggs, season to taste and stir gently until just scrambled. Remove from the heat."
                +"Put the bread in the toaster, and arrange the sausages, mushrooms and tomatoes with the bacon, black pudding and beans on the warm plates. When the toast pops up, butter it, then put a slice on each plate and top with scrambled egg. Serve with HP sauce or tomato ketchup on the side, and a pot of tea."); 
				
            mealdb.insertRow(true, false, false, false, false, false, false, true, "Scrambled eggs", 2, "1. Crack the eggs into a measuring jug. \n \n"
                +"2. Add a tiny pinch of salt and pepper, then use a fork to beat them together well. \n \n"
                +"3. Put a medium saucepan over a low heat and add the butter. \n \n"
                +"4. Leave it to melt slowly, then when it starts to bubble carefully pour in the eggs. \n \n"
                +"5. Stir slowly with a wooden spoon, or a spatula if you've got one, so you can get right to the edges of the pan. \n \n"
                +"6. Keep gently stirring until the eggs still look silky, slightly runny and slightly underdone, and then remove from the heat – the heat of the pan will continue to cook the eggs to perfection. \n \n"
                +"7. Serve with lightly buttered toast.");
                
            mealdb.insertRow(true, false, false, false, true, false, false, false, "Icelandic rice pudding", 2, "Put the dried fruit in a stainless steel pan with 1 tablespoon of the sugar and enough water to cover. "
                +"Put on the hob and heat gently for 10 minutes, then add 5 tablespoons of cold water and whiz in a blender. "
                +"Push through a sieve, taste and add a little sugar if necessary. "
                +"Place the rice, milk, cream, remaining sugar and cinnamon in a deep pot and heat gently. "
                +"Score the vanilla pod lengthways, scrape the seeds out, adding both to the pot."
                +"Stir well and bring gently to the boil. \n \n"
                +"Simmer very gently for 30 to 35 minutes, stirring every now and then until all the liquid is absorbed and the rice is just cooked. "
                +"Spoon the cooked rice pudding into a serving dish, swirl the fruit sauce into it and top with a dollop of crème fraîche. "
                +"Scatter with fresh redcurrants before serving." );
            mealdb.insertRow(false, false, true, false, false, false, false, false, "Asian squash salad with crispy duck", 2, "Preheat the oven to 180ºC/350ºF/gas 4. Wash the duck and pat it dry, inside and out, with kitchen paper, then rub it all over, inside as well, with salt and pepper. Place it in a tray and roast in the preheated oven for around 2 hours, turning it every now and then. Halfway through, you'll probably need to drain away a lot of the fat from the bird. Don't throw it away, though! Sieve it and keep the fat (but no meat juices) in a jar in the fridge for a couple of months and use it to roast potatoes. \n \n"
                +"In a pestle and mortar or a Flavour Shaker, bash up the dried chillies and coriander seeds and add the ground cinnamon and a good pinch of salt and pepper. Scoop the seeds out of your squash and put them to one side. Cut the squash into wedges, place them in a roasting tray, and drizzle over enough olive oil just to coat. Sprinkle over the ground spices and give the squash a good toss, spreading the pieces out in one layer. Once the duck has been in the oven for 1 hour and 15 minutes, add the tray of squash to the oven and roast for about 45 minutes. \n \n"
                +"Meanwhile, rinse the squash seeds after removing any fibres. Season with salt and pepper and drizzle with olive oil. Toast them in a dry frying pan until they're golden and crisp, and put aside. To make the dressing, put the lime juice and zest into a bowl and add the same amount of extra virgin olive oil, plus the sesame oil and the soy sauce. Stir in the sugar, chilli, garlic, the green spring onion ends and coriander stalks. Taste and adjust the sweet-and-sourness and the seasoning. You want it to be a little limey, to contrast with the rich duck. \n \n"
                +"After 2 hours, if the duck is nice and crispy, and the squash soft and sticky, take both trays out of the oven. If they need more time, leave them in until perfectly done. Using two forks, shred the duck meat off the bone and put into a large bowl. While the duck and squash are still warm, toss with the toasted seeds, half the coriander leaves, half the mint and half the white spring onion slices. Pour on the dressing and toss together. Serve sprinkled with the rest of the coriander, mint and white spring onion slices.");
			mealdb.insertRow(false, true, false, false, false, false, false, true, "Anglo French Toastie", 1, "2 slices white bread, around 1cm thick \n"
				    +"unsalted butter \n"
				    +"15 g Comte cheese \n"
				    +"15 g Westcombe or artisan Cheddar cheese \n"
				    +"2 slices higher-welfare roasted ham \n"
				    +"½ tablespoon white wine vinegar \n"
				    +"1 tablespoon rapeseed oil \n"
				    +"freshly ground black pepper \n"
				    +"sea salt \n"
				    +"1 small eating apple \n\n"
				    +"1 handful mixed salad leaves , such as frisse, watercress, rocketLightly butter the slices of bread, then place buttered-side down on a chopping board. \n"
				    +"Slice and lay the cheeses over one piece of bread, top with the ham then place the second piece of bread on top, buttered-side up.\n"
				    +"Place into a preheated toastie maker, clamp the lid down and toast for 4 to 5 minutes, or until golden and crispy.\n"
				    +"Meanwhile, add the vinegar and oil to a clean jam jar with a little pinch of salt and pepper, pop the lid on and shake to combine.\n"
				    +"Cut the apple into matchsticks, removing the core, then toss with the salad leaves and dressing. \n"
				    +"Serve the golden toastie with the fresh salad on the side, and tuck in. \n");
			 mealdb.insertRow(false, true, true, false, true, false, true, true, "Roasted chicken breast with pancetta, leeks & thyme", 1, "1 higher-welfare chicken breast \n"
				    +"1 large leek \n"
				    +"a few sprigs fresh thyme \n"
				    +"1 good lug olive oil \n"
				    +"1 pinch sea salt \n"
				    +"1 pinch freshly ground black pepper \n"
				    +"1 small swig white wine \n"
				    +"4 slices higher-welfare pancetta \n"
				    +"olive oil \n"
				    +"2 whole sprigs thymePreheat the oven to 200°C/400°F/gas 6. \n\n"
					+"Put 1 chicken breast, skin removed, in a bowl. \n"
					+"Trim and wash a large leek, remove the outer leaves, then slice it into 0.5cm/¼ inch pieces. \n"
					+"Add these to the bowl with the leaves of a few sprigs of fresh thyme, a good lug of olive oil, a pinch of sea salt and freshly ground black pepper and a small swig of white wine and toss together. \n"
					+"Place your leek and flavourings from the bowl into the tray, then wrap the chicken breast in 4 slices of pancetta. \n"
					+"This will not only flavour the chicken but also protect it while it cooks. \n"
					+"Try and bend the sides of the tray in towards the chicken so the leeks don't burn during cooking. \n"
					+"Drizzle with olive oil, place a couple of whole thyme sprigs on top and cook in the middle of the oven for 25 to 35 minutes.\n");
			 mealdb.insertRow(false, true, false, false, false, false, false, true, "Reuben-ish sandwich", 1, "2 big slices rye bread, 1cm in size \n"
					 +"low fat mayonnaise \n"
					 +"3 heaped tablespoons sauerkraut \n"
					 +"1 fresh red chilli, deseeded and finely sliced \n"
					 +"3 slices pastrami \n"
					 +"a few gherkins \n"
					 +"60 g Swiss cheese \n"
					 +"1 handful watercress leaves, to serve \n\n"
					 +"Grill the slices of bread on a griddle pan until lightly toasted on both sides then spread one side of each with mayonnaise. \n"
					 +"Put some of the sauerkraut and some of the chilli on 2 of the slices, and top with a couple of slices of pastrami. \n"
					 +"Top with the remaining sauerkraut and chilli and the sliced gherkins, then grate the Swiss cheese over the top. \n"
					 +"Preheat a hot grill. Place the slices with toppings under the grill until the cheese is melted and dribbling. \n"
					 +"Stack the sandwich together, adding a few watercress leaves and finishing with the final slice of toast. \n"
					 +"Press down lightly and use wooden skewers to hold together. Tuck in! \n");
			 mealdb.insertRow(false, true, true, false, false, false, true, true, "Gluten-free Pizza", 1, "250 ml semi-skimmed milk \n"
				    +"1 x 7 g sachet of dried yeast \n"
				    +"2½ teaspoons caster sugar \n"
				    +"400 g gluten-free bread flour, plus extra for dusting \n"
				    +"1 teaspoon xanthan gum \n"
				    +"1 teaspoon fine sea salt \n"
				    +"1 large free-range egg \n"
				    +"olive oil \n"
				    +"½ teaspoon bicarbonate of soda \n"
				    +"2 teaspoons cider vinegar \n"
				    +"For the topping \n"
				    +"½ bunch of fresh basil \n"
				    +"1 clove of garlic, peeled \n"
				    +"1 x 400 g tin of plum tomatoes \n"
				    +"sea salt \n"
				    +"freshly ground black pepper \n"
				    +"2 x 125 g balls of buffalo mozzarella \n\n"
				    +"Preheat the oven to 220ºC/425ºF/gas 7. \n"
					+"Place a pizza stone or a large baking tray in the oven to heat up. \n"
					+"Heat the milk in a small pan over a low heat until lukewarm, then place 50ml into a jug with the yeast and sugar. \n"
					+"Mix well, then set aside for a few minutes until starting to bubble. \n"
					+"Meanwhile, sieve the flour, xanthan gum and salt into a large bowl, then make a well in the middle. \n"
					+"In a separate bowl, combine the egg, 3 tablespoons of olive oil and the remaining milk, then pour it into the well, along with the yeast mixture. \n"
					+"Gradually bring the mixture together with a fork until it forms a smooth dough. \n"
					+"In a small cup, combine the bicarbonate of soda and vinegar, then quickly knead it into the mixture. \n"
					+"Place the dough into a lightly oiled bowl, cover with a damp tea towel, then leave to prove in a warm place for around 1 hour, or until doubled in size. \n"
					+"Meanwhile, make the topping. Pick the basil leaves and set aside, then finely chop the stalks and the garlic. \n"
					+"Heat a splash of olive oil in a large frying pan over a medium heat, then add the basil stalks and garlic. \n"
					+"Cook for a few minutes, or until golden. \n"
					+"Pour in the tinned tomatoes, break them up with the back of a wooden spoon, then cook for 5 to 10 minutes, or until thickened. \n"
					+"Transfer to a blender along with half the reserved basil leaves, blitz until smooth, then season to taste. \n"
					+"Once doubled in size, divide the dough into four equal-sized pieces on a flour-dusted surface. \n"
					+"Roll out until roughly 30cm in diameter and 2mm thick. \n"
					+"Place the pizza bases onto the preheated pizza stone or baking tray (you'll need to do this in batches), then spread over the tomato sauce, leaving a rough 2cm gap around the edge. \n"
					+"Tear over the mozzarella, then pop in the hot oven for 10 to 12 minutes or until golden and crisp. \n"
					+"Scatter the reserved basil leaves on top, then serve.");
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId(); 
		
		if (id == R.id.action_settings) {
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);    
        return myGestureDetector.onTouchEvent(ev); 
    }
	
	public void share(View view){
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		intent.setType("text/plain");
		String shareBody = name;
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I like this Meal");
		intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(intent);
	}
}
