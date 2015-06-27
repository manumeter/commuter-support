package ch.zhaw.mas8i.pendlersupport.view.swing;

import javax.swing.JLabel;

import ch.zhaw.mas8i.pendlersupport.Log;
import ch.zhaw.mas8i.pendlersupport.controller.ConnectionTracker;
import ch.zhaw.mas8i.pendlersupport.model.LoadImpossibleException;
import ch.zhaw.mas8i.pendlersupport.model.NoConfigurationException;
import ch.zhaw.mas8i.pendlersupport.model.NoConnectionException;
import ch.zhaw.mas8i.pendlersupport.model.SaveImpossibleException;
import ch.zhaw.mas8i.pendlersupport.model.TransportDataException;
import ch.zhaw.mas8i.pendlersupport.view.Messages;

/**
 * That's the implementation of Messages as a JLabel for Swing.
 */
public class SwingMessages extends JLabel implements Messages {
	private static final long serialVersionUID = 1L;
	private static final Object LOCK = new Object();

	/**
	 * Constructor set the text to " " to force the UI's
	 * LayoutManager to save the space for this JLabel.
	 */
	public SwingMessages() {
		this.setText(" ");
	}
	
	@Override
	public void setMessage(Exception ex) {
		synchronized(LOCK) {
			try {
				throw ex;
			}
			catch (LoadImpossibleException e) {
				loadImpossible(e);
			}
			catch (SaveImpossibleException e) {
				saveImpossible(e);
			}
			catch (NoConnectionException e) {
				noConnection(e);
			}
			catch (TransportDataException e) {
				noTransportData(e);
			}
			catch (NoConfigurationException e) {
				noConfiguration(e);
			}
			catch (Exception e) {
				this.setText("An unknown error occured: " + e.getClass().getName());
			}
		}
	}
	
	private void unsetTextSeconds(final int sleep) {
		final String text = getText();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(sleep * 1000);
				} catch (InterruptedException e) {
					Log.warning(e.getMessage());
				}
				synchronized (LOCK) {
					if (text.equals(SwingMessages.this.getText())) {
						SwingMessages.this.setText(" ");
					}
				}
			}
			
		}).start();
	}
	
	private void loadImpossible(LoadImpossibleException e) {
		setText("Warning: Could not load configuration from " + e.getSource() + "!");
		unsetTextSeconds(15);
	}

	private void saveImpossible(SaveImpossibleException e) {
		setText("Error: Could not save configuration to " + e.getDestination() + "!");
		unsetTextSeconds(15);
	}

	private void noConnection(NoConnectionException e) {
		String connection = "from " + e.getConnection().getStart() + " to " + e.getConnection().getDestination();
		if (e.getConnection().getVia() != null) {
			connection += " via " + e.getConnection().getVia();
		}
		setText("Warning: Could not find a connection " + connection + "!");
		unsetTextSeconds(ConnectionTracker.getRefreshRate());
	}
	
	private void noTransportData(TransportDataException e) {
		setText("Warning: Currently no connection to transport data source!");
		unsetTextSeconds(ConnectionTracker.getRefreshRate());
	}
	
	private void noConfiguration(NoConfigurationException e) {
		setText("Info: No connection loaded.");
		unsetTextSeconds(ConnectionTracker.getRefreshRate());
	}
	
	/**
	 * Always set the same text also for this JLabels tool tip.
	 */
	@Override
	public void setText(String text) {
		super.setText(text);
		this.setToolTipText(text.trim());
	}

}
