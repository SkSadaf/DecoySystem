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
	public static Socket socket,soc;
	String user,msg1;
	String sourceip,destinationip,action;
	int sourceport,destinationport,key;
	BigInteger bn;
	Connection con;
	public Listen(int port,String u)
	{
		try
		{
			user=u;
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
				socket = serv.accept();
				Packet p = (Packet)new ObjectInputStream(socket.getInputStream()).readObject();
				Server.jTextArea1.append("\nClient Request a File File Name is :"+p.getFileName());
				String filename=p.getFileName();
				File f=new File("file/"+filename);
				String msg="";
				if(f.exists())
				{
					FileInputStream fin=new FileInputStream(f);
					byte b[]=new byte[fin.available()];
					fin.read(b,0,b.length);
					fin.close();
					msg=new String(b);
					String str="",enmsg="";
					Random r=new Random();
					bn=new BigInteger(16,r);
							
							try
							{
								key=Integer.parseInt(bn.toString());
							for(int i=0;i<=msg.length();i++)
							{
								if(i==0)
								{
									enmsg="@"+String.valueOf((int)msg.charAt(i)+Integer.parseInt(bn.toString()));
									msg1=enmsg;
								}
								else
								{
									enmsg=enmsg+"@"+String.valueOf((int)msg.charAt(i)+Integer.parseInt(bn.toString()));
									msg1=enmsg;
								
								}	
						
							}
							}
							catch(Exception de) 		
							{
								//System.out.println("String over  ->"+de);
							}	
					
					Server.jTextArea1.append("\nFile Is Available.");
					Server.jTextArea1.append("\nFile Is Encrypted.");
					Server.jTextArea1.append("\nThe Encrypted File Content is \n"+msg1);
					
			
				}
				else
				{
					msg1="File Is Not Available";
					Server.jTextArea1.append("\nFile Is Not Available.so,Send error Message");
				}
				System.out.println(msg);
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
			    	Packet p1= new Packet(100);
					p1.setFileName(p.getFileName());						
					p1.setMsg(msg1);
					p1.setkey(key);
					System.out.println("server key -->"+key);
					p1.setMachineip(p.getMachineip());
					p1.setPort(p.getPort());
					new ObjectOutputStream(soc.getOutputStream()).writeObject(p1);
					System.out.println("Send Completed .."+port);
					soc.close();
						
				}
				else
				{
					JOptionPane.showMessageDialog(new Frame(),"FireWall Is Not Available");
				}			
				socket.close();			    	
				
			}
			catch(Exception ex)
			{
				System.out.println(ex);
			}	
		}
	}
    
}

	