package zesters.fenal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


public class buscaISBN extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busca_isbn);

        subtituloBuscaIsbn = (TextView) findViewById(R.id.text_titulo_busca_isbn);
        busquedaText = (EditText) findViewById(R.id.edit_busca_isbn);
        muestraResultadoView = (TextView) findViewById(R.id.datos_libro);
        buscarBoton = (Button) findViewById(R.id.boton_buscar);

        buscarBoton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
        tarjetaSd = Environment.getExternalStorageDirectory();
        archivo = new File(tarjetaSd.getAbsolutePath(), "libro.dat");

            acceso = new RandomAccessFile(archivo, "rw");

            acceso.seek(0);

            busqueda = busquedaText.getText().toString();

            for (int i = 0; i < acceso.length(); i++) {
                posInicial = (int) acceso.getFilePointer();
                posRegistro = (int) acceso.getFilePointer();
                ISBN = acceso.readUTF();
                acceso.seek(posInicial);
                acceso.seek(acceso.getFilePointer() + 50);
                posInicial = (int) acceso.getFilePointer();
                nombreLibro = acceso.readUTF();
                acceso.seek(posInicial);
                acceso.seek(acceso.getFilePointer() + 50);
                posInicial = (int) acceso.getFilePointer();
                autor = acceso.readUTF();
                acceso.seek(posInicial);
                acceso.seek(acceso.getFilePointer() + 50);
                posInicial = (int) acceso.getFilePointer();
                editorial = acceso.readUTF();
                acceso.seek(posInicial);
                acceso.seek(acceso.getFilePointer() + 50);
                posInicial = (int) acceso.getFilePointer();
                genero = acceso.readUTF();
                acceso.seek(posInicial);
                acceso.seek(acceso.getFilePointer() + 50);
                costo = acceso.readFloat();

                i = (int) acceso.getFilePointer();

                if (ISBN.equals(busqueda)) {
                    encontrado = true;
                    muestraResultadoView.setText(getResources().getString(R.string.data_found)+"\n\n" +
                            getResources().getString(R.string.text_isbn) +": "+ ISBN + "\n" +
                            getResources().getString(R.string.text_nombre_libro) +": "+ nombreLibro + "\n" +
                            getResources().getString(R.string.text_autor) +": "+ autor + "\n" +
                            getResources().getString(R.string.text_editorial) +": "+ editorial + "\n" +
                            getResources().getString(R.string.text_genero) +": "+ genero + "\n" +
                            getResources().getString(R.string.text_costo) +": "+ costo);
                }
            }
            if(!encontrado){
                muestraResultadoView.setText(getResources().getString(R.string.book_dont_found));
            }

            acceso.close();
            encontrado = false;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.write_book_data), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.write_book_data), Toast.LENGTH_SHORT).show();
        }catch (NumberFormatException e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.write_book_data), Toast.LENGTH_SHORT).show();
        }
    }
    protected EditText busquedaText;
    protected TextView muestraResultadoView;
    protected TextView subtituloBuscaIsbn;
    protected String busqueda, ISBN,nombreLibro,autor,editorial,genero;
    protected int posInicial;
    protected float costo;
    protected boolean encontrado;
    protected int posRegistro;
    protected Button buscarBoton;
    protected File tarjetaSd;
    protected File archivo;
    protected RandomAccessFile acceso;
}
