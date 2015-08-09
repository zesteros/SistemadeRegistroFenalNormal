package zesters.fenal;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class anadeLibro extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anade_libro);
        try {
            isbnText = (EditText) findViewById(R.id.edit_isbn);
            nombreLibroText = (EditText) findViewById(R.id.edit_nombre_libro);
            autorText = (EditText) findViewById(R.id.edit_autor);
            editorialText = (EditText) findViewById(R.id.edit_editorial);
            costoText = (EditText) findViewById(R.id.edit_costo);

            anadeLibroBoton = (Button) findViewById(R.id.anade_libro);

            anadeLibroBoton.setOnClickListener(this);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.field_empty), Toast.LENGTH_SHORT).show();
        }
    }
    public void generoSeleccionado(View view) {
        try {
            checked = ((RadioButton) view).isChecked();
            switch (view.getId()) {
                case R.id.radio_button_suspenso:
                    if (checked)
                        genero = getResources().getString(R.string.genero_suspenso);
                    break;
                case R.id.radio_button_terror:
                    if (checked)
                        genero = getResources().getString(R.string.genero_terror);
                    break;
                case R.id.radio_button_educativo:
                    genero = getResources().getString(R.string.genero_educativo);
                    break;
                case R.id.radio_button_programacion:
                    genero = getResources().getString(R.string.genero_programacion);
                    break;
                default:
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.field_empty), Toast.LENGTH_SHORT).show();
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.field_empty), Toast.LENGTH_SHORT).show();
            }
    }
    @Override
    public void onClick(View v) {
        try {
        ISBN = isbnText.getText().toString();
        nombreLibro = nombreLibroText.getText().toString();
        autor = autorText.getText().toString();
        editorial = editorialText.getText().toString();
        costo = Float.parseFloat(costoText.getText().toString());

            tarjetaSd = Environment.getExternalStorageDirectory();
            archivo = new File(tarjetaSd.getAbsolutePath(), "libro.dat");

            acceso = new RandomAccessFile(archivo, "rw");

            posInicioRegistro = (int) acceso.length();

            acceso.seek(posInicioRegistro);

            for (int i = 0; i < 50; i++) {
                acceso.writeByte(0);
            }
            posFinal = (int) acceso.getFilePointer();
            acceso.seek(posInicioRegistro);
            acceso.writeUTF(ISBN);
            acceso.seek(posFinal);

            posInicial = (int) acceso.getFilePointer();
            for (int i = 0; i < 50; i++) {
                acceso.writeByte(0);
            }
            posFinal = (int) acceso.getFilePointer();
            acceso.seek(posInicial);
            acceso.writeUTF(nombreLibro);
            acceso.seek(posFinal);

            posInicial = (int) acceso.getFilePointer();
            for (int i = 0; i < 50; i++) {
                acceso.writeByte(0);
            }
            posFinal = (int) acceso.getFilePointer();
            acceso.seek(posInicial);
            acceso.writeUTF(autor);
            acceso.seek(posFinal);

            posInicial = (int) acceso.getFilePointer();
            for (int i = 0; i < 50; i++) {
                acceso.writeByte(0);
            }
            posFinal = (int) acceso.getFilePointer();
            acceso.seek(posInicial);
            acceso.writeUTF(editorial);
            acceso.seek(posFinal);

            posInicial = (int) acceso.getFilePointer();
            for (int i = 0; i < 50; i++) {
                acceso.writeByte(0);
            }
            posFinal = (int) acceso.getFilePointer();
            acceso.seek(posInicial);
            acceso.writeUTF(genero);
            acceso.seek(posFinal);

            acceso.writeFloat(costo);

            Toast.makeText(this, getResources().getString(R.string.add_book_success), Toast.LENGTH_SHORT).show();

            acceso.close();
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.field_empty), Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.field_empty), Toast.LENGTH_SHORT).show();

        }catch(NumberFormatException e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.field_empty), Toast.LENGTH_SHORT).show();
        }
    }
    protected EditText isbnText;
    protected EditText nombreLibroText;
    protected EditText autorText;
    protected EditText editorialText;
    protected EditText costoText;
    protected Button anadeLibroBoton;
    protected String ISBN;
    protected String nombreLibro;
    protected String autor;
    protected String editorial;
    protected String genero = "Otros";
    protected float costo;
    protected boolean checked;
    protected int posInicioRegistro;
    protected int posInicial;
    protected int posFinal;
    protected RandomAccessFile acceso;
    protected File tarjetaSd;
    protected File archivo;

}
