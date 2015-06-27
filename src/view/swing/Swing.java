package ch.zhaw.mas8i.pendlersupport.view.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ch.zhaw.mas8i.pendlersupport.controller.ConfigurationHandler;
import ch.zhaw.mas8i.pendlersupport.controller.TransportData;
import ch.zhaw.mas8i.pendlersupport.view.ConnectionInfo;
import ch.zhaw.mas8i.pendlersupport.view.Menu;
import ch.zhaw.mas8i.pendlersupport.view.Messages;
import ch.zhaw.mas8i.pendlersupport.view.TrafficLight;
import ch.zhaw.mas8i.pendlersupport.view.UserInterface;

/**
 * That's the implementation of the user interface with Swing (and AWT).
 */
public class Swing extends UserInterface {
	private final JFrame frame;
	private final JPanel trafficLight;
	private final JLabel messages;
	private final JLabel connectionInfo;
	private final Menu menu;
	private ConfigurationHandler configHandler;
	
	/**
	 * Constructor initiates UI components and builds the whole
	 * UI (without displaying it).
	 * 
	 * @param  data TransportData object
	 * @param  configHandler ConfigurationHandler object
	 */
	public Swing(TransportData data, ConfigurationHandler configHandler) {
		super(data, configHandler);
		this.frame = new JFrame("Pendlersupport");
		this.trafficLight = new SwingTrafficLight();
		this.messages = new SwingMessages();
		this.connectionInfo = new SwingConnectionInfo();
		this.menu = new SwingMenu(data, configHandler);
		this.configHandler = configHandler;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(createMenuBar());
		frame.getContentPane().add(trafficLight, BorderLayout.CENTER);
		frame.getContentPane().add(messages, BorderLayout.SOUTH);
		frame.setSize(200, 350);
	}
	
	/**
	 * Load the configuration and display the UI
	 */
	@Override
	public void start() {
		if (configHandler.hasSaved()) {
			configHandler.load();
		}
		
	    frame.setVisible(true);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("Connection");
		menuBar.add(fileMenu);
		
		JMenuItem configMenuItem = new JMenuItem("Settings...");
		fileMenu.add(configMenuItem);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		configMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    	new Thread(menu).start();
			}
		});
	    
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    	System.exit(0);
			}
		});
	    
		return menuBar;
	}

	@Override
	protected ConnectionInfo getConnectionInfo() {
		return (ConnectionInfo) connectionInfo;
	}

	@Override
	protected Messages getMessages() {
		return (Messages) messages;
	}

	@Override
	protected TrafficLight getTrafficLight() {
		return (TrafficLight) trafficLight;
	}

}
