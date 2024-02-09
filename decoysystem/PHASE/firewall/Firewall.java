import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class Firewall extends javax.swing.JFrame 
{
	String uid="";	
	Connection con;
	int port;
    public Firewall(String u,int p) {
    	try
		{
			uid=u;
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
        jScrollPane1 = new JScrollPane(jTextArea1);
        jTextArea1.setEditable(false);
        jButton3 = new javax.swing.JButton ();
        jTextArea1.setText("FireWall Started");
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
        jTextArea1.setFont (new java.awt.Font ("Verdana", 0, 14));
        jTextArea1.setBackground (java.awt.Color.black);

 		jScrollPane1.setBounds(20, 110, 350, 370);
        getContentPane ().add (jScrollPane1);

        
        jButton3.setForeground (java.awt.Color.white);
        jButton3.setFont (new java.awt.Font ("Comic Sans MS", 1, 14));
        jButton3.setText ("Exit ");
        jButton3.setBackground (java.awt.Color.black);
        jButton3.addActionListener (new java.awt.event.ActionListener ()
         {
            public void actionPerformed (java.awt.event.ActionEvent evt) 
            {
            	try
  				{
  					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
  					
  					Statement st2=con.createStatement();
					String sq="update Firewall set status='no' where username='"+uid+"'";
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
        jButton3.setBounds(160, 480, 100, 40);
         getContentPane ().add (jButton3);

    }

  
    private void exitForm(java.awt.event.WindowEvent evt) 
    {
    	try
  				{
  					Statement st2=con.createStatement();
					String sq="update Firewall set status='no' where username='"+uid+"'";
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

    public boolean copyFile(String fpath,String fname)
     {
           System.out.println(fpath);
           System.out.println(fname);
           try
           {
	           java.io.File file = new java.io.File(fpath);
	           byte b[] = new byte[(int)file.length()];
	           java.io.FileInputStream  fin = new java.io.FileInputStream(file);
	           fin.read(b);
	           fin.close();
	           String dir = System.getProperty("user.dir");
	           java.io.FileOutputStream fout = new java.io.FileOutputStream(dir+"\\Files\\"+fname);
	           fout.write(b);
	           fout.close();
           }
           catch(Exception e)
           {
	              return false;
           }
           return true;
    }


    
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;

}



