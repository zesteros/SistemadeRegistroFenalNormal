package zesters.fenal;

import android.app.Activity;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class buscaCliente extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busca_cliente);

        subtituloBuscaCliente = (TextView) findViewById(R.id.titulo_busca_cliente);
        buscarBoton = (Button) findViewById(R.id.boton_buscar);

        busquedaText = (EditText) findViewById(R.id.edit_busca_cliente);
        muestraResultadoView = (TextView) findViewById(R.id.datos_cliente);

        buscarBoton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            tarjetaSd = Environment.getExternalStorageDirectory();
            archivo = new File(tarjetaSd.getAbsolutePath(), "cliente.dat");

            acceso = new RandomAccessFile(archivo, "rw");

            acceso.seek(0);
            busqueda = Integer.parseInt(busquedaText.getText().toString());

            for (int i = 0; i < acceso.length(); i++) {

                posRegistro = i;
                noCliente = acceso.readInt();
                posInicial = (int) acceso.getFilePointer();
                nombreCliente = acceso.readUTF();
                acceso.seek(posInicial + 50);
                posInicial = (int) acceso.getFilePointer();
                procedencia = acceso.readUTF();
                acceso.seek(posInicial + 50);
                posInicial = (int) acceso.getFilePointer();
                correo = acceso.readUTF();
                acceso.seek(posInicial + 50);
                celular = acceso.readInt();

                i = (int) acceso.getFilePointer();

                if (noCliente == busqueda) {
                    encontrado = true;

                    muestraResultadoView.setText(getResources().getString(R.string.data_found) + "\n\n" +
                            getResources().getString(R.string.text_numero_cliente) + ": " + noCliente + "\n" +
                            getResources().getString(R.string.text_nombre_cliente) + ": " + nombreCliente + "\n" +
                            getResources().getString(R.string.text_procedencia) + ": " + procedencia + "\n" +
                            getResources().getString(R.string.text_correo) + ": " + correo + "\n" +
                            getResources().getString(R.string.text_numero_telefonico) + ": " + celular);
                }
            }
            if (!encontrado) {
                muestraResultadoView.setText(R.string.customer_dont_found);
            }
            encontrado = false;
            acceso.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Toast.makeText(this, getResources().getString(R.string.write_customer_data), Toast.LENGTH_SHORT).show();
        }
    }
    protected int noCliente;
    protected int posInicial;
    protected int celular;
    protected int busqueda;
    protected int posRegistro;
    protected String nombreCliente;
    protected String procedencia;
    protected String correo;
    protected EditText busquedaText;
    protected TextView muestraResultadoView, subtituloBuscaCliente;
    protected boolean encontrado;
    protected Button buscarBoton;
    protected RandomAccessFile acceso;
    protected File tarjetaSd;
    protected File archivo;
}
