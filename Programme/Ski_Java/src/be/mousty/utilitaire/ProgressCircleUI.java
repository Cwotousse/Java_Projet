package be.mousty.utilitaire;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressCircleUI extends BasicProgressBarUI {
    @Override public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);
        int v = Math.max(d.width, d.height);
        d.setSize(v, v);
        return d;
    }
    @Override public void paint(Graphics g, JComponent c) {
        //public void paintDeterminate(Graphics g, JComponent c) {
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth  = progressBar.getWidth()  - b.right - b.left;
        int barRectHeight = progressBar.getHeight() - b.top - b.bottom;
        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double degree = 360 * progressBar.getPercentComplete();
        double sz = Math.min(barRectWidth, barRectHeight);
        double cx = b.left + barRectWidth  * .5;
        double cy = b.top  + barRectHeight * .5;
        double or = sz * .5;
        //double ir = or - 20;
        double ir = or * .5; //.8;
        Shape inner  = new Ellipse2D.Double(cx - ir, cy - ir, ir * 2, ir * 2);
        Shape outer  = new Ellipse2D.Double(cx - or, cy - or, sz, sz);
        Shape sector = new Arc2D.Double(cx - or, cy - or, sz, sz, 90 - degree, degree, Arc2D.PIE);

        Area foreground = new Area(sector);
        Area background = new Area(outer);
        Area hole = new Area(inner);

        foreground.subtract(hole);
        background.subtract(hole);

        // draw the track
        g2.setPaint(new Color(0xDDDDDD));
        g2.fill(background);

        // draw the circular sector
        //AffineTransform at = AffineTransform.getScaleInstance(-1.0, 1.0);
        //at.translate(-(barRectWidth + b.left * 2), 0);
        //AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(degree), cx, cy);
        //g2.fill(at.createTransformedShape(area));
        g2.setPaint(progressBar.getForeground());
        g2.fill(foreground);
        g2.dispose();

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            paintString(g, b.left, b.top, barRectWidth, barRectHeight, 0, b);
        }
    }
}

class Task extends SwingWorker<String, Void> {
    private final Random rnd = new Random();
    @Override public String doInBackground() {
        int current = 0;
        int lengthOfTask = 100;
        while (current <= lengthOfTask && !isCancelled()) {
            try { // dummy task
                Thread.sleep(rnd.nextInt(50) + 1);
            } catch (InterruptedException ex) {
                return "Interrupted";
            }
            setProgress(100 * current / lengthOfTask);
            current++;
        }
        return "Done";
    }
}

class ProgressListener implements PropertyChangeListener {
    private final JProgressBar progressBar;
    protected ProgressListener(JProgressBar progressBar) {
        this.progressBar = progressBar;
        this.progressBar.setValue(0);
    }
    @Override public void propertyChange(PropertyChangeEvent e) {
        String strPropertyName = e.getPropertyName();
        if ("progress".equals(strPropertyName)) {
            progressBar.setIndeterminate(false);
            int progress = (Integer) e.getNewValue();
            progressBar.setValue(progress);
        }
    }
}

/*import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicProgressBarUI;



public class ProgressCircleUI extends BasicProgressBarUI {
	@Override public Dimension getPreferredSize(JComponent c) {
		Dimension d = super.getPreferredSize(c);
		int v = Math.max(d.width, d.height);
		d.setSize(v, v);
		return d;
	}
	@Override public void paint(Graphics g, JComponent c) {
		Insets b = progressBar.getInsets(); // area for border
		int barRectWidth  = progressBar.getWidth()  - b.right - b.left;
		int barRectHeight = progressBar.getHeight() - b.top - b.bottom;
		if (barRectWidth <= 0 || barRectHeight <= 0) {
			return;
		}

		// draw the cells
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(progressBar.getForeground());
		double degree = 360 * progressBar.getPercentComplete();
		double sz = Math.min(barRectWidth, barRectHeight);
		double cx = b.left + barRectWidth  * .5;
		double cy = b.top  + barRectHeight * .5;
		double or = sz * .5;
		double ir = or * .5; //or - 20;
		Shape inner = new Ellipse2D.Double(cx - ir, cy - ir, ir * 2, ir * 2);
		Shape outer = new Arc2D.Double(
				cx - or, cy - or, sz, sz, 90 - degree, degree, Arc2D.PIE);
		Area area = new Area(outer);
		area.subtract(new Area(inner));
		g2.fill(area);
		g2.dispose();

		// Deal with possible text painting
		if (progressBar.isStringPainted()) {
			paintString(g, b.left, b.top, barRectWidth, barRectHeight, 0, b);
		}
	}

	// Pour voir comment ça fonctionne
	 public static void main(String... args) {
		EventQueue.invokeLater(() -> {
			JFrame f = new JFrame();
			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			f.getContentPane().add(new CircularProgressTest().makeUI());
			f.setSize(320, 240);
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		});
	}
	 
	
	class CircularProgressTest {
		public JComponent makeUI() {
			JProgressBar progress = new JProgressBar();
			// use JProgressBar#setUI(...) method
			progress.setUI(new ProgressCircleUI());
			progress.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
			progress.setStringPainted(true);
			progress.setFont(progress.getFont().deriveFont(24f));
			progress.setForeground(Color.ORANGE);

			(new Timer(50, e -> {
				int iv = Math.min(100, progress.getValue() + 1);
				progress.setValue(iv);
			})).start();

			JPanel p = new JPanel();
			p.add(progress);
			return p;
		}
	}
}*/
