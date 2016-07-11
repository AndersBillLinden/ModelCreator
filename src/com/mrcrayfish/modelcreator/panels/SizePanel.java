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

public class SizePanel extends JPanel implements IValueUpdater
{
	private static final long serialVersionUID = 1L;

	private ElementManager manager;

	private JButton btnPlusX;
	private JButton btnPlusY;
	private JButton btnPlusZ;
	private JTextField xSizeField;
	private JTextField ySizeField;
	private JTextField zSizeField;
	private JButton btnNegX;
	private JButton btnNegY;
	private JButton btnNegZ;

	private DecimalFormat df = new DecimalFormat("#.#");

	public SizePanel(ElementManager manager)
	{
		this.manager = manager;
		setLayout(new GridLayout(3, 3, 4, 4));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(221, 221, 228), 5), "<html><b>Size</b></html>"));
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
		xSizeField = new JTextField();
		ySizeField = new JTextField();
		zSizeField = new JTextField();
		btnNegX = new JButton(Icons.arrow_down);
		btnNegY = new JButton(Icons.arrow_down);
		btnNegZ = new JButton(Icons.arrow_down);
	}

	private void trySetWidth()
	{
		Element element = manager.getSelectedElement();
		if (element != null)
		{
			double oldWidth = element.getWidth();
			double newWidth = Parser.parseDouble(xSizeField.getText(), element.getWidth());

			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					element.setWidth(newWidth);
				}

				@Override
				public void undo()
				{
					element.setWidth(oldWidth);
				}

				@Override
				public void update()
				{
					element.updateUV();
					manager.updateValues();
				}
			});
		}
	}

	private void trySetHeight()
	{
		Element element = manager.getSelectedElement();
		if (element != null)
		{
			double oldHeight = element.getHeight();
			double newHeight = Parser.parseDouble(ySizeField.getText(), element.getHeight());

			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					element.setHeight(newHeight);
				}

				@Override
				public void undo()
				{
					element.setHeight(oldHeight);
				}

				@Override
				public void update()
				{
					element.updateUV();
					manager.updateValues();
				}
			});
		}
	}

	private void trySetDepth()
	{
		Element element = manager.getSelectedElement();
		if (element != null)
		{
			double oldDepth = element.getDepth();
			double newDepth = Parser.parseDouble(zSizeField.getText(), element.getDepth());

			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					element.setDepth(newDepth);
				}

				@Override
				public void undo()
				{
					element.setDepth(oldDepth);
				}

				@Override
				public void update()
				{
					element.updateUV();
					manager.updateValues();
				}
			});
		}
	}

	private void tryAddToWidth(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{

			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					cube.addWidth(delta);
				}

				@Override
				public void undo()
				{
					cube.addWidth(-delta);
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

	private void tryAddToHeight(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					cube.addHeight(delta);
				}

				@Override
				public void undo()
				{
					cube.addHeight(-delta);
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

	private void tryAddToDepth(float delta)
	{
		Element cube = manager.getSelectedElement();
		if (cube != null)
		{
			UndoQueue.performPush(new UndoQueue.Task()
			{
				@Override
				public void perform()
				{
					cube.addDepth(delta);
				}

				@Override
				public void undo()
				{
					cube.addDepth(-delta);
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

	public void initProperties()
	{
		Runnable trySetWidth = () ->
		{
			Element element = manager.getSelectedElement();
			if (element != null)
			{
				double oldWidth = element.getWidth();
				double newWidth = Parser.parseDouble(xSizeField.getText(), element.getWidth());
				
				UndoQueue.performPush(new UndoQueue.Task()
				{
					public void perform()
					{
						element.setWidth(newWidth);
					}
	
					public void undo()
					{
						element.setWidth(oldWidth);
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
		xSizeField.setSize(new Dimension(62, 30));
		xSizeField.setFont(defaultFont);
		xSizeField.setHorizontalAlignment(JTextField.CENTER);
		xSizeField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetWidth();
				}
			}
		});
		xSizeField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetWidth();
			}
		});

		Runnable trySetHeight = () ->
		{
			Element element = manager.getSelectedElement();
			if (element != null)
			{
				double oldHeight = element.getHeight();
				double newHeight = Parser.parseDouble(ySizeField.getText(), element.getHeight());
				
				UndoQueue.performPush(new UndoQueue.Task()
				{
					public void perform()
					{
						element.setHeight(newHeight);
					}
	
					public void undo()
					{
						element.setHeight(oldHeight);
					}
	
					public void update()
					{
						element.updateUV();
						manager.updateValues();
					}
				});
			}			
		};
		
		ySizeField.setSize(new Dimension(62, 30));
		ySizeField.setFont(defaultFont);
		ySizeField.setHorizontalAlignment(JTextField.CENTER);
		ySizeField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetHeight();
				}
			}
		});
		ySizeField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetHeight();
			}
		});

		Runnable trySetDepth = () ->
		{
			Element element = manager.getSelectedElement();
			if (element != null)
			{
				double oldDepth = element.getDepth();
				double newDepth = Parser.parseDouble(zSizeField.getText(), element.getDepth());
				
				UndoQueue.performPush(new UndoQueue.Task()
				{
					public void perform()
					{
						element.setDepth(newDepth);
					}
	
					public void undo()
					{
						element.setDepth(oldDepth);
					}
	
					public void update()
					{
						element.updateUV();
						manager.updateValues();
					}
				});
			}			
		};
		
		zSizeField.setSize(new Dimension(62, 30));
		zSizeField.setFont(defaultFont);
		zSizeField.setHorizontalAlignment(JTextField.CENTER);
		zSizeField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					trySetDepth();
				}
			}
		});
		zSizeField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				trySetDepth();
			}
		});

		btnPlusX.addActionListener(e ->
		{
			tryAddToWidth((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? 1.0F : 0.1F);
		});
		btnPlusX.setPreferredSize(new Dimension(62, 30));
		btnPlusX.setFont(defaultFont);
		btnPlusX.setToolTipText("<html>Increases the width.<br><b>Hold shift for decimals</b></html>");

		btnPlusY.addActionListener(e ->
		{
			tryAddToHeight((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? 1.0F : 0.1F);
		});
		btnPlusY.setPreferredSize(new Dimension(62, 30));
		btnPlusY.setFont(defaultFont);
		btnPlusY.setToolTipText("<html>Increases the height.<br><b>Hold shift for decimals</b></html>");

		btnPlusZ.addActionListener(e ->
		{
			tryAddToDepth((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? 1.0F : 0.1F);
		});
		btnPlusZ.setPreferredSize(new Dimension(62, 30));
		btnPlusZ.setFont(defaultFont);
		btnPlusZ.setToolTipText("<html>Increases the depth.<br><b>Hold shift for decimals</b></html>");

		btnNegX.addActionListener(e ->
		{
			tryAddToWidth((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? -1.0F : -0.1F);
		});
		btnNegX.setPreferredSize(new Dimension(62, 30));
		btnNegX.setFont(defaultFont);
		btnNegX.setToolTipText("<html>Decreases the width.<br><b>Hold shift for decimals</b></html>");

		btnNegY.addActionListener(e ->
		{
			tryAddToHeight((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? -1.0F : -0.1F);
		});
		btnNegY.setPreferredSize(new Dimension(62, 30));
		btnNegY.setFont(defaultFont);
		btnNegY.setToolTipText("<html>Decreases the height.<br><b>Hold shift for decimals</b></html>");

		btnNegZ.addActionListener(e ->
		{
			tryAddToDepth((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? -1.0F : -0.1F);
		});
		btnNegZ.setPreferredSize(new Dimension(62, 30));
		btnNegZ.setFont(defaultFont);
		btnNegZ.setToolTipText("<html>Decreases the depth.<br><b>Hold shift for decimals</b></html>");
	}

	public void addComponents()
	{
		add(btnPlusX);
		add(btnPlusY);
		add(btnPlusZ);
		add(xSizeField);
		add(ySizeField);
		add(zSizeField);
		add(btnNegX);
		add(btnNegY);
		add(btnNegZ);
	}

	@Override
	public void updateValues(Element cube)
	{
		if (cube != null)
		{
			xSizeField.setEnabled(true);
			ySizeField.setEnabled(true);
			zSizeField.setEnabled(true);
			xSizeField.setText(df.format(cube.getWidth()));
			ySizeField.setText(df.format(cube.getHeight()));
			zSizeField.setText(df.format(cube.getDepth()));
		}
		else
		{
			xSizeField.setEnabled(false);
			ySizeField.setEnabled(false);
			zSizeField.setEnabled(false);
			xSizeField.setText("");
			ySizeField.setText("");
			zSizeField.setText("");
		}
	}
}
