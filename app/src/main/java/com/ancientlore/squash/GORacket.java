package com.ancientlore.squash;

import android.graphics.Rect;


class GORacket extends GameObject
{
	private GOStateHorizontal state;
	private int speed;

	GORacket(int displayX, int displayY)
	{
		state = GOStateHorizontal.STOP;
		speed = displayX / 30;

		int newWidth = displayX / 4;
		int newHeight = newWidth / 4;
		this.setRect(new Rect(displayX / 2 - newWidth / 2, displayY - newHeight,
				displayX / 2 + newWidth / 2, displayY));
		//image = BitmapFactory.decodeResource(context.getResources(), R.drawable.platform);
		//image =  Bitmap.createScaledBitmap(image, newWidth, newHeight, false);
	}

	void moveLeft()
	{
		getRect().left -= speed;
		getRect().right -= speed;
	}

	void moveRight()
	{
		getRect().left += speed;
		getRect().right += speed;
	}

	GOStateHorizontal getState()
	{
		return state;
	}

	int getSpeed()
	{
		return speed;
	}

	void setState(GOStateHorizontal state)
	{
		this.state = state;
	}

	void setSpeed(int speed)
	{
		this.speed = speed;
	}
}
