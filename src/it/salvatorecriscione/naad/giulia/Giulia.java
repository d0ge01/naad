package it.salvatorecriscione.naad.giulia;

import it.salvatorecriscione.naad.debug.Debug;

import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

public class Giulia {
	private int snaplen = 64 * 1024;
	private int flags = Pcap.MODE_PROMISCUOUS;
	private int timeout = 10 * 1000;
	private List<PcapIf> alldevs = new ArrayList<PcapIf>();
	private StringBuilder errbuf = new StringBuilder();
	
	public List<PcapIf> getInterface()
	{
		
		
		int r = Pcap.findAllDevs(alldevs, errbuf);
		
		if ( r == Pcap.NOT_OK || alldevs.isEmpty())
		{
			System.out.println("non posso leggere la lista dei dispositivi.. errore: " 
					+ errbuf.toString());
			return null;
			
		}
		
		System.out.println("Schede di rete trovate:\n");
		int i = 0;
		for ( PcapIf device : alldevs )
		{
			String desc = "";
			if ( device.getDescription() != null )
				desc = device.getDescription();
			else
				desc = "Nessuna descrizione..";	
			
			Debug.print(" <" + i++ + ">  Name: " +  device.getName() + " Desc:" + desc);
		}
		return alldevs;
	}
	
	public Pcap getPcap(PcapIf device)
	{
		return Pcap.openLive(device.getName(), snaplen, flags, 
				timeout, errbuf);
	}
}
