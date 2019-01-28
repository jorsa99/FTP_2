package es.fempa.jorge.ftp;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

public class FtpThread extends Thread {


    public enum Mode {Upload, Download}

    private final String hostIp = "192.168.2.10";
    private final int hostPort = 21;

    private String ruta, fichero, texto;

    private Mode mode;
    private FTPClient client;

    public FtpThread(Mode mode, String _ruta, String _fichero, String _texto){
        this.ruta = _ruta;
        this.fichero = _fichero;
        this.texto = _texto;
        client = new FTPClient();
        this.mode = mode;
    }

    //THREAD METHODS

    @Override
    public void run() {
        connect();
        switch (mode){
            case Upload:
                upload();
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
        connect();

        try {

            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            String data = "/Desktop"+ fichero ;

            FileInputStream in = new FileInputStream(new File(data));
            boolean result = client.storeFile(ruta+"/"+fichero, in);
            in.close();
            if (result){
                Log.e("1", "FUUUUUUUUUUUUUUUUUUUUUUNCIONA");
            } else {
                Log.e("2", "NO VAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
            client.logout();
            client.disconnect();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void download(){

    }

}
