
import java.awt.*;
import java.util.*;
import javax.swing.JFrame;
import java.io.*;

class GBar {
	String text;
	int value;

	GBar(String t, int v) {
		text = t;
		value = v;
	}
}

public class GraphBeginnings extends JFrame {

	ArrayList<GBar> gbarArr = new ArrayList<GBar>();

	GraphBeginnings(ArrayList<GBar> garr, String title) {
		super(title);

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

	public void paint(Graphics g) {
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
			g.fillRect(x_bar_start, y_start, scaledValue, bar_height);

			y_start += fontHeight + 10;
		}

		g.drawLine(strMaxWidth, top, strMaxWidth, dimen.height);
	}

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

		try {
			File fi = new File(args[0]);
			Scanner scan = new Scanner(fi);
			scan.nextLine();
			if (scan.hasNextLine()) {

				line = scan.nextLine();
				StringTokenizer reader = new StringTokenizer(line, ";");
				name = reader.nextToken();
				num = Integer.parseInt(reader.nextToken().toString().trim());
				garr.add(new GBar (name, num));
			} else {
				scan.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

		GraphBeginnings gb = new GraphBeginnings(garr, title);
		title = gb.getTitle(args[0]);
		gb.setTitle(title);

	}

}
