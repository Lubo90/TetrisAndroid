package ue.katowice.tetris;

public class FiguraI extends FiguraBase {
    public FiguraI()
    {
        _figura[0] = new Kwadrat(0, 4);
        _figura[1] = new Kwadrat(1, 4);
        _figura[2] = new Kwadrat(2, 4);
        _figura[3] = new Kwadrat(3, 4);
    }
}
