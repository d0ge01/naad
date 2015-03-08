package it.salvatorecriscione.naad.giulia;

import it.salvatorecriscione.naad.debug.Debug;
import it.salvatorecriscione.naad.paola.Paola;
import it.salvatorecriscione.naad.util.net.PacketIP;

import java.util.Date;
import java.util.Vector;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;

public class Intercepter extends Thread
{
	private Paola al;
	
	public Intercepter(Paola lvup)
	{
		al = lvup;
	}
	
	public void run() 
	{
		Giulia intercepter = new Giulia();
		StringBuilder errbuf = new StringBuilder();
		
		PcapIf device = (PcapIf) intercepter.getInterface().get(0);
		
		Pcap pcap = intercepter.getPcap(device);
		
		if ( pcap == null )
		{
			it.salvatorecriscione.naad.debug.Error.error("Errore aprendo il device per la cattura " + errbuf.toString());
			return;
		}
		
		PcapBpfProgram program = new PcapBpfProgram();
		String query = "port http";
		boolean filter = false;
		int optimize = 0;
		int netmask = 0xFFFFFF00;
		
		if ( pcap.compile(program, query, optimize, netmask) != Pcap.OK)
		{
			it.salvatorecriscione.naad.debug.Error.error(pcap.getErr());
			return;
		}
		
		if (filter && pcap.setFilter(program) != Pcap.OK)
		{
			it.salvatorecriscione.naad.debug.Error.error(pcap.getErr());
			return;
		}
		
		Vector<PacketIP> pack = new Vector<PacketIP>();
		
		PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>()
				{
					public void nextPacket(PcapPacket packet, String user)
					{
						Ip4 ip = new Ip4();
						byte[] dIP = new byte[4], sIP = new byte[4];
						
						if ( packet.hasHeader(ip)) 
						{
							dIP = packet.getHeader(ip).destination();
							sIP = packet.getHeader(ip).source();
							PacketIP p = new PacketIP(dIP, sIP);
							pack.add(p);
							
							if ( pack.size() > 100 )
							{
								al.collect(pack);
								pack.clear();
							}
							//Debug.print(p.toString());
						} else
						{
							//Debug.print("pacchetto a " + new Date(packet.getCaptureHeader().timestampInMillis()));
						}
					}
				};
			
		pcap.loop(0, jpacketHandler, null);
		pcap.close();
	}

}
