package bjc.imgchain.pipeline;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bjc.imgchain.ImgChain;

/**
 * GUI to pick a pipeline to apply.
 * @author Ben Culkin
 *
 */
public class PipelinePicker extends JDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * The name of the pipe to apply.
	 */
	public String pipeName;

	/**
	 * Create a new pipeline picker GUI.
	 */
	public PipelinePicker() {
		super();

		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Apply a Pipeline");

		setupGUI();
	}

	private void setupGUI() {
		setLayout(new BorderLayout());

		DefaultListModel<String> pipeModel = new DefaultListModel<>();
		for (String pipelneName : ImgChain.chan.pipelineRepo.keySet()) {
			pipeModel.addElement(pipelneName);
		}

		JList<String> pipeList = new JList<>(pipeModel);
		JScrollPane listScroll = new JScrollPane(pipeList);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));

		JButton addStage = new JButton("Apply Pipe");
		addStage.addActionListener((ev) -> {
			pipeName = pipeList.getSelectedValue();

			setVisible(false);
			dispose();
		});

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((ev) -> {
			setVisible(false);
			dispose();
		});

		buttonPanel.add(addStage);
		buttonPanel.add(cancel);

		add(listScroll, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);
	}
}
