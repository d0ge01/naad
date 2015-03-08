package it.salvatorecriscione.naad.util.net;

public class PacketIP extends Packet{
	private byte[] d;
	private byte[] m;
	public PacketIP(byte[] dest, byte[] mitt)
	{
		d = dest;
		m = mitt;
	}
	
	public String DestIP() 
	{
		return org.jnetpcap.packet.format.FormatUtils.ip(d);
	}
	
	public String MittIP() 
	{
		return org.jnetpcap.packet.format.FormatUtils.ip(m);
	}
	
	public String toString()
	{
		return "Ricevuto pacchetto da " +
				org.jnetpcap.packet.format.FormatUtils.ip(d)  + " per " +
				org.jnetpcap.packet.format.FormatUtils.ip(m);
	}
}
