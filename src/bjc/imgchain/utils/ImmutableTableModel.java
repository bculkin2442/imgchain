package bjc.imgchain.utils;

import javax.swing.table.DefaultTableModel;

public class ImmutableTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 6459890821518409439L;

	public ImmutableTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}


	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}