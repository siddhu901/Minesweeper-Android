//  Author: Siddhartha Kakarla
//  Course Number: CSE 651
//  HOMEWORK-05

//  MainActivity.java
//  Minesweeper
//
//  Created by Siddhartha Kakarla on 4/3/14.
//  Copyright (c) 2014 Siddhartha Kakarla. All rights reserved.
//

package com.example.minesweeper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MinesView myview=new MinesView(this);
		setContentView(myview);
		myview.requestFocus();
		
	}

	
}
