package com.mrcrayfish.modelcreator.dialog;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.mrcrayfish.modelcreator.ForgeImporter;
import com.mrcrayfish.modelcreator.texture.ForgeZipFile;
import com.mrcrayfish.modelcreator.util.components.HBox;
import com.mrcrayfish.modelcreator.util.components.HGap;
import com.mrcrayfish.modelcreator.util.components.ImportedModel;
import com.mrcrayfish.modelcreator.util.components.MarginBox;
import com.mrcrayfish.modelcreator.util.components.Table;
import com.mrcrayfish.modelcreator.util.components.VBox;
import com.mrcrayfish.modelcreator.util.components.VGap;
import com.mrcrayfish.modelcreator.util.components.VStretchBox;

public class ImportFromMinecraftDialog
{
	private static JDialog dialog;

	public static void show(JFrame parent, ImportListener listener)
	{
		JPanel dialogContent = getDialogContent(parent, listener);
		dialog = createDialog(parent, dialogContent);
		showDialog(dialog);
	}

	private static JPanel getDialogContent(JFrame parent, ImportListener listener)
	{
		ArrayList<String> versions = ForgeImporter.GetAvailableMinecraftVersions();

		JComboBox<String> cbxVersions = new JComboBox<String>(versions.toArray(new String[0]));
		JTextArea ta = new JTextArea();
		JScrollPane ta_sp = new JScrollPane(ta);
		ta_sp.setPreferredSize(new Dimension(200, 200));
		
		cbxVersions.setSelectedIndex(-1);

		Table<ImportedModel> table = new Table<ImportedModel>(new ImportedModel());
		
		Table<ImportedModel.BlockState> tblBlockStates
			= new Table<ImportedModel.BlockState>(new ImportedModel.BlockState(null));
		
		tblBlockStates.setPreferredSize(new Dimension(200, 200));
		
		ArrayList<ImportedModel> list = new ArrayList<ImportedModel>();
		
		cbxVersions.addActionListener(e ->
		{
			String version = (String)cbxVersions.getSelectedItem();
			
			ForgeZipFile file = new ForgeZipFile(version);
			ImportedModel[] models = file.getModelJsonFiles();

			table.setContents(models);
		});	

		table.setPreferredSize(new Dimension(200, 400));
		
		JButton okButton = new JButton("ok");
		okButton.setEnabled(false);
		
		table.setSelectionListener(new Table.SelectionListener<ImportedModel>()
		{
			@Override
			public void selectionChanged(ImportedModel record)
			{
				String version = (String)cbxVersions.getSelectedItem();
				okButton.setEnabled(true);
				ForgeZipFile zipFile = new ForgeZipFile(version);
				ImportedModel.BlockState[] blockStates = ForgeImporter.getBlockStates(record);
				
				if (blockStates != null)
					tblBlockStates.setContents(blockStates);
				else
					tblBlockStates.clearContents();

				String json = zipFile.openModel(record);
				ta.setText(json);
			}
		});
		
		okButton.addActionListener(e ->
		{
			ImportedModel model = table.getSelectedItem();
			dialog.dispose();
			listener.onImportRequested(model);
		});
		
		return new MarginBox(
			new HBox(
				new VBox
				(
					new JLabel("Select minecraft version:"),
					VGap.withhHeight(3),
					new VStretchBox(cbxVersions),
					VGap.withhHeight(15),
					table,
					VGap.withhHeight(15),
					okButton
				),
				HGap.withhWidth(15),
				new VBox
				(
					new JLabel("Block states:"),
					VGap.withhHeight(3),
					tblBlockStates,
					VGap.withhHeight(15)
				)
			)
		);
	}

	private static JDialog createDialog(JFrame parent, JPanel dialogContent)
	{
		JDialog dialog = new JDialog(parent, "Import from Minecraft", false);

		Container c = dialog.getContentPane();
		Point pt = c.getLocation();
		pt = SwingUtilities.convertPoint(c, pt, dialog);
		
		dialog.setResizable(false);

		dialog.add(dialogContent);
		dialog.pack();
				
		Dimension contentSize = dialogContent.getPreferredSize();
		contentSize.height += pt.y;
		
		dialog.setPreferredSize(contentSize);
		return dialog;
	}

	private static void showDialog(JDialog dialog)
	{
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	@FunctionalInterface
	public interface ImportListener
	{
		public void onImportRequested(ImportedModel model);
	}
}
