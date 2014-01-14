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

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import connection.ConnectionManager;
import robotica.Agent;
import robotica.CompletedTask;

public class FancyPanel extends JPanel implements Observer, KeyListener,
		ActionListener
{
	private FancyModel model;
	private Timer timer;
	private ConnectionManager conMan = ConnectionManager.getInstance();

	public FancyPanel(FancyModel model)
	{
		this.model = model;
		// this.setPreferredSize(new Dimension(1900, 1000));
		this.setPreferredSize(new Dimension(1500, 650));
		timer = new Timer(100, this);
		timer.start();

		JButton waiterBut = new JButton("conWaiter");
		JButton custBut = new JButton("conCustomer");
		JButton cookBut = new JButton("conCook");
		JButton testBut = new JButton("TEST");

		waiterBut.setSize(100, 50);

		this.add(waiterBut);
		this.add(custBut);
		this.add(cookBut);
		this.add(testBut);

		waiterBut.addActionListener(this);
		custBut.addActionListener(this);
		cookBut.addActionListener(this);
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1500, 650);

		Graphics2D g2d = (Graphics2D) g;

		Font bigFont = new Font("Arial", Font.PLAIN, 24);
		Font smallFont = new Font("Arial", Font.PLAIN, 20);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < model.size(); i++)
		{
			Agent ag = model.getAgentAt(i);
			if (ag != null)
			{
				g.setColor(Color.GREEN);
				g.fillRect(i * 300, 0, 290, 650);
				g.setColor(Color.BLACK);
				g2d.setFont(bigFont);
				g2d.drawString("Agent: " + ag.name(), 20 + i * 300, 50);
				g2d.setFont(smallFont);
				g2d.drawString("Current state: " + ag.currentState().name(),
						20 + i * 300, 100);
				if (ag.currentState().hasTarget())
					g2d.drawString(ag.currentState().target(), 20 + i * 300,
							100);

				if (ag.coupledStateSize() > 0)
					g2d.drawString("Coupled states", 80 + i * 300, 145);

				/*
				 * for(int j = 0; j < ag.coupledStateSize(); j++)
				 * g2d.drawString(
				 * ag.getCoupledState(j).getOwnState().name()+" --- "
				 * +ag.getCoupledState(i).getTargetState().name(), 20 + i*300,
				 * 180 + j*50);
				 */

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		repaint();

		if (arg0.getActionCommand() != null)
		{
			if (arg0.getActionCommand() == "conWaiter")
				conMan.connect("GoodWaiter");
			else if (arg0.getActionCommand() == "conCustomer")
				conMan.connect("GoodCustomer");
			else if (arg0.getActionCommand() == "conCook")
				conMan.connect("GoodCook");
			else if(arg0.getActionCommand() == "RESET")
			{
				for (int i = 0; i < model.size(); i++)
				{
					//model.
				}
			}
		

		}
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
