package zesters.fenal;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class listaEditorial extends ActionBarActivity {

    RandomAccessFile acceso;
    int l = 150, canEditoriales, repeticion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_editorial);
        ListView listaEditorial = (ListView) findViewById(android.R.id.list);
        try {

            File tarjetaSd = Environment.getExternalStorageDirectory();
            File archivo = new File(tarjetaSd.getAbsolutePath(), "libro.dat");

            RandomAccessFile acceso = new RandomAccessFile(archivo, "rw");

            if(acceso.length()==0){
                Toast.makeText(this, "No hay registro de libros", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                while (l < acceso.length()) {
                    acceso.seek(l);
                    canEditoriales++;
                    l += 254;
                }


                String editoriales[] = new String[canEditoriales];

                l = 150;
                int j = 0;
                while (l < acceso.length()) {
                    acceso.seek(l);
                    editoriales[j] = acceso.readUTF();
                    l += 254;
                    j++;
                }

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        editoriales);
                listaEditorial.setAdapter(adaptador);
            }
                acceso.close();
            } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }



}
