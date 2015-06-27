package ch.zhaw.mas8i.pendlersupport.view.swing;

import javax.swing.JLabel;

import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.view.ConnectionInfo;

/**
 * That's the implementation of ConnectionInfo as a JLabel for Swing.
 */
public class SwingConnectionInfo extends JLabel implements ConnectionInfo {
	private static final long serialVersionUID = 1L;

	@Override
	public void setConnection(Connection connection) {
		// This version of the UI will not provide this information
		// since they are available in the configuration menu anyway.
	}

}
