
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

  
public class ProjectFrame extends JFrame {
	  
	private JButton chooseButton;
	private JButton discoverButton;
	private String Filename;
    private ImageIcon imageForJF2;
    private ImageIcon imageForJF3;
	Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
 
	public ProjectFrame() {
		this.setTitle("BTS");
		JPanel panel = createMain();
		this.setSize((int)screenSize.getHeight(),(int)screenSize.getWidth());
		this.add(panel);
		
	}
	public JPanel createMain() {
		
		class chooseActionListener implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					JF2 new2;
					try {
						new2 = new JF2();
						new2.setLocation(50, 50);
				      new2.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				      new2.setVisible(true);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		class discoverActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				JF3 new3 = new JF3();
			      new3.setLocation(50, 50);
			      new3.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			      new3.setVisible(true);
			}
		}
		
		
		LoadFileToJF2();
		LoadFileToJF3();
		
		chooseButton.setBorder(new EtchedBorder());		
		chooseButton.addActionListener(new chooseActionListener());
		
		
		
		JPanel jPanel= new JPanel();
		
		
				
	    discoverButton.setBorder(new EtchedBorder());
	    discoverButton.addActionListener(new discoverActionListener());
	    
	    jPanel.add(chooseButton);
	    jPanel.add(discoverButton);
	    jPanel.setLayout(new GridLayout(1, 2));
	    return jPanel;
	}
	public void LoadFileToJF2()
    {
            imageForJF2 = new ImageIcon("2.jpg");//設定檔名
            Dimension dimension = new Dimension(imageForJF2.getIconWidth(), imageForJF2.getIconHeight());
            try
            {	
            	chooseButton = new JButton("Choooooose what you like !",imageForJF2);
            	chooseButton.setPreferredSize(dimension);
            	chooseButton.setMaximumSize(dimension);
            	chooseButton.setMinimumSize(dimension);
            	
            }
            catch(Exception e)
            {
                    javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: "+Filename);
                    imageForJF2=null;//如果錯誤的話顯示錯誤訊息
            }
    }
	public void LoadFileToJF3()
    {
            imageForJF3 = new ImageIcon("3.jpg");//設定檔名
            Dimension dimension = new Dimension(imageForJF3.getIconWidth(), imageForJF3.getIconHeight());
            try
            {	
            	discoverButton = new JButton("Discover by Genre & Mood !",imageForJF3);
            	discoverButton.setPreferredSize(dimension);
            	discoverButton.setMaximumSize(dimension);
            	discoverButton.setMinimumSize(dimension);
            	
            }
            catch(Exception e)
            {
                    javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: "+Filename);
                    imageForJF3=null;//如果錯誤的話顯示錯誤訊息
            }
    }
 
 }
