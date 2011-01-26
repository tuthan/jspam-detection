
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
import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.lang.*;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
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
        int snaplen =64*2048;
        int promicious= Pcap.MODE_NON_PROMISCUOUS ;
        int timeout= 10*1000;
        boolean capturing = true;
        PcapBpfProgram filter = new PcapBpfProgram();
        String expression = "tcp port 80 or port 25";
        int optimize = 0;         // 0 = false
        int netmask = 0xFFFFFF00; // 255.255.255.0\
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
         //tao dispatcher
         while(capturing)
         {
             Pcap pcap= Pcap.openLive(netInterface.getName(), snaplen, promicious, timeout, err);

         // compile filter

            if(pcap==null)
            {
                System.out.println("khong the mo card mang");
            }
            if (pcap.compile(filter, expression, optimize, netmask) != Pcap.OK)
            {
                System.out.println(pcap.getErr());
                return;
            }
            //gan filter vao pcap
            pcap.setFilter(filter);
            // vong loop bat goi tin

            JBufferHandler<String> printSummaryHandler = new JBufferHandler<String>()
            {
                Tcp tcp = new Tcp();
                InetAddress dest_ip;
                InetAddress sour_ip;
                final PcapPacket packet = new PcapPacket(JMemory.POINTER);
                public void nextPacket(PcapHeader header, JBuffer buffer, String user)
                {
                    Timestamp timestamp =  new Timestamp(header.timestampInMillis());
                    packet.peer(buffer);
                    packet.getCaptureHeader().peerTo(header, 0);
                    packet.scan(Ethernet.ID);
                    packet.getHeader(tcp);
                    if (packet.hasHeader(tcp))
                    {
                      String str =""+tcp.destination();
                     System.out.printf("tcp.dst_port=%d%n", tcp.destination());
                     System.out.printf("tcp.src_port=%d%n", tcp.source());
                     System.out.printf("tcp.ack=%x%n", tcp.ack());
                    }
                    Ip4 ip = new Ip4();
                    packet.getHeader(ip);
                    if (packet.hasHeader(ip) )
                    {
                        try
                        {
                        dest_ip = InetAddress.getByAddress(ip.destination());
                        sour_ip = InetAddress.getByAddress(ip.source());
                        System.out.println("Dia chi dich: " + dest_ip.toString());
                        System.out.println("Dia chi source: " + sour_ip.toString());
                        System.out.println("thoi gian: " + timestamp.toString());
                        //    System.out.println(packet.getState().toDebugString());
                    } catch (UnknownHostException ex)
                    {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                  
                }
            };
              // dat vong loop la 10 packet
        pcap.loop(10, printSummaryHandler, "jNetPcap rocks!");
        //tao file luu .pcap
        StringBuilder errbuf = new StringBuilder();
        String fname = "test/test-afs.pcap";
        Pcap pcap1 = Pcap.openOffline(fname, errbuf);
        String ofile = "tmp-capture-file.cap";
        PcapDumper dumper = pcap1.dumpOpen(ofile); // output file
        JBufferHandler<PcapDumper> dumpHandler = new JBufferHandler<PcapDumper>() {

          public void nextPacket(PcapHeader header, JBuffer buffer, PcapDumper dumper) {

            dumper.dump(header, buffer);
          }
        };
        pcap.loop(10,dumpHandler, dumper); // Special native dumper call to loop

        File file = new File(ofile);
        System.out.printf("%s file has %d bytes in it!\n", ofile, file.length());

        dumper.close();
        //end code tao file luu
        // dong pcap
        pcap.close();
    }
    }
 public static String getHexString(byte[] b)
 {
     String result = "";
       for (int i=0; i < b.length; i++)
       {
        result +=Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
  return result;
}

   }

