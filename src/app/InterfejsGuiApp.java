package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.freixas.jcalendar.JCalendarCombo;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.JXTipOfTheDay;
import org.jdesktop.swingx.tips.DefaultTip;
import org.jdesktop.swingx.tips.DefaultTipOfTheDayModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.apache.log4j.*;


import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Cursor;

/**
 * Program InterfejsGuiApp3.0
 * Klasa InterfejsGuiApp 
 * @version 3.0 (15.05.2017)
 * @author Małgorzata Zawisza
*/

public class InterfejsGuiApp extends JFrame implements ActionListener{
	
	private JPanel glownyPanel, statusPanel;
	private JMenu plik, edycja, obliczenia, pomoc;
	private JMenuItem zero, save, random, exit, cut, copy, paste, average, add, max, min, abProgram, abAuthor;
	private JTable table;
	private JToolBar toolBar;
	private JSpinner spinnerWiersz, spinnerKolumna;
	private JTextField textField;
	private JButton btnOK, btnLosuj, btnOblicz, btnZapisz, btnWyzeruj;
	private JComboBox<String> comboBox;
	private JTextArea textArea;
	private JSeparator separatorGorny, separatorDolny, separatorDolny2;
	private JLabel lblWprowadzLiczb, lblNumerWiersza, lblNumerKolumny, lblObliczenia, lblStatusInfo;
	private JCalendarCombo kalendarz;
	
	private JXPanel taskPane;
	private ChartPanel chartPanel;
	
	private String schowek, formatowanaData;
	
	private static Logger logger;

	
	
	/* OPIS KOMPONENTÓW -------------------------------------------------------------------------*/
	
	/**
	 * Metoda tworząca i pokazująca okno ze wskazówkami 'Tip of the day'
	 */
	public void showTipOfTheDay(){
		
		DefaultTipOfTheDayModel model = new DefaultTipOfTheDayModel();
		model.add( new DefaultTip ("Wskazówka nr 1",
			      "To jest pierwsza pouczająca wskazówka.") );

		model.add( new DefaultTip ("Wskazówka nr 2",
			      "A to z kolei pouczająca wskazówka nr 2.") );

		
		JXTipOfTheDay tipOfTheDayy = new JXTipOfTheDay(model);
		
		JXPanel panel = new JXPanel();
		tipOfTheDayy.showDialog(panel);
	}
	
