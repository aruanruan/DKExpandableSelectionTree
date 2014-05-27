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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import android.content.Context;

public class ModelSectionObject {	
	
	//================================================================================
    // Variables
    //================================================================================
	private String modelSectionObjectId;
	private String name;
	private List <ModelObject> modelObjectList;
	private boolean selected;
	
	
	//================================================================================
    // Constructor
    //================================================================================
	public ModelSectionObject(String modelSectionObjectId, String name) {
	    this.modelSectionObjectId = modelSectionObjectId;
	    this.name = name;
	    
	    // Randomizing name
	    this.name = RandomStringUtils.random(10, true, false) + " " + RandomStringUtils.random(10, true, false);
	}
	
	
	//================================================================================
    // Getters and Setters
    //================================================================================
	public String getModelObjectSectionId() {
		return modelSectionObjectId;
	}


	public void setModelObjectSectionId(String modelSectionObjectId) {
		this.modelSectionObjectId = modelSectionObjectId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public List <ModelObject> getModelObjectList() {
		return modelObjectList;
	}


	public void setModelObjectList(List <ModelObject> modelObjectList) {
		this.modelObjectList = modelObjectList;
	}
	
	
	//================================================================================
    // Other
    //================================================================================
	
	// Возвращает список из объектов секций рубрик, считанных из файла CSV
	public static ArrayList<ModelSectionObject> modelSectionObjectListFromFile(Context context) {
		// Считываем все строки из файла
		ArrayList<String> tempList = Helper.readTextFileIntoArrayList("ModelObjectSections.csv", context);
		
		// Массив объектов рубрик
		ArrayList<ModelSectionObject> arrayList = new ArrayList<ModelSectionObject>();
		
		for (String str : tempList) {
			// Разбиваем каждую строку на компоненты и создаем из них объект
			String[] fieldArray = str.split(";");
			ModelSectionObject modelSectionObject = new ModelSectionObject(fieldArray[0], fieldArray[1]);
			arrayList.add(modelSectionObject);
		}
		
		return arrayList;
	}
	
	@Override
    public String toString() {
        return this.name;
    }


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
