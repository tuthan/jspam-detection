/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jspamdetection;

/**
 *
 * @author jspamdetection team
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.net.NetworkInterface;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.lang.*;
import java.io.InputStreamReader;
 import org.jnetpcap.Pcap;
 import org.jnetpcap.PcapIf;
 import org.jnetpcap.packet.PcapPacket;
 import org.jnetpcap.packet.PcapPacketHandler;
 import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.JBufferHandler;
import org.jnetpcap.JCaptureHeader;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.tcpip.Tcp;
import sun.nio.ch.SocketOpts;
import sun.nio.ch.SocketOpts.IP.TCP;

/**
 *
 * @author LVT
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        //tao 1 list de chua cac card mang
        List<PcapIf> alldevice= new ArrayList<PcapIf>();
      //    List<String> ans = new ArrayList<String>();
        StringBuilder err= new StringBuilder();
        //goi phuong thuc findalldevs de tim cac card mang
        int r= Pcap.findAllDevs(alldevice, err);
        //System.out.println(r);
        if(r==Pcap.NOT_OK|| alldevice.isEmpty())
        {
            System.err.printf("ko co device nao",err.toString());
            return;
        }
       /*  for (PcapIf device : alldevice)
         {
             System.out.println(device.getName());
          System.out.println(device.getDescription());

         }*/
        for(int i=0;i<alldevice.size();i++)
        {
             System.out.println("#" + i + ": " + alldevice.get(i).getDescription());

        }
       //  String l = System.in.readline.trim();
        InputStreamReader isr= new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int I=0;
        try{
            String l=br.readLine();
            I=new Integer(l).intValue();
        }catch(Exception e)
        {
            System.out.println(e);
        }

         //Integer i = Integer.valueOf();
         PcapIf netInterface = alldevice.get(I);
         System.out.println(alldevice.get(I).getDescription());
         //open nic  to capture packet
         int snaplen =64*2048;
         int promicious= Pcap.MODE_PROMISCUOUS;
         int timeout= 10*1000;
         Pcap pcap= Pcap.openLive(netInterface.getName(), snaplen, promicious, timeout, err);
         if(pcap==null)
         {
            System.out.println("khong the mo card mang");
         }
         // vong loop bat goi tin



   JBufferHandler<String> printSummaryHandler = new JBufferHandler<String>()
   {

	public void nextPacket(PcapHeader header, JBuffer buffer, String user)
        {
	Timestamp timestamp =  new Timestamp(header.timestampInMillis());
        final PcapPacket packet = new PcapPacket(JMemory.POINTER);
	 packet.peer(buffer);
         packet.getCaptureHeader().peerTo(header, 0);
         packet.scan(Ethernet.ID);
         Tcp tcp = new Tcp();
         if (packet.hasHeader(tcp))
         {
             System.out.println(packet.getCaptureHeader());
         }
        // System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
	//			    timestamp.toString(), // timestamp to 1 ms accuracy
	//			    header.caplen(), // Length actually captured
	//			    header.wirelen(), // Original length of the packet
	//			    user // User supplied object
	//			    );
			}
		};
                // dat vong loop la 10 packet
		pcap.loop(10, printSummaryHandler, "jNetPcap rocks!");
                // dong pcap
		pcap.close();
}
}
