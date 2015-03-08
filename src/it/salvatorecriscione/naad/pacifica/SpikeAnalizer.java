package it.salvatorecriscione.naad.pacifica;

public class SpikeAnalizer {
	private double toll;
	
	public SpikeAnalizer(double faultTollerance)
	{
		toll = faultTollerance;
	}
	
	
	public boolean test(double value1, double value2)
	{
		if ( value2 > value1 + toll )
			return true;
		else
			return false;
	}
}
