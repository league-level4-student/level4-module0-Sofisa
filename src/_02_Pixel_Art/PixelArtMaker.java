package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;




//import _05_Serialization.SaveData;

public class PixelArtMaker implements MouseListener {
	// .dat file for saving
	private static final String PIXEL_FILE = "src/_02_Pixel_Art/SavedPixel.dat";

	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	JButton save;
	
	ColorSelectionPanel csp;

	public void start() {
		gip = new GridInputPanel(this);
		window = new JFrame("Pixel Art");
		window.setLayout(new FlowLayout());
		window.setResizable(false);

		window.add(gip);

		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void submitGridData(int w, int h, int r, int c) {
		
		gp = new GridPanel(w, h, r, c);
		csp = new ColorSelectionPanel();
		window.remove(gip);
		window.add(gp);
		window.add(csp);
		
		
		// save button code
		save = new JButton();
		save.setText("save art");
		window.add(save);

		gp.repaint();
		gp.addMouseListener(this);
		window.pack();
	}

	//save grid
	private static void saveGrid(GridPanel gp2) {
		try (FileOutputStream fos = new FileOutputStream(new File(PIXEL_FILE));
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(gp2);
		} catch (IOException p) {
			p.printStackTrace();
		}
	}
	
	
	//load grid
	public static SaveGrid load() {
		try (FileInputStream fis = new FileInputStream(new File(PIXEL_FILE)); ObjectInputStream ois = new ObjectInputStream(fis)) {
			return (SaveGrid) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			
			// This can occur if the object we read from the file is not
			// an instance of any recognized class
			e.printStackTrace();
			return null;
		}
	}
	
	
	

	public static void main(String[] args) {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();

		// if save button pressed
		if (e.getSource() == save) {
			saveGrid(gp);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
