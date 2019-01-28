package es.fempa.jorge.ftp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import es.fempa.jorge.ftp.FtpThread.Mode;

public class MainActivity extends AppCompatActivity {

    //NO UI FIELDS

    private FtpThread thread;

    //UI FIELDS

    private Button
            btnUpload,
            btnDownload;

    private EditText
            etPath,
            etFile,
            etText;

    private String ruta, texto, fichero;

    //MAIN ACTIVITY METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        //Upload button listener
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruta = etPath.getText().toString();
                fichero = etFile.getText().toString();
                texto = etText.getText().toString();

                thread = new FtpThread(Mode.Upload, ruta, fichero, texto);
                thread.start();
            }
        });

        //Download button listener
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruta = etPath.getText().toString();
                fichero = etFile.getText().toString();
                texto = etText.getText().toString();

                thread = new FtpThread(Mode.Download, ruta, fichero, texto);
                thread.start();
            }
        });
    }


    //NO UI METHODS

    private void initializeComponents(){
        btnUpload = findViewById(R.id.btn_subir);
        btnDownload = findViewById(R.id.btn_descargar);
        etPath = findViewById(R.id.et_ruta);
        etFile = findViewById(R.id.et_fichero);
        etText = findViewById(R.id.et_texto);
    }
}
