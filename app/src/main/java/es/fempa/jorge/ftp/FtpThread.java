package es.fempa.jorge.ftp;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;

public class FtpThread extends Thread {

    public enum Mode {Upload, Download}

    private Context context;

    private final String hostIp = "192.168.2.10";

    private Mode mode;
    private FTPClient client;

    private EditText
            etPath,
            etFile,
            etContent;

    private String
            path,
            file,
            content;

    public FtpThread(Context context, Mode mode, EditText etPath, EditText etFile, EditText etContent){
        this.context = context;
        this.mode = mode;
        this.etPath = etPath;
        this.etFile = etFile;
        this.etContent = etContent;
    }

    //THREAD METHODS

    @Override
    public void run() {
        path = etPath.getText().toString();
        file = etFile.getText().toString();
        content = etContent.getText().toString();
        connect(mode);
    }

    //CLASS METHODS

    //Connection methods

    private void connect(Mode mode){
        client = new FTPClient();
        try{
            client.setConnectTimeout(10000);
            client.connect(InetAddress.getByName(hostIp));
            client.login("2599130_jorge", "habbojorge1");
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            if(!FTPReply.isPositiveCompletion(client.getReply()))
                disconnect();
            else if (mode.equals(Mode.Upload)){
                upload();
            } else if(mode.equals(Mode.Download)){
                download();
            }
        } catch (SocketException e){
            //TODO: Handle exception.
        } catch (IOException e){
            //TODO: Handle exception.
        }

    }

    private void upload(){
        try {
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            String data = "/Desktop"+ file ;

            FileInputStream in = new FileInputStream(new File(data));
            boolean result = client.storeFile(path+"/"+file, in);
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

    private void download() {
        //Path is entered.
        if(path.length()>0){
            try {
                if(client.changeWorkingDirectory(path)){
                    //File name not entered: list file names.
                    if(file.length()==0) {
                        String[] nameArray = client.listNames();
                        if(nameArray.length != 0) {
                            for(String name : nameArray)
                                etContent.append(name+"\n");
                        }
                        else{
                            //TODO: path is empty ERROR.
                        }
                    }
                    //File name entered: read file content.
                    else {
                        boolean fileExists = false;
                        File temp = new File(context.getDataDir()+"myfile.txt");
                        InputStream in;
                        OutputStream os;
                        for(FTPFile currentFile : client.listFiles()){
                            if(currentFile.getName().equals(file)){
                                fileExists = true;
                                in = client.retrieveFileStream(currentFile.getName());
                                os = new BufferedOutputStream(new FileOutputStream(temp));
                                byte[] bytes = new byte[4096];
                                int readBytes;
                                while((readBytes = in.read(bytes)) != -1)
                                    os.write(bytes, 0, readBytes);
                                //TODO: falta escritura en fichero local
                            }
                        }
                        if(!fileExists){
                            //TODO: file not found ERROR.
                        }
                    }

                }
                else{
                    //TODO: Path doesn't exist ERROR.
                }
            } catch (IOException e) {
                //TODO: Handle exception. Change directory failure.
            }
        }
        else{
            //TODO: Path not entered ERROR.
        }
    }

    private void disconnect() {
        if(client.isConnected()){
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e){
                //TODO: Handle exception. Disconnection failure.
            }

        }
    }

    private void errorSender(){

    }

}
