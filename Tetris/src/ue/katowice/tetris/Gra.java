package ue.katowice.tetris;

import java.util.Random;
import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Gra extends View {
	FiguraBase _aktualnaFigura, _nastepnaFigura;
	Plansza _plansza;
	Kontroler _kontroler;
	Timer _drawTimer;
	Context _c;
	Activity _mainActv;
	int _punktacja;
	
	int _ekranWidth;
	boolean _canMove, _canDraw;
	
	//consts
	final static int RozmiarKostkiPx = 30;
	final static int SzerokoscPlanszy = 10;
	final static int WysokoscPlanszy = 15;
	
	public Gra(Context c, Activity mainActv, Kontroler kontroler, Timer drawTimer, int width)
	{
		super(c);
		_c = c;
		_mainActv = mainActv;
		_ekranWidth = width;
		int poczatekX = (_ekranWidth - RozmiarKostkiPx * (SzerokoscPlanszy)) / 2;
		_plansza = new Plansza(this, poczatekX, 30, SzerokoscPlanszy, WysokoscPlanszy, RozmiarKostkiPx); // 15 x 10
		_kontroler = kontroler;
		_drawTimer = drawTimer;
		_canMove = true;
		_canDraw = true;
		NowaFigura(true);
		this.RysujFigure();
	}
	
	public void NowaFigura(boolean firstFigure)
	{
		Random liczLosowa = new Random();
		if(_aktualnaFigura==null)
		{
			switch(liczLosowa.nextInt( 7 ))
			{
	            case 0:_aktualnaFigura = new FiguraT();break;    
	            case 1:_aktualnaFigura = new FiguraL1();break;  
	            case 2:_aktualnaFigura = new FiguraL2();break;  
	            case 3:_aktualnaFigura = new FiguraS1();break;  
	            case 4:_aktualnaFigura = new FiguraS2();break;  
	            case 5:_aktualnaFigura = new FiguraI();break;  
	            case 6:_aktualnaFigura = new FiguraK();break; 
	            default:_aktualnaFigura = new FiguraK();break; 
	        }
	    }
		else
			_aktualnaFigura = _nastepnaFigura;
		
		switch(liczLosowa.nextInt( 7 ))
		{
	    	case 0:_nastepnaFigura = new FiguraT();break;    
	    	case 1:_nastepnaFigura = new FiguraL1();break;  
	    	case 2:_nastepnaFigura = new FiguraL2();break;  
	    	case 3:_nastepnaFigura = new FiguraS1();break;  
	    	case 4:_nastepnaFigura = new FiguraS2();break;  
	    	case 5:_nastepnaFigura = new FiguraI();break;  
	    	case 6:_nastepnaFigura = new FiguraK();break; 
	    	default:_nastepnaFigura = new FiguraK();break; 
		}
		
		if(firstFigure == false)
			if(this.CzyGoraPlanszy(_aktualnaFigura))
			{
				//koniec gry
				_kontroler.cancel();
				_drawTimer.cancel();
				this.ShowKoniecGryToast();
			}
	}

	public FiguraBase GetAktualnaFigura()
	{
		return _aktualnaFigura;
	}
	
	public void RysujFigure()
	{
		_plansza.RysujFigure(_aktualnaFigura);
	}
	public void ZmazFigure()
	{
		_plansza.ZmazFigure(_aktualnaFigura);
	}
	
	public boolean SprawdzKolizje()
	{
		_plansza.SprawdzZapelnienie();
		
		if(!_plansza.CzyDolPlanszy(_aktualnaFigura))
		{
			if(_plansza.CzyJestKolizja(_aktualnaFigura))
			{
				//_plansza.RysujFigure(_aktualnaFigura);
				_punktacja+=20;
				NowaFigura(false);
				return true;
			}
		}
		else
		{
			_punktacja+=20;
			NowaFigura(false);
			return true;
		}
		return false;
	}
	
	public boolean CzyGoraPlanszy(FiguraBase figura)
	{
		return _plansza.CzyGoraPlanszy(figura);
	}
	
	public void DodajPunkty(int pkt)
	{
		_punktacja += pkt;
	}
	
	public void CanMove(boolean canMove)
	{
		_canMove = canMove;
	}
	public boolean CanMove()
	{
		return _canMove;
	}
	
	public void CanDraw(boolean canDraw)
	{
		_canDraw = canDraw;
	}
	public boolean CanDraw()
	{
		return _canDraw;
	}
	
	private void ShowKoniecGryToast()
	{
		_mainActv.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(_c, "Koniec gry " + _punktacja + " pkt", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int touchAxisX = (int)event.getX();

		if(CanMove())
		{
			CanDraw(false);
			ZmazFigure();
			if(touchAxisX < (_ekranWidth / 2))
			{
				_aktualnaFigura.PrzesunLewo(_plansza.GetKratki());
			}
			else
			{
				_aktualnaFigura.PrzesunPrawo(_plansza.GetKratki());
			}
			RysujFigure();
			CanDraw(true);
		}
		
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO rysowanie
		super.onDraw(canvas);
		this.CanMove(false);
		_plansza.RysujKrawedzie(canvas);
		_plansza.RysujPanele(canvas);
		this.CanMove(true);
	}
}
