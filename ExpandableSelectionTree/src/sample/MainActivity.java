//
//  Created by Dennis Kutlubaev on 27.05.14.
//  This code is distributed under the terms and conditions of the MIT license.
//  Copyright (c) 2014 Dennis Kutlubaev (alwawee@gmail.com)
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

package sample;

import java.util.List;

import com.alwawee.expandableselectiontree.R;

import expandableselectiontree.ExpandableSelectionTreeActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        Helper.fullModelObjectArrayList = ModelObject.modelObjectListFromFile(this);
        Log.v(Helper.TAG, "Full Number of objects = " + Helper.fullModelObjectArrayList.size());
        
        Helper.fullModelObjectSectionArrayList = ModelSectionObject.modelSectionObjectListFromFile(this);
        Log.v(Helper.TAG, "Full Number of object sections = " + Helper.fullModelObjectSectionArrayList.size());
	}

	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/


	public void buttonClicked(View view) {
		Intent intent = new Intent(this, ExpandableSelectionTreeActivity.class);
		startActivity(intent);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Button button = (Button) findViewById(R.id.button);
		String buttonTitle = getResources().getString(R.string.select_items_button_title);
		
		List<ModelObject> modelObjectList = FilterState.sharedState.getModelObjectList();
		
		if (modelObjectList == null) {
			button.setText(buttonTitle);
			return;
		}
		
		if (modelObjectList.size() > 0) {
			buttonTitle = getResources().getString(R.string.is_selected) + ": " + modelObjectList.size() + " " + getResources().getString(R.string.items);
		}
		button.setText(buttonTitle);
	}
}
