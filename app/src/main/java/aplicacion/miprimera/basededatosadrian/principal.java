package aplicacion.miprimera.basededatosadrian;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class principal extends ActionBarActivity {

    EditText Et_serie, Et_modelo, Et_direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Et_serie = (EditText) findViewById(R.id.et_serie);
        Et_modelo = (EditText) findViewById(R.id.et_modelo);
        Et_direccion = (EditText)findViewById(R.id.et_direccion);


    }


    public void guarda (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agencia", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String id_serie = Et_serie.getText().toString();
        String modelo = Et_modelo.getText().toString();
        String direccion = Et_direccion.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("id_serie", id_serie);
        registro.put("modelo", modelo);
        registro.put("direccion", direccion);

        bd.insert("agencia", null, registro);
        bd.close();

        Et_serie.setText("");
        Et_modelo.setText("");
        Et_direccion.setText("");
        Toast.makeText(this,"Se agrego un nuevo auto",Toast.LENGTH_SHORT).show();
    }


    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agencia", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id_serie = Et_serie.getText().toString();
        Cursor fila = bd.rawQuery("select modelo, direccion from agencia where id_serie=" + id_serie, null);
        if (fila.moveToFirst()) {
            Et_modelo.setText(fila.getString(0));
            Et_direccion.setText(fila.getString(1));

        } else {
            Toast.makeText(this,"No existe el auto que indico",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }





    public void eliminar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agencia", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id_serie = Et_serie.getText().toString();
        int cant = bd.delete("agencia","id_serie=" + id_serie, null);
        bd.close();

        Et_serie.setText("");
        Et_modelo.setText("");
        Et_direccion.setText("");

        if (cant == 1) {
            Toast.makeText(this, "Se borró el auto que indico",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el auto que indico",Toast.LENGTH_SHORT).show();
        }
    }

    public void modificacion (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agencia", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String id_serie = Et_serie.getText().toString();
        String modelo = Et_modelo.getText().toString();
        String direccion = Et_direccion.getText().toString();


        ContentValues registro = new ContentValues();

        registro.put("id_serie", id_serie);
        registro.put("modelo", modelo);
        registro.put("direccion", direccion);

        int cant = bd.update("agencia", registro, "id_serie=" + id_serie, null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se modificaron los datos del auto",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el auto que indico",Toast.LENGTH_SHORT).show();
        }

    }

    public void limpia (View v){
        Et_serie.setText("");
        Et_modelo.setText("");
        Et_direccion.setText("");



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
