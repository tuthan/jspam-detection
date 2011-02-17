/*
 * GuiJnetPcapView.java
 */

package guijnetpcap;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import java.sql.Timestamp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.jnetpcap.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.jnetpcap.packet.format.FormatUtils;
/**
 * The application's main frame.
 */
 
public class GuiJnetPcapView extends FrameView {

    public List<PcapIf> alldevice= new ArrayList<PcapIf>();
    public StringBuilder err= new StringBuilder();
    public int r= Pcap.findAllDevs(alldevice, err);
    public String codeName;
    public String result="";
    public Pcap pcap;
    public boolean capture;
    public GuiJnetPcapView(SingleFrameApplication app) {
        super(app);

        initComponents();

        for(int i=0;i<alldevice.size();i++)
            cbbDevice.addItem(alldevice.get(i).getDescription().toString());

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
               // statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                //statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        //statusAnimationLabel.setIcon(idleIcon);
        //progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                       // statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    //progressBar.setVisible(true);
                    //progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                   // statusAnimationLabel.setIcon(idleIcon);
                    //progressBar.setVisible(false);
                   // progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    //statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                   // progressBar.setVisible(true);
                    //progressBar.setIndeterminate(false);
                    //progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = GuiJnetPcapApp.getApplication().getMainFrame();
            aboutBox = new GuiJnetPcapAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        GuiJnetPcapApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btCapture = new javax.swing.JButton();
        cbbDevice = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        txtDescrip = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaData = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainPanel.setMaximumSize(new java.awt.Dimension(1024, 768));
        mainPanel.setMinimumSize(new java.awt.Dimension(800, 600));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Time", "Ip Source", "PortSource", "Ip Destination", "Port Destination"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setEnabled(false);
        jTable1.setName("jTable1"); // NOI18N
        jTable1.setRowSelectionAllowed(false);
        jScrollPane2.setViewportView(jTable1);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(guijnetpcap.GuiJnetPcapApp.class).getContext().getResourceMap(GuiJnetPcapView.class);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N
        jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
        jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
        jTable1.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title5")); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        btCapture.setText(resourceMap.getString("btCapture.text")); // NOI18N
        btCapture.setName("btCapture"); // NOI18N
        btCapture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCaptureMouseClicked(evt);
            }
        });

        cbbDevice.setName("cbbDevice"); // NOI18N
        cbbDevice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbDeviceItemStateChanged(evt);
            }
        });

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setEnabled(false);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        txtDescrip.setName("txtDescrip"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(229, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDescrip, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                            .addComponent(cbbDevice, 0, 152, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCapture)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(29, 29, 29))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbDevice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(btCapture))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel5.setIcon(resourceMap.getIcon("jLabel5.icon")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        areaData.setColumns(20);
        areaData.setEditable(false);
        areaData.setRows(5);
        areaData.setName("areaData"); // NOI18N
        jScrollPane1.setViewportView(areaData);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(1947, 1947, 1947)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(1559, 1559, 1559)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1559, Short.MAX_VALUE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel1)
                        .addGap(155, 155, 155)
                        .addComponent(jLabel2))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(guijnetpcap.GuiJnetPcapApp.class).getContext().getActionMap(GuiJnetPcapView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void cbbDeviceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbDeviceItemStateChanged
        btCapture.setEnabled(true);
        //get code name of NIC when item changed
        for(int i=0;i<alldevice.size();i++)
            if(alldevice.get(i).getDescription().toString().equals(cbbDevice.getSelectedItem().toString()))
                codeName = alldevice.get(i).getName().toString();
    }//GEN-LAST:event_cbbDeviceItemStateChanged

    private void btCaptureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCaptureMouseClicked
        // TODO add your handling code here:
          btCapture.setEnabled(false);
          jButton2.setEnabled(true);          
          thread_cap.start();
                 
    }//GEN-LAST:event_btCaptureMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        btCapture.setEnabled(true);
        jButton2.setEnabled(false);
        pcap.close();
        thread_cap.stop();
        
    }//GEN-LAST:event_jButton2MouseClicked

    Runnable runable_cap = new Runnable() {

        public void run() {
           int snaplen =64*2048;
        int promicious= Pcap.MODE_NON_PROMISCUOUS ;
        int timeout= 10*1000;
        //end
        //parameter of filter
        PcapBpfProgram filter = new PcapBpfProgram();
        String expression = "tcp port 110 or port 25";
        int optimize = 0;         // 0 = false
        int netmask = 0xFFFFFF00; // 255.255.255.0
        //end



        //open live to capture packet
         pcap= Pcap.openLive(codeName, snaplen, promicious, timeout, err);
         //edit filter
         if(!txtDescrip.getText().equals(""))
         {
            expression=expression+" or host "+txtDescrip.getText();
         }
  
        if (pcap.compile(filter, expression, optimize, netmask) != Pcap.OK)
            {

                return;
           }
          //add filter in pcap
          pcap.setFilter(filter);
          //Create a loop
          PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>()
          {
               Tcp tcp = new Tcp();
             Udp udp = new Udp();
            Ip4 ip = new Ip4();
            String ip_address = null;
            String des;
            String src;
        
             ArrayList<PcapPacket> listPacket = new ArrayList<PcapPacket>();
           public void nextPacket(PcapPacket packet, String user)
            {
                //add packet into arraylist

                this.listPacket.add(packet);

                Object[] col={"#",
                        "Time",
                        "Ip Source",
                        "Port Source",
                        "Ip Destination",
                        "Port Destination"

                       };
                Object[][] obj = new Object[this.listPacket.size()][col.length];

                //Create loop to add value into Jtable
                for(int i=0;i<this.listPacket.size();i++)
                {
                    PcapPacket Packet= this.listPacket.get(i);

                    Timestamp timestamp =  new Timestamp(Packet.getCaptureHeader().timestampInMillis());
                    obj[i][0]=Packet.getFrameNumber();
                    obj[i][1]=timestamp.toString();
                    Packet.getHeader(ip);
                    if(Packet.hasHeader(ip))
                    {
                        obj[i][2]=FormatUtils.ip(ip.source());
                        obj[i][4]=FormatUtils.ip(ip.destination());

                    }
                       Packet.getHeader(tcp);
                        Packet.getHeader(udp);
                    if (Packet.hasHeader(tcp))
                    {
                        obj[i][3] = new Integer(tcp.destination()).toString();
                        obj[i][5] = new Integer(tcp.source()).toString();
                    }
                    else if (Packet.hasHeader(udp))
                    {
                        obj[i][3] = new Integer(udp.destination()).toString();
                        obj[i][5] = new Integer(udp.source()).toString();
                    }
              

                }
               DefaultTableModel model= new DefaultTableModel(obj, col);

                jTable1.setModel(model);

                packet.getHeader(ip);
                if (packet.hasHeader(ip) )
                {
                   ip_address = FormatUtils.ip(ip.source())+"||"+FormatUtils.ip(ip.destination());
                }
                packet.getHeader(tcp);
                if (packet.hasHeader(tcp))
                {
                    des = new Integer(tcp.destination()).toString();
                    src = new Integer(tcp.source()).toString();
                }
                int length =  ip.length() - ip.hlen() * 4 - tcp.hlen() * 4;
                byte [] byte_array = packet.getByteArray(0, length);
                String message = new String(byte_array);
                 result +=packet.getFrameNumber()+"||"+ip_address+"||"+src+"||"+des+ "||"+ message +"\n";
                // System.out.println(result);
                // System.out.println(message);
                 areaData.setText(result);
                 //areaData.updateUI();
                 //areaData.validate();
            }            
        };

              // dat vong loop la 10 packet
        pcap.loop(Pcap.LOOP_INFINATE, jpacketHandler, "jSpam Mail Detector!");
        //pcap.close();
        }
    };

    Thread thread_cap = new Thread(runable_cap); //Tao thread captrue goi tin
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaData;
    private javax.swing.JButton btCapture;
    private javax.swing.JComboBox cbbDevice;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField txtDescrip;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
