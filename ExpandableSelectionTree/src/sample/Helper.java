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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;

public class Helper {

	// ================================================================================
	// Переменные
	// ================================================================================
	final static public String TAG = "DebugLogMessage";

	// Список всех рубрик
	public static ArrayList<ModelObject> fullModelObjectArrayList;

	// Список всех секций рубрик
	public static ArrayList<ModelSectionObject> fullModelObjectSectionArrayList;


	// ================================================================================
	// Общие методы
	// ================================================================================

	// Считывает файл и возвращает список строк
	static public ArrayList<String> readTextFileIntoArrayList(String fileName,
			Context context) {
		ArrayList<String> temp = new ArrayList<String>();

		try {
			// Открываем файл
			AssetManager assetManager = context.getAssets();
			InputStream inputStream = assetManager.open(fileName);
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			// Считываем все строки и помещаем их в массив temp
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				temp.add(line);
			}
		} catch (IOException e) {
			Log.v(Helper.TAG, e.getMessage());
			e.printStackTrace();
		}

		return temp;
	}
	

	static public boolean isNewAPI() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}
	
	
	public static String generateString(Random rng, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
}
