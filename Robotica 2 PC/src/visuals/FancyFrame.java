package visuals;

import javax.swing.JFrame;

public class FancyFrame extends JFrame
{
	private FancyPanel panel;

	public FancyFrame(FancyModel model)
	{
		super("Fancy");

		panel = new FancyPanel(model);
		this.add(panel);
		panel.setVisible(true);
		this.addKeyListener(panel);
	}
}