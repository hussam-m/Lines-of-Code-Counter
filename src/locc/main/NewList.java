package locc.main;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class NewList extends JList {

	public List<ExtensionWrapper> items = new LinkedList<ExtensionWrapper>();
	public DefaultListModel<String> model = new DefaultListModel<String>();
	
	@SuppressWarnings("unchecked")
	public NewList(List<ExtensionWrapper> objects, String[] items) {
		super(items);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setModel(model);
		for(int i = 0; i < objects.size(); i++)
			model.addElement(objects.get(i).toString());
		
		ToolTipManager.sharedInstance().registerComponent(this);
		
		this.items = objects;
	}

	public String getToolTipText(MouseEvent event) {
		Point p = event.getPoint();
		int location = locationToIndex(p);
		String tip = null;
		if(location < items.size() && location >= 0)
			tip = items.get(location).getToolTip();
		return tip;
	}
	
	public void addExtension(ExtensionWrapper item) {
		items.add(item);
		model.addElement(item.toString());
	}
	
	public void deleteExtension(int index) {
		items.remove(index);
		model.remove(index);
	}
	
	public ExtensionWrapper getExtensionWrapperAt(int index) {
		return items.get(index);
	}
	
	public List<ExtensionWrapper> getAllExtensionWrappers() {
		return items;
	}
	
}
