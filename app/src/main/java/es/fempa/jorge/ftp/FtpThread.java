package es.fempa.jorge.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.net.SocketException;

public class FtpThread extends Thread {

    public enum Mode {Upload, Download}

    private final String hostIp = "192.168.2.10";
    private final int hostPort = 21;

    private Mode mode;
    private FTPClient client;

    public FtpThread(Mode mode){
        client = new FTPClient();
        this.mode = mode;
    }

    //THREAD METHODS

    @Override
    public void run() {
        connect();
        switch (mode){
            case Upload:

                break;

            case Download:

                break;

            default:

                break;
        }
    }

    //CLASS METHODS

    private void connect(){
        try{
            client.connect(hostIp, hostPort);
            client.enterLocalPassiveMode();
            client.login("2599130_jorge", "habbojorge1");
        } catch (SocketException e){

        } catch (IOException e){

        }

    }

    private void upload(){

    }

    private void download(){

    }

}
