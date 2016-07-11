package com.mrcrayfish.modelcreator.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mrcrayfish.modelcreator.Icons;
import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.util.Parser;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class PositionPanel extends JPanel implements IValueUpdater
{

	private static final long serialVersionUID = 1L;

	private ElementManager manager;

	private JButton btnPlusX;
	private JButton btnPlusY;
	private JButton btnPlusZ;
	private JTextField xPositionField;
	private JTextField yPositionField;
	private JTextField zPositionField;
	private JButton btnNegX;
	private JButton btnNegY;
	private JButton btnNegZ;

	public final static DecimalFormat df = new DecimalFormat("#.#");

	public PositionPanel(ElementManager manager)
	{
		this.manager = manager;
		setLayout(new GridLayout(3, 3, 4, 4));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(221, 221, 228), 5), "<html><b>Position</b></html>"));
		setMaximumSize(new Dimension(186, 124));
		setAlignmentX(JPanel.CENTER_ALIGNMENT);
		initComponents();
		initProperties();
		addComponents();
	}

	public void initComponents()
	{
		btnPlusX = new JButton(Icons.arrow_up);
		btnPlusY = new JButton(Icons.arrow_up);
		btnPlusZ = new JButton(Icons.arrow_up);
		xPositionField = new JTextField();
		yPositionField = new JTextField();
		zPositionField = new JTextField();
		btnNegX = new JButton(Icons.arrow_down);
		btnNegY = new JButton(Icons.arrow_down);
		btnNegZ = new JButton(Icons.arrow_down);
	}

	private void trySetStartX()
	{
		Element element = manager.getSelectedElement();
		if (element != null)
		{
			double oldStartX = element.getStartX();
			double newStartX = Parser.parseDouble(xPositionField.getText(), element.getStartX());
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					element.setStartX(newStartX);
				}

				@Override
				public void undo()
				{
					element.setStartX(oldStartX);
				}

				@Override
				public void update()
				{
					element.updateUV();
					manager.updateValues();
				}
			});
		}
	};

	private void trySetStartY()
	{
		Element element = manager.getSelectedElement();
		if (element != null)
		{
			double oldStartY = element.getStartY();
			double newStartY = Parser.parseDouble(yPositionField.getText(), element.getStartY());
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					element.setStartZ(newStartY);
				}

				@Override
				public void undo()
				{
					element.setStartZ(oldStartY);
				}

				@Override
				public void update()
				{
					element.updateUV();
					manager.updateValues();
				}
			});
		}
	};

	private void trySetStartZ()
	{
		Element element = manager.getSelectedElement();
		if (element != null)
		{
			double oldStartZ = element.getStartZ();
			double newStartZ = Parser.parseDouble(zPositionField.getText(), element.getStartZ());
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					element.setStartZ(newStartZ);
				}

				@Override
				public void undo()
				{
					element.setStartZ(oldStartZ);
				}

				@Override
				public void update()
				{
					element.updateUV();
					manager.updateValues();
				}
			});
		}
	};

	private void tryAddToX(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					cube.addStartX(delta);
				}

				@Override
				public void undo()
				{
					cube.addStartX(-delta);
				}

				@Override
				public void update()
				{
					xPositionField.setText(df.format(cube.getStartX()));
				}
			});
		}
	}

	private void tryAddToY(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					cube.addStartY(delta);
				}

				@Override
				public void undo()
				{
					cube.addStartY(-delta);
				}

				@Override
				public void update()
				{
					yPositionField.setText(df.format(cube.getStartY()));
				}
			});
		}
	}

	private void tryAddToZ(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					cube.addStartZ(delta);
				}

				@Override
				public void undo()
				{
					cube.addStartZ(-delta);
				}

				@Override
				public void update()
				{
					zPositionField.setText(df.format(cube.getStartZ()));
				}
			});
		}
	}

	public void initProperties()
	{
		Runnable trySetStartX = () ->
		{
			Element element = manager.getSelectedElement();
			if (element != null)
			{
				double oldStartX = element.getStartX();
				double newStartX = Parser.parseDouble(xPositionField.getText(), element.getStartX());
				UndoQueue.performPush(new UndoQueue.Task()
				{
					public void perform()
					{
						element.setStartX(newStartX);
					}

					public void undo()
					{
						element.setStartX(oldStartX);
					}

					public void update()
					{
						element.updateUV();
						manager.updateValues();
					}
				});
			}
		};
		
		Font defaultFont = new Font("SansSerif", Font.BOLD, 20);
		xPositionField.setSize(new Dimension(62, 30));
		xPositionField.setFont(defaultFont);
		xPositionField.setHorizontalAlignment(JTextField.CENTER);
		xPositionField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetStartX();
				}
			}
		});
		xPositionField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				Element element = manager.getSelectedElement();
				if (element != null)
				{
					trySetStartX();
				}
			}
		});

		Runnable trySetStartY = () ->
		{
			Element element = manager.getSelectedElement();
			if (element != null)
			{
				double oldStartY = element.getStartY();
				double newStartY = Parser.parseDouble(yPositionField.getText(), element.getStartY());
				UndoQueue.performPush(new UndoQueue.Task()
				{
					public void perform()
					{
						element.setStartZ(newStartY);
					}

					public void undo()
					{
						element.setStartZ(oldStartY);
					}

					public void update()
					{
						element.updateUV();
						manager.updateValues();
					}
				});
			}
		};
		
		yPositionField.setSize(new Dimension(62, 30));
		yPositionField.setFont(defaultFont);
		yPositionField.setHorizontalAlignment(JTextField.CENTER);
		yPositionField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetStartY();
				}
			}
		});
		yPositionField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				Element element = manager.getSelectedElement();
				if (element != null)
				{
					trySetStartY();
				}
			}
		});

		Runnable trySetStartZ = () ->
		{
			Element element = manager.getSelectedElement();
			if (element != null)
			{
				double oldStartZ = element.getStartZ();
				double newStartZ = Parser.parseDouble(zPositionField.getText(), element.getStartZ());
				UndoQueue.performPush(new UndoQueue.Task()
				{
					public void perform()
					{
						element.setStartZ(newStartZ);
					}

					public void undo()
					{
						element.setStartZ(oldStartZ);
					}

					public void update()
					{
						element.updateUV();
						manager.updateValues();
					}
				});
			}
		};
		
		zPositionField.setSize(new Dimension(62, 30));
		zPositionField.setFont(defaultFont);
		zPositionField.setHorizontalAlignment(JTextField.CENTER);
		zPositionField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetStartZ();
				}
			}
		});
		zPositionField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetStartZ();
			}
		});

		btnPlusX.addActionListener(e ->
		{
			tryAddToX(((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? 1.0F : 0.1F);
		});
		btnPlusX.setPreferredSize(new Dimension(62, 30));
		btnPlusX.setFont(defaultFont);
		btnPlusX.setToolTipText("<html>Increases the X position.<br><b>Hold shift for decimals</b></html>");

		btnPlusY.addActionListener(e ->
		{
			tryAddToY(((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? 1.0F : 0.1F);
		});
		btnPlusY.setPreferredSize(new Dimension(62, 30));
		btnPlusY.setFont(defaultFont);
		btnPlusY.setToolTipText("<html>Increases the Y position.<br><b>Hold shift for decimals</b></html>");

		btnPlusZ.addActionListener(e ->
		{
			tryAddToZ(((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? 1.0F : 0.1F);
		});
		btnPlusZ.setPreferredSize(new Dimension(62, 30));
		btnPlusZ.setFont(defaultFont);
		btnPlusZ.setToolTipText("<html>Increases the Z position.<br><b>Hold shift for decimals</b></html>");

		btnNegX.addActionListener(e ->
		{
			tryAddToX(((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? -1.0F : -0.1F);
		});
		btnNegX.setPreferredSize(new Dimension(62, 30));
		btnNegX.setFont(defaultFont);
		btnNegX.setToolTipText("<html>Decreases the X position.<br><b>Hold shift for decimals</b></html>");

		btnNegY.addActionListener(e ->
		{
			tryAddToY(((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? -1.0F : -0.1F);
		});
		btnNegY.setPreferredSize(new Dimension(62, 30));
		btnNegY.setFont(defaultFont);
		btnNegY.setToolTipText("<html>Decreases the Y position.<br><b>Hold shift for decimals</b></html>");

		btnNegZ.addActionListener(e ->
		{
			Element cube = manager.getSelectedElement();
		});
		btnNegZ.setPreferredSize(new Dimension(62, 30));
		btnNegZ.setFont(defaultFont);
		btnNegZ.setToolTipText("<html>Decreases the Z position.<br><b>Hold shift for decimals</b></html>");
	}

	public void addComponents()
	{
		add(btnPlusX);
		add(btnPlusY);
		add(btnPlusZ);
		add(xPositionField);
		add(yPositionField);
		add(zPositionField);
		add(btnNegX);
		add(btnNegY);
		add(btnNegZ);
	}

	@Override
	public void updateValues(Element cube)
	{
		if (cube != null)
		{
			xPositionField.setEnabled(true);
			yPositionField.setEnabled(true);
			zPositionField.setEnabled(true);
			xPositionField.setText(df.format(cube.getStartX()));
			yPositionField.setText(df.format(cube.getStartY()));
			zPositionField.setText(df.format(cube.getStartZ()));
		}
		else
		{
			xPositionField.setEnabled(false);
			yPositionField.setEnabled(false);
			zPositionField.setEnabled(false);
			xPositionField.setText("");
			yPositionField.setText("");
			zPositionField.setText("");
		}
	}
}
