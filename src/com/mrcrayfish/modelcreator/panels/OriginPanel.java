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

public class OriginPanel extends JPanel implements IValueUpdater
{
	private static final long serialVersionUID = 1L;

	private ElementManager manager;

	private JButton btnPlusX;
	private JButton btnPlusY;
	private JButton btnPlusZ;
	private JTextField xOriginField;
	private JTextField yOriginField;
	private JTextField zOriginField;
	private JButton btnNegX;
	private JButton btnNegY;
	private JButton btnNegZ;

	private DecimalFormat df = new DecimalFormat("#.#");

	public OriginPanel(ElementManager manager)
	{
		this.manager = manager;
		setLayout(new GridLayout(3, 3, 4, 4));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(221, 221, 228), 5), "<html><b>Origin</b></html>"));
		setMaximumSize(new Dimension(186, 124));
		initComponents();
		initProperties();
		addComponents();
	}

	public void initComponents()
	{
		btnPlusX = new JButton(Icons.arrow_up);
		btnPlusY = new JButton(Icons.arrow_up);
		btnPlusZ = new JButton(Icons.arrow_up);
		xOriginField = new JTextField();
		yOriginField = new JTextField();
		zOriginField = new JTextField();
		btnNegX = new JButton(Icons.arrow_down);
		btnNegY = new JButton(Icons.arrow_down);
		btnNegZ = new JButton(Icons.arrow_down);
	}

	private void trySetOriginX()
	{
		Element element = manager.getSelectedElement();
		if (element != null)
		{
			double oldOriginX = element.getOriginX();
			double newOriginX = (Parser.parseDouble(xOriginField.getText(), oldOriginX));
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					element.setOriginX(newOriginX);
				}

				@Override
				public void undo()
				{
					element.setOriginX(oldOriginX);
				}

				@Override
				public void update()
				{
					xOriginField.setText(df.format(element.getOriginZ()));
					manager.updateValues();
				}
			});
		}
	}

	private void trySetOriginY()
	{
		Element element = manager.getSelectedElement();
		if (element != null)
		{
			double oldOriginY = element.getOriginY();
			double newOriginY = (Parser.parseDouble(yOriginField.getText(), oldOriginY));
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					element.setOriginX(newOriginY);
				}

				@Override
				public void undo()
				{
					element.setOriginX(oldOriginY);
				}

				@Override
				public void update()
				{
					yOriginField.setText(df.format(element.getOriginY()));
					manager.updateValues();
				}
			});
		}
	}

	private void trySetOriginZ()
	{
		Element element = manager.getSelectedElement();
		if (element != null)
		{
			double oldOriginZ = element.getOriginZ();
			double newOriginZ = (Parser.parseDouble(zOriginField.getText(), oldOriginZ));
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					element.setOriginZ(newOriginZ);
				}

				@Override
				public void undo()
				{
					element.setOriginZ(oldOriginZ);
				}

				@Override
				public void update()
				{
					zOriginField.setText(df.format(element.getOriginZ()));
					manager.updateValues();
				}
			});
		}
	}

	private void tryAddOriginX(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					cube.addOriginX(delta);
				}

				@Override
				public void undo()
				{
					cube.addOriginX(-delta);
				}

				@Override
				public void update()
				{
					xOriginField.setText(df.format(cube.getOriginX()));
					manager.updateValues();
				}
			});
		}
	}

	private void tryAddOriginY(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					cube.addOriginY(delta);
				}

				@Override
				public void undo()
				{
					cube.addOriginY(-delta);
				}

				@Override
				public void update()
				{
					yOriginField.setText(df.format(cube.getOriginY()));
					manager.updateValues();
				}
			});
		}
	}

	private void tryAddOriginZ(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					cube.addOriginZ(delta);
				}

				@Override
				public void undo()
				{
					cube.addOriginZ(-delta);
				}

				@Override
				public void update()
				{
					zOriginField.setText(df.format(cube.getOriginZ()));
					manager.updateValues();
				}
			});
		}
	}

	public void initProperties()
	{
		Font defaultFont = new Font("SansSerif", Font.BOLD, 20);
		xOriginField.setSize(new Dimension(62, 30));
		xOriginField.setFont(defaultFont);
		xOriginField.setHorizontalAlignment(JTextField.CENTER);
		xOriginField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetOriginX();
				}
			}
		});
		xOriginField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetOriginX();
			}
		});

		yOriginField.setSize(new Dimension(62, 30));
		yOriginField.setFont(defaultFont);
		yOriginField.setHorizontalAlignment(JTextField.CENTER);
		yOriginField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				trySetOriginY();
			}
		});
		yOriginField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetOriginY();
			}
		});

		zOriginField.setSize(new Dimension(62, 30));
		zOriginField.setFont(defaultFont);
		zOriginField.setHorizontalAlignment(JTextField.CENTER);
		zOriginField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				trySetOriginZ();
			}
		});
		zOriginField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetOriginZ();
			}
		});

		btnPlusX.addActionListener(e ->
		{
			float delta = ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? 1.0F : 0.1F;
			tryAddOriginX(delta);
		});
		btnPlusX.setPreferredSize(new Dimension(62, 30));
		btnPlusX.setFont(defaultFont);
		btnPlusX.setToolTipText("<html>Increases the X origin.<br><b>Hold shift for decimals</b></html>");

		btnPlusY.addActionListener(e ->
		{
			float delta = ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? 1.0F : 0.1F;
			tryAddOriginY(delta);
		});
		btnPlusY.setPreferredSize(new Dimension(62, 30));
		btnPlusY.setFont(defaultFont);
		btnPlusY.setToolTipText("<html>Increases the Y origin.<br><b>Hold shift for decimals</b></html>");

		btnPlusZ.addActionListener(e ->
		{
			float delta = ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? 1.0F : 0.1F;
			tryAddOriginZ(delta);
		});
		btnPlusZ.setPreferredSize(new Dimension(62, 30));
		btnPlusZ.setFont(defaultFont);
		btnPlusZ.setToolTipText("<html>Increases the Z origin.<br><b>Hold shift for decimals</b></html>");

		btnNegX.addActionListener(e ->
		{
			float delta = ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? -1.0F : -0.1F;
			tryAddOriginX(delta);
		});
		btnNegX.setPreferredSize(new Dimension(62, 30));
		btnNegX.setFont(defaultFont);
		btnNegX.setToolTipText("<html>Decreases the X origin.<br><b>Hold shift for decimals</b></html>");

		btnNegY.addActionListener(e ->
		{
			float delta = ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? -1.0F : -0.1F;
			tryAddOriginY(delta);
		});
		btnNegY.setPreferredSize(new Dimension(62, 30));
		btnNegY.setFont(defaultFont);
		btnNegY.setToolTipText("<html>Decreases the Y origin.<br><b>Hold shift for decimals</b></html>");

		btnNegZ.addActionListener(e ->
		{
			float delta = ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? -1.0F : -0.1F;
			tryAddOriginZ(delta);
		});
		btnNegZ.setPreferredSize(new Dimension(62, 30));
		btnNegZ.setFont(defaultFont);
		btnNegZ.setToolTipText("<html>Decreases the Z origin.<br><b>Hold shift for decimals</b></html>");
	}

	public void addComponents()
	{
		add(btnPlusX);
		add(btnPlusY);
		add(btnPlusZ);
		add(xOriginField);
		add(yOriginField);
		add(zOriginField);
		add(btnNegX);
		add(btnNegY);
		add(btnNegZ);
	}

	@Override
	public void updateValues(Element cube)
	{
		if (cube != null)
		{
			xOriginField.setEnabled(true);
			yOriginField.setEnabled(true);
			zOriginField.setEnabled(true);
			xOriginField.setText(df.format(cube.getOriginX()));
			yOriginField.setText(df.format(cube.getOriginY()));
			zOriginField.setText(df.format(cube.getOriginZ()));
		}
		else
		{
			xOriginField.setEnabled(false);
			yOriginField.setEnabled(false);
			zOriginField.setEnabled(false);
			xOriginField.setText("");
			yOriginField.setText("");
			zOriginField.setText("");
		}
	}
}
