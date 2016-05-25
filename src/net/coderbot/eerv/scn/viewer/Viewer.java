package net.coderbot.eerv.scn.viewer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class Viewer
{
	public static void main(String[] args) throws Exception
	{
		JFrame frame = new JFrame();
		frame.setSize(1024, 768);
		frame.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0)
			{
				
			}

			@Override
			public void windowClosed(WindowEvent arg0)
			{
				
			}

			@Override
			public void windowClosing(WindowEvent arg0)
			{
				System.exit(0);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0)
			{
				
			}

			@Override
			public void windowIconified(WindowEvent arg0)
			{
				
			}

			@Override
			public void windowOpened(WindowEvent arg0)
			{
				
			}
			
		});
		frame.setVisible(true);
		
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		JButton open = new JButton();
		
		JLabel welcome = new JLabel("Welcome to the Empire Earth Map Viewer");
		open.setText("Open Map File");
		options.add(welcome);
		options.add(open);
		
		JPanel ui = new JPanel();
		ui.add(options, BorderLayout.CENTER);
		
		frame.add(ui);
	}
}
