package com.ancientlore.squash;

import android.graphics.Rect;


class ManagerLevel
{
	private static volatile ManagerLevel instance = null;
	private String level;

	private Rect gameField;
	private Rect gridField;

	private int gridWidth;
	private int gridHeight;
	private int blockSize;
	private GOBlock[][] blocks;
	private int countOfBlocks;
	private GORacket racket;
	private GOBall ball;

	private ManagerLevel()
	{
	}

	static ManagerLevel getInstance()
	{
		if (instance == null)
		{
			synchronized (ManagerLevel.class)
			{
				if (instance == null)
				{
					instance = new ManagerLevel();
				}
			}
		}
		return instance;
	}

	void initialize(String newLevel, Rect newField)
	{
		level = newLevel;
		gameField = newField;
		countOfBlocks = 0;
		load();
	}

	void load()
	{
		int[][] grid = LevelData.getLevel(level);
		gridHeight = grid.length;
		gridWidth = grid[0].length;

		int gridPadding = gameField.right / gridWidth * 2;
		blockSize = (gameField.right - gridPadding * 2) / gridWidth;
		gridField = new Rect(gridPadding, gameField.top + gridPadding,
				gameField.right - gridPadding,
				gameField.top + gridPadding + gridHeight * blockSize);

		blocks = new GOBlock[gridHeight][gridWidth];
		for (int i = 0; i < gridHeight; i++)
		{
			for (int j = 0; j < gridWidth; j++)
			{
				if (grid[i][j] != 0)
				{
					countOfBlocks++;
				}
				blocks[i][j] = new GOBlock(grid[i][j],
						new Rect(gridField.left + j * blockSize, gridField.top + i * blockSize,
								gridField.left + (j + 1) * blockSize, gridField.top + (i + 1) * blockSize));
			}
		}

		racket = new GORacket(gameField.right, gameField.bottom);
		ball = new GOBall(gameField.right, gameField.top, gameField.bottom);
	}

	void update(ManagerGame _gm, ManagerSound _sm)
	{
		if (!_gm.isWin() && !_gm.isLoose())
		{
			//Racket movement phase
			if (racket.getState() == GOStateHorizontal.LEFT && racket.getRect().left >= 0)
			{
				racket.moveLeft();
			}
			if (racket.getState() == GOStateHorizontal.RIGHT && racket.getRect().right <= gameField.right)
			{
				racket.moveRight();
			}
			//Ball movement phase
			if (ball.getStateX() == GOStateHorizontal.LEFT && ball.getRect().left >= 0)
			{
				ball.moveLeft();
			}
			else if (ball.getStateX() == GOStateHorizontal.RIGHT && ball.getRect().right <= gameField.right)
			{
				ball.moveRight();
			}
			else
			{
				ball.switchDirectionX();
			}
			if (ball.getStateY() == GOStateVertical.UP && ball.getRect().top >= gameField.top)
			{
				ball.moveUp();
			}
			else
			{
				if (ball.getStateY() == GOStateVertical.DOWN)
				{
					if (ball.getRect().bottom <= gameField.bottom)
					{
						ball.moveDown();
					}
					else
					{
						_gm.takeLive();
						ball.switchDirectionY();
					}
				}
				else
				{
					ball.switchDirectionY();
				}
			}
			if (ball.isBoosted())
			{
				ball.slowdown();
			}
			//Racket/Ball intersection check
			if (Rect.intersects(racket.getRect(), ball.getRect()))
			{
				if (ball.getStateY() == GOStateVertical.DOWN)
				{
					ball.switchDirectionY();
					if (racket.getState() != GOStateHorizontal.STOP)
					{
						ball.setBoost(racket.getSpeed() * 2);
						if (ball.getStateX() == racket.getState())
						{
							ball.switchDirectionX();
						}
						_sm.playSound("hit_racket_1");
					}
					else
					{
						_sm.playSound("hit_racket_2");
					}
				}
			}
			if (Rect.intersects(ball.getRect(), gridField))
			{
				for (int i = 0; i < gridHeight; i++)
				{
					for (int j = 0; j < gridWidth; j++)
					{
						if (Rect.intersects(ball.getRect(), blocks[i][j].getRect()) &&
								!blocks[i][j].getContent().equals("none"))
						{
							countOfBlocks--;
							_gm.updateScore(1);
							blocks[i][j].deleteBlock();
							ball.switchDirectionX();
							ball.switchDirectionY();
							_sm.playSound("hit_wall");
							break;
						}
					}
				}
			}
			if (_gm.getScore() > _gm.getBestScore())
			{
				_gm.setBestScore(_gm.getScore());
				ActivityMain.editor.putLong("HiScore", _gm.getScore());
				ActivityMain.editor.commit();
			}
			if (countOfBlocks == 0)
			{
				_sm.playSound("win");
				_gm.setWin(true);
			}
			if (_gm.isLoose())
			{
				_sm.playSound("loose");
				return;
			}
		}
	}

	public int getGridWidth()
	{
		return gridWidth;
	}

	public int getGridHeight()
	{
		return gridHeight;
	}

	public GOBlock getBlock(int y, int x)
	{
		return blocks[y][x];
	}

	GORacket getRacket()
	{
		return racket;
	}

	GOBall getBall()
	{
		return ball;
	}

	public Rect getGameField()
	{
		return gameField;
	}
}
