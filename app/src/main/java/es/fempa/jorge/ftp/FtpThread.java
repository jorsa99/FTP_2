package es.fempa.jorge.ftp;

import android.widget.EditText;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;

public class FtpThread extends Thread {

    public enum Mode {Upload, Download}

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

    public FtpThread(Mode mode, EditText etPath, EditText etFile, EditText etContent){
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
            client.setFileType(FTP.ASCII_FILE_TYPE);
            if(!FTPReply.isPositiveCompletion(client.getReply()))
                disconnect();
            else{

            }
        } catch (SocketException e){
            //TODO: Handle exception.
        } catch (IOException e){
            //TODO: Handle exception.
        }

    }

    private void upload(){

    }

    private void download() {
        //Path is entered.
        if(path.length()>0){
            try {
                if(client.changeWorkingDirectory(path)){
                    for (FTPFile currentFile : client.listFiles()){
                        //File name not entered: listing file names.
                        if(file.length()==0)
                            etContent.append(currentFile.getName()+"\n");
                        //File name entered: display file content.
                        else{
                            if (currentFile.getName().equals(file)){
                                File downloadedFile = new File("download.txt");
                                OutputStream os = new BufferedOutputStream(new FileOutputStream(downloadedFile));
                                InputStream is = client.retrieveFileStream(file);
                                byte[] byteArray = new byte[4096];
                                int bytesRead;
                                while((bytesRead = is.read(byteArray)) != -1)
                                    os.write(byteArray, 0, bytesRead);
                                os.close();
                                is.close();
                                break;
                            }
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