	/**
	 * Metoda tworząca obiekt typu histogram
	 * @return zwraca obiekt typu ChartPanel
	 */
	public ChartPanel createChart(){
		
		HistogramDataset hd = new HistogramDataset();

		double values[] = new double[25];
		int i = 0;
		
		for (int col = 0; col<5; col++){
			
			for(int row = 0; row<5; row++){
				
				values [i] = Double.parseDouble( String.valueOf( table.getValueAt(row, col) ) );	
				//System.out.println(row + " " + col + " <- " + i + " = " + Integer.parseInt( String.valueOf( table.getValueAt(row, col) ) ));
				i++;
			}
		}
		
        hd.addSeries("series1", values, 21, -10.5, 10.5);
        
        JFreeChart chart = ChartFactory.createHistogram("Histogram",
                "wartość komórki", "ilosc wystapienia", hd, PlotOrientation.VERTICAL, false, false,
                false);
        
        
        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(new NumberTickUnit(1));
        xAxis.setRange(-10.5, 10.5);
        xAxis.setLabel("wartość komórki");
        xAxis.setLabelFont(new Font("Tahoma", Font.PLAIN, 13));
        xAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 13));
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickUnit(new NumberTickUnit(5));
        yAxis.setRange(0, 25);
        yAxis.setLabel("ilość wsytąpienia");
        yAxis.setLabelFont(new Font("Tahoma", Font.PLAIN, 13));
        yAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 13));
        
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(95, 121, 155));
        plot.setDomainAxis(xAxis);
        plot.setRangeAxis(yAxis);
        
        ChartPanel cp = new ChartPanel(chart);
        cp.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        cp.setBounds(210, 307, 564, 150);
        
        return cp;
	}
	
	/**
	 * Metoda aktualizująca obiekt typu histogram
	 */
	public void updateChart(){
		
		chartPanel.setVisible(false);
		
		HistogramDataset hd = new HistogramDataset();

		double values[] = new double[25];
		int i = 0;
		
		for (int col = 0; col<5; col++){
			
			for(int row = 0; row<5; row++){
				
				values [i] = Double.parseDouble( String.valueOf( table.getValueAt(row, col) ) );	
				//System.out.println(row + " " + col + " <- " + i + " = " + Integer.parseInt( String.valueOf( table.getValueAt(row, col) ) ));
				i++;
			}
		}
		
        hd.addSeries("series1", values, 21, -10.5, 10.5);
        
        JFreeChart chart = ChartFactory.createHistogram("Histogram",
                "wartość komórki", "ilość wystąpienia", hd, PlotOrientation.VERTICAL, false, false,
                false);
        
        
        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(new NumberTickUnit(1));
        xAxis.setRange(-10.5, 10.5);
        xAxis.setLabel("wartość komórki");
        xAxis.setLabelFont(new Font("Tahoma", Font.PLAIN, 13));
        xAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 13));
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickUnit(new NumberTickUnit(5));
        yAxis.setRange(0, 25);
        yAxis.setLabel("ilość wsytąpienia");
        yAxis.setLabelFont(new Font("Tahoma", Font.PLAIN, 13));
        yAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 13));
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(95, 121, 155));
        plot.setDomainAxis(xAxis);
        plot.setRangeAxis(yAxis);
        
        
        
        chartPanel = createChart();
        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        chartPanel.setBounds(210, 307, 564, 150);
        glownyPanel.add(chartPanel);
        chartPanel.setVisible(true);

        
	}
	
	/**
	 * Metoda tworząca obiekt typu panel pod TaskPane
	 * @return zwraca obiekt typu JXPanel
	 */
	public JXPanel createTaskPane(){    
		
		JXPanel panel = new JXPanel();
		panel.setBounds(0, 50, 200, 578);
		panel.setLayout(null);

		JXButton b1, b3, b4, b5, b6, b7, b8;
		b1 = new JXButton();
		b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b1.setSize(40, 15);
		b1.setBorderPainted(false);
		b1.setPaintBorderInsets(false);
		b1.setOpaque(false);
		b1.setBorder(null);
		b1.setFont(new Font("Tahoma", Font.BOLD, 13));
		b1.setText("Zapisz dokument");
		b1.setBackground(new Color(51, 102, 204));
		
		//InterfejsGuiApp.class.getResource("resources/images/strzalka.png");
		b1.setIcon(new ImageIcon("bin/resources/images/strzalka.png"));
		
		b1.setHorizontalAlignment(JXButton.LEFT);
		b1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				try {
					saveToFile();
				} catch (FileNotFoundException e1) {
					
					e1.printStackTrace();
				}
				lblStatusInfo.setText("Zapisano do pliku table.txt");
            }
        });
		
		b3 = new JXButton();
		b3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b3.setBorderPainted(false);
		b3.setPaintBorderInsets(false);
		b3.setOpaque(false);
		b3.setBorder(null);
		b3.setFont(new Font("Tahoma", Font.BOLD, 13));
		b3.setText("Oblicz średnią");
		b3.setBackground(new Color(51, 102, 204));
		b3.setIcon(new ImageIcon("bin/resources/images/strzalka.png"));
		b3.setHorizontalAlignment(JXButton.LEFT);
		b3.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				formatDate();
				textArea.setText( "Obliczono: średnia\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getAverage()) );
				lblStatusInfo.setText("Obliczono średnią");
            }
        });
		
		b4 = new JXButton();
		b4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b4.setBorderPainted(false);
		b4.setPaintBorderInsets(false);
		b4.setOpaque(false);
		b4.setBorder(null);
		b4.setFont(new Font("Tahoma", Font.BOLD, 13));
		b4.setText("Oblicz sumę");
		b4.setBackground(new Color(51, 102, 204));
		b4.setIcon(new ImageIcon("bin/resources/images/strzalka.png"));
		b4.setHorizontalAlignment(JXButton.LEFT);
		b4.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				formatDate();
				textArea.setText( "Obliczono: sumę\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getSum()) );
				lblStatusInfo.setText("Obliczono sumę");
            }
        });
		
		b5 = new JXButton();
		b5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b5.setBorderPainted(false);
		b5.setPaintBorderInsets(false);
		b5.setOpaque(false);
		b5.setBorder(null);
		b5.setFont(new Font("Tahoma", Font.BOLD, 13));
		b5.setText("Wyznacz min");
		b5.setBackground(new Color(51, 102, 204));
		b5.setIcon(new ImageIcon("bin/resources/images/strzalka.png"));
		b5.setHorizontalAlignment(JXButton.LEFT);
		b5.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				formatDate();
				textArea.setText( "Wyznaczono: minimum\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getMin()) );
				lblStatusInfo.setText("Wyznaczono minimum");
            }
        });
		
		b6 = new JXButton();
		b6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b6.setBorderPainted(false);
		b6.setPaintBorderInsets(false);
		b6.setOpaque(false);
		b6.setBorder(null);
		b6.setFont(new Font("Tahoma", Font.BOLD, 13));
		b6.setText("Wyznacz maks");
		b6.setBackground(new Color(51, 102, 204));
		b6.setIcon(new ImageIcon("bin/resources/images/strzalka.png"));
		b6.setHorizontalAlignment(JXButton.LEFT);
		b6.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				formatDate();
				textArea.setText( "Wyznaczono: maksimum\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getMax()) );
				lblStatusInfo.setText("Wyznaczono maksimum");
            }
        });
		
		b7 = new JXButton();
		b7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b7.setBorderPainted(false);
		b7.setPaintBorderInsets(false);
		b7.setOpaque(false);
		b7.setBorder(null);
		b7.setFont(new Font("Tahoma", Font.BOLD, 13));
		b7.setText("A autorze");
		b7.setBackground(new Color(51, 102, 204));
		b7.setIcon(new ImageIcon("bin/resources/images/strzalka.png"));
		b7.setHorizontalAlignment(JXButton.LEFT);
		b7.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				showAboutAuthor();
				lblStatusInfo.setText("Pokazano dane o autorze");
            }
        });
		
		b8 = new JXButton();
		b8.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b8.setBorderPainted(false);
		b8.setPaintBorderInsets(false);
		b8.setOpaque(false);
		b8.setBorder(null);
		b8.setFont(new Font("Tahoma", Font.BOLD, 13));
		b8.setText("O programie");
		b8.setBackground(new Color(51, 102, 204));
		b8.setIcon(new ImageIcon("bin/resources/images/strzalka.png"));
		b8.setHorizontalAlignment(JXButton.LEFT);
		b8.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				showAboutProgram();
				lblStatusInfo.setText("Pokazano dane o programie");
            }
        });

		
		JXTaskPaneContainer taskpanecontainer = new JXTaskPaneContainer();
		taskpanecontainer.setBackground(new Color(129, 157, 193));
		taskpanecontainer.setBounds(0, 0, 200, 578);
		
		JXTaskPane taskpane1 = new JXTaskPane();
		taskpane1.getContentPane().setBackground(new Color(211, 224, 240));
		taskpane1.setFont(new Font("Tahoma", Font.BOLD, 14));
		taskpane1.setTitle("Plik");
		taskpane1.setIcon(new ImageIcon("bin/resources/images/plik.jpg"));
		taskpane1.add(b1);
		
		JXTaskPane taskpane2 = new JXTaskPane();
		taskpane2.getContentPane().setBackground(new Color(211, 224, 240));
		taskpane2.setFont(new Font("Tahoma", Font.BOLD, 14));
		taskpane2.setTitle("Obliczenia");
		taskpane2.setIcon(new ImageIcon("bin/resources/images/obliczenia.jpg"));
		taskpane2.add(b3);
		taskpane2.add(b4);
		taskpane2.add(b5);
		taskpane2.add(b6);
		
		JXTaskPane taskpane3 = new JXTaskPane();
		taskpane3.getContentPane().setBackground(new Color(211, 224, 240));
		taskpane3.setFont(new Font("Tahoma", Font.BOLD, 14));
		taskpane3.setTitle("Aplikacja");
		taskpane3.setIcon(new ImageIcon("bin/resources/images/aplikacja.jpg"));
		taskpane3.add(b7);
		taskpane3.add(b8);
		  
		taskpanecontainer.add(taskpane1);
		taskpanecontainer.add(taskpane2);
		taskpanecontainer.add(taskpane3);
		
		panel.add(taskpanecontainer, BorderLayout.CENTER);
		 
		 
		return panel;

	}
	
	/**
	 * Metoda tworząca obiekt typu MenuBar
	 * @return zwraca obiekt typu JMenuBar
	 */
	public JMenuBar createMenuBar(){
		
		JMenuBar menuBarr = new JMenuBar();
		menuBarr.setBackground(new Color(176, 196, 222));
		
		plik = new JMenu("Plik");
		menuBarr.add(plik);
		save = new JMenuItem("Zapisz tabelę");
		save.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				try {
					saveToFile();
					lblStatusInfo.setText("Zapisano do table.txt");
				}
				catch (FileNotFoundException e1){
					
					e1.printStackTrace();
				}
            }
        });
		plik.add(save);
		zero = new JMenuItem("Wyzeruj tabelę");
		zero.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				sureToSetZero();
				lblStatusInfo.setText("Wyzerowano tablicę");
            }
        });
		plik.add(zero);
		random = new JMenuItem("Losuj wartości");
		random.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				setRandoms();
				lblStatusInfo.setText("Wylosowano wartości");
            }
        });
		plik.add(random);
		exit = new JMenuItem("Wyjście");
		exit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				closeProgram();
            }
        });
		plik.add(exit);
		
		edycja = new JMenu("Edycja");
		menuBarr.add(edycja);
		
		cut = new JMenuItem("Wytnij", KeyEvent.VK_X);
		cut.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				schowek = textField.getText();
				textField.setText("");
				lblStatusInfo.setText("Wycięto zawartość pola");
				
            }
        });
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		edycja.add(cut);
		
		copy = new JMenuItem("Kopiuj", KeyEvent.VK_C);
		copy.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				schowek = textField.getText();
				lblStatusInfo.setText("Skopiowano zawartość pola");
				
            }
        });
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		edycja.add(copy);
		
		paste = new JMenuItem("Wklej", KeyEvent.VK_V);
		paste.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				textField.setText(schowek);
				lblStatusInfo.setText("Wklejono zawartość pola");
				
            }
        });
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		edycja.add(paste);
		
		obliczenia = new JMenu("Obliczenia");
		menuBarr.add(obliczenia);
		average = new JMenuItem("Oblicz średnią");
		average.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				formatDate();
				textArea.setText( "Obliczono: średnia\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getAverage()) );
				lblStatusInfo.setText("Obliczono średnią");
            }
        });
		obliczenia.add(average);
		add = new JMenuItem("Oblicz sumę");
		add.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				formatDate();
				textArea.setText( "Obliczono: suma\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getSum()) );
				lblStatusInfo.setText("Obliczono sumę");
            }
        });
		obliczenia.add(add);
		max = new JMenuItem("Wyznacz maksimum");
		max.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				formatDate();
				textArea.setText( "Wyznaczono: maksimum\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getMax()) );
				lblStatusInfo.setText("Wyznaczono maksimum");
            }
        });
		obliczenia.add(max);
		min = new JMenuItem("Wyznacz minimum");
		min.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				formatDate();
				textArea.setText( "Wyznaczono: minimum\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getMin()) );
				lblStatusInfo.setText("Wyznaczono minimum");
            }
        });
		obliczenia.add(min);
		
		pomoc = new JMenu("Pomoc");
		menuBarr.add(pomoc);	
		abProgram = new JMenuItem("O programie");
		abProgram.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				showAboutProgram();
				lblStatusInfo.setText("Pokazano informacje o programie");
            }
        });
		pomoc.add(abProgram);
		abAuthor = new JMenuItem("O autorze");
		abAuthor.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				showAboutAuthor();
				lblStatusInfo.setText("Pokazano informacje o autorze");
            }
        });
		pomoc.add(abAuthor);
		
		return menuBarr;
	}
	
	/**
	 * Metoda tworząca obiekt typu panel o statusie programu
	 * @param label zmienna typu JLabel
	 * @param x zmienna określająca położenie komponentu w osi X
	 * @param y zmienna określająca położenie komponentu w osi y
	 * @param width zmienna określająca szerokość komponentu
	 * @param height zmienna określająca wysokość komponentu
	 * @return zwraca obiekt typu ChartPanel
	 */
	public JPanel createStatusPanel(JLabel label, int x, int y, int width, int height){
		
		JPanel statusPanell = new JPanel();
		statusPanell.setBorder(new LineBorder(new Color(122, 138, 153)));
		statusPanell.setBounds(0, 627, 794, 23);
		statusPanell.setBackground(new Color(240, 248, 255));
		statusPanell.setLayout(null);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStatus.setBounds(10, 1, 46, 18);
		
		statusPanell.add(lblStatus);

		return statusPanell;
	}
	
	/**
	 * Metoda tworząca obiekt pole tekstowe statusu programu
	 * @return zwraca obiekt typu JLabel
	 */
	public JLabel createStatusLabel(){
		
		JLabel label = new JLabel();
		label.setText("Gotowy do pracy");
		label.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		label.setBackground(new Color(255, 255, 255));
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(66, 2, 236, 18);
		
		statusPanel.add(label);
		
		return label;
	}
	
	/**
	 * Metoda tworząca obiekt typu toolBar
	 * @return zwraca obiekt typu JToolBar
	 */
	public JToolBar createToolBar(){
		
		JToolBar toolBarr = new JToolBar();
		toolBarr.setBackground(new Color(240, 248, 255));
		toolBarr.setLayout(null);
		toolBarr.setFloatable(false);
		toolBarr.setBounds(0, 0, 800, 50);
		
		JButton b1 = new JButton();
		b1.setToolTipText("Zapisz");
		b1.setIcon(new ImageIcon("bin/resources/images/zapisz.jpg"));
		b1.setBounds(15, 5, 40, 40);
		b1.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){ 
            	try {
					saveToFile();
					lblStatusInfo.setText("Zapisano do pliku table.txt");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
            }
        });
		toolBarr.add(b1);
		
		JButton b2 = new JButton();
		b2.setToolTipText("Wyjdź");
		b2.setIcon(new ImageIcon("bin/resources/images/wyjscie.jpg"));
		b2.setBounds(60, 5, 40, 40);
		b2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){ 
				closeProgram();
            }
        });
		toolBarr.add(b2);
		
		JButton b3 = new JButton();
		b3.setToolTipText("Oblicz średnią");
		b3.setIcon(new ImageIcon("bin/resources/images/srednia.jpg"));
		b3.setBounds(130, 5, 40, 40);
		b3.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){ 
            	formatDate();
            	textArea.setText( "Obliczono: średnia\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getAverage()) );
            	lblStatusInfo.setText("Obliczono średnią");
            }
        });
		toolBarr.add(b3);
		
		JButton b4 = new JButton();
		b4.setToolTipText("Oblicz sumę");
		b4.setIcon(new ImageIcon("bin/resources/images/suma.jpg"));
		b4.setBounds(175, 5, 40, 40);
		b4.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){ 
            	formatDate();
            	textArea.setText( "Obliczono: suma\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getSum()) );
            	lblStatusInfo.setText("Obliczono sumę");
            }
        });
		toolBarr.add(b4);
		
		JButton b5 = new JButton();
		b5.setToolTipText("Wyznacz minimum");
		b5.setIcon(new ImageIcon("bin/resources/images/min.jpg"));
		b5.setBounds(220, 5, 40, 40);
		b5.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){ 
            	formatDate();
            	textArea.setText( "Wyznaczono: minimum\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getMin()) );
            	lblStatusInfo.setText("Wyznaczono minimum");
            }
        });
		toolBarr.add(b5);
		
		JButton b6 = new JButton();
		b6.setToolTipText("Wyznacz maksimum");
		b6.setIcon(new ImageIcon("bin/resources/images/max.jpg"));
		b6.setBounds(265, 5, 40, 40);
		b6.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){ 
            	formatDate();
            	textArea.setText( "Wyznaczono: maksimum\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getMax()) );
            	lblStatusInfo.setText("Wyznaczono maksimum");
            }
        });
		toolBarr.add(b6);
		
		JButton b7 = new JButton();
		b7.setToolTipText("O autorze");
		b7.setIcon(new ImageIcon("bin/resources/images/OAutorze.jpg"));
		b7.setBounds(335, 5, 40, 40);
		toolBarr.add(b7);
		b7.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){ 
            	showAboutAuthor();
            	lblStatusInfo.setText("Pokazano informacje o autorze");
            }
        });
		
		JButton b8 = new JButton();
		b8.setToolTipText("O programie");
		b8.setIcon(new ImageIcon("bin/resources/images/OProgramie.jpg"));
		b8.setBounds(380, 5, 40, 40);
		toolBarr.add(b8);
		b8.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){ 
            	showAboutProgram();
            	lblStatusInfo.setText("Pokazano informacje o programie");
            }
        });
		
		return toolBarr;
	}
	
	/**
	 * Metoda tworząca obiekt typu Table
	 * @return zwraca obiekt typu JTable
	 */
	public JTable createTable(){
		
		JTable tablee = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] { "1", "2", "3", "4", "5" });
		
		model.addRow(new Object[] { new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0) });
		model.addRow(new Object[] { new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0) });
		model.addRow(new Object[] { new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0) });
		model.addRow(new Object[] { new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0) });
		model.addRow(new Object[] { new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0) });

		tablee = new JTable();
		tablee.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tablee.setEnabled(false);
		tablee.setModel(model);
		tablee.setBounds(210, 100, 462, 150);
		tablee.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tablee.setRowHeight(0, 30);
		tablee.setRowHeight(1, 30);
		tablee.setRowHeight(2, 30);
		tablee.setRowHeight(3, 30);
		tablee.setRowHeight(4, 30);
		
		DefaultTableCellRenderer toRight = new DefaultTableCellRenderer();
		toRight.setHorizontalAlignment(SwingConstants.RIGHT);
		tablee.getColumnModel().getColumn(0).setCellRenderer(toRight);
		tablee.getColumnModel().getColumn(1).setCellRenderer(toRight);
		tablee.getColumnModel().getColumn(2).setCellRenderer(toRight);
		tablee.getColumnModel().getColumn(3).setCellRenderer(toRight);
		tablee.getColumnModel().getColumn(4).setCellRenderer(toRight);
		

		return tablee;
	}
	
	/**
	 * Metoda tworząca obiekt typu JLabel
	 * @param name zmienna określająca tekst wyświetlany w polu
	 * @param font zmienna określająca rodzaj czcionki
	 * @param x zmienna określająca położenie komponentu w osi X
	 * @param y zmienna określająca położenie komponentu w osi y
	 * @param width zmienna określająca szerokość komponentu
	 * @param height zmienna określająca wysokość komponentu
	 * @return zwraca obiekt typu JLabel
	 */
	public JLabel createLabel(String name, Font font, int x, int y, int width, int height){
		
		JLabel labell = new JLabel(name);
		labell.setFont(font);
		labell.setBounds(x, y, width, height);
		
		return labell;
	}
	
	/**
	 * Metoda tworząca obiekt typu JSpinner
	 * @param model zmienna typu SpinnerModel zawierająca model spinnera
	 * @param x zmienna określająca położenie komponentu w osi X
	 * @param y zmienna określająca położenie komponentu w osi y
	 * @param width zmienna określająca szerokość komponentu
	 * @param height zmienna określająca wysokość komponentu
	 * @return zwraca obiekt typu JSpinner
	 */
	public JSpinner createSpinner(SpinnerModel model, int x, int y, int width, int height){
		
		JSpinner spinnerr = new JSpinner();
		spinnerr.setModel(model);
		spinnerr.setBounds(x, y, width, height);
		
		return spinnerr;
	}
	
	/**
	 * Metoda tworząca obiekt typu JComboBox
	 * @return zwraca obiekt typu JComboBox
	 */
	public JComboBox<String> createComboBox(){
		
		String[] menuStrings = { "Oblicz średnią", "Oblicz sumę", "Wyznacz minimum", "Wyznacz maksimum" };
		
		JComboBox<String> comboBoxx = new JComboBox<String>(menuStrings);
		comboBoxx.setSelectedIndex(0);
		comboBoxx.addActionListener(this);
		comboBoxx.setBounds(355, 263, 127, 20);
		
		return comboBoxx;
	}
	
	/**
	 * Metoda tworząca obiekt typu JTextField
	 * @return zwraca obiekt typu JTextField
	 */
	public JTextField createTextField(){
		
		JTextField textFieldd = new JFormattedTextField();
		textFieldd.setBounds(334, 69, 70, 20);
		textFieldd.setColumns(10);
		textFieldd.setText("");
		
		FocusListener highlight = new FocusListener() {

            public void focusGained(FocusEvent e) {
                e.getComponent().setBackground(new Color(211, 232, 247));
            }

            public void focusLost(FocusEvent e) {
                e.getComponent().setBackground(UIManager.getColor("TextField.background"));
            }
        };
		textFieldd.addFocusListener(highlight);
		
		textFieldd.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){ btnOK.doClick(); }
        });
		
		return textFieldd;
	}
	
	/**
	 * Metoda tworząca obiekt typu JTextArea
	 * @return zwraca obiekt typu JTextArea
	 */
	public JTextArea createTextArea(){
		
		JTextArea textAreaa = new JTextArea();
		textAreaa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textAreaa.setEditable(false);
		textAreaa.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textAreaa.setBounds(210, 485, 564, 118);
		
		return textAreaa;
	}
	
	/**
	 * Metoda tworząca obiekt typu JSeparator
	 * @param x zmienna określająca położenie komponentu w osi X
	 * @param y zmienna określająca położenie komponentu w osi y
	 * @param width zmienna określająca szerokość komponentu
	 * @param height zmienna określająca wysokość komponentu
	 * @return zwraca obiekt typu JSeparator
	 */
	public JSeparator createSeparator(int x, int y, int width, int height){
		JSeparator separatorr = new JSeparator();
		separatorr = new JSeparator();
		separatorr.setBounds(x, y, width, height);
		
		return separatorr;
	}
	
	/**
	 * Metoda tworząca obiekt typu JButton
	 * @param name zmienna określająca nazwę komponentu
	 * @param x zmienna określająca położenie komponentu w osi X
	 * @param y zmienna określająca położenie komponentu w osi y
	 * @param width zmienna określająca szerokość komponentu
	 * @param height zmienna określająca wysokość komponentu
	 * @return zwraca obiekt typu JSeparator
	 */
	public JButton createButton(String name, int x, int y, int width, int height){
		JButton buttonn = new JButton(name);
		buttonn.setBounds(x, y, width, height);
		buttonn.addActionListener(this);
	
		return buttonn;
	}
	
	/**
	 * Metoda tworząca obiekt typu JCalendarCombo
	 * @return zwraca obiekt typu JCalendarCombo
	 */
	public JCalendarCombo createCalendar(){
		
		JCalendarCombo kalendarzz = new JCalendarCombo();
		kalendarzz = new JCalendarCombo();        
		kalendarzz.setLocation(591, 263);
		kalendarzz.setSize(183, 20);
		
		return kalendarzz;
	}
	
	/**
	 * Metoda tworząca obiekt typu JPanel
	 * @param color zmienna określając color komponentu
	 * @param x zmienna określająca położenie komponentu w osi X
	 * @param y zmienna określająca położenie komponentu w osi y
	 * @param width zmienna określająca szerokość komponentu
	 * @param height zmienna określająca wysokość komponentu
	 * @return zwraca obiekt typu JPanel
	 */
	public JPanel createPanel(Color color, int x, int y, int width, int height){
		
		JPanel panell = new JPanel();
		panell.setBounds(x, y, width, height);
		panell.setBackground(color);
		
		return panell;
	}
	
	
	/*INICJACJA KOMPONENTOW I DODANIE DO PANELU-------------------------------------------------*/
	
	/**
	 * Metoda inicjalizująca komponenty
	 */
	public void initComponents(){
		
		setJMenuBar(createMenuBar());
		toolBar = createToolBar();
		statusPanel = createStatusPanel(lblStatusInfo, 0, 430, 800, 20);
		lblStatusInfo = createStatusLabel();
		table = createTable();
		lblWprowadzLiczb = createLabel("Wprowadz liczbę: ", new Font("Tahoma", Font.PLAIN, 14), 226, 69, 115, 20);
		lblNumerWiersza = createLabel("Wiersz: ", new Font("Tahoma", Font.PLAIN, 14), 424, 69, 55, 20);
		lblNumerKolumny = createLabel("Kolumna: ", new Font("Tahoma", Font.PLAIN, 14), 535, 69, 62, 20);
		lblObliczenia =  createLabel("Wykonaj obliczenia: ", new Font("Tahoma", Font.PLAIN, 14), 220, 262, 131, 20);
		textField = createTextField();
		spinnerKolumna = createSpinner(new SpinnerNumberModel(1, 1, 5, 1), 477, 69, 39, 20);
		spinnerWiersz = createSpinner(new SpinnerNumberModel(1, 1, 5, 1), 603, 69, 39, 20);
		comboBox = createComboBox();
		btnOK = createButton("OK", 700, 65, 55, 28);
		btnLosuj = createButton("Losuj", 682, 198, 92, 28);
		btnOblicz = createButton("Oblicz", 495, 261, 70, 23);
		btnZapisz = createButton("Zapisz", 682, 117, 92, 28);
		btnWyzeruj = createButton("Wyzeruj", 682, 156, 92, 28);
		textArea = createTextArea();
		separatorGorny = createSeparator(210, 293, 564, 123);
		separatorDolny = createSeparator(210, 256, 564, 123);
		separatorDolny2 = createSeparator(210, 468, 564, 123);
		
		taskPane = createTaskPane();
		kalendarz = createCalendar();
		chartPanel = createChart();
		
	}
	
	/**
	 * Metoda tworząca obiekt typu JPanel i dodająca do niego komponenty
	 * @param x zmienna określająca położenie komponentu w osi X
	 * @param y zmienna określająca położenie komponentu w osi y
	 * @param width zmienna określająca szerokość komponentu
	 * @param height zmienna określająca wysokość komponentu
	 * @return zwraca obiekt typu JPanel
	 */
	public JPanel createPanel(int x, int y, int width, int height){
		
		JPanel panell = new JPanel();
		panell.setBackground(new Color(176, 196, 222));
		panell.setBounds(x, y, width, height);
		panell.setLayout(null);
		
		panell.add(toolBar);
		panell.add(statusPanel);
		panell.add(statusPanel);
		panell.add(table);
		panell.add(lblWprowadzLiczb);
		panell.add(lblNumerWiersza);
		panell.add(lblNumerKolumny);
		panell.add(lblObliczenia);
		panell.add(textField);
		panell.add(spinnerKolumna);
		panell.add(spinnerWiersz);
		panell.add(comboBox);
		panell.add(btnOK);
		panell.add(btnLosuj);
		panell.add(btnOblicz);
		panell.add(btnZapisz);
		panell.add(btnWyzeruj);
		panell.add(textArea);
		panell.add(separatorGorny);
		panell.add(separatorDolny);
		panell.add(separatorDolny2);
		
		panell.add(taskPane);
		panell.add(kalendarz);
		panell.add(chartPanel);
		
		return panell;
	}
	
	
	
	
	/*KONSTRUKTOR--------------------------------------------------------------------------*/
	
	/**
	 * Konstruktor bezparametrowy
	 */
	public InterfejsGuiApp(){
	
		setTitle("Interfejs ver 3.0");
		setSize(800, 700);
		setLocationRelativeTo(null);													
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				
				closeProgram();
			}
		});
		
		
		JPanel cpanel = (JPanel) getContentPane();
		
		initComponents();
		glownyPanel = createPanel(0, 0, 800, 700);
		cpanel.add(glownyPanel);
		
	}
	
	
	

	
	
	/* OPIS METOD ---------------------------------------------------------------------*/
	
	
	/**
	 * Metoda wczytująca ustawienia obiektu typu Logger
	 */
	private void runLogger(){
		
		try {
			logger = Logger.getLogger("app.InterfejsGuiApp.class");
			
			String log4jConfPath = "bin/log4j.properties";
			PropertyConfigurator.configure(log4jConfPath);
			
			logger.info("Uruchomiono program");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Metoda ustawiająca w tabeli wszystkie komórki na wartość 0
	 */
	public void setZeros(){
		
		for (int col = 0; col<5; col++){
			
			for(int row = 0; row<5; row++){
				
				table.setValueAt("0", row, col);
			}
		}
		updateChart();
	}
	
	/**
	 * Metoda ustawiająca w tabeli wszystkie komórki na wartość losową od -10 do 10
	 */
	public void setRandoms(){
		int max, result;
		Random rand;
		
		for (int col = 0; col<5; col++){
			
			for(int row = 0; row<5; row++){
				
				rand = new Random();
				max = rand.nextInt(21);
				result = 10 - max;
				table.setValueAt(result, row, col);
			}
		}
		updateChart();
	}
	
	/**
	 * Metoda zapisująca wartości tabeli do pliku tekstowego
	 * @throws FileNotFoundException jeżeli nie znaleziono pliku
	 */
	public void saveToFile() throws FileNotFoundException{
		
		int ostrzezenie = JOptionPane.showConfirmDialog(null, "Czy jesteś pewien, że chcesz zapisać dane do pliku tekstowego?", "Ostrzeżenie", JOptionPane.OK_CANCEL_OPTION);
		
		if (ostrzezenie == JOptionPane.OK_OPTION){
		
			try {
			
				PrintWriter zapis = new PrintWriter("table.txt");
				for (int col = 0; col<5; col++){
				
					for(int row = 0; row<5; row++){
					
						zapis.println( Integer.parseInt( String.valueOf( table.getValueAt(row, col))) );
					}
				}
				JOptionPane.showMessageDialog(null, "Dane zostały zapisane do pliku tekstowego!", "Potwierdzenie", JOptionPane.INFORMATION_MESSAGE);
				logger.info("Wykonano zapis danych do pliku");
				zapis.close();	
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		else{}	
		
	}
	
	/**
	 * Metoda uaktywniająca JOptionPane przed metodą zerowania
	 */
	public void sureToSetZero(){
		
		int ostrzezenie = JOptionPane.showConfirmDialog(null, "Czy jesteś pewien, że chcesz wyzerować wartości w tabeli?", "Ostrzeżenie", JOptionPane.OK_CANCEL_OPTION);
		
		if (ostrzezenie == JOptionPane.OK_OPTION) setZeros();
		else{}
	}
	
	/**
	 * Metoda uaktywniająca JOptionPane przed metodą zamykania oraz metoda zamykająca program
	 */
	public void closeProgram(){
		
		int ostrzezenie = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz wyjść?", "Ostrzeżenie", JOptionPane.OK_CANCEL_OPTION);
		
		if (ostrzezenie == JOptionPane.OK_OPTION){
			logger.info("Zamknieto program");
			System.exit(0);
		}
		else{}
	}
	
	/**
	 * Metoda obliczająca średnią liczb w komórkach
	 * @return zwraca wartość średniej typu double 
	 */
	public double getAverage(){
		
		double result = 0;
		
		for (int col = 0; col<5; col++){
			
			for(int row = 0; row<5; row++){
				
				result = result + Double.parseDouble( String.valueOf( table.getValueAt(row, col) ) );
			}
		}
		
		if(result == 0) return 0.0;
		return result/25;
	}
	
	/**
	 * Metoda obliczająca sumę liczb w komórkach
	 * @return zwraca wartość sumy typu double 
	 */
	public double getSum(){
		
		double result = 0;
		
		for (int col = 0; col<5; col++){
			
			for(int row = 0; row<5; row++){
				
				result = result + Double.parseDouble( String.valueOf( table.getValueAt(row, col) ));
			}
		}

		return result;
	}
	
	/**
	 * Metoda wyznaczająca maksimum z liczb w komórkach
	 * @return zwraca wartość maksimum typu int
	 */
	public int getMax(){
		
		int result = Integer.parseInt( String.valueOf( table.getValueAt(0, 0) ));
		
		for (int col = 0; col<5; col++){
			
			for(int row = 0; row<5; row++){
				
				if ( result < Integer.parseInt( String.valueOf( table.getValueAt(row, col) )) ) 
					result = Integer.parseInt( String.valueOf( table.getValueAt(row, col) ));
				else {}
			}
		}

		return result;
	}
	
	/**
	 * Metoda wyznaczająca minimum z liczb w komórkach
	 * @return zwraca wartość minimum typu int
	 */
	public int getMin(){
		
		int result = Integer.parseInt( String.valueOf( table.getValueAt(0, 0) ));
		
		for (int col = 0; col<5; col++){
			
			for(int row = 0; row<5; row++){
				
				if ( result > Integer.parseInt( String.valueOf( table.getValueAt(row, col) )) ) 
					result = Integer.parseInt( String.valueOf( table.getValueAt(row, col) ));
				else {}
			}
		}

		return result;
	}
	
	/**
	 * Metoda uzupełniająca tabelę o wartości zapisane w JTextField
	 * @throws NumberFormatException jeżeli format zmiennej wejsciowej nie jest prawidlowy
	 * @throws OutOfBoundsException jeżeli zmienna wejsciowa jest poza zakresem
	 */
	public void setValueFromTextField() throws NumberFormatException, OutOfBoundsException{
		
		if (Integer.parseInt(textField.getText()) < -10 || Integer.parseInt(textField.getText()) > 10) throw new OutOfBoundsException();
		else{
			
			Integer.parseInt(textField.getText());
			table.setValueAt(textField.getText(), (Integer) spinnerKolumna.getValue()-1, (Integer) spinnerWiersz.getValue()-1);
			updateChart();
		}
		
	}
	
	/**
	 * Metoda aktualizująca datę
	 */
	public void formatDate(){
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date data = kalendarz.getDate();
		formatowanaData = df.format(data);
	}
	
	/**
	 * Metoda wczytująca stronę internetową o programie
	 */
	public void showAboutProgram(){
		
		try {
			File htmlFile = new File("bin/resources/help/index.html");
			Desktop.getDesktop().browse(htmlFile.toURI());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Metoda pokazująca okno JFrame z informacjami o autorze
	 */
	public void showAboutAuthor(){
		
		JFrame ramka = new JFrame();
		JButton button = new JButton("Ok");
		
		ramka.setTitle("O autorze Interfejsu");
		ramka.setBackground(new Color(240, 248, 255));
		ramka.setSize(560, 250);
		ramka.setLocationRelativeTo(null);
		ramka.setResizable(false);
		ramka.getContentPane().setLayout(null);
		
		JLabel logo = new JLabel();
		logo = new JLabel(new ImageIcon("bin/resources/images/wizytowka.png"));
		logo.setBounds(0, -15, 560, 250);
		
		
		JLabel wizytowka = new JLabel();
		wizytowka.setFont(new Font("Tahoma", Font.PLAIN, 14));
		wizytowka.setBounds(180, 20, 300, 200);
		wizytowka.setText("<html><center><b>Program:</b> Interfejs<br/><b>Wersja:</b> 3.0<br/><b>Autor:</b> Małgorzata Zawisza<br/><b>Kontakt:</b> milele.fushi@gmail.com</center></html>");
		
		button.setBounds(250, 170, 70, 30);
		button.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){ 
            	ramka.setVisible(false);
            	remove(ramka);
            }
        });
		
		
		ramka.getContentPane().add(wizytowka);
		ramka.getContentPane().add(button);
		ramka.getContentPane().add(logo);
		ramka.setVisible(true);
	}
	
	
	
	/*MOJE WYJATKI--------------------------------------------------------------------------*/
	
	/**
	 * Klasa stworzona na potrzeby programu, opisująca wyjątek OutOfBoundsException przy przekroczeniu zakresu liczby
	 */
	public class OutOfBoundsException extends Exception{
		
		public OutOfBoundsException(){
			
		}
	}
	
	/*AKCJE ZWIAZANE Z KOMPONENTAMI----------------------------------------------------------*/
	
	/**
	 * Metoda obsługująca zdarzenia akcji
	 * @param e zmienna typu ActionEvent
	 */
	public void actionPerformed(ActionEvent e){
		
		try{
			Object source = e.getSource();
			if (source == btnOK){
				
				setValueFromTextField();
				lblStatusInfo.setText("Dodano liczbę do tablicy");

			}
				
			else if(source == btnOblicz){
				
				formatDate();
				
				if ( comboBox.getSelectedItem() == "Oblicz średnią" ) {
					textArea.setText( "Obliczono: średnia\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getAverage()) );
					lblStatusInfo.setText("Obliczono średnią");
				}
				else if( comboBox.getSelectedItem() == "Oblicz sumę" ){
					textArea.setText( "Obliczono: suma\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getSum()) );
					lblStatusInfo.setText("Obliczono sumę");
				}
				else if( comboBox.getSelectedItem() == "Wyznacz minimum" ){
					textArea.setText( "Wyznaczono: minimum\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getMin()) );
					lblStatusInfo.setText("Wyznaczono minimium");
				}
				else if( comboBox.getSelectedItem() == "Wyznacz maksimum" ){
					textArea.setText( "Wyznaczono: maksimum\nData: " + formatowanaData + "\nWynik: " + String.valueOf(getMax()) );
					lblStatusInfo.setText("Wyznaczono maksimum");
				}
				else{}

			}
				
			else if(source == btnZapisz){
				
				saveToFile();
				lblStatusInfo.setText("Zapisano do table.txt");
			}
			
			else if(source == btnWyzeruj){
				
				sureToSetZero();
				lblStatusInfo.setText("Wyzerowano tablicę");
				
			}
			
			else if(source == btnLosuj){
				
				setRandoms();
				lblStatusInfo.setText("Wylosowano wartości");
			}
			
			else if(source == save){
				
				saveToFile();
				lblStatusInfo.setText("Zapisano do table.txt");
			}
			
			
		}
		catch(NumberFormatException ne){
				
			JOptionPane.showMessageDialog(null, "Zły format. Wprowadz cyfrę lub liczbę z przedziału od -10 do 10!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
			textField.setBackground(new Color(229, 155, 155));
			textField.setText("");
			logger.error("Zly format danej wejsciowej!");
		} 
		catch (OutOfBoundsException oob){
			
			JOptionPane.showMessageDialog(null, "Wybierz liczby z przedziału od -10 do 10!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
			textField.setBackground(new Color(229, 155, 155));
			textField.setText("");
			logger.error("Dane wejsciowe spoza zakresu!");
		}
		catch (FileNotFoundException fnf){
			
			fnf.printStackTrace();
		}
				
	}
	
	
	
	/*MAIN------------------------------------------------------------------------------------*/
	
	/**
	 * Publiczna metoda uruchomieniowa tworząca obiekt klasy
	 * @param args tablica zmiennych typu String
	 */
	public static void main(String[] args) {
		try {
			InterfejsGuiApp window = new InterfejsGuiApp();
			window.setVisible(true);
			window.runLogger();
			window.showTipOfTheDay();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
