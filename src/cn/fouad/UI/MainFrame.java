package cn.fouad.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MouseInputAdapter;

import cn.fouad.commons.GameConfiguration;
import cn.fouad.commons.Point;
import cn.fouad.listener.BeginListener;
import cn.fouad.listener.GameListener;
import cn.fouad.service.impl.GameServiceImpl;

/**
 * MainFrame
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public final int DEFAULTWIDTH = 830;
	public final int DEFAULTHEIGHT = 600;
	private JPanel backgroundPanel = null;

	public MainFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "WARNING",
					JOptionPane.WARNING_MESSAGE);
		}
		initialize();
		setVisible(true);
	}

	public void printComponents(Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("images/bg_image.jpg")), 0, 0,
					500, 500, null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("serial")
	public JPanel createPanel(final ImageIcon imageIcon) {
		return new JPanel() {
			protected void paintComponent(Graphics g) {
				g.drawImage(imageIcon.getImage(), 0, 0, getWidth(),
						getHeight(), null);
			}

		};
	}

	private void initialize() {
		setTitle("LinkedGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
		this.setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		setLayout(new BorderLayout(5, 0));
		backgroundPanel = createPanel(new ImageIcon("images/bg_image.jpg"));
		add(backgroundPanel, BorderLayout.CENTER);
		// Configuration
		final GameConfiguration config = new GameConfiguration(16, 10,
				new Point(50, 100), 10, 150);
		final GameServiceImpl gameService = new GameServiceImpl(config);
		final GamePanel gamePanel = new GamePanel(gameService);
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(new Color(127, 174, 252));
		controlPanel.setBorder(new EtchedBorder());

		BoxLayout controlLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
		controlPanel.setLayout(controlLayout);
		add(controlPanel, BorderLayout.WEST);

		JPanel logoPanel = new JPanel();
		logoPanel.setBorder(new EtchedBorder());
		logoPanel.setBackground(new Color(127, 174, 252));
		Image logoImage = null;
		try {
			logoImage = ImageIO.read(new File("images/fouadLogo.gif"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
		logoPanel.add(logoLabel);
		controlPanel.add(logoPanel);
		controlPanel.add(createBlankPanel());

		JPanel alogoPanel = new JPanel();
		alogoPanel.setBorder(new EtchedBorder());
		Image alogoImage = null;
		try {
			alogoImage = ImageIO.read(new File("images/logo.gif"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		JLabel alogoLable = new JLabel(new ImageIcon(alogoImage));
		alogoPanel.setBackground(new Color(127, 174, 252));
		alogoPanel.add(alogoLable);
		controlPanel.add(alogoPanel);
		controlPanel.add(createBlankPanel());

		JPanel pointTextPanel = new JPanel();
		pointTextPanel.setBackground(new Color(169, 210, 254));
		pointTextPanel.setBorder(new EtchedBorder());
		JLabel pointTextLabel = new JLabel();
		pointTextLabel.setText("SCORES");
		pointTextPanel.add(pointTextLabel);
		controlPanel.add(pointTextPanel);

		JPanel pointPanel = new JPanel();
		pointPanel.setBorder(new EtchedBorder());
		pointPanel.setBackground(new Color(208, 223, 255));
		final JLabel pointLabel = new JLabel();
		pointLabel.setText("0");
		pointPanel.add(pointLabel);
		controlPanel.add(pointPanel);
		controlPanel.add(createBlankPanel());

		JPanel timeTextPanel = new JPanel();
		timeTextPanel.setBackground(new Color(169, 210, 254));
		timeTextPanel.setBorder(new EtchedBorder());
		JLabel timeTextLabel = new JLabel();
		timeTextLabel.setText("Remaining Time");
		timeTextPanel.add(timeTextLabel);
		controlPanel.add(timeTextPanel);

		// count time
		JPanel timePanel = new JPanel();
		timePanel.setBorder(new EtchedBorder());
		timePanel.setBackground(new Color(208, 223, 255));
		JLabel timeLabel = new JLabel(); //
		timeLabel.setText("0   S");
		timePanel.add(timeLabel);
		controlPanel.add(timePanel);
		controlPanel.add(createBlankPanel());

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setBackground(new Color(127, 174, 252));
		JButton beginButton = new JButton("START");
		buttonsPanel.add(beginButton);
		JLabel blankLabel = new JLabel();
		blankLabel.setText("     ");
		buttonsPanel.add(blankLabel);

		BeginListener beginListener = new BeginListener(this, gamePanel,
				gameService, pointLabel, timeLabel, config);
		beginButton.addMouseListener(beginListener);
		GameListener gameListener = new GameListener(gameService, gamePanel,
				pointLabel, beginListener);
		gamePanel.addMouseListener(gameListener);

		JButton exitButton = new JButton("EXIT");
		exitButton.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		buttonsPanel.add(exitButton);
		controlPanel.add(buttonsPanel);
		controlPanel.add(createBlankPanel());
	}

	public void romoveBackGroundPanel() {
		this.remove(backgroundPanel);
	}

	private JPanel createBlankPanel() {
		JPanel blankPanel = new JPanel();
		blankPanel.setBackground(new Color(127, 174, 252));
		JLabel blankLabel = new JLabel();
		blankLabel.setText("      ");
		blankPanel.add(blankLabel);
		return blankPanel;
	}

}
