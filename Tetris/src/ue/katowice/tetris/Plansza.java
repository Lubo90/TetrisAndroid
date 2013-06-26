package ue.katowice.tetris;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Plansza {
	boolean[][] _kratkaZajeta;
	Panel[][] _panele;
	int _poczPlanszyX, _poczPlanszyY; // okreœlaj¹ miejsce, w którym zaczyna siê lewy, górny róg planszy
	int _rozmX, _rozmY;
	int _rozmKwadratu;
	Gra _gra;
	
	public Plansza(Gra gra, int poczX, int poczY, int rozmX, int rozmY, int rozmiarKwadratu)
	{
		_poczPlanszyX = poczX;
		_poczPlanszyY = poczY;
		_rozmX = rozmX;
		_rozmY = rozmY;
		_rozmKwadratu = rozmiarKwadratu;
		_gra = gra;
		
		 _kratkaZajeta = new boolean[_rozmY][_rozmX];
		 _panele = new Panel[_rozmY][_rozmX];
		
		for(int i = 0; i < _kratkaZajeta.length; i++)
			for(int j = 0; j < _kratkaZajeta[i].length; j++)
			{
				_kratkaZajeta[i][j] = false;
				_panele[i][j] = new Panel(j, i);
			}
	}
	
	public boolean CzyDolPlanszy(FiguraBase figura)
	{
		for(int i = 0; i < 4; i++)
			if(figura.GetKwadrat(i).GetY() == 14)
			{
				PolozFigure(figura);
				RysujFigure(figura);
				return true;
			}
		return false;
	}
	
	public void SprawdzZapelnienie()
	{
		List<Integer> zapelnioneWiersze = new ArrayList<Integer>();
		
		for(int i = 0; i < _kratkaZajeta.length; i++)
		{
			boolean isPelny = true;
			for(int j = 0; j < _kratkaZajeta[i].length; j++)
			{
				if(_kratkaZajeta[i][j] == false)
				{
					isPelny = false;
					break;
				}
			}
			if(isPelny)
				zapelnioneWiersze.add(i);
		}
		
		for(int i = 0; i < zapelnioneWiersze.size(); i++)
		{
			for(int j = zapelnioneWiersze.get(i); j > 0; j--)
			{
				for(int kol = 0; kol < _kratkaZajeta[j].length; kol++)
				{
					if(_panele[j-1][kol].CzyWypelnic())
						_panele[j][kol].SetWypelniony();
					else
						_panele[j][kol].SetPusty();
					_kratkaZajeta[j][kol] = _kratkaZajeta[j-1][kol];
				}
			}
			
			for(int kol = 0; kol < _kratkaZajeta[0].length; kol++)
			{
				_panele[0][kol].SetPusty();
				_kratkaZajeta[0][kol] = false;
			}
			
			_gra.DodajPunkty(50);
		}
	}
	
	public boolean CzyJestKolizja(FiguraBase figura)
	{
		boolean jestKolizja = false;
		
		for(int i = 0; i < 4; i++)
		{
			if(_kratkaZajeta[figura.GetKwadrat(i).GetY()+1][figura.GetKwadrat(i).GetX()] == true)
			{
				PolozFigure(figura);
				RysujFigure(figura);
				jestKolizja = true;
				break;
			}
		}
		
		return jestKolizja;
	}
	
	public boolean[][] GetKratki()
	{
		return _kratkaZajeta;
	}
	
	public boolean CzyGoraPlanszy(FiguraBase figura)
	{
		for(int i = 0; i < 4; i++)
			if(_kratkaZajeta[figura.GetKwadrat(i).GetY()][figura.GetKwadrat(i).GetX()] == true)
				return true;
		return false;
	}
	
	public void ZmazFigure(FiguraBase figura)
	{
		for(int i = 0; i < 4; i++)
		{
			_panele[figura.GetKwadrat(i).GetY()][figura.GetKwadrat(i).GetX()].SetPusty();
		}
	}
	
	public void RysujFigure(FiguraBase figura)
	{
		for(int i = 0; i < 4; i++)
		{
			_panele[figura.GetKwadrat(i).GetY()][figura.GetKwadrat(i).GetX()].SetWypelniony();
		}
	}
	
	public void PolozFigure(FiguraBase figura)
	{
		this.RysujFigure(figura);
		for(int i = 0; i < 4; i++)
			_kratkaZajeta[figura.GetKwadrat(i).GetY()][figura.GetKwadrat(i).GetX()] = true;
	}
	
	public void RysujKrawedzie(Canvas canva)
	{
		Paint p = new Paint();
		p.setColor(Color.RED);
		canva.drawLine(_poczPlanszyX, _poczPlanszyY-1, _poczPlanszyX + (_rozmX*_rozmKwadratu), _poczPlanszyY-1, p); // góra
		canva.drawLine(_poczPlanszyX-1, _poczPlanszyY, _poczPlanszyX-1, _poczPlanszyY + (_rozmY*_rozmKwadratu), p); // lewa
		canva.drawLine(_poczPlanszyX+1 + (_rozmX*_rozmKwadratu), _poczPlanszyY, _poczPlanszyX + (_rozmX*_rozmKwadratu)+1, _poczPlanszyY + (_rozmY*_rozmKwadratu), p); // prawa
		canva.drawLine(_poczPlanszyX, _poczPlanszyY + (_rozmY*_rozmKwadratu)+1, _poczPlanszyX + (_rozmX*_rozmKwadratu), _poczPlanszyY + (_rozmY*_rozmKwadratu)+1, p); // dó³
	}
	
	public void RysujPanele(Canvas canva)
	{
		for(int i = 0; i < _panele.length; i++)
			for(int j = 0; j < _panele[i].length; j++)
				_panele[i][j].Narysuj(canva, _poczPlanszyX, _poczPlanszyY, _rozmKwadratu);
	}
}
