package zesters.fenal;

import android.app.AlertDialog;
import android.content.*;
import android.os.*;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.*;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        titulo1 = (TextView) findViewById(R.id.texto1);
        titulo2 = (TextView) findViewById(R.id.texto2);

        anadeRegistro = (Button) findViewById(R.id.boton1);
        buscarCliente = (Button) findViewById(R.id.boton2);
        buscaIsbn = (Button) findViewById(R.id.boton3);
        eliminaCliente = (Button) findViewById(R.id.boton4);
        listaLibro = (Button) findViewById(R.id.boton5);
        libroPorEditorial = (Button) findViewById(R.id.boton6);
        modificaRegistro = (Button) findViewById(R.id.boton7);

        anadeRegistro.setOnClickListener(this);
        buscarCliente.setOnClickListener(this);
        buscaIsbn.setOnClickListener(this);
        eliminaCliente.setOnClickListener(this);
        listaLibro.setOnClickListener(this);
        libroPorEditorial.setOnClickListener(this);
        modificaRegistro.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        botonOprimido = v.getId();

        buscaClienteIntent = new Intent(this,buscaCliente.class);
        buscaISBNIntent = new Intent(this,buscaISBN.class);
        eliminaClienteIntent = new Intent(this, eliminaCliente.class);
        listaLibrosIntent = new Intent(this, listaLibros.class);
        listaEditorialIntent = new Intent(this, listaEditorial.class);
        anadeClienteIntent = new Intent(this,anadeCliente.class);
        anadeLibroIntent = new Intent(this,anadeLibro.class);
        intentModifica = new Intent(this, modificarRegistro.class);

        try {
            switch (botonOprimido) {

                case R.id.boton1:
                    addRegisterDialog = new android.app.AlertDialog.Builder(this);
                    addRegisterDialog.setTitle(getResources().getString(R.string.boton1));
                    addRegisterDialog.setMessage(getResources().getString(R.string.add_register));
                    addRegisterDialog.setPositiveButton(getResources().getString(R.string.title_activity_anade_libro), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(anadeLibroIntent);
                        }
                    });
                    addRegisterDialog.setNeutralButton(getResources().getString(R.string.title_activity_anade_cliente), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(anadeClienteIntent);
                        }
                    });
                    addRegisterDialog.create();
                    addRegisterDialog.show();
                    break;
                case R.id.boton2:
                    startActivity(buscaClienteIntent);
                    break;
                case R.id.boton3:
                    startActivity(buscaISBNIntent);
                    break;
                case R.id.boton4:
                    startActivity(eliminaClienteIntent);
                    break;
                case R.id.boton5:
                    startActivity(listaLibrosIntent);
                    break;
                case R.id.boton6:
                    startActivity(listaEditorialIntent);
                    break;
                case R.id.boton7:
                    dialog = new android.app.AlertDialog.Builder(this);

                    dialog.setTitle(getResources().getString(R.string.boton7));
                    dialog.setMessage(getResources().getString(R.string.text_modificar_title));
                    dialog.setNeutralButton(getResources().getString(R.string.title_activity_modifica_cliente), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intentModifica.putExtra(MODIFICAR_CLIENTE, true);
                            startActivity(intentModifica);
                        }
                    });
                    dialog.setPositiveButton(getResources().getString(R.string.title_activity_modifica_libro), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intentModifica.putExtra(MODIFICAR_LIBRO, true);
                            startActivity(intentModifica);
                        }
                    });
                    dialog.create();
                    dialog.show();
                    break;
            }
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        dialog = new android.app.AlertDialog.Builder(this);

        botonOprimidoMenu = item.getItemId();

        if (botonOprimidoMenu == R.id.menu_info) {
            dialog.setTitle(getResources().getString(R.string.datos_title));
            dialog.setMessage(getResources().getString(R.string.datos_developer1)+"\n"+
                                getResources().getString(R.string.datos_developer2).toUpperCase());
            dialog.setNeutralButton(R.string.aceptar_alert, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.create();
            dialog.show();
            return true;
        }else if(botonOprimidoMenu == R.id.salir){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.despedida), Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected TextView titulo1;
    protected TextView titulo2;
    protected Button anadeRegistro;
    protected Button buscarCliente;
    protected Button buscaIsbn;
    protected Button eliminaCliente;
    protected Button listaLibro;
    protected Button libroPorEditorial;
    protected Button modificaRegistro;
    protected Intent buscaClienteIntent;
    protected Intent buscaISBNIntent;
    protected Intent eliminaClienteIntent;
    protected Intent listaLibrosIntent;
    protected Intent listaEditorialIntent;
    protected int botonOprimido;
    protected int botonOprimidoMenu;
    protected AlertDialog.Builder dialog;
    protected AlertDialog.Builder addRegisterDialog;
    protected Intent anadeClienteIntent;
    protected Intent anadeLibroIntent;
    protected Intent intentModifica;
    protected String MODIFICAR_CLIENTE = "modificaCliente";
    protected String MODIFICAR_LIBRO = "modificaLibro";
    private CharSequence mTitle;

}
