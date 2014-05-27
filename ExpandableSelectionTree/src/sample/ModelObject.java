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

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import android.content.Context;
import android.util.Log;

public class ModelObject {	
	
	//================================================================================
    // Variables
    //================================================================================
	private String modelObjectId;
	private String name;
	private String modelSectionObjectId;
	private boolean selected;
	
	
	//================================================================================
    // Constructor
    //================================================================================
	public ModelObject(String modelObjectId, String name, String modelSectionObjectId) {
	    this.modelObjectId = modelObjectId;
	    this.name = name;
	    
	    // Randomizing name
	    this.name = RandomStringUtils.random(10, true, false);
	    
	    this.modelSectionObjectId = modelSectionObjectId;
	}
	
	
	//================================================================================
    // Getters & Setters
    //================================================================================
	public String getModelObjectId() {
		return modelObjectId;
	}


	public void setModelObjectId(String modelObjectId) {
		this.modelObjectId = modelObjectId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getModelObjectSectionId() {
		return modelSectionObjectId;
	}


	public void setModelObjectSectionId(String modelSectionObjectId) {
		this.modelSectionObjectId = modelSectionObjectId;
	}
	
	
	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	//================================================================================
    // Other
    //================================================================================
	
	// Возвращает список из объектов рубрик, считанных из файла CSV
	public static ArrayList<ModelObject> modelObjectListFromFile(Context context) {
		// Считываем все строки из файла
		ArrayList<String> tempList = Helper.readTextFileIntoArrayList("ModelObjects.csv", context);
		
		// Массив объектов рубрик
		ArrayList<ModelObject> arrayList = new ArrayList<ModelObject>();
		
		for (String str : tempList) {
			// Разбиваем каждую строку на компоненты и создаем из них объект
			String[] fieldArray = str.split(";");
			ModelObject modelObject = new ModelObject(fieldArray[0], fieldArray[1], fieldArray[2]);
			arrayList.add(modelObject);
		}
		
		return arrayList;
	}
	
	
	// Возвращает строку из Id рубрик в ArrayList через запятую
	public static String modelObjectIdString (ArrayList<ModelObject> ModelObjectList) {	
		ArrayList<String> stringList = ModelObject.stringIdListFromModelObjectList(ModelObjectList);
		
		// Apache Commons Lang 3 рулит, велосипед не изобретаем
		String resultString = StringUtils.join(stringList.toArray(),",");
        Log.v(Helper.TAG, "ModelObject Id string = " + resultString);
        
        return resultString;
	}
	
	
	// Возвращает строку из имен рубрик в ArrayList через запятую
	public static String ModelObjectNameString (ArrayList<ModelObject> ModelObjectList) {	
		ArrayList<String> stringNameList = new ArrayList<String> ();
		for (ModelObject ModelObject : ModelObjectList) {
			stringNameList.add(ModelObject.getName());
		}

		String resultString = StringUtils.join(stringNameList.toArray(),",");
		Log.v(Helper.TAG, "ModelObject Id string = " + resultString);

		return resultString;
	}
	
	
	// Возвращает массив рубрик из строки из Id рубрик через запятую, используя полный список рубрик
	public static ArrayList<ModelObject> modelObjectListFromIdString (String modelObjectIdString, ArrayList<ModelObject> fullModelObjectArrayList) {
		
		ArrayList<ModelObject> resultList = new ArrayList<ModelObject> ();
		
		if (modelObjectIdString == null) return resultList;
		
		// Получаем массив из всех Id всех рубрик
		ArrayList<String> stringList = ModelObject.stringIdListFromModelObjectList(fullModelObjectArrayList);
		
		// Находим индекс каждого имени рубрики и по индексу сам регион в массиве всех рубрик, затем добавляем его в результирующий массив
		String[] stringArray = modelObjectIdString.split(",");
        for (String modelObjectId : stringArray) {
        	int index = stringList.indexOf(modelObjectId);
        	ModelObject ModelObject = fullModelObjectArrayList.get(index);
        	resultList.add(ModelObject);
		}
        
        Log.v(Helper.TAG, "Number of ModelObjects in result list = " + resultList.size());
        
        return resultList;
	}
	
	
	@Override
    public String toString() {
        return this.name;
    }
	
	
	private static ArrayList<String> stringIdListFromModelObjectList (ArrayList<ModelObject> ModelObjectList) {
		ArrayList<String> stringIdList = new ArrayList<String> ();
		for (ModelObject ModelObject : ModelObjectList) {
			stringIdList.add(ModelObject.getModelObjectId());
		}
		
		return stringIdList;
	}	
	
	
	@Override
	public boolean equals(Object obj) { 	
	   EqualsBuilder builder = new EqualsBuilder().append(this.getModelObjectId(), ((ModelObject) obj).getModelObjectId());
	   boolean result = builder.isEquals();
	   return result;
	}
}
