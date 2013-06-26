package ue.katowice.tetris;

import java.util.TimerTask;

public class Kontroler extends TimerTask {
	Gra _gra;
	
	public Kontroler()
	{
	}
	
	public void SetGra(Gra gra)
	{
		_gra = gra;
	}
	
	@Override
	public void run() {
		_gra.CanDraw(false);
		_gra.CanMove(false);
		_gra.ZmazFigure();
		if(!_gra.SprawdzKolizje())
		{
			_gra.GetAktualnaFigura().PrzesunDol();
		}
		_gra.RysujFigure();
		_gra.CanDraw(true);
		_gra.CanMove(true);
		_gra.DodajPunkty(2);
	}

}
