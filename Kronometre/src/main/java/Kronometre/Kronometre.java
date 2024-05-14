package Kronometre;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Kronometre extends JFrame implements ActionListener {

	private JPanel contentPane;
	protected static Kronometre frame;
	protected Timer timer;
	protected ButtonGroup group;
	protected JLabel l0,l1,l2,l3,l4;
	protected JTextField t1,t2,t3,t4,t5;
	protected JRadioButton rb1, rb2;
	protected JButton btn0,btn1,btn2,btn3,btn4,btn5,btn6;
	protected int saat=0;
	protected int dakika=0;
	protected int saniye=0;
	protected int salise=0;
	protected boolean timerState=false;
	protected Robot robot;
	
	public static boolean isSpacedEnded(String s)
	{
		return s!=null && Character.isWhitespace(s.charAt(s.length()-1));
	}
	
	public boolean isFractional(String s) {  
	    return s != null && (s.matches("[-+]?\\d+\\.+") || s.matches("[-+]?\\d+[^0-9.]") || s.matches("[-+]?\\d+\\.+0*[1-9]*+[^0-9]+"));  
	}  
	
	public boolean isFractionalNumeric(String s) {  
	    return s != null && (s.matches("[-+]?\\d+\\.+0*[1-9]+"));  
	}  
	
	public boolean isPositiveExactNumeric(String s) {  
	    return s != null && s.matches("[+]?\\d*\\.?0*");  
	}  
	
	
	public boolean isNegativeExactNumeric(String s) {  
	    return s != null && s.matches("[-]\\d*\\.?0*");  
	}  
	
	public boolean isExactNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?0*");  
	}  
	
	public boolean isNegativeNumeric(String s) {  
	    return s != null && s.matches("[-]\\d*\\.?\\d+");  
	} 
	
	public boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Kronometre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Kronometre() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,300);
		setLocationRelativeTo(null);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		l0= new JLabel("KRONOMETRE");
		l0.setSize(200,20);
		l0.setLocation(175,10);
		contentPane.add(l0);
		
		l1= new JLabel("saat");
		l1.setSize(50,20);
		l1.setLocation(30,50);
		contentPane.add(l1);
		
		t1=new JTextField();
		t1.setSize(50,20);
		t1.setLocation(30,70);
		contentPane.add(t1);
		t1.setFocusable(false);
		t1.getDocument().addDocumentListener(dl1);
		
		l2= new JLabel("dakika");
		l2.setSize(50,20);
		l2.setLocation(110,50);
		contentPane.add(l2);
		
		t2=new JTextField();
		t2.setSize(50,20);
		t2.setLocation(110,70);
		contentPane.add(t2);
		t2.setFocusable(false);
		t2.getDocument().addDocumentListener(dl2);
		
		l3= new JLabel("saniye");
		l3.setSize(50,20);
		l3.setLocation(190,50);
		contentPane.add(l3);
		
		t3=new JTextField();
		t3.setSize(50,20);
		t3.setLocation(190,70);
		contentPane.add(t3);
		t3.setFocusable(false);
		t3.getDocument().addDocumentListener(dl3);
		
		l4= new JLabel("salise");
		l4.setSize(50,20);
		l4.setLocation(290,50);
		contentPane.add(l4);
		
		t4=new JTextField();
		t4.setSize(50,20);
		t4.setLocation(290,70);
		contentPane.add(t4);
		t4.setFocusable(false);
		t4.getDocument().addDocumentListener(dl4);
		
		btn0= new JButton("YeniDeğer");
		btn0.setSize(130,20);
		btn0.setLocation(30,110);
		contentPane.add(btn0);
		btn0.addActionListener(al0);
		
		rb1= new JRadioButton("İleri");
		rb1.setSize(50,20);
		rb1.setLocation(190,110);
		contentPane.add(rb1);
		rb1.addActionListener(this);
		
		rb2= new JRadioButton("Geri");
		rb2.setSize(50,20);
		rb2.setLocation(290,110);
		contentPane.add(rb2);
		rb2.addActionListener(this);
		
		group=new ButtonGroup();
		group.add(rb1);
		group.add(rb2);
		
		btn1= new JButton("Başla");
		btn1.setSize(130,20);
		btn1.setLocation(30,150);
		contentPane.add(btn1);
		btn1.addActionListener(al1);
		
		btn2= new JButton("Durdur");
		btn2.setSize(80,20);
		btn2.setLocation(190,150);
		contentPane.add(btn2);
		btn2.addActionListener(al2);
		
		btn3= new JButton("Sıfırla");
		btn3.setSize(80,20);
		btn3.setLocation(290,150);
		contentPane.add(btn3);
		btn3.addActionListener(al3);
		
		t5=new JTextField();
		t5.setSize(200,20);
		t5.setLocation(30,110);
		contentPane.add(t5);
		t5.setFocusable(false);
		t5.setVisible(false);
		
		btn5=new JButton("Değerleri Gir");
		btn5.setSize(200,20);
		btn5.setLocation(30,150);
		contentPane.add(btn5);
		btn5.setFocusable(false);
		btn5.setVisible(false);
		btn5.addActionListener(al5);
		
		btn6=new JButton("Değerleri girmekten vazgeç");
		btn6.setSize(200,20);
		btn6.setLocation(30,190);
		contentPane.add(btn6);
		btn6.setFocusable(false);
		btn6.setVisible(false);
		btn6.addActionListener(al6);
		
		timer=new Timer(1000/60,this);
	
		
		
		
		
	}
	
	

	public void actionPerformed(ActionEvent e) 
	{
		long start=System.currentTimeMillis();
		if(rb1.isSelected() && timerState==true)
		{
			
			salise++;
			if(salise==60) {salise=0; saniye++;}
			if(saniye==60) {saniye=0; dakika++;}
			if(dakika==60) {dakika=0; saat++;}
			
			if(String.valueOf(saat).length()==1)
			{
				t1.setText("0"+saat);
			}
			else if(String.valueOf(saat).length()>1)
			{
				t1.setText(""+saat);
			}
			if(String.valueOf(dakika).length()==1)
			{
				t2.setText("0"+dakika);
			}
			else if(String.valueOf(dakika).length()>1)
			{
				t2.setText(""+dakika);
			}
			if(String.valueOf(saniye).length()==1)
			{
				t3.setText("0"+saniye);
			}
			else if(String.valueOf(saniye).length()>1)
			{
				t3.setText(""+saniye);
			}
			if(String.valueOf(salise).length()==1)
			{
				t4.setText("0"+salise);
			}
			else if(String.valueOf(salise).length()>1)
			{
				t4.setText(""+salise);
			}
			
			
			
		}
		else if(rb2.isSelected() && timerState==true)
		{
			if(salise>0)
			{
				salise--;
			}
			else if(salise==0 &&saniye>0)
			{
				salise=60;
				saniye--;
			}
			else if(salise==0 && saniye==0 &&dakika>0)
			{
				saniye=60;
				dakika--;
			}
			else if(salise==0 && saniye==0 && dakika==0 &&saat>0)
			{
					dakika=60;
					saat--;
			}
			
			
			else if(salise==0 && saniye==0 && dakika==0 && saat==0)
			{
				timer.stop();
				timerState=false;
			}
			
			if(String.valueOf(saat).length()==1)
			{
				t1.setText("0"+saat);
			}
			else if(String.valueOf(saat).length()>1)
			{
				t1.setText(""+saat);
			}
			if(String.valueOf(dakika).length()==1)
			{
				t2.setText("0"+dakika);
			}
			else if(String.valueOf(dakika).length()>1)
			{
				t2.setText(""+dakika);
			}
			if(String.valueOf(saniye).length()==1)
			{
				t3.setText("0"+saniye);
			}
			else if(String.valueOf(saniye).length()>1)
			{
				t3.setText(""+saniye);
			}
			if(String.valueOf(salise).length()==1)
			{
				t4.setText("0"+salise);
			}
			else if(String.valueOf(salise).length()>1)
			{
				t4.setText(""+salise);
			}
		}
		long end=System.currentTimeMillis();
		long difference=end-start;
		if(1000/60-(int)difference>=0)
		{timer.setDelay(1000/60-(int) difference);}
		else
		{timer.setDelay(0);}
		
	}
	
	
	DocumentListener dl1=new DocumentListener() 
	{

		public void insertUpdate(DocumentEvent e) 
		{
			if(!t1.getText().matches("^[0]?[0]?") && !t1.getText().matches("^[0]?[1-9]") && !t1.getText().matches("[1-9][0-9]*"))
			{
	
				t1.requestFocus();
				t1.setCaretPosition(t1.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
			
			
			
		}

		public void removeUpdate(DocumentEvent e) 
		{
			if(!t1.getText().matches("^[0]?[0]?") && !t1.getText().matches("^[0]?[1-9]") && !t1.getText().matches("[1-9][0-9]*"))
			{
	
				t1.requestFocus();
				t1.setCaretPosition(t1.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
		}

		public void changedUpdate(DocumentEvent e) 
		{
			if(!t1.getText().matches("^[0]?[0]?") && !t1.getText().matches("^[0]?[1-9]") && !t1.getText().matches("[1-9][0-9]*"))
			{
	
				t1.requestFocus();
				t1.setCaretPosition(t1.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
			
		}
	};
	
	DocumentListener dl2=new DocumentListener() 
	{

		public void insertUpdate(DocumentEvent e) 
		{
			
			if(!t2.getText().matches("^[0]?[0]?") && !t2.getText().matches("^[0]?[1-9]") && !t2.getText().matches("[1-9][0-9]*"))
			{
	
				t2.requestFocus();
				t2.setCaretPosition(t2.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
			
			
		}

		public void removeUpdate(DocumentEvent e) 
		{
			if(!t2.getText().matches("^[0]?[0]?") && !t2.getText().matches("^[0]?[1-9]") && !t2.getText().matches("[1-9][0-9]*"))
			{
	
				t2.requestFocus();
				t2.setCaretPosition(t2.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
		}

		public void changedUpdate(DocumentEvent e) 
		{
			if(!t2.getText().matches("^[0]?[0]?") && !t2.getText().matches("^[0]?[1-9]") && !t2.getText().matches("[1-9][0-9]*"))
			{
	
				t2.requestFocus();
				t2.setCaretPosition(t2.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
		}
	};
	
	DocumentListener dl3=new DocumentListener() 
	{

		public void insertUpdate(DocumentEvent e) 
		{
			if(!t3.getText().matches("^[0]?[0]?") && !t3.getText().matches("^[0]?[1-9]") && !t3.getText().matches("[1-9][0-9]*"))
			{
	
				t3.requestFocus();
				t3.setCaretPosition(t3.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
			
			
		}

		public void removeUpdate(DocumentEvent e) 
		{
			if(!t3.getText().matches("^[0]?[0]?") && !t3.getText().matches("^[0]?[1-9]") && !t3.getText().matches("[1-9][0-9]*"))
			{
	
				t3.requestFocus();
				t3.setCaretPosition(t3.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
		}

		public void changedUpdate(DocumentEvent e) 
		{
			if(!t3.getText().matches("^[0]?[0]?") && !t3.getText().matches("^[0]?[1-9]") && !t3.getText().matches("[1-9][0-9]*"))
			{
	
				t3.requestFocus();
				t3.setCaretPosition(t3.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
			
		}
	};
	
	DocumentListener dl4=new DocumentListener() 
	{

		public void insertUpdate(DocumentEvent e) 
		{
			
			if(!t4.getText().matches("^[0]?[0]?") && !t4.getText().matches("^[0]?[1-9]") && !t4.getText().matches("[1-9][0-9]*"))
			{
	
				t4.requestFocus();
				t4.setCaretPosition(t4.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
			
		}

		public void removeUpdate(DocumentEvent e) 
		{
			if(!t4.getText().matches("^[0]?[0]?") && !t4.getText().matches("^[0]?[1-9]") && !t4.getText().matches("[1-9][0-9]*"))
			{
	
				t4.requestFocus();
				t4.setCaretPosition(t4.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
		}

		public void changedUpdate(DocumentEvent e) 
		{
			if(!t4.getText().matches("^[0]?[0]?") && !t4.getText().matches("^[0]?[1-9]") && !t4.getText().matches("[1-9][0-9]*"))
			{
	
				t4.requestFocus();
				t4.setCaretPosition(t4.getText().length()); 
				try {
					robot=new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				
			}
			
			
		}
	};
	
	ActionListener al0=new ActionListener() 
	{

		public void actionPerformed(ActionEvent e) {
			
			timer.stop();
			timerState=false;
			
			t1.setText("");
			t2.setText("");
			t3.setText("");
			t4.setText("");
			
			l0.setText("DEĞERLERİ GİRİNİZ");
			t1.setFocusable(true);
			t2.setFocusable(true);
			t3.setFocusable(true);
			t4.setFocusable(true);
			
			btn0.setVisible(false);
			btn1.setVisible(false);
			btn2.setVisible(false);
			btn3.setVisible(false);
			rb1.setVisible(false);
			rb2.setVisible(false);
			
			t5.setVisible(true);
			btn5.setVisible(true);
			btn6.setVisible(true);
			
			
		}
		
	};
	
	ActionListener al6=new ActionListener() 
	{

		public void actionPerformed(ActionEvent e) 
		{
			
				saat=0;
				dakika=0;
				saniye=0;
				salise=0;
				
				l0.setText("KRONOMETRE");
				t1.setFocusable(false);
				t2.setFocusable(false);
				t3.setFocusable(false);
				t4.setFocusable(false);
				
				btn0.setVisible(true);
				btn1.setVisible(true);
				btn2.setVisible(true);
				btn3.setVisible(true);
				rb1.setVisible(true);
				rb2.setVisible(true);
				
				t5.setVisible(false);
				btn5.setVisible(false);
				btn6.setVisible(false);
				
		
			
		}
		
	};
	
	ActionListener al5=new ActionListener() 
	{

		public void actionPerformed(ActionEvent e) 
		{
			if(isPositiveExactNumeric(t1.getText()) && isPositiveExactNumeric(t2.getText())
					&& isPositiveExactNumeric(t3.getText())&& isPositiveExactNumeric(t4.getText()) 
					&& Integer.valueOf(t2.getText())<60 && Integer.valueOf(t3.getText())<60 && Integer.valueOf(t4.getText())<60) 
			{
				saat=Integer.valueOf(t1.getText());
				dakika=Integer.valueOf(t2.getText());
				saniye=Integer.valueOf(t3.getText());
				salise=Integer.valueOf(t4.getText());
				
				l0.setText("KRONOMETRE");
				t1.setFocusable(false);
				t2.setFocusable(false);
				t3.setFocusable(false);
				t4.setFocusable(false);
				
				btn0.setVisible(true);
				btn1.setVisible(true);
				btn2.setVisible(true);
				btn3.setVisible(true);
				rb1.setVisible(true);
				rb2.setVisible(true);
				
				t5.setVisible(false);
				btn5.setVisible(false);
				btn6.setVisible(false);
				
			}
			else 
			{
				t5.setText("Eksik veya hatalı değer");
			}
			
		}
		
	};
	
	ActionListener al1=new ActionListener() 
	{

		public void actionPerformed(ActionEvent e) {
			timer.setDelay(1000/60);
			timer.start();
			timerState=true;
			btn1.setText("Başla");
			
		}
		
	};
	
	ActionListener al2=new ActionListener() 
	{

		public void actionPerformed(ActionEvent e) {
			
		
			timer.stop();
			timerState=false;
			btn1.setText("Devam Et");
			
		}
		
	};
	
	ActionListener al3=new ActionListener() 
	{

		public void actionPerformed(ActionEvent e) {
			timer.stop();
			timerState=false;
			btn1.setText("Başla");
			saat=0;
			dakika=0;
			saniye=0;
			salise=0;
			t1.setText("00");
			t2.setText("00");
			t3.setText("00");
			t4.setText("00");
			
		}
		
	};

}
