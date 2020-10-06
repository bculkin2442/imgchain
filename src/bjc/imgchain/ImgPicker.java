package bjc.imgchain;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Image picker dialog.
 * 
 * @author Ben Culkin
 *
 */
public class ImgPicker extends JDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * Nane of the picked image.
	 */
	public String imageName;

	/**
	 * Create a new image picker.
	 */
	public ImgPicker() {
		super();

		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Recall an Image");

		setupGUI();
	}

	private void setupGUI() {
		setLayout(new BorderLayout());

		DefaultListModel<String> imgModel = new DefaultListModel<>();
		for (String imgName : ImgChain.chan.imageRepo.keySet()) {
			imgModel.addElement(imgName);
		}

		JList<String> imgList = new JList<>(imgModel);
		JScrollPane listScroll = new JScrollPane(imgList);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));

		JButton addStage = new JButton("Recall Image");
		addStage.addActionListener((ev) -> {
			imageName = imgList.getSelectedValue();

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
