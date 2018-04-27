package bjc.imgchain.pipeline.stages;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bjc.imgchain.ImgChain;
import bjc.imgchain.pipeline.Pipeline;
import bjc.imgchain.pipeline.PipelinePicker;
import bjc.imgchain.pipeline.StageType;

public class PipeStage extends AbstractPipelineStage {
	private String pipeName;

	public PipeStage() {
		super(StageType.IMGTRANS);
	}

	@Override
	public Image process(Image inp) {
		Pipeline pipeline = ImgChain.chan.pipelineRepo.get(pipeName);

		return pipeline.process(inp);
	}

	@Override
	public String name() {
		return "Sub-pipeline";
	}

	@Override
	public String description() {
		return "Run the steps of another pipeline";
	}

	@Override
	public JComponent getEditor() {
		JPanel holder = new JPanel();
		holder.setLayout(new BorderLayout());

		JLabel pipeLabel = new JLabel("Pipeline");

		JTextField pipeField = new JTextField(80);
		pipeField.addPropertyChangeListener("value", (ev) -> {
			pipeName = pipeField.getText();
		});

		JButton pipeButton = new JButton("Select Pipeline");
		pipeButton.addActionListener((ev) -> {
			PipelinePicker pick = new PipelinePicker();

			pick.pack();
			pick.setVisible(true);

			if (pick.pipeName == null) {
				System.out.println("WARN: picked null pipe");
				return;
			}

			pipeField.setText(pick.pipeName);
		});

		holder.add(pipeLabel, BorderLayout.LINE_START);
		holder.add(pipeField, BorderLayout.CENTER);
		holder.add(pipeButton, BorderLayout.LINE_END);
		
		return holder;
	}
}
