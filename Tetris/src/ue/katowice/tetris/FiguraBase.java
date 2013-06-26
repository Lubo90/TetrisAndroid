package ue.katowice.tetris;

public class FiguraBase {
	Kwadrat[] _figura = new Kwadrat[4];

	protected FiguraBase() {
	}
	
	protected FiguraBase(FiguraBase figura)
	{
		for(int i = 0; i < 4; i++)
			_figura[i] = new Kwadrat(figura.GetKwadrat(i));
	}
	
	public Kwadrat GetKwadrat(int i)
	{
		if(i < 0 || i > 4)
			return null;
		return _figura[i];
	}
	
	public void PrzesunDol()
	{
		for(int i = 0; i < _figura.length; i++)
		{
			_figura[i]._y++;
		}
	}
	public void PrzesunLewo(boolean[][] kratkaZajeta)
	{
		for(int i = 0; i < _figura.length; i++)
		{
			Kwadrat figura = _figura[i];
			if(figura.GetX()+1 <= 9 && figura.GetX()-1 >0)
				if(kratkaZajeta[figura.GetY()][figura.GetX()-1] == true)
					return;
		}
		
		for(int i = 0; i < _figura.length; i++)
			if((_figura[i]._x - 1) < 0)
				return;
		
		for(int i = 0; i < _figura.length; i++)
		{
			_figura[i]._x--;
		}
	}
	public void PrzesunPrawo(boolean[][] kratkaZajeta)
	{
		for(int i = 0; i < _figura.length; i++)
		{
			Kwadrat figura = _figura[i];
			if(figura.GetX()+1 <= 9 && figura.GetX()-1 >0)
				if(kratkaZajeta[figura.GetY()][figura.GetX()+1] == true)
					return;
		}
		
		for(int i = 0; i < _figura.length; i++)
			if((_figura[i]._x + 1) > 9)
				return;
		
		for(int i = 0; i < _figura.length; i++)
		{
			_figura[i]._x++;
    	}
	}
	/*public void PrzesunGora()
	{
		for(int i = 0; i < _figura.length; i++)
    	{
    		_figura[i]._y--;
    	}
	}*/
}
