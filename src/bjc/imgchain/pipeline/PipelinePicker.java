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

public class PipelinePicker extends JDialog {
	private static final long serialVersionUID = 1L;

	public String pipeName;

	public PipelinePicker() {
		super();

		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Add a Stage");

		setupGUI();
	}

	private void setupGUI() {
		setLayout(new BorderLayout());

		DefaultListModel<String> pipeModel = new DefaultListModel<>();
		for (String pipeName : ImgChain.chan.pipelineRepo.keySet()) {
			pipeModel.addElement(pipeName);
		}

		JList<String> pipeList = new JList<>(pipeModel);
		JScrollPane listScroll = new JScrollPane(pipeList);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));

		JButton addStage = new JButton("Select Pipe");
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
