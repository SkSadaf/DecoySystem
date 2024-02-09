import java.io.*;

public class Packet   implements Serializable
{
      String msg,machineip,status,name;
      byte data[];
	  int mode,port,key ;
	  String filename;
      boolean flag,fl; 
	 public Packet(int i)
        {
            data = new byte[i];
		    mode = 0;
        }
        public void setMsg(String s) {
                msg =s;
        }
        public String getMsg() {
                return msg;
        }
        public void setname(String s) {
                name =s;
        }
        public String getname() {
                return name;
        }

		public void setFileName(String s) {
                filename=s;
        }
        public String getFileName() {
                return filename;
        }

	public void setMachineip(String s) {
                machineip=s;
        }
        public String getMachineip() {  
                      return machineip;
        }
    public void setPort(int s) {
                port=s;
        }
        public int getPort() {  
                      return port;
        }
        public void setStatus(String s) {
                status=s;
        }
        public String getStatus() {  
                      return status;
        }
      public void setkey(int s)
        {
        	key=s;
        }
        public int getkey()
        {
        	return key;
        }

}


