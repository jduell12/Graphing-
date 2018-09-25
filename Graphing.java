

import java.awt.*;
import java.util.*;
import javax.swing.JFrame;
import java.io.*;

//creates the bars in the bar graph with an appropriate label. Text is the label for the bar and the value is how far the bar will appear on the graph 
class GBar {
	String text;
	int value;

	GBar(String t, int v) {
		text = t;
		value = v;
	}
}

public class GraphBeginnings extends JFrame {

	// an arrayList that holds the bars and labels that were created with GBar class
	ArrayList<GBar> gbarArr = new ArrayList<GBar>();

	// creates a graph based on the bars and labels in the arrayList
	GraphBeginnings(ArrayList<GBar> garr, String title) {
		super(title);

		gbarArr = garr;
		setSize(600, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// gets the max width of the longest text in the arrayList
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

	// gets the largest value from the arrayList bars
	int getMaxBarWidth(ArrayList<GBar> garr) {
		int maxValue = 0;
		for (int i = 0; i < garr.size(); i++) {
			int value = garr.get(i).value;
			if (value > maxValue) {
				maxValue = value;
			}
		}
		return maxValue;
	}

	// creates the bar graph
	public void paint(Graphics g) {
		int border = 10;
		super.paint(g);
		Dimension dimen = getSize();
		Insets insets = getInsets();
		int top = insets.top + border;
		int left = insets.left + border;
		int right = insets.right + border;

		Font font = g.getFont();
		FontMetrics fm = getFontMetrics(font);
		int fontHeight = fm.getHeight();
		int maxAscent = fm.getMaxAscent();

		int strMaxWidth = left + getMaxTextWidth(gbarArr, fm);
		int x_bar_start = strMaxWidth + 1;

		int barMaxValue = getMaxBarWidth(gbarArr);
		double scale = (dimen.width - x_bar_start - right) / (double) barMaxValue;

		int y_start = top;
		int bar_height = fontHeight;

		for (int i = 0; i < gbarArr.size(); i++) {
			String text = gbarArr.get(i).text;
			int strWidth = fm.stringWidth(text);
			int value = gbarArr.get(i).value;
			int scaledValue = (int) (value * scale);
			g.drawString(text, strMaxWidth - strWidth, y_start + maxAscent);
			g.setColor(Color.green);
			g.fillRect(x_bar_start, y_start, scaledValue, bar_height);
			g.setColor(Color.BLACK);

			y_start += fontHeight + 10;
		}
		// changes color on the bars to green
		g.setColor(Color.GREEN);
		g.drawLine(strMaxWidth, top, strMaxWidth, dimen.height);
		// changes color back to black so text is written in black
		g.setColor(Color.BLACK);

		// creates a red border around the graph
		Stroke stroke1 = new BasicStroke(10);
		g.setColor(Color.RED);
		((Graphics2D) g).setStroke(stroke1);
		g.drawRect(3, insets.top + 3, ((dimen.width) - 6), (dimen.height) - 28);

	}

	// Gets the title from the first line in the file 
	public String getTitle(String fileName) {
		String title = "";
		try {
			File fi = new File(fileName);
			Scanner scan = new Scanner(fi);
			title = scan.nextLine();
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return title;
	}

	public static void main(String[] args) {
		String title = "";
		String name = "";
		int num = 0;
		String line = "";

		ArrayList<GBar> garr = new ArrayList<GBar>();

		// gets the file that was inputed as an argument to use to crete the graph
		try {
			File fi = new File(args[0]);
			Scanner scan = new Scanner(fi);
			
			// skips first line since that is used as the title 
			scan.nextLine();

			// as long as the file has more input it grabs the text and number value and creates a new bar with the text and number value and adds it to the arrayList
			while (scan.hasNext()) {
				line = scan.nextLine();
				StringTokenizer reader = new StringTokenizer(line, ";");
				name = reader.nextToken();
				num = Integer.parseInt(reader.nextToken().toString().trim());
				garr.add(new GBar(name, num));
			}
			if (!scan.hasNext()) {
				scan.close();
			}

		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

		GraphBeginnings gb = new GraphBeginnings(garr, title);
		// gets the title from the first line in the file and sets the title to that line
		title = gb.getTitle(args[0]);
		gb.setTitle(title);

	}

}
