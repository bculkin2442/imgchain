package bjc.imgchain;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

import bjc.imgchain.pipeline.MutablePipeline;
import bjc.imgchain.pipeline.PipelineStage;
import bjc.imgchain.pipeline.stages.BrightnessStage;
import bjc.imgchain.pipeline.stages.ColorSkewStage;
import bjc.imgchain.pipeline.stages.GaussStage;
import bjc.imgchain.pipeline.stages.GreyscaleStage;
import bjc.imgchain.pipeline.stages.IDStage;
import bjc.imgchain.pipeline.stages.LoadStage;
import bjc.imgchain.pipeline.stages.NegativeStage;
import bjc.imgchain.pipeline.stages.PipeStage;
import bjc.imgchain.pipeline.stages.RecallStage;
import bjc.imgchain.pipeline.stages.SaveStage;
import bjc.imgchain.pipeline.stages.StagePicker;
import bjc.imgchain.pipeline.stages.StashStage;
import bjc.imgchain.pipeline.stages.ThresholdStage;

/**
 * Edit an image pipeline.
 * 
 * @author acm
 *
 */
public class ImgPipeline extends JInternalFrame {
	private static final long serialVersionUID = 2252473157369730085L;

	private MutablePipeline editing;

	/**
	 * Create a editor for a new image pipeline.
	 */
	public ImgPipeline() {
		super("Pipeline Editor - New pipeline", true, true, true, true);

		editing = new MutablePipeline();

		setupGUI();
	}

	/**
	 * Create an editor for an existing pipeline.
	 * 
	 * @param pipe
	 *                The pipeline to edit.
	 */
	public ImgPipeline(MutablePipeline pipe) {
		super("Pipeline Editor - New pipeline", true, true, true, true);

		editing = pipe;

		setupGUI();
	}

	private void setupGUI() {
		setSize(320, 320);
		setLayout(new GridLayout(1, 1));

		JPanel stageEditor = new JPanel();
		stageEditor.setLayout(new BorderLayout());
		TitledBorder border = new TitledBorder(new BevelBorder(BevelBorder.LOWERED), "No Stage Selected");
		stageEditor.setBorder(border);

		DefaultListModel<PipelineStage> mod = new DefaultListModel<>();
		for (PipelineStage stage : editing.stages()) {
			mod.addElement(stage);
		}

		JList<PipelineStage> stageList = new JList<>(mod);
		stageList.addListSelectionListener(e -> {
			if (e.getValueIsAdjusting())
				return;

			PipelineStage stag = stageList.getSelectedValue();

			stageEditor.removeAll();

			stageEditor.add(stag.getEditor(), BorderLayout.CENTER);
			border.setTitle(stag.name());

			stageEditor.repaint();
		});
		
		JScrollPane stageScroll = new JScrollPane(stageList);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));

		JPanel stageButtons = new JPanel();
		stageButtons.setLayout(new GridLayout(1, 2));

		JButton addStage = new JButton("Add Stage");
		addStage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StagePicker pick = new StagePicker();
				pick.pack();
				pick.setVisible(true);

				if (pick.stageName == null) {
					System.out.println("WARN: picked null stage");
					return;
				}

				PipelineStage stag;
				/*
				 * :AddStage
				 */
				switch (pick.stageName) {
				case "Identity": {
					stag = new IDStage();
				}
					break;
				case "Greyscale": {
					stag = new GreyscaleStage();
				}
					break;
				case "Color Skew": {
					stag = new ColorSkewStage();
				}
					break;
				case "Sepia": {
					/*
					 * NOTE: these values were pulled from somewhere on the internet, and tweaked
					 * slightly to work better.
					 */
					stag = new ColorSkewStage(.393, .769, .189, .349, .686, .168, .272, .534, .131);
				}
				case "Negative": {
					stag = new NegativeStage();
				}
					break;
				case "Gaussian Blur": {
					stag = new GaussStage();
				}
					break;
				case "Tint": {
					stag = new BrightnessStage();
				}
					break;
				case "Threshold": {
					stag = new ThresholdStage();
				}
					break;
				case "Sub-pipeline": {
					stag = new PipeStage();
				}
					break;
				case "Load Image": {
					stag = new LoadStage();
				}
					break;
				case "Save Image": {
					stag = new SaveStage();
				}
					break;
				case "Stash Image": {
					stag = new StashStage();
				}
					break;
				case "Recall Image": {
					stag = new RecallStage();
				}
					break;
				default:
					JOptionPane.showMessageDialog(ImgChain.chan.desktop, String
							.format("Attempted to add unknown stage '%s'", pick.stageName));
					return;
				}

				editing.addStage(stag);
				mod.addElement(stag);

				stageList.setSelectedValue(stag, true);
			}
		});

		JButton delStage = new JButton("Delete Stage");
		delStage.addActionListener(e -> {
			int res = JOptionPane.showInternalConfirmDialog(this,
					"Are you sure you want to remove the stage?");

			if (res != JOptionPane.YES_OPTION) {
				return;
			}

			int idx = stageList.getSelectedIndex();

			mod.remove(idx);
			editing.removeStage(idx);

			stageEditor.removeAll();
			border.setTitle("No Stage Selected");
		});

		stageButtons.add(addStage);
		stageButtons.add(delStage);

		JButton savePipeline = new JButton("Save Pipeline");
		savePipeline.addActionListener((ev) -> {
			String newName = JOptionPane.showInternalInputDialog(this, "Enter Pipeline Name");

			editing.name(newName);

			ImgChain.chan.addPipe(editing);

			setTitle("Pipeline Editor - " + newName);
		});

		buttonPanel.add(stageButtons);
		buttonPanel.add(savePipeline);

		listPanel.add(stageScroll, BorderLayout.CENTER);
		listPanel.add(buttonPanel, BorderLayout.PAGE_END);

		JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, stageEditor);

		add(main);
	}
}
