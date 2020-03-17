import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.mysql.cj.xdevapi.Result;
 
public class JF2 extends JFrame {
	
	private JCheckBox chineseCheck;
	private JCheckBox englishCheck;
	private JCheckBox koreanCheck;
	
	private JCheckBox edmCheck;
	private JCheckBox popCheck;
	private JCheckBox rythemAndBlueCheck;
	private JCheckBox rockCheck;
	private JCheckBox hiphopCheck;
	
	private JButton searchBtn;
	private JTextArea resultTextArea;
	Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private ArrayList<String> urlList;
	private ArrayList<String> songList;
	private ResultSet result;
	private JPanel panel4;
	private  JPanel panel  = new JPanel();
	private JButton clearBtn;
	private JTextField searchField;
	private JLabel searchLabel;
	private JButton searchBtn2;
	private JLabel introduction;
	
	
  public JF2() throws SQLException {
    this.setTitle("Frame2");
    this.setSize(new Dimension(1200, 600));
    JPanel panel = MainPanel();
    this.add(panel);
    
  }
  public JPanel MainPanel() throws SQLException {
	  JPanel panel1 = Language();
	  JPanel panel2 = MusicStyle();
	  introduction = new JLabel("Please choose at least one language and any genre you like(Private recommendation!)");
	  createSeacrhButton();
	  createClearButton();
	  createSearchField();
	  createSearchBtn2();
	  resultTextArea = new JTextArea(20, 40);
	  resultTextArea.setEditable(false);
	 
		  
	  panel.setLayout(new BorderLayout());
	  
	  JPanel panel3 = new JPanel();
	   panel3.add(introduction);
	   panel3.add(panel1);
	   panel3.add(panel2);
	   panel3.add(searchBtn);
	   
	 JPanel panel5 = new JPanel();
	 panel5.add(searchLabel);
	 panel5.add(searchField);
	 panel5.add(searchBtn2);
	 
	 
	  panel4 = new JPanel();
	  panel.add(panel5,BorderLayout.EAST);
	  panel.add(panel3,BorderLayout.NORTH);
	  panel.add(resultTextArea,BorderLayout.CENTER);
	  panel.add(panel4,BorderLayout.WEST);
	  panel.add(clearBtn,BorderLayout.SOUTH);
	  return panel;
  }
  public JPanel Language() {
	  JPanel panel1 = new JPanel();
	  panel1.setBorder(new TitledBorder(new EtchedBorder(), "Language"));
	  chineseCheck = new JCheckBox("Chinese");
	  englishCheck = new JCheckBox("English");
	  koreanCheck = new JCheckBox("Korean");
	  panel1.add(chineseCheck);
	  panel1.add(englishCheck);
	  panel1.add(koreanCheck);
	 
	 return panel1; 
  }
  public JPanel MusicStyle() {
	  JPanel panel2 = new JPanel();
	  panel2.setBorder(new TitledBorder(new EtchedBorder(), "Music Style"));
	  edmCheck = new JCheckBox("EDM");
	  popCheck = new JCheckBox("pop");
	  rythemAndBlueCheck = new JCheckBox("R&B");
	  rockCheck = new JCheckBox("Rock/Band");
	  hiphopCheck = new JCheckBox("Hiphop");
	  
	  
	  panel2.add(edmCheck);
	  panel2.add(popCheck);
	  panel2.add(rythemAndBlueCheck);
	  panel2.add(rockCheck);
	  panel2.add(hiphopCheck);
	 
	 return panel2; 
  } 
  public void createSeacrhButton() {
	  searchBtn = new JButton("Search");
	  	class searchActionListener implements ActionListener{
	  		public void actionPerformed(ActionEvent e) {	
	  			try {
					search();
					createSouthPanel();
					clearBtn.setEnabled(true);
					searchBtn.setEnabled(false);
					panel.revalidate();
					panel.repaint();				
					} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
	  		}  
	  }
	  	searchBtn.addActionListener(new searchActionListener());
  }
  
  public void search() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * FROM Songlist_Backup WHERE Language IN (?, ?, ?) AND Genre IN (?, ?, ?, ?, ?) ORDER BY RAND() LIMIT 5");
			stat.setString(1, null);
			stat.setString(2, null);
			stat.setString(3, null);
			stat.setString(4, null);
			stat.setString(5, null);
			stat.setString(6, null);
			stat.setString(7, null);
			stat.setString(8, null);
				
				if(chineseCheck.isSelected()) {
					stat.setString(1,"Chinese");
				}
				if(englishCheck.isSelected()) {
					stat.setString(2, "English");
				}
				if(koreanCheck.isSelected()) {
					stat.setString(3, "Korean");
				}
				if(edmCheck.isSelected()) {
					stat.setString(4, "Eletro");
				}
				if(popCheck.isSelected()) {
					stat.setString(5, "Pop");
				}
				if(rythemAndBlueCheck.isSelected()) {
					stat.setString(6, "R&B");
				}
				if(hiphopCheck.isSelected()) {
					stat.setString(7, "Hip-Hop");
				}
				if(rockCheck.isSelected()) {
					stat.setString(8, "Rock/Band");
				}
				
