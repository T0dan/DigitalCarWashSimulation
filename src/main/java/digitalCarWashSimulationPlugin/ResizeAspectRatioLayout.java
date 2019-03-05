package digitalCarWashSimulationPlugin;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class ResizeAspectRatioLayout implements LayoutManager {

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return parent.getComponent(0).getPreferredSize();
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return parent.getComponent(0).getPreferredSize();
	}

	@Override
	public void layoutContainer(Container parent) {
		Component content = parent.getComponent(0);
		Insets insets = parent.getInsets();
		int containerWidth = parent.getWidth() - insets.left - insets.right;
		int containerHeight = parent.getHeight() - insets.top - insets.bottom;
		
		Dimension contentSize = content.getPreferredSize();
		Dimension newContentSize;
		
		double widthScale = (double)containerWidth / contentSize.getWidth();
		double heightScale = (double)containerHeight / contentSize.getHeight();
		
		if (widthScale < heightScale) {
		newContentSize = new Dimension((int) (widthScale * contentSize.getWidth()), (int)(widthScale * contentSize.getHeight()));
		} else {
			newContentSize = new Dimension((int) (heightScale * contentSize.getWidth()), (int)(heightScale * contentSize.getHeight()));
		}
		
		int offsetX = (int)((containerWidth - newContentSize.getWidth())/2);
		int offsetY = (int)((containerHeight - newContentSize.getHeight())/2);
			
		content.setBounds(offsetX, offsetY, (int)newContentSize.getWidth(), (int)newContentSize.getHeight());
	}}