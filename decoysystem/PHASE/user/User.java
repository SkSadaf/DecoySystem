import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class User extends javax.swing.JFrame 
{
	String uid="",usermachineip="",sec="",status="";	
	Connection con;
	int userport;
	Socket soc;
    public User(String u,String m,int p,String sta) {
    	try
		{
			uid=u;
			usermachineip=m;
			userport=p;
			status=sta;
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:decoysystem");
			con.setAutoCommit(true);
			initComponents ();
        }
		catch(Exception thr)
		{
			System.out.println("err"+thr);
		}		
        
    }

    private void initComponents ()
     {
        jLabel1 = new javax.swing.JLabel (new ImageIcon("vpn.jpg"));
        jTextArea1 = new javax.swing.JTextArea ();
        jTextFiled1 = new javax.swing.JTextField ();
        jButton2 = new javax.swing.JButton ();
        jButton4 = new javax.swing.JButton ();
        jButton3 = new javax.swing.JButton ();
        jLabel2 = new JLabel();//"ON LINE CLIENTS         :");
        jLabel3 = new JLabel("ENTER THE FILE NAME :");
        jLabel4 = new JLabel();//"SECRECT KEY                :");
        jTextFiled2 = new javax.swing.JTextField ();
        jTextFiled2.setEditable(false);
        jTextFiled2.setText(sec);
	    jCombo=new JComboBox();
	    getContentPane ().setLayout (null);
        addWindowListener (new java.awt.event.WindowAdapter () {
            public void windowClosing (java.awt.event.WindowEvent evt) {
                exitForm (evt);
            }
        }
        );
        jLabel1.setBounds(5,0,380,100);
        getContentPane ().add (jLabel1);

        jTextArea1.setForeground (java.awt.Color.white);
        jTextArea1.setFont (new java.awt.Font ("Arial", 0, 14));
        jTextArea1.setBackground (java.awt.Color.black);
        jsp=new JScrollPane(jTextArea1);
        jsp.setBounds(20, 140, 350, 230);
		getContentPane ().add (jsp);
		jLabel2.setForeground(java.awt.Color.black);
		jLabel2.setFont (new java.awt.Font ("Verdana",Font.BOLD, 15));
        jCombo.setBounds(0,0,0,0);
        getContentPane ().add (jCombo);

        jLabel3.setForeground(java.awt.Color.black);
		jLabel3.setFont (new java.awt.Font ("Verdana",Font.BOLD, 15));
		jLabel3.setBounds(5, 100, 200, 25);		
        getContentPane ().add (jLabel3);
        jTextFiled1.setBounds(220, 100, 150, 25);
        getContentPane ().add (jTextFiled1);
        
        jLabel4.setForeground(java.awt.Color.black);
		jLabel4.setFont (new java.awt.Font ("Verdana",Font.BOLD, 15));
        
        jButton2.setForeground (java.awt.Color.white);
        jButton2.setFont (new java.awt.Font ("Comic Sans MS", 1, 14));
        jButton2.setText ("DownLoad");
        jButton2.setBackground (java.awt.Color.black);
        jButton2.addActionListener (new java.awt.event.ActionListener ()
         {
            public void actionPerformed (java.awt.event.ActionEvent evt) 
            {
        		if(jTextFiled1.getText().equals(""))
				{
					JOptionPane.showMessageDialog(new Frame(),"ENTER THE FILE NAME");
				}
				else
				{
					try
					{
							UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
						Statement st1=con.createStatement();
						String sql="select username,machineip,port from firewall where status='yes'";
						ResultSet rs=st1.executeQuery(sql);
						String str="",mip="";
						int port=0;		
						while(rs.next())
	        			{
				  			str=rs.getString("username");
				  			mip=rs.getString("machineip");
				  			port=rs.getInt("port");			  	
						}
		    			st1.close();
		    			if(str!="")
						{
							soc =new Socket(mip,port);
			    			Packet p = new Packet(100);
							p.setFileName(jTextFiled1.getText());	
							p.setMsg("");
							
							p.setMachineip(usermachineip);
							p.setPort(userport);
							p.setStatus(status);
							new ObjectOutputStream(soc.getOutputStream()).writeObject(p);
							System.out.println("Send Completed .."+port);
							soc.close();	
						}
						else
						{
							JOptionPane.showMessageDialog(new Frame(),"FireWall Is Not Available");
						}
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				}	
					
            }
        });
        
         jButton2.setBounds(50, 380, 120, 40);
         getContentPane ().add (jButton2);
       
        jButton3.setForeground (java.awt.Color.white);
        jButton3.setFont (new java.awt.Font ("Comic Sans MS", 1, 14));
        jButton3.setText ("Exit");
        jButton3.setBackground (java.awt.Color.black);
        jButton3.addActionListener (new java.awt.event.ActionListener ()
         {
            public void actionPerformed (java.awt.event.ActionEvent evt) 
            {
            	try
  				{
  					Statement st2=con.createStatement();
					String sq="update user set status='no' where username='"+uid+"'";
					int s=st2.executeUpdate(sq);
					if(s>0)
					{
						System.out.println("Seetedd"+s);
						System.out.println(sq);
					}
					st2.close();
					con.close();
				}
				catch(Exception e)
	 			{		
	 				System.out.println(e);
				}	
				dispose();
				System.exit(0);
            }
        }
        );
        jButton3.setBounds(250, 380, 100, 40);
        getContentPane ().add (jButton3);
    }

  
    private void exitForm(java.awt.event.WindowEvent evt) 
    {
    	try
  		{
  			Statement st2=con.createStatement();
			String sq="update user set status='no' where username='"+uid+"'";
			System.out.println(sq);
			int s=st2.executeUpdate(sq);
			if(s>0)
				System.out.println("Seetedd");
			st2.close();
			con.close();
		}
		catch(Exception e)
	 	{		
	 		System.out.println(e);
		}	
		dispose();
		System.exit(0);
      }

    
  
    private javax.swing.JLabel jLabel1,jLabel2,jLabel3,jLabel4;
    public static javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextField jTextFiled1,jTextFiled2;
    private javax.swing.JButton jButton1;
    
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jsp;
    private javax.swing.JComboBox jCombo;
}

