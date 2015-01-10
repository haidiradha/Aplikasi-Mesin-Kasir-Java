package Tampilan;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Setting.*;

public class Laporan extends JFrame {
	private Utama core;
	final public static int HEADER = 0, BODY = 1, FOOTER = 2;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextArea jl;

	public Laporan(Utama core, String[] data) {
		this.core = core;

		setIconImage(Toolkit.getDefaultToolkit().getImage(Laporan.class.getResource("/Gambar/okboy.png")));
		setResizable(false);

		setSize(450, 400);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(Color.WHITE);
		jl = new JTextArea(data[HEADER] + data[BODY] + data[FOOTER]);
		jl.setFont(new Font("Courier", Font.PLAIN, 11));
		jl.setBounds(0, 0, getWidth(), getHeight());
		jl.setEditable(false);

		container.add(jl);
	}

}
