package it.salvatorecriscione.naad;

import it.salvatorecriscione.naad.giulia.Intercepter;
import it.salvatorecriscione.naad.jiny.Dbmg;
import it.salvatorecriscione.naad.paola.Paola;

public class Main {
	public static void main(String[] args)
	{
		Dbmg jiny = new Dbmg("root","","naad");
		Paola paola = new Paola(jiny, false);
		Intercepter giulia = new Intercepter(paola);
		
		giulia.run();
	}
}
