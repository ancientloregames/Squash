package com.ancientlore.squash;

import android.graphics.Color;
import android.graphics.Rect;


class GOBlock extends GameObject
{
	private String content;

	public int getColor()
	{
		return color;
	}

	private int color;

	GOBlock(int type, Rect newRect)
	{
		this.setRect(newRect);
		setContent(type);
	}

	private void setContent(int type)
	{
		switch (type)
		{
			case 0:
				content = "none";
				break;
			case 1:
				content = "empty";
				color = Color.WHITE;
				break;
			case 2:
				content = "bonus";
				color = Color.GRAY;
				break;
			case 3:
				content = "bonus";
				color = Color.DKGRAY;
				break;
			case 4:
				content = "bonus";
				color = Color.BLACK;
				break;
		}
	}

	String getContent()
	{
		return content;
	}

	void deleteBlock()
	{
		content = "none";
	}
}
