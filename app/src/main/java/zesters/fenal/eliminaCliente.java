package zesters.fenal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
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


public class eliminaCliente extends ActionBarActivity implements View.OnClickListener {

    protected int noClientePos;
    protected int nombreClientePos;
    protected int procedenciaPos;
    protected int correoPos;
    protected int celularPos;
    protected int noCliente;
    protected int posInicial;
    protected int celular;
    protected int busqueda;
    protected AlertDialog.Builder borrarClienteAlert, clienteBorradoAlert, clienteBorradoExitosamenteAlert;
    protected RandomAccessFile acceso;
    protected EditText busquedaText;
    protected TextView muestraResultadoView;
    protected String nombreCliente;
    protected String procedencia;
    protected String correo;
    protected boolean encontrado;
    protected Button borrarBoton;
    protected Button buscarBoton;
    protected File tarjetaSd;
    protected File archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elimina_cliente);

        busquedaText = (EditText) findViewById(R.id.edit_busca_cliente);
        muestraResultadoView = (TextView) findViewById(R.id.datos_cliente);

        buscarBoton = (Button) findViewById(R.id.boton_buscar);
        borrarBoton = (Button)findViewById(R.id.boton_borrar_registro);

        buscarBoton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        try {
        tarjetaSd = Environment.getExternalStorageDirectory();
        archivo = new File(tarjetaSd.getAbsolutePath(), "cliente.dat");
        borrarClienteAlert = new AlertDialog.Builder(this);
        clienteBorradoExitosamenteAlert = new AlertDialog.Builder(this);


            acceso = new RandomAccessFile(archivo, "rw");

            acceso.seek(0);
            busqueda = Integer.parseInt(busquedaText.getText().toString());

            for (int i = 0; i < acceso.length(); i++) {

                noCliente = acceso.readInt();
                noClientePos = (int) acceso.getFilePointer();
                posInicial = (int) acceso.getFilePointer();
                nombreCliente = acceso.readUTF();
                nombreClientePos = (int)acceso.getFilePointer();
                acceso.seek(posInicial + 50);
                posInicial = (int) acceso.getFilePointer();
                procedencia = acceso.readUTF();
                procedenciaPos = (int)acceso.getFilePointer();
                acceso.seek(posInicial + 50);
                posInicial = (int) acceso.getFilePointer();
                correo = acceso.readUTF();
                correoPos = (int)acceso.getFilePointer();
                acceso.seek(posInicial + 50);
                celular = acceso.readInt();
                celularPos = (int)acceso.getFilePointer();

                i = (int) acceso.getFilePointer();


                if (noCliente == busqueda) {
                    encontrado = true;
                    muestraResultadoView.setText(getResources().getString(R.string.data_found)+"\n\n" +
                            getResources().getString(R.string.text_numero_cliente) +": "+ noCliente + "\n" +
                            getResources().getString(R.string.text_nombre_cliente) +": "+ nombreCliente + "\n" +
                            getResources().getString(R.string.text_procedencia) +": "+ procedencia + "\n" +
                            getResources().getString(R.string.text_correo) +": "+ correo + "\n" +
                            getResources().getString(R.string.text_numero_telefonico) +": "+ celular);
                            acceso.close();
                            borrarBoton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    borrarClienteAlert.setTitle(R.string.titulo_borrar_registro_alert);
                                    borrarClienteAlert.setMessage(R.string.contenido_borrar_registro_alert);
                                    borrarClienteAlert.setPositiveButton(R.string.aceptar_alert, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                tarjetaSd = Environment.getExternalStorageDirectory();
                                                archivo = new File(tarjetaSd.getAbsolutePath(), "cliente.dat");
                                                acceso = new RandomAccessFile(archivo, "rw");

                                                acceso.seek(noClientePos - 4);

                                                for (int j = 0; j < 4; j++) {
                                                    acceso.writeByte(0);
                                                }
                                                acceso.seek(noClientePos);
                                                for (int j = 0; j < nombreClientePos - noClientePos; j++) {
                                                    acceso.writeByte(0);
                                                }
                                                acceso.seek(nombreClientePos);
                                                for (int j = 0; j < procedenciaPos - nombreClientePos; j++) {
                                                    acceso.writeByte(0);
                                                }
                                                acceso.seek(procedenciaPos);
                                                for (int j = 0; j < correoPos - procedenciaPos; j++) {
                                                    acceso.writeByte(0);
                                                }
                                                acceso.seek(correoPos);
                                                for (int j = 0; j < celularPos - correoPos; j++) {
                                                    acceso.writeByte(0);

                                                }
                                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.customer_deleted), Toast.LENGTH_LONG).show();
                                                acceso.close();
                                                finish();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    borrarClienteAlert.setNegativeButton(R.string.declinar_alert, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    borrarClienteAlert.create();
                                    borrarClienteAlert.show();
                                    }
                                }

                                );
                            }
                }
            if(!encontrado){
                muestraResultadoView.setText("No se encontro el cliente");
            }
            encontrado = false;
            acceso.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            Toast.makeText(this, getResources().getString(R.string.field_delete_customer_empty), Toast.LENGTH_SHORT).show();

        }

    }
}
