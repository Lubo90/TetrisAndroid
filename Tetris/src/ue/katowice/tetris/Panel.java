package ue.katowice.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Panel {
	int _x, _y;
	boolean _czyWypelnic;
	
	public Panel(int x, int y)
	{
		_x = x;
		_y = y;
		_czyWypelnic = false;
	}
	
	public void SetWypelniony()
	{
		_czyWypelnic = true;
	}
	public void SetPusty()
	{
		_czyWypelnic = false;
	}
	public boolean CzyWypelnic()
	{
		return _czyWypelnic;
	}
	
	public void Narysuj(Canvas canva, int poczX, int poczY, int rozmiar)
	{
		Paint p;
		if(_czyWypelnic)
		{
			p = new Paint();
			p.setColor(Color.BLUE);
			p.setStyle(Style.FILL);
			canva.drawRect(poczX + _x * rozmiar + 1, poczY + _y * rozmiar + 1, poczX + _x * rozmiar + rozmiar - 1, poczY + _y * rozmiar + rozmiar - 1, p);
		}
		
		p = new Paint();
		p.setColor(Color.CYAN);
		canva.drawLine(poczX + _x * rozmiar, poczY + _y * rozmiar, poczX + _x * rozmiar + rozmiar, poczY + _y * rozmiar, p); // góra
		canva.drawLine(poczX + _x * rozmiar, poczY + _y * rozmiar, poczX + _x * rozmiar, poczY + _y * rozmiar + rozmiar, p); // lewa
		canva.drawLine(poczX + _x * rozmiar + rozmiar, poczX + _x * rozmiar, poczX + _x * rozmiar + rozmiar, poczY + _y * rozmiar + rozmiar, p); // prawa
		canva.drawLine(poczX + _x * rozmiar, poczY + _y * rozmiar + rozmiar, poczX + _x * rozmiar + rozmiar, poczY + _y * rozmiar + rozmiar, p); // dó³
	}
}