				result = stat.executeQuery();
				urlList = new ArrayList<String>();
				songList = new ArrayList<String>();
				String str = null;
				str = String.format("%2s\t%5s\t\n", "11: ", "BTS");
				str += String.format("%50s\t %20s\t %50s\t \n", "Song", "Artist", "URL");
				while(result.next()) {
					  urlList.add(result.getString("URL"));
					  songList.add(result.getString("Song"));
					  str += String.format("%50s\t %20s\t %50s\t \n", result.getString("Song"),result.getString("Artist"),result.getString("URL"));
				  }
				resultTextArea.setText(str);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
  }	
  
  public void createSouthPanel() throws SQLException {

	  JLabel label1 = new JLabel(songList.get(0));
	  label1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label1.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				 try {
				        Desktop.getDesktop().browse(new URI(urlList.get(0)));   
				    } catch (IOException | URISyntaxException e1) {
				        e1.printStackTrace();
				    }
			    }
			 
			    @Override
			    public void mouseEntered(MouseEvent e) {
			    	label1.setText("<html><a href=''>GoGoGoGoGoGo</a></html>");
			    }
			 
			    @Override
			    public void mouseExited(MouseEvent e) {
			        // the mouse has exited the label
			    	label1.setText("goooooood La");
			    }
		});
	  JLabel label2 = new JLabel(songList.get(1));
	  label2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label2.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				 try {
				        Desktop.getDesktop().browse(new URI(urlList.get(1)));   
				    } catch (IOException | URISyntaxException e1) {
				        e1.printStackTrace();
				    }
			    }
			    public void mouseEntered(MouseEvent e) {
			    	label2.setText("<html><a href=''>GoGoGoGoGoGo</a></html>");
			    }
			 
			    @Override
			    public void mouseExited(MouseEvent e) {
			        // the mouse has exited the label
			    	label2.setText("goooooood La");
			    }
		});
	  JLabel label3 = new JLabel(songList.get(2));
	  label3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label3.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				 try {
				        Desktop.getDesktop().browse(new URI(urlList.get(2)));   
				    } catch (IOException | URISyntaxException e1) {
				        e1.printStackTrace();
				    }
			    }
			    public void mouseEntered(MouseEvent e) {
			    	label3.setText("<html><a href=''>GoGoGoGoGoGo</a></html>");
			    }
			    public void mouseExited(MouseEvent e) {
			        // the mouse has exited the label
			    	label3.setText("goooooood La");
			    }
		});
	  JLabel label4 = new JLabel(songList.get(3));
	  label4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label4.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				 try {
				        Desktop.getDesktop().browse(new URI(urlList.get(3)));   
				    } catch (IOException | URISyntaxException e1) {
				        e1.printStackTrace();
				    }
			    }
			    public void mouseEntered(MouseEvent e) {
			    	label4.setText("<html><a href=''>GoGoGoGoGoGo</a></html>");
			    }
			    public void mouseExited(MouseEvent e) {
			        // the mouse has exited the label
			    	label4.setText("goooooood La");
			    }
		});
	  JLabel label5 = new JLabel(songList.get(4));
	  label5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label5.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				 try {
				        Desktop.getDesktop().browse(new URI(urlList.get(4)));   
				    } catch (IOException | URISyntaxException e1) {
				        e1.printStackTrace();
				    }
			    }
			    public void mouseEntered(MouseEvent e) {
			    	label5.setText("<html><a href=''>GoGoGoGoGoGo</a></html>");
			    }
			    public void mouseExited(MouseEvent e) {
			        // the mouse has exited the label
			    	label5.setText("goooooood La");
			    }
		});
	 	
	  panel4.setLayout(new GridLayout(5,1));
	  panel4.add(label1);
	  panel4.add(label2);
	  panel4.add(label3);
	  panel4.add(label4);
	  panel4.add(label5);
	  
  }
  public void createClearButton() {
		clearBtn = new JButton("Clear");
		class clearListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				resultTextArea.setText("");
				panel4.removeAll();
				songList.clear();
				urlList.clear();
				clearBtn.setEnabled(false);
				searchBtn.setEnabled(true);
				panel.revalidate();
				panel.repaint();
			}
		}
		clearBtn.addActionListener(new clearListener());
	}
	
  public void createSearchField() {
	  searchLabel = new JLabel("Internet Search! (don't put any space in)");
	  searchField = new JTextField(10); 
  }
  
  public void createSearchBtn2()
  {
	  searchBtn2 = new JButton("search online!");
		class search2ActionListener implements ActionListener{
	  		public void actionPerformed(ActionEvent e) {
	  			
	  			String baseUrl = "https://www.last.fm/search?q=";
	  			String searchWord = searchField.getText();
	  			String totalUrl = baseUrl+searchWord;
	  			
	  			Desktop desktop = Desktop.getDesktop();   
	  			URI uri = null;
				try {
					uri = new URI(totalUrl);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
	  			try {
					desktop.browse(uri);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
	  		}
  }
		ActionListener search2Listener = new search2ActionListener();
		searchBtn2.addActionListener(search2Listener);
 }
    
  
}
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
