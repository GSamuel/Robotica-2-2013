package visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.Timer;

import robotica.Agent;

public class FancyPanel extends JPanel implements Observer, KeyListener,
		ActionListener
{
	private FancyModel model;
	private Timer timer;

	public FancyPanel(FancyModel model)
	{
		this.model = model;
		//this.setPreferredSize(new Dimension(1900, 1000));
		this.setPreferredSize(new Dimension(1500,650));
		timer = new Timer(100, this);
		timer.start();
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1500, 650);

		Graphics2D g2d = (Graphics2D) g;
		 
		Font bigFont = new Font("Arial", Font.PLAIN, 24);
		Font smallFont = new Font ("Arial", Font.PLAIN, 20);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		
		for(int i = 0; i < model.size(); i++)
		{
			Agent ag = model.getAgentAt(i);
			if (ag != null)
			{
				g.setColor(Color.GREEN);
				g.fillRect(i*300, 0, 290, 650);
				g.setColor(Color.BLACK);
				g2d.setFont(bigFont);
				g2d.drawString("Agent: "+ag.name(), 20, 50);
				g2d.setFont(smallFont);
				g2d.drawString("Current state: "+ag.currentState().name(), 20, 150);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub

	}

}
