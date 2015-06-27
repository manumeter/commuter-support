package ch.zhaw.mas8i.pendlersupport.view.swing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.rakudave.suggest.JSuggestField;
import ch.zhaw.mas8i.pendlersupport.Log;
import ch.zhaw.mas8i.pendlersupport.controller.ConfigurationHandler;
import ch.zhaw.mas8i.pendlersupport.controller.ConnectionTracker;
import ch.zhaw.mas8i.pendlersupport.controller.TransportData;
import ch.zhaw.mas8i.pendlersupport.model.Configuration;
import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.model.Location;
import ch.zhaw.mas8i.pendlersupport.model.TransportDataException;
import ch.zhaw.mas8i.pendlersupport.view.Menu;

/**
 * That's the implementation of Menu for Swing.
 * We're using JSuggestField of ch.rakudave, which is a bit buggy.
 * We've fixed the worst bugs but there may be more!
 */
public class SwingMenu implements Menu {
	private final JFrame frame;
	private final TransportData data;
	private final ConfigurationHandler configHandler;
	private JSuggestField startField;
	private JSuggestField destinationField;
	private JSuggestField viaField;
	private JTextField offsetWalkField;
	private JTextField offsetSprintField;
	private JTextField maxWaitField;
	
	/**
	 * Constructor needs TransportData and ConfigurationHandler.
	 * It already creates the JFrame without displaying it.
	 * 
	 * @param data TransportData object
	 * @param configHandler ConfigurationHandler object
	 */
	public SwingMenu(TransportData data, ConfigurationHandler configHandler) {
		this.frame = new JFrame("Settings");
		this.data = data;
		this.configHandler = configHandler;
		
		frame.setContentPane(createPane());
        frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	private JPanel createPane() {
		JPanel pane = new JPanel();
		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);

		// Create labels, fields and button
		
		JLabel startLabel = new JLabel("Start:", SwingConstants.RIGHT);
		startField = new JSuggestField(frame, new Vector<String>());
		startField.getDocument().addDocumentListener(new LocationSuggestor(startField));
		
		JLabel destinationLabel = new JLabel("Destination:", SwingConstants.RIGHT);
		destinationField = new JSuggestField(frame, new Vector<String>());
		destinationField.getDocument().addDocumentListener(new LocationSuggestor(destinationField));
		
		JLabel viaLabel = new JLabel("Via (optional):", SwingConstants.RIGHT);
		viaField = new JSuggestField(frame, new Vector<String>());
		viaField.getDocument().addDocumentListener(new LocationSuggestor(viaField));
		
		JLabel offsetWalkLabel = new JLabel("Offset normal:", SwingConstants.RIGHT);
		JLabel offsetWalkInfoLabel = new JLabel("The time in minutes you normally need to get to the start location.");
		offsetWalkInfoLabel.setFont(new Font("Info", Font.PLAIN, 11));
		offsetWalkField = new JTextField();
		offsetWalkField.setHorizontalAlignment(JTextField.RIGHT);
		
		JLabel offsetSprintLabel = new JLabel("Offset hurry:", SwingConstants.RIGHT);
		JLabel offsetSprintInfoLabel = new JLabel("The time in minutes you need if you hurry.");
		offsetSprintInfoLabel.setFont(new Font("Info", Font.PLAIN, 11));
		offsetSprintField = new JTextField();
		offsetSprintField.setHorizontalAlignment(JTextField.RIGHT);
		
		JLabel maxWaitLabel = new JLabel("Max. wait:", SwingConstants.RIGHT);
		JLabel maxWaitInfoLabel = new JLabel("The time in minutes you are willing to wait at the start location.");
		maxWaitInfoLabel.setFont(new Font("Info", Font.PLAIN, 11));
		maxWaitField = new JTextField();
		maxWaitField.setHorizontalAlignment(JTextField.RIGHT);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		
		// Define layout
		
		int col1Width = 120;
		int colGap = 3;
		int rowGap = 3;
		int fieldHeight = 24;
		
		layout.setHorizontalGroup(layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(colGap)
				.addComponent(startLabel, col1Width, col1Width, col1Width)
				.addGap(colGap)
				.addComponent(startField, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(colGap)
			)
			.addGroup(layout.createSequentialGroup()
				.addGap(colGap)
				.addComponent(destinationLabel, col1Width, col1Width, col1Width)
				.addGap(colGap)
				.addComponent(destinationField, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(colGap)
			)
			.addGroup(layout.createSequentialGroup()
				.addGap(colGap)
				.addComponent(viaLabel, col1Width, col1Width, col1Width)
				.addGap(colGap)
				.addComponent(viaField, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(colGap)
			)
			.addGroup(layout.createSequentialGroup()
				.addGap(colGap)
				.addComponent(offsetWalkLabel, col1Width, col1Width, col1Width)
				.addGap(colGap)
				.addComponent(offsetWalkField, 40, 40, 40)
				.addGap(colGap)
				.addComponent(offsetWalkInfoLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(colGap)
			)
			.addGroup(layout.createSequentialGroup()
				.addGap(colGap)
				.addComponent(offsetSprintLabel, col1Width, col1Width, col1Width)
				.addGap(colGap)
				.addComponent(offsetSprintField, 40, 40, 40)
				.addGap(colGap)
				.addComponent(offsetSprintInfoLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(colGap)
			)
			.addGroup(layout.createSequentialGroup()
				.addGap(colGap)
				.addComponent(maxWaitLabel, col1Width, col1Width, col1Width)
				.addGap(colGap)
				.addComponent(maxWaitField, 40, 40, 40)
				.addGap(colGap)
				.addComponent(maxWaitInfoLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(colGap)
			)
			.addGroup(layout.createSequentialGroup()
				.addGap(colGap + col1Width + colGap)
				.addComponent(saveButton, 120, 120, 120)
			)
		);
		
		layout.setVerticalGroup(layout
			.createSequentialGroup()
			.addGap(rowGap)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(startLabel)
				.addComponent(startField, fieldHeight, fieldHeight, fieldHeight)
			)
			.addGap(rowGap)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(destinationLabel)
				.addComponent(destinationField, fieldHeight, fieldHeight, fieldHeight)
			)
			.addGap(rowGap)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(viaLabel)
				.addComponent(viaField, fieldHeight, fieldHeight, fieldHeight)
			)
			.addGap(rowGap)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(offsetWalkLabel)
				.addComponent(offsetWalkField, fieldHeight, fieldHeight, fieldHeight)
				.addComponent(offsetWalkInfoLabel)
			)
			.addGap(rowGap)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(offsetSprintLabel)
				.addComponent(offsetSprintField, fieldHeight, fieldHeight, fieldHeight)
				.addComponent(offsetSprintInfoLabel)
			)
			.addGap(rowGap)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(maxWaitLabel)
				.addComponent(maxWaitField, fieldHeight, fieldHeight, fieldHeight)
				.addComponent(maxWaitInfoLabel)
			)
			.addGap(rowGap)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(saveButton)
			)
			.addGap(rowGap)
		);
		
		return pane;
	}
	
	private void save() {
		
		// Validate input
		Location start = findLocation(startField.getText());
		Location destination = findLocation(destinationField.getText());
		Location via = null;
		if (!viaField.getText().isEmpty()) {
			via = findLocation(viaField.getText());
		}
		int offsetWalk = findInteger(offsetWalkField.getText());
		int offsetSprint = findInteger(offsetSprintField.getText());
		int maxWait = findInteger(maxWaitField.getText());
		
		Connection connection = new Connection(start, destination, via);
		Configuration config = new Configuration(connection, offsetWalk, offsetSprint, maxWait);
		
		// Update config and rapidly initiate a new pass of ConnectionTracker
		configHandler.setConfiguration(config);
		new Thread(ConnectionTracker.getInstance(data, configHandler)).start();
		
		frame.setVisible(false);
		configHandler.save();
	}
	
	private Location findLocation(String string) {
		List<Location> locations = new ArrayList<>();
		for(int i = 0; i <= 3; i++) {
			try {
				locations = data.getPossibleLocationList(string);
				break;
			}
			catch (TransportDataException e) {
				// Tries 3 times (unstable Internet connection), after that, returns given string as location
				Log.warning(e.getMessage());
			}
		}
		
		if (locations.size() > 0) {
			return locations.get(0);
		}
		else {
			return new Location(string);
		}
	}
	
	private int findInteger(String string) {
		int integer = 0;
		try {
			integer = Integer.parseInt(string);
		}
		catch (NumberFormatException e) {
			// Returns 0 if given string is not a number
			Log.warning(e.getMessage());
		}
		return integer;
	}
	
	/**
	 * Sets the content of all TextFields to the value in
	 * the current configuration and displays the JFrame.
	 */
	@Override
	public void run() {
		if(configHandler.hasLoaded()) {
			startField.setText(configHandler.getConfiguration().getConnection().getStart().getName());
			destinationField.setText(configHandler.getConfiguration().getConnection().getDestination().getName());
			Location via = configHandler.getConfiguration().getConnection().getVia();
			if (via == null) {
				viaField.setText("");
			}
			else {
				viaField.setText(via.getName());
			}
			offsetWalkField.setText(Integer.toString(configHandler.getConfiguration().getOffsetWalk()));
			offsetSprintField.setText(Integer.toString(configHandler.getConfiguration().getOffsetSprint()));
			maxWaitField.setText(Integer.toString(configHandler.getConfiguration().getMaxWait()));
		}
		
		frame.setVisible(true);
	    frame.toFront();
	    frame.repaint();
	}
	
	private class LocationSuggestor implements DocumentListener {
		private final JSuggestField field;
		
		public LocationSuggestor(JSuggestField field) {
			this.field = field;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// Do nothing
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// Start in new thread to avoid hanging up the UI
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (field.getText().length() > 3) {
						List<Location> locations = null;
						try {
							locations = data.getPossibleLocationList(field.getText());
						} catch (TransportDataException e1) {
							Log.error(e1.getMessage());
						}
						if (locations.size() > 1 && !locations.get(0).getName().equals(field.getText())) {
							Vector<String> suggestions = new Vector<>();
							for (Location location : locations) {
								suggestions.add(location.getName());
							}
							field.setSuggestData(suggestions);
							field.showSuggest();
							// Kind of a race condition is possible here, but also wanted!
						}
					}
				}
			}).start();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if (field.getText().length() == 0) {
				field.setSuggestData(new Vector<String>());
				field.hideSuggest();
			}
		}
	}
}
