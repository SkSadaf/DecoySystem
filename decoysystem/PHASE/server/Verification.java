import java.io.*;
import java.util.*;
import java.net.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
public class Verification
{
	Connection con;
	Verification(String uid,String pw)
	{
		try
		{		 
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:decoysystem");
			con.setAutoCommit(true);
			existuser(uid,pw);
		}
		catch(Exception thr)
		{
			System.out.println("err"+thr);
		}		
	}
	public void existuser(String uid,String pwd)
	{
		try
		{
				
			String na=InetAddress.getLocalHost().getHostAddress();
   			Statement st1=con.createStatement();
			String sql="select username,machineip,port from server where username='"+uid+"' and password='"+pwd+"'";
			ResultSet rs=st1.executeQuery(sql);
			String str="",mip="",group="";
			int port=0;		
			while(rs.next())
	        {
			  	str=rs.getString("username");
			  	mip=rs.getString("machineip");
			  	port=rs.getInt("port");
				   }
		    st1.close();
		    if(str=="")
			{
				JOptionPane.showMessageDialog(null,"Invalid User Name or Password .."); 
				ServerLogin l= new ServerLogin ();
       			l.setSize(400,200); 
       			l.setVisible(true);
       			l.setResizable(false);
       		}
			else
			{
			 	Statement st2=con.createStatement();
			 	String sq="update server set status='yes',machineip='"+na+"' where username='"+str+"'";
				System.out.println(sq);
				int s2=st2.executeUpdate(sq);
				Thread.sleep(500);
				Listen l=new Listen(port,str);
				l.start();	
				Server s=new Server(str,port);
				s.show ();
		   		s.setSize(400,547);
		   		s.setTitle("SERVER");
		   		s.setDefaultCloseOperation(0);
		   		s.setVisible(true);
			   	
			 }		   	
		}
		catch(Exception e)
		{
			System.out.println("The Errep "+e);
		}
		
	}
	
}
