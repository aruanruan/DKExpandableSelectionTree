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
import java.util.HashSet;
import java.util.List;

import sample.FilterState;
import sample.Helper;
import sample.ModelObject;
import sample.ModelSectionObject;

import com.alwawee.expandableselectiontree.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class ExpandableSelectionTreeAdapter extends BaseExpandableListAdapter implements OnChildClickListener {

	//=================================================================================
	// Variables
	//=================================================================================
	private LayoutInflater inflater;

	// We use HashSet instead of Lists to make sure, modelObjects are not duplicated.
	private HashSet<ModelObject> selectedModelObjectHashSet;
	private HashSet<ModelSectionObject> selectedModelObjectSectionHashSet;
	private ExpandableSelectionTreeActivity activity;

	//=================================================================================
	// Constructor
	//=================================================================================
	public ExpandableSelectionTreeAdapter (Context context, ExpandableListView listView) {
		inflater = LayoutInflater.from(context);

		// Initializing HashSets
		selectedModelObjectHashSet = new HashSet<ModelObject>();
		selectedModelObjectSectionHashSet = new HashSet<ModelSectionObject>();

		// Filling Sets of selected modelObjects from FilterState
		FilterState filterState = FilterState.sharedState;
		if (filterState.getModelObjectList() != null) {
			selectedModelObjectHashSet = new HashSet<ModelObject>(filterState.getModelObjectList());
		}

		// Creating arrays of modelObjects for each section
		for ( ModelSectionObject modelSectionObject:Helper.fullModelObjectSectionArrayList ) {

			List<ModelObject> temp = new ArrayList<ModelObject>();

			for (ModelObject modelObject : Helper.fullModelObjectArrayList) {
				if (modelObject.getModelObjectSectionId().equals(modelSectionObject.getModelObjectSectionId())) {

					// Selecting modelObject if it is present in FilterState
					if (selectedModelObjectHashSet.contains(modelObject)) {
						modelObject.setSelected(true);
					}

					// Adding modelObject to section
					temp.add(modelObject);
				}
			}

			modelSectionObject.setModelObjectList(temp);
		}
	}


	//=================================================================================
	// Other
	//=================================================================================
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ModelSectionObject modelSectionObject = Helper.fullModelObjectSectionArrayList.get(groupPosition);
		ModelObject modelObject = modelSectionObject.getModelObjectList().get(childPosition);
		return modelObject;
	}


	@Override
	public long getChildId(int groupPosition, int childPosition) {
		ModelSectionObject modelSectionObject = Helper.fullModelObjectSectionArrayList.get(groupPosition);
		ModelObject modelObject = modelSectionObject.getModelObjectList().get(childPosition);
		return Long.valueOf(modelObject.getModelObjectId());
	}


	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ModelSectionObject modelSectionObject = Helper.fullModelObjectSectionArrayList.get(groupPosition);
		final ModelObject modelObject = modelSectionObject.getModelObjectList().get(childPosition);

		convertView = inflater.inflate(R.layout.offer_trade_expandable_list_child_item, parent, false);

		TextView nameTextView = (TextView) convertView.findViewById(R.id.modelObjectTextView);
		nameTextView.setText(getChild(groupPosition, childPosition).toString());

		// Setting up checkbox
		CheckBox cb = (CheckBox) convertView.findViewById( R.id.modelObjectCheckBox );

		cb.setChecked( modelObject.isSelected() );
		// Handling checkbox events
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				modelObject.setSelected(isChecked);

				if ( isChecked ) {
					getSelectedModelObjectHashSet().add(modelObject);
				}
				else {
					getSelectedModelObjectHashSet().remove(modelObject);
				}

				// Updating title of a parent Activity
				if (activity != null) {
					activity.updateTitle();
				}
			}
		});

		return convertView;
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		ModelSectionObject modelSectionObject = Helper.fullModelObjectSectionArrayList.get(groupPosition);
		return modelSectionObject.getModelObjectList().size();
	}


	@Override
	public Object getGroup(int groupPosition) {
		return Helper.fullModelObjectSectionArrayList.get(groupPosition);
	}


	@Override
	public int getGroupCount() {
		return Helper.fullModelObjectSectionArrayList.size();
	}


	@Override
	public long getGroupId(int groupPosition) {
		ModelSectionObject modelSectionObject = Helper.fullModelObjectSectionArrayList.get(groupPosition);
		return Long.valueOf(modelSectionObject.getModelObjectSectionId());
	}


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final ModelSectionObject modelSectionObject = Helper.fullModelObjectSectionArrayList.get(groupPosition);
		final ExpandableSelectionTreeAdapter adapter = this;

		// Getting all modelObjects for a current section
		final List<ModelObject> modelObjectList = modelSectionObject.getModelObjectList();

		// Determing a number of selected modelObjects
		int selectedCount = 0;
		for ( ModelObject modelObject : modelObjectList ) {
			if ( modelObject.isSelected() ) selectedCount ++;
		}

		convertView = inflater.inflate(R.layout.offer_trade_expandable_list_group_item, parent, false);
		TextView nameTextView = (TextView) convertView.findViewById(R.id.modelObjectTextView);

		String nameText = getGroup(groupPosition).toString();
		if (selectedCount > 0) {
			nameText += " (" + Integer.toString(selectedCount) + ")";
		}
		nameTextView.setText(nameText);

		// Setting up checkbox
		CheckBox cb = (CheckBox) convertView.findViewById( R.id.modelObjectGroupCheckBox );

		cb.setChecked( modelSectionObject.isSelected() );
		// Checkbox event handler
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				modelSectionObject.setSelected(isChecked);

				List<ModelObject> modelObjectList = modelSectionObject.getModelObjectList();

				if ( isChecked ) {
					selectedModelObjectSectionHashSet.add(modelSectionObject);
				}
				else {
					selectedModelObjectSectionHashSet.remove(modelSectionObject);
				}

				// Selecting or deselecting all modelObjects in a current section
				for (ModelObject modelObject : modelObjectList) {
					modelObject.setSelected(isChecked);

					// Adding or deleting modelObjects from HashSets
					if ( isChecked ) {
						getSelectedModelObjectHashSet().add(modelObject);
					}
					else {
						getSelectedModelObjectHashSet().remove(modelObject);
					}
				}

				// Updating title
				if (activity != null) {
					activity.updateTitle();
				}

				// Updating tree
				adapter.notifyDataSetChanged();
			}
		});

		return convertView;
	}


	@Override
	public boolean hasStableIds() {
		return true;
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}


	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		ModelSectionObject modelSectionObject = Helper.fullModelObjectSectionArrayList.get(groupPosition);
		ModelObject modelObject = modelSectionObject.getModelObjectList().get(childPosition);

		boolean isChecked = ! modelObject.isSelected();
		modelObject.setSelected(isChecked);

		CheckBox cb = (CheckBox) v.findViewById( R.id.modelObjectCheckBox );
		cb.setChecked( isChecked );

		// Updating current cell
		v.invalidate();
		v.requestLayout();

		return true;
	}


	public HashSet<ModelObject> getSelectedModelObjectHashSet() {
		return selectedModelObjectHashSet;
	}


	public void setSelectedModelObjectHashSet(HashSet<ModelObject> selectedModelObjectHashSet) {
		this.selectedModelObjectHashSet = selectedModelObjectHashSet;
	}


	public ExpandableSelectionTreeActivity getActivity() {
		return activity;
	}


	public void setActivity(ExpandableSelectionTreeActivity activity) {
		this.activity = activity;
	}


	public void clear() {
		for (ModelObject modelObject : Helper.fullModelObjectArrayList) {
			modelObject.setSelected(false);
		}

		for (ModelSectionObject modelSectionObject : Helper.fullModelObjectSectionArrayList) {
			modelSectionObject.setSelected(false);
		}

		selectedModelObjectHashSet.clear();
		selectedModelObjectSectionHashSet.clear();

		// Updating title
		if (activity != null) {
			activity.updateTitle();
		}

		// Reloading tree
		final ExpandableSelectionTreeAdapter adapter = this;
		adapter.notifyDataSetChanged();
	}
}
