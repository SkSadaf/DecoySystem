import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*; 
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.*;
class Listen extends Thread
{

	public static ServerSocket serv;
	public static Socket socket,soc;
	String user;
	String msg1="";
	String sourceip,destinationip,action;
	int sourceport,destinationport,key;
	Connection con;
	BigInteger bn;
	
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
				System.out.println("Recieve Thread is Listening");
				socket = serv.accept();
				Packet p = (Packet)new ObjectInputStream(socket.getInputStream()).readObject();
				String g_string=p.getMsg();
				System.out.println(p.getFileName());					
				if(g_string.equalsIgnoreCase(""))
				{
					Firewall.jTextArea1.append("\nClient Request a File File Name is :"+p.getFileName());
					if(p.getStatus().equalsIgnoreCase("valid"))
					{
						Statement st1=con.createStatement();
						String sql="select username,machineip,port from server where status='yes'";
						System.out.println(sql);
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
							p1.setMsg("");
							p1.setMachineip(p.getMachineip());
							p1.setPort(p.getPort());							
							new ObjectOutputStream(soc.getOutputStream()).writeObject(p1);
							System.out.println("Send Completed .."+port);
							soc.close();	
						}
						else
							JOptionPane.showMessageDialog(new Frame(),"Server Is Not Available");
					}
					else
					{
						
						
						
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
							String enmsg="";
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
								System.out.println("String over  ->"+de);
							}	
								
							Firewall.jTextArea1.append("\nFile Is Available.");
							Firewall.jTextArea1.append("\nFile Is Encrypted.");
							Firewall.jTextArea1.append("\nThe Encrypted File Content is "+msg1);
						
						//	Firewall.jTextArea1.append("\nFile Is Splited to Three Equal Parts.");
						//	Firewall.jTextArea1.append("\nFile Part Is :Packet1.");
						//	Firewall.jTextArea1.append("\nFile Part Is :Packet2.");
						//	Firewall.jTextArea1.append("\nFile Part Is :Packet3.");
						}
						else
						{
							msg1="File Is Not Available";
							Firewall.jTextArea1.append("\nFile Is Not Available.so,Send error Message");
						}
						soc =new Socket(p.getMachineip(),p.getPort());
			    		Packet p1= new Packet(100);
						p1.setFileName(p.getFileName());	
						msg1.toString();					
						p1.setMsg(msg1);
						p1.setkey(key);
						new ObjectOutputStream(soc.getOutputStream()).writeObject(p1);
						System.out.println("Send Completed .."+p.getMsg());
						soc.close();					
					}
											
				}
				else
				{
					soc =new Socket(p.getMachineip(),p.getPort());
			    	Packet p1= new Packet(100);
					p1.setFileName(p.getFileName());						
					p1.setMsg(p.getMsg());
					p1.setkey(p.getkey());
					new ObjectOutputStream(soc.getOutputStream()).writeObject(p1);
					System.out.println("Send Completed .."+p.getMsg());
					System.out.println("Send Completed .."+p.getkey());
					soc.close();
					Firewall.jTextArea1.append("\nFile Content Recieved From Server and Send To Client");	
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

	