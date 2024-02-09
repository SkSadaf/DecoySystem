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
			String status="";	
			String na=InetAddress.getLocalHost().getHostAddress();
			Statement st0=con.createStatement();
			String sql0="select username,machineip,port,status from User where username='"+uid+"' and password='"+pwd+"' and status='yes'";
			ResultSet rs0=st0.executeQuery(sql0);
			String str="",mip="",group="";
			int port=0;		
			if(rs0.next())
		    {
		    	if(!rs0.getString("status").equalsIgnoreCase("yes"))
		    	{
	   				Statement st1=con.createStatement();
					String sql="select username,machineip,port from User where username='"+uid+"' and password='"+pwd+"'";
					ResultSet rs=st1.executeQuery(sql);
					while(rs.next())
	        		{
			  			str=rs.getString("username");
			  			mip=rs.getString("machineip");
			  			port=rs.getInt("port");
					}
		    		st1.close();
		    	}
		    	else
		    	{
		    		status="already";
		    		str=rs0.getString("username");
				  	mip=rs0.getString("machineip");
				  	port=rs0.getInt("port");
					st0.close();
		    	}
		    }
		    else
		    {
		    	Statement st1=con.createStatement();
				String sql="select username,machineip,port from User where username='"+uid+"' and password='"+pwd+"'";
				ResultSet rs=st1.executeQuery(sql);
				while(rs.next())
	        	{
					str=rs.getString("username");
					mip=rs.getString("machineip");
					port=rs.getInt("port");
				}
		    	st1.close();	    
		    }
		    if(str=="")
			{
				JOptionPane.showMessageDialog(null,"Invalid User Name or Password .."); 
			    Toolkit tk = Toolkit.getDefaultToolkit();
				int width = 400;
				int height = 200;
				UserLogin SerMFrm = new UserLogin();
				SerMFrm.setLocation((tk.getScreenSize().width - width) / 2, (tk.getScreenSize().height - height) / 2);
				SerMFrm.setSize(width, height);
				SerMFrm.setVisible(true);
			}
			else
			{
			 	Statement st2=con.createStatement();
			 	String sq="update User set status='yes',machineip='"+na+"' where username='"+str+"'";
				System.out.println(sq);
				int s2=st2.executeUpdate(sq);
				Thread.sleep(500);
				Listen l=new Listen(port,str);
				l.start();			
				Statement st3=con.createStatement();
				String sql3="select question,answer from question";
				ResultSet rs3=st3.executeQuery(sql3);
				String question="",answer="";	
				int count=0,errorcount=0;
				while(rs3.next())
	        	{
			  		question=rs3.getString("question");
			  		answer=rs3.getString("answer");
			  		String ans=JOptionPane.showInputDialog(new Frame(),question);
			  		if(ans.equalsIgnoreCase(answer))
			  		{
			  			count++;
			  		}
			  		else
			  		{
			  			errorcount++;
			  		}
			  			
				
			  	}
			  	if(count==6)
			  		{
			  			User r=new User(str,mip,port,"valid");
 			 		    r.show ();
						r.setSize(400,460);
						r.setTitle("User");
						//break;
					}
					else if(errorcount>=1)//||status.equalsIgnoreCase("already"))
			  		{
			  			User r=new User(str,mip,port,"invalid");
 			 		    r.show ();
						r.setSize(400,460);
						r.setTitle("User");
					//	break;
					}				
			   	st3.close();
			}
		 		   	
		}
		catch(Exception e)
		{
			System.out.println("The Errep "+e);
			System.exit(0);
		}
		
	}
	
}
