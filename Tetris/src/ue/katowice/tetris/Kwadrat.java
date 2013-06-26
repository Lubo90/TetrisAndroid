package ue.katowice.tetris;

public class Kwadrat {
	int _x, _y;
	
	public Kwadrat(int y, int x)
	{
		_x = x;
		_y = y;
	}
	
	public Kwadrat(Kwadrat kwadrat) {
		_x = kwadrat.GetX();
		_y = kwadrat.GetY();
	}

	public int GetX()
	{
		return _x;
	}
	public int GetY()
	{
		return _y;
	}
}
