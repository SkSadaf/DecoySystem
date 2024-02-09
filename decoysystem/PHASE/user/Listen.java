import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*; 
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.*;
class Listen extends Thread
{

	public static ServerSocket serv;
	public static Socket socket;
	String user,orm="";
	String msg[]=new String[3000];
	String sourceip,destinationip,action;
	int sourceport,destinationport,get;
	Connection con;
	public Listen(int port,String u)
	{
		try
		{
			user=u;
			get=port;
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:decoysystem");
			con.setAutoCommit(true);
			serv = new ServerSocket(port);
			
		}
		catch(Exception ex)
		{		
			JOptionPane.showMessageDialog(new Frame(),"THIS USER NAME ALREADY EXISTS");
			System.exit(0);
		}
	}


	public void run()
	{
		while(true)
		{
			try
			{
				System.out.println("Receive Thread is Listening");
				System.out.println("user port is "+get);
				socket = serv.accept();
				Packet p = (Packet)new ObjectInputStream(socket.getInputStream()).readObject();
				String g_string=p.getMsg();
				int key=p.getkey();
				System.out.println(g_string);
				if(g_string.equalsIgnoreCase("File Is Not Available"))
				{
					User.jTextArea1.append("\nFile Is Not Available");
					JOptionPane.showMessageDialog(new Frame(),"File Is Not Available");
					
				}
				else
				{
					FileOutputStream fout=new FileOutputStream("file/"+p.getFileName());
					
					StringTokenizer s=new StringTokenizer(g_string,"@");
					
					while(s.hasMoreTokens())
					{
					orm=orm+String.valueOf((char)(Integer.parseInt(s.nextToken())-key));
					
					}
					
								
					
					fout.write(orm.getBytes());
					fout.close();
					User.jTextArea1.append("\nFile Is Available.");
				//	User.jTextArea1.append("\nFile Part Packet1 Is Received");
				//	User.jTextArea1.append("\nFile Part Packet2 Is Received");
					//User.jTextArea1.append("\nFile Part Packet3 Is Received");
					User.jTextArea1.append("\nThe Encrypted File Content is \n"+g_string);
					User.jTextArea1.append("\nThe Original File Content is \n"+orm);
					User.jTextArea1.append("\nFile DownLoaded successfully");
					JOptionPane.showMessageDialog(new Frame(),"File DownLoaded Successfully");	
					orm="";				
				}
				socket.close();			    	
				
			}
			catch(Exception ex)
			{
				System.out.println("ifkarudeen3 "+ex);
			}	
		}
	}
    
}

	