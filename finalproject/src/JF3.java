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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JF3 extends JFrame{
	
	private JButton indieBtn;
	private JButton studychillBtn;
	private JButton hypeBtn;
	private ResultSet result;
	private JTextArea resultArea= new JTextArea(20, 30);
	private JPanel panel4;
	private ArrayList<String> songlist = new ArrayList<String>();
	private ArrayList<String> urlList = new ArrayList<String>();
	private JButton clearBtn;
	private JButton searchBtn2;
	private JTextField searchField;
	private JLabel searchLabel;
	
	private JPanel panel2 = new JPanel();
	
	public JF3() {
		this.setTitle("JF3");
	    this.setSize(new Dimension(1200, 600));
		this.setLayout(new BorderLayout());
		JPanel mainPanel = mainPanel();
		this.add(mainPanel,BorderLayout.CENTER);
	}
public JPanel mainPanel() {
		
		createChillBtn();
		createHypeBtn();
		createIndieBtn();
		createClearButton();
		
		createSearchBtn2();
		createSearchField();
		
		clearBtn.setEnabled(false);
		JPanel panel = new JPanel();
				
		panel.add(studychillBtn);
		panel.add(hypeBtn);
		panel.add(indieBtn);
		
		panel.setLayout(new GridLayout(1, 3));
		resultArea.setEditable(false);
		
		JPanel panel3 = new JPanel();
		panel3.add(searchLabel);
		panel3.add(searchField);
		panel3.add(searchBtn2);
		
		panel2.setLayout(new BorderLayout());
		panel4 = new JPanel();
		panel2.add(panel,BorderLayout.NORTH);
		panel2.add(panel3,BorderLayout.EAST);
		panel2.add(resultArea,BorderLayout.CENTER);
		panel2.add(panel4,BorderLayout.WEST);
		panel2.add(clearBtn,BorderLayout.SOUTH);
		
		return panel2;
	}
public void createChillBtn() {
		studychillBtn = new JButton("Study & Chill");
			class chillListener implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						search1();
						createSouthPanel();
						panel2.revalidate();
						panel2.repaint();
						buttonFalse();
						clearBtn.setEnabled(true);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			studychillBtn.addActionListener(new chillListener());
	}
public void createIndieBtn() {
		indieBtn = new JButton("INDIE VIBES");
		class indieListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					search3();
					createSouthPanel();
					panel2.revalidate();
					panel2.repaint();
					buttonFalse();
					clearBtn.setEnabled(true);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		indieBtn.addActionListener(new indieListener());
	}
public void createHypeBtn() {
		hypeBtn = new JButton("Hype");
		class hypeListener implements ActionListener  {
			public void actionPerformed(ActionEvent e) {
				try {
					search2();
					createSouthPanel();
					panel2.revalidate();
					panel2.repaint();
					buttonFalse();
					clearBtn.setEnabled(true);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		hypeBtn.addActionListener(new hypeListener());
	}
public void search1() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * FROM Songlist_Backup WHERE Mood = ?  ORDER BY RAND() LIMIT 5");
			
			
				stat.setString(1, "Study and chill");
	
				
				result = stat.executeQuery();
				
				String str = null;
				str = String.format("%10s\t%10s\t\n", "11: ", "BTS");
				str += String.format("%50s\t %20s\t %50s\t \n", "Song", "Artist", "URL");
				while(result.next()) {	
					songlist.add(result.getString("Song"));
					urlList.add(result.getString("URL"));
					str += String.format("%50s\t %20s\t %50s\t \n", result.getString("Song"),result.getString("Artist"),result.getString("URL"));
				}
				resultArea.setText(str);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
  }	
public void search2() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * FROM Songlist_Backup WHERE Mood = ?  ORDER BY RAND() LIMIT 5");
			
			
				stat.setString(1,"Hype");
	
				
				result = stat.executeQuery();
				
				String str = null;
				str = String.format("%2s\t%5s\t\n", "11: ", "BTS");
				str += String.format("%50s\t %20s\t %50s\t \n", "Song", "Artist", "URL");
				while(result.next()) {
					songlist.add(result.getString("Song"));
					urlList.add(result.getString("URL"));
						str += String.format("%50s\t %20s\t %50s\t \n", result.getString("Song"),result.getString("Artist"),result.getString("URL"));
						
				}
				resultArea.setText(str);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
  }	
	
public void search3() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * FROM Songlist_Backup WHERE Mood = ?  ORDER BY RAND() LIMIT 5");
			
			
				stat.setString(1, "Indie vibes");
	
				
				result = stat.executeQuery();
				
				String str = null;
				str = String.format("%10s\t%10s\t\n", "11: ", "BTS");
				str += String.format("%50s\t %20s\t %50s\t \n", "Song", "Artist", "URL");
				songlist = new ArrayList<String>();
				while(result.next()) {	
						
						songlist.add(result.getString("Song"));
						urlList.add(result.getString("URL"));
						str += String.format("%50s\t %20s\t %50s\t \n", result.getString("Song"),result.getString("Artist"),result.getString("URL"));
						
				}
				resultArea.setText(str);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
  }	
	
public void createSouthPanel() throws SQLException {
		  JLabel label1 = new JLabel(songlist.get(0));
		  label1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			label1.addMouseListener(new MouseAdapter() {
				 public void mouseClicked(MouseEvent e) {
					 try {
					        Desktop.getDesktop().browse(new URI(urlList.get(0)));   
					    } catch (IOException | URISyntaxException e1) {
					        e1.printStackTrace();
					    }
				    }
				    public void mouseEntered(MouseEvent e) {
				    	label1.setText("<html><a href=''>GoGoGoGoGoGo</a></html>");
				    }
				    public void mouseExited(MouseEvent e) {
				        // the mouse has exited the label
				    	label1.setText("goooooood La");
				    }
			});
		  JLabel label2 = new JLabel(songlist.get(1));
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
				    public void mouseExited(MouseEvent e) {
				        // the mouse has exited the label
				    	label2.setText("goooooood La");
				    }
			});
		  JLabel label3 = new JLabel(songlist.get(2));
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
		  JLabel label4 = new JLabel(songlist.get(3));
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
		  JLabel label5 = new JLabel(songlist.get(4));
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
				resultArea.setText("");
				panel4.removeAll();
				songlist.clear();
				urlList.clear();
				buttonTrue();
				clearBtn.setEnabled(false);
				panel2.revalidate();
				panel2.repaint();
			}
		}
		clearBtn.addActionListener(new clearListener());
	}
	public void buttonFalse() {
		indieBtn.setEnabled(false);
		studychillBtn.setEnabled(false);
		hypeBtn.setEnabled(false);
	}
	public void buttonTrue() {
		indieBtn.setEnabled(true);
		studychillBtn.setEnabled(true);
		hypeBtn.setEnabled(true);
	}
	
	public void createSearchField() {
		  searchLabel = new JLabel("Internet search! (don't put any space in)");
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
