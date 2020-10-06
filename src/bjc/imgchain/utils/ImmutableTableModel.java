package bjc.imgchain.utils;

import javax.swing.table.DefaultTableModel;

/**
 * An immmutable table model.
 * @author Ben Culkin
 *
 */
public class ImmutableTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 6459890821518409439L;

	/**
	 * Create a new immutable table model
	 * 
	 * @param data The data in the model.
	 * @param columnNames The column names for the model.
	 */
	public ImmutableTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}