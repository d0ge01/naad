package it.salvatorecriscione.naad.paola;

import it.salvatorecriscione.naad.debug.Debug;
import it.salvatorecriscione.naad.jiny.Dbmg;
import it.salvatorecriscione.naad.util.net.PacketIP;

import java.util.Vector;

public class Paola {
	private Vector<String> mitt = new Vector<String>();
	private Vector<String> dest = new Vector<String>();
	private Vector<String> mittBuff = new Vector<String>();
	private Vector<String> destBuff = new Vector<String>();
	
	
	private Dbmg db;
	private boolean enableLocalProtection = true;
	
	public Paola(Dbmg culo, boolean enablelocal)
	{
		db = culo;
		enableLocalProtection = enablelocal;
	}
	
	public Paola(Dbmg culo)
	{
		db = culo;
	}
	
	public void collect(Vector<PacketIP> l)
	{
		Debug.print("READY TO PARSE 100 PACKET");
		for ( int i = 0 ; i < l.size() ; i++ )
		{
			PacketIP p = l.get(i);
			if ( mitt.indexOf(p.MittIP()) == -1 )
				if (!localIp(p.MittIP()))
				{
					mitt.add(p.MittIP());
					mittBuff.add(p.MittIP());
				}
			if ( dest.indexOf(p.DestIP()) == -1 ) 
				if (!localIp(p.MittIP()))
				{
					dest.add(p.DestIP());
					destBuff.add(p.DestIP());
				}
		}
		
		Debug.print("Mittenti:");
		
		String buff = "";
		int tot = mittBuff.size();
		
		for ( int i = 0 ; i < tot; i++)
		{
			Debug.print("" + i + "- " + mittBuff.get(i));
			buff = buff + "(NULL, '"+ mittBuff.get(i) + "')";
			buff = i != tot - 1 ? buff + "," : buff;
		}
		db.executeQuery("INSERT INTO mittenti VALUES " + buff + ";");
		Debug.print("Destinatari:");
		
		tot = destBuff.size();
		buff = "";
		for ( int i = 0 ; i < tot; i++)
		{
			Debug.print("" + i + "- " + destBuff.get(i));
			buff = buff + "(NULL, '"+ destBuff.get(i) + "')";
			buff = i != tot - 1 ? buff + "," : buff;
		}
		db.executeQuery("INSERT INTO destinatari VALUES " + buff + ";");
		Debug.print("Inserite le informazioni nel db");
		mittBuff.clear();
		destBuff.clear();
	}
	
	private boolean localIp(String ip)
	{
		if ( !enableLocalProtection )
			return false;
		if ( ip.contains("192.168."))
			return true;
		if ( ip.contains("10.10.10"))
			return true;
		return false;
	}
}
