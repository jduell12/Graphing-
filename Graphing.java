
import java.awt.*;
import java.util.*;
import javax.swing.JFrame;

class GBar {
	String text;
	int value; 
	GBar(String t, int v){
		text = t;
		value = v;
	}
}

public class GraphBeginnings extends JFrame {

	ArrayList<GBar> gbarArr = new ArrayList<GBar>();
	
	GraphBeginnings(ArrayList<GBar> garr){
		super("Graph Beginnings");
		
		gbarArr = garr;
		setSize(600, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	int getMaxTextWidth(ArrayList<GBar> garr, FontMetrics fm) {
		int maxValue = 0;
		for (int i = 0; i < garr.size(); i++) {
			int width = fm.stringWidth(garr.get(i).text);
			if (width > maxValue) {
				maxValue = width;
			}
		}
		return maxValue;
	}
	
	public void pain (Graphics g) {
		super.paint(g);
		Dimension dimen = getSize();
		Insets insets = getInsets();
		int top = insets.top;
		int left = insets.left;
		int right = insets.right;
		
		Font font = g.getFont();
		FontMetrics fm = getFontMetrics(font);
		int fontHeight = fm.getHeight();
		int maxAscent = fm.getMaxAscent();
	}
	
}
