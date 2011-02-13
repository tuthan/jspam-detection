/*
 * GuiJnetPcapApp.java
 */

package guijnetpcap;

import javax.swing.table.DefaultTableModel;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;


/**
 * The main class of the application.
 */
public class GuiJnetPcapApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new GuiJnetPcapView(this));

    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of GuiJnetPcapApp
     */
    public static GuiJnetPcapApp getApplication() {
        return Application.getInstance(GuiJnetPcapApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(GuiJnetPcapApp.class, args);

    }

}
