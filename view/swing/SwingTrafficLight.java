package ch.zhaw.mas8i.pendlersupport.view.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import ch.zhaw.mas8i.pendlersupport.model.ConnectionStatus;
import ch.zhaw.mas8i.pendlersupport.model.Time;
import ch.zhaw.mas8i.pendlersupport.view.TrafficLight;

/**
 * That's the implementation of TrafficLight as JPanel for Swing.
 */
public class SwingTrafficLight extends JPanel implements TrafficLight {
	private static final long serialVersionUID = 1L;
	private static final int OUTER_GAP = 8;
	private static final Color RED_OFF = new Color(128, 0, 0);
	private static final Color RED_ON = Color.RED;
	private static final Color ORANGE_OFF = new Color(127, 139, 12);
	private static final Color ORANGE_ON = Color.YELLOW;
	private static final Color GREEN_OFF = new Color(0, 96, 0);
	private static final Color GREEN_ON = Color.GREEN;
	private ConnectionStatus status = ConnectionStatus.UNKNOWN;
	private final JLabel timeLabel;
	private String timeLabelText;

	/**
	 * The constructor disables the LayoutManager to be able
	 * to place all elements by pixel. And it places a new JLabel.
	 */
	public SwingTrafficLight() {
		this.setLayout(null);
		timeLabel = new JLabel("", SwingConstants.CENTER);
		this.add(timeLabel);
	}
	
	@Override
	public void setStatus(ConnectionStatus status, Time nextTime) {
		timeLabelText = nextTime == null ? "Loading" : nextTime.toString();
		
		switch (status) {
			case GREEN:   timeLabel.setToolTipText("You have enough time to reach the connection at " + timeLabelText + "."); break;
			case ORANGE:  timeLabel.setToolTipText("You will need to hurry to catch the connection at " + timeLabelText + "."); break;
			case RED:     timeLabel.setToolTipText("You missed the last connection, the next one is too far away (" + timeLabelText + ")."); break;
			case UNKNOWN: timeLabel.setToolTipText(""); break;
		}
		
		this.status = status;
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		paintTrafficLight(g);
		placeTimeLabel();
	}
	
	private void placeTimeLabel() {
		timeLabel.setBounds(getPosition(status));
		
		// Calculate optimal font size
		int width = timeLabel.getFontMetrics(timeLabel.getFont()).stringWidth(timeLabelText);
		double ratio = (double)timeLabel.getWidth() / (double)(width * 1.8);
		int fontSize = Math.min((int)(timeLabel.getFont().getSize() * ratio), timeLabel.getHeight());
		
		timeLabel.setText("<html><body><span style=\"font-size:" + fontSize + "px;\">" + timeLabelText + "</span></body></html>");
	}
	
	private void paintTrafficLight(Graphics g) {
		Rectangle pFrame = getPosition(ConnectionStatus.UNKNOWN);
		Rectangle pRed = getPosition(ConnectionStatus.RED);
		Rectangle pOrange = getPosition(ConnectionStatus.ORANGE);
		Rectangle pGreen = getPosition(ConnectionStatus.GREEN);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// This fixes a strange rendering behavior under Windows (otherwise it acts
		// like there is no color defined in background and shows random content)
		g2d.setColor(UIManager.getColor("Panel.background"));
		g2d.fillPolygon(getFrame(new Rectangle(0, 0, this.getWidth(), this.getHeight())));
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillPolygon(getFrame(pFrame));
		g2d.setColor(status == ConnectionStatus.RED ? RED_ON : RED_OFF);
		g2d.fillOval(pRed.x, pRed.y, pRed.width, pRed.height);
		g2d.setColor(status == ConnectionStatus.ORANGE ? ORANGE_ON : ORANGE_OFF);
		g2d.fillOval(pOrange.x, pOrange.y, pOrange.width, pOrange.height);
		g2d.setColor(status == ConnectionStatus.GREEN ? GREEN_ON : GREEN_OFF);
		g2d.fillOval(pGreen.x, pGreen.y, pGreen.width, pGreen.height);
	}
	
	private Polygon getFrame(Rectangle rect) {
		Polygon p = new Polygon();
		p.addPoint(rect.x, rect.y);
		p.addPoint((rect.x + rect.width), rect.y);
		p.addPoint((rect.x + rect.width), (rect.y + rect.height));
		p.addPoint(rect.x, (rect.y + rect.height));
		return p;
	}
	
	private Rectangle getPosition(ConnectionStatus status) {
		int innerGap;
		int width;
		int x;
		int y;
		
		if (this.getWidth() * 3 > this.getHeight()) {
			width = (this.getHeight() - OUTER_GAP * 2) / 3 ;
			x = (this.getWidth() - width) / 2;
			y = OUTER_GAP;
			innerGap = width / 10;
		}
		else {
			width = this.getWidth() - OUTER_GAP * 2;
			x = OUTER_GAP;
			y = ((this.getHeight() - width * 3) / 2);
			innerGap = width / 10;
		}
		
		switch(status) {
			case GREEN:   return new Rectangle((x + innerGap), (y + innerGap + width * 2), (width - innerGap * 2), (width - innerGap * 2));
			case ORANGE:  return new Rectangle((x + innerGap), (y + innerGap + width), (width - innerGap * 2), (width - innerGap * 2));
			case RED:     return new Rectangle((x + innerGap), (y + innerGap), (width - innerGap * 2), (width - innerGap * 2));
			case UNKNOWN: return new Rectangle(x, y, width, (width * 3)); // whole frame
			default:      return new Rectangle(0, 0, 0, 0); // invisible
		}
	}
}
