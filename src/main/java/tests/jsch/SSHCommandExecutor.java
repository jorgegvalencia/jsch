package tests.jsch;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
 
 
public class SSHCommandExecutor {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        String host="luria.dia.fi.upm.es";
        String command1="./start_servers.sh";
        String command2="./public_mm/bin/mmserver14 --relaxed_model";
        try{
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
             
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command2);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
             
            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            //while(true){
              while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
              }
              /*System.out.println(channel.getExitStatus());
              if(channel.isClosed()){
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
              }*/
              try{Thread.sleep(1000);}catch(Exception ee){}
            //}
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }
 
    }
 
}
