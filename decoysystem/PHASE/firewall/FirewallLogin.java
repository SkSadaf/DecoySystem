
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class FirewallLogin extends JFrame {

    public FirewallLogin()
     {
	  initComponents ();
	     }
    private void initComponents () 
    {
        
        jPanel2 = new JPanel ();
        jPanel3 = new JPanel ();
        jLabel1 = new JLabel ("User Name");
        jLabel2 = new JLabel ("Password");
       
        jTextField2 = new JTextField (20);
	    txtpwd = new JPasswordField (20);
        jTextField2.setText("fire1");
		txtpwd.setText("fire1");                
                
        btnlogin = new JButton ("Login");
        btnlogin.setBackground(new Color(140,170,250));
	    
        setTitle("Firewall Login Form");
        
        btnlogin.addActionListener(new ActionListener()
        {
			public void actionPerformed(ActionEvent ae)
			{
				if(isValid(txtpwd) && isValid(jTextField2)) 
				{
                    dispose();
                    Verification k= new Verification(jTextField2.getText(),txtpwd.getText());
					
				}
					else
						JOptionPane.showMessageDialog(null,"TextField is empty  ..");

			}
		});
        



        addWindowListener (new WindowAdapter () {
            public void windowClosing (WindowEvent evt) {
                        closeDialog (evt);
            }
        }
        );

          getContentPane ().add (jPanel2, BorderLayout.CENTER);
          getContentPane ().add (jPanel3, BorderLayout.SOUTH);
          jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER)); 
           jPanel3.setBackground(new Color(150,160,230));
          jPanel2.setLayout (null);
          jPanel2.setBorder (new EtchedBorder());
           jPanel2.setBackground(new Color(150,160,230));
          jPanel2.add(jLabel1);
          jLabel1.setBounds(50, 40, 90, 20);  //
          jPanel2.add (jTextField2);
          jTextField2.setBounds(190, 40, 150, 20);
          jPanel2.add (jLabel2);  
          jLabel2.setBounds(50, 90, 90, 20);
          jPanel2.add (txtpwd);
          txtpwd.setBounds(190, 90, 150, 20);
          
          jPanel3.add (btnlogin);
	  	
          
          setResizable(false);           
    }
    private void closeDialog(WindowEvent evt) {
                System.exit(0);
    }                      
    private boolean isValid(JTextField txt) {
	
			if(txt.getText().trim().length() > 0 )
				return  true;
			else
				return false; 
    }
    public static void main (String args[]) {
       FirewallLogin l= new FirewallLogin ();
       l.setSize(400,200); 
       l.setVisible(true);
       l.setResizable(false);
	 
    }
      JPanel jPanel2;
      JLabel jLabel1,jLabel2,group;
      JTextField jTextField2,txtserver;
      JPasswordField txtpwd;
      JPanel jPanel3;
	  JButton btnlogin,btnregister;
}