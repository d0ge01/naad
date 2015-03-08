package it.salvatorecriscione.naad.debug;

public class Debug {
	private static boolean enabled = true;
	public static void print(String txt)
	{
		if  ( enabled )
			System.out.println("[!] " + txt);
	}
}
