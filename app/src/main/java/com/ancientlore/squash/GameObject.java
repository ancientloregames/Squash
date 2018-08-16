package com.ancientlore.squash;

import android.graphics.Bitmap;
import android.graphics.Rect;


abstract class GameObject
{

	private Rect rect;
	private Bitmap image;

	Rect getRect()
	{
		return rect;
	}

	Bitmap getImage()
	{
		return image;
	}

	void setRect(Rect rect)
	{
		this.rect = rect;
	}

	void setImage(Bitmap image)
	{
		this.image = image;
	}
}
