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

package expandableselectiontree;

import java.util.ArrayList;
import java.util.List;

import sample.FilterState;
import sample.ModelObject;

import com.alwawee.expandableselectiontree.R;

import android.app.ExpandableListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class ExpandableSelectionTreeActivity extends ExpandableListActivity implements OnChildClickListener {
    
	//=================================================================================
	// Variables
	//=================================================================================
    private ExpandableSelectionTreeAdapter expandableListAdapter;
    
    
	//=================================================================================
	// Getters & Setters
	//=================================================================================
	public ExpandableSelectionTreeAdapter getExpandableListAdapter() {
		return expandableListAdapter;
	}

	public void setExpandableListAdapter(ExpandableSelectionTreeAdapter expandableListAdapter) {
		this.expandableListAdapter = expandableListAdapter;
	}
    
    
	//=================================================================================
	// Activity Life Cycle
	//=================================================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Setting up adapter
        ExpandableListView listView = this.getExpandableListView();
        expandableListAdapter = new ExpandableSelectionTreeAdapter(this, listView);
        setExpandableListAdapter(expandableListAdapter);
        
        // What happens, when a cell is clicked
        listView.setOnChildClickListener(expandableListAdapter);
        
        setListAdapter(expandableListAdapter);
        
        // Adapter should know it's activity to update title
        expandableListAdapter.setActivity(this);
    }
    
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	
    	selectClicked();
    };
    
    
	//=================================================================================
	// Setting up Menu
	//=================================================================================
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_expandable_selection_tree, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		if (item.getItemId() == R.id.action_bar_select) {
			selectClicked();
		}
		else if (item.getItemId() == R.id.action_bar_clear) {
			clearClicked();
		}
		else {
			selectClicked();
		}
		return true;
	}
	
	
	//=================================================================================
	// Other
	//=================================================================================
	private void selectClicked() {
		// Converting HashSet -> List
		List<ModelObject> list = new ArrayList<ModelObject>(expandableListAdapter.getSelectedModelObjectHashSet());
		
		// We save a selected list in a Singleton called FilterState
		FilterState.sharedState.setModelObjectList(list);
		
		// Closing Activity
    	finish();
	}
	
	
	private void clearClicked() {
		expandableListAdapter.clear();
	}
	
	
	public void updateTitle() {
		Resources resources = this.getResources();
		this.setTitle(resources.getString(R.string.is_selected) + ": " + expandableListAdapter.getSelectedModelObjectHashSet().size());
	}
}

