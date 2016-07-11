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
import com.mrcrayfish.modelcreator.element.Face;
import com.mrcrayfish.modelcreator.util.Parser;
import com.mrcrayfish.modelcreator.util.UndoQueue;

public class UVPanel extends JPanel implements IValueUpdater
{
	private static final long serialVersionUID = 1L;

	private ElementManager manager;
	private JButton btnPlusX;
	private JButton btnPlusY;
	private JTextField xStartField;
	private JTextField yStartField;
	private JButton btnNegX;
	private JButton btnNegY;

	private JButton btnPlusXEnd;
	private JButton btnPlusYEnd;
	private JTextField xEndField;
	private JTextField yEndField;
	private JButton btnNegXEnd;
	private JButton btnNegYEnd;

	private DecimalFormat df = new DecimalFormat("#.#");

	public UVPanel(ElementManager manager)
	{
		this.manager = manager;
		setLayout(new GridLayout(3, 4, 4, 4));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(221, 221, 228), 5), "<html><b>UV</b></html>"));
		setMaximumSize(new Dimension(186, 124));
		initComponents();
		initProperties();
		addComponents();
	}

	public void initComponents()
	{
		btnPlusX = new JButton(Icons.arrow_up);
		btnPlusY = new JButton(Icons.arrow_up);
		xStartField = new JTextField();
		yStartField = new JTextField();
		btnNegX = new JButton(Icons.arrow_down);
		btnNegY = new JButton(Icons.arrow_down);

		btnPlusXEnd = new JButton(Icons.arrow_up);
		btnPlusYEnd = new JButton(Icons.arrow_up);
		xEndField = new JTextField();
		yEndField = new JTextField();
		btnNegXEnd = new JButton(Icons.arrow_down);
		btnNegYEnd = new JButton(Icons.arrow_down);
	}

	private void trySetStartU()
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			Face face = cube.getSelectedFace();
			if (face != null)
			{
				double oldStartU = face.getStartU();
				double newStartU = Parser.parseDouble(xStartField.getText(), face.getStartU());
				UndoQueue.performPush(new UndoQueue.Task()
				{
					@Override
					public void perform()
					{
						face.setStartU(newStartU);
					}

					@Override
					public void undo()
					{
						face.setStartU(oldStartU);
					}

					@Override
					public void update()
					{
						face.updateUV();
						manager.updateValues();
					}
				});
			}
		}
	}

	private void trySetEndU()
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			Face face = cube.getSelectedFace();
			if (face != null)
			{
				double oldEndU = face.getEndU();
				double newEndU = Parser.parseDouble(xEndField.getText(), face.getEndU());
				UndoQueue.performPush(new UndoQueue.Task()
				{
					@Override
					public void perform()
					{
						face.setEndU(newEndU);
					}

					@Override
					public void undo()
					{
						face.setEndU(oldEndU);
					}

					@Override
					public void update()
					{
						face.updateUV();
						manager.updateValues();
					}
				});
			}
		}
	}

	private void trySetStartV()
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			Face face = cube.getSelectedFace();
			if (face != null)
			{
				double oldStartV = face.getStartV();
				double newStartV = Parser.parseDouble(yStartField.getText(), face.getStartV());
				UndoQueue.performPush(new UndoQueue.Task()
				{
					@Override
					public void perform()
					{
						face.setStartV(newStartV);
					}

					@Override
					public void undo()
					{
						face.setStartU(oldStartV);
					}

					@Override
					public void update()
					{
						face.updateUV();
						manager.updateValues();
					}
				});
			}
		}
	}

	private void trySetEndV()
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			Face face = cube.getSelectedFace();
			if (face != null)
			{
				double oldEndV = face.getEndV();
				double newEndV = Parser.parseDouble(yEndField.getText(), face.getEndV());
				UndoQueue.performPush(new UndoQueue.Task()
				{
					@Override
					public void perform()
					{
						face.setEndV(newEndV);
					}

					@Override
					public void undo()
					{
						face.setEndV(oldEndV);
					}

					@Override
					public void update()
					{
						face.updateUV();
						manager.updateValues();
					}
				});
			}
		}
	}

	private void tryAddToStartU(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			Face face = cube.getSelectedFace();
			if (face != null)
			{

				UndoQueue.performPush(new UndoQueue.Task()
				{
					@Override
					public void perform()
					{
						face.addTextureX(delta);
					}

					@Override
					public void undo()
					{
						face.addTextureX(-delta);
					}

					@Override
					public void update()
					{
						cube.updateUV();
						manager.updateValues();
					}
				});
			}
		}
	}

	private void tryAddToEndU(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			Face face = cube.getSelectedFace();
			if (face != null)
			{
				UndoQueue.performPush(new UndoQueue.Task()
				{
					@Override
					public void perform()
					{
						face.addTextureXEnd(delta);
					}

					@Override
					public void undo()
					{
						face.addTextureXEnd(-delta);
					}

					@Override
					public void update()
					{
						cube.updateUV();
						manager.updateValues();
					}
				});
			}
		}
	}

	private void tryAddToStartV(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			Face face = cube.getSelectedFace();
			if (face != null)
			{
				UndoQueue.performPush(new UndoQueue.Task()
				{
					@Override
					public void perform()
					{
						face.addTextureY(delta);
					}

					@Override
					public void undo()
					{
						face.addTextureY(-delta);
					}

					@Override
					public void update()
					{
						cube.updateUV();
						manager.updateValues();
					}
				});
			}
		}
	}

	private void tryAddToEndV(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			Face face = cube.getSelectedFace();
			if (face != null)
			{
				UndoQueue.performPush(new UndoQueue.Task()
				{
					@Override
					public void perform()
					{
						face.addTextureYEnd(delta);
					}

					@Override
					public void undo()
					{
						face.addTextureYEnd(-delta);
					}

					@Override
					public void update()
					{
						cube.updateUV();
						manager.updateValues();
					}
				});
			}
		}
	}

	public void initProperties()
	{
		Font defaultFont = new Font("SansSerif", Font.BOLD, 20);
		xStartField.setSize(new Dimension(62, 30));
		xStartField.setFont(defaultFont);
		xStartField.setHorizontalAlignment(JTextField.CENTER);
		xStartField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetStartU();
				}
			}
		});
		xStartField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				if (manager.getSelectedElement() != null)
				{
					trySetStartU();
				}
			}
		});

		yStartField.setSize(new Dimension(62, 30));
		yStartField.setFont(defaultFont);
		yStartField.setHorizontalAlignment(JTextField.CENTER);
		yStartField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetStartV();
				}
			}
		});
		yStartField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetStartV();
			}
		});

		xEndField.setSize(new Dimension(62, 30));
		xEndField.setFont(defaultFont);
		xEndField.setHorizontalAlignment(JTextField.CENTER);
		xEndField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetEndU();
				}
			}
		});
		xEndField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetEndU();
			}
		});

		yEndField.setSize(new Dimension(62, 30));
		yEndField.setFont(defaultFont);
		yEndField.setHorizontalAlignment(JTextField.CENTER);
		yEndField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetEndV();
				}
			}
		});
		yEndField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetEndV();
			}
		});

		btnPlusX.addActionListener(e ->
		{
			tryAddToStartU((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? 1.0F : 0.1F);
		});

		btnPlusX.setSize(new Dimension(62, 30));
		btnPlusX.setFont(defaultFont);
		btnPlusX.setToolTipText("<html>Increases the start U.<br><b>Hold shift for decimals</b></html>");

		btnPlusY.addActionListener(e ->
		{
			tryAddToStartV((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? 1.0F : 0.1F);
		});
		btnPlusY.setPreferredSize(new Dimension(62, 30));
		btnPlusY.setFont(defaultFont);
		btnPlusY.setToolTipText("<html>Increases the start V.<br><b>Hold shift for decimals</b></html>");

		btnNegX.addActionListener(e ->
		{
			tryAddToStartU(((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? -1.0F : -0.1F);
		});
		btnNegX.setSize(new Dimension(62, 30));
		btnNegX.setFont(defaultFont);
		btnNegX.setToolTipText("<html>Decreases the start U.<br><b>Hold shift for decimals</b></html>");

		btnNegY.addActionListener(e ->
		{
			tryAddToStartV(((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? -1.0F : -0.1F);
		});
		btnNegY.setSize(new Dimension(62, 30));
		btnNegY.setFont(defaultFont);
		btnNegY.setToolTipText("<html>Decreases the start V.<br><b>Hold shift for decimals</b></html>");

		btnPlusXEnd.addActionListener(e ->
		{
			tryAddToEndU(((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) ? -1.0F : -0.1F);
		});
		btnPlusXEnd.setSize(new Dimension(62, 30));
		btnPlusXEnd.setFont(defaultFont);
		btnPlusXEnd.setToolTipText("<html>Increases the end U.<br><b>Hold shift for decimals</b></html>");

		btnPlusYEnd.addActionListener(e ->
		{
			tryAddToEndV((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? 1.0F : 0.1F);
		});
		btnPlusYEnd.setPreferredSize(new Dimension(62, 30));
		btnPlusYEnd.setFont(defaultFont);
		btnPlusYEnd.setToolTipText("<html>Increases the end V.<br><b>Hold shift for decimals</b></html>");

		btnNegXEnd.addActionListener(e ->
		{
			tryAddToEndU((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? -1.0F : -0.1F);
		});
		btnNegXEnd.setSize(new Dimension(62, 30));
		btnNegXEnd.setFont(defaultFont);
		btnNegXEnd.setToolTipText("<html>Decreases the end U.<br><b>Hold shift for decimals</b></html>");

		btnNegYEnd.addActionListener(e ->
		{
			tryAddToEndV((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? -1.0F : -0.1F);
		});
		btnNegYEnd.setSize(new Dimension(62, 30));
		btnNegYEnd.setFont(defaultFont);
		btnNegYEnd.setToolTipText("<html>Decreases the end V.<br><b>Hold shift for decimals</b></html>");
	}

	public void addComponents()
	{
		add(btnPlusX);
		add(btnPlusY);
		add(btnPlusXEnd);
		add(btnPlusYEnd);
		add(xStartField);
		add(yStartField);
		add(xEndField);
		add(yEndField);
		add(btnNegX);
		add(btnNegY);
		add(btnNegXEnd);
		add(btnNegYEnd);
	}

	@Override
	public void updateValues(Element cube)
	{
		if (cube != null)
		{
			xStartField.setEnabled(true);
			yStartField.setEnabled(true);
			xEndField.setEnabled(true);
			yEndField.setEnabled(true);
			xStartField.setText(df.format(cube.getSelectedFace().getStartU()));
			yStartField.setText(df.format(cube.getSelectedFace().getStartV()));
			xEndField.setText(df.format(cube.getSelectedFace().getEndU()));
			yEndField.setText(df.format(cube.getSelectedFace().getEndV()));
		}
		else
		{
			xStartField.setEnabled(false);
			yStartField.setEnabled(false);
			xEndField.setEnabled(false);
			yEndField.setEnabled(false);
			xStartField.setText("");
			yStartField.setText("");
			xEndField.setText("");
			yEndField.setText("");
		}
	}
}
