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
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.lang.*;
 import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
 import org.jnetpcap.Pcap;
 import org.jnetpcap.PcapIf;
 import org.jnetpcap.packet.PcapPacket;
 import org.jnetpcap.packet.PcapPacketHandler;
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
         int snaplen =2048;
         int promicious= Pcap.MODE_PROMISCUOUS;
         int timeout= 60*1000;
         Pcap pcap= Pcap.openLive(netInterface.getName(), snaplen, promicious, timeout, err);


    }

}
