package es.fempa.jorge.ftp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.apache.commons.net.ftp.*;

public class MainActivity extends AppCompatActivity {


    private FTPClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new FTPClient();
    }

    //FUNCIONES NO UI

    public void Connect(){

    }
}
