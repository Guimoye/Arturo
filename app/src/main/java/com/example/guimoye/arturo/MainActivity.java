package com.example.guimoye.arturo;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guimoye.arturo.Despacho.DespachoFragment;
import com.example.guimoye.arturo.Inventario.InventarioFragment;
import com.example.guimoye.arturo.Loggin.LoginActivity;
import com.example.guimoye.arturo.Materiales.MaterialesFragment;
import com.example.guimoye.arturo.Mensajes.mensajesFragment;
import com.example.guimoye.arturo.Perdidas.PerdidasFragment;
import com.example.guimoye.arturo.Productos.ProductosFragment;
import com.example.guimoye.arturo.Reportes.ReptsFragment;
import com.example.guimoye.arturo.Ubicacion.GmapFragment;
import com.example.guimoye.arturo.Usuarios.UsursFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String tipo,usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //para poner iconos originales
        //navigationView.setItemIconTintList(null);

         //actualizar datos de cabezera
        Bundle bolsa = getIntent().getExtras();
        View hView =  navigationView.getHeaderView(0);
        TextView nombreNav  = (TextView)hView.findViewById(R.id.nombreNav);
        TextView correoNav  = (TextView)hView.findViewById(R.id.correoNav);
        ImageView imagenNav = (ImageView)hView.findViewById(R.id.imagenNav);
        nombreNav.setText("Bienvenido Usuario:");
        usuario =   bolsa.getString("usuario");
        tipo    =   bolsa.getString("tipo");
        correoNav.setText(usuario);
        //imagenNav.setImageResource(R.drawable.ic_menu_send);


        // para abrir fragment automaticamente
        navigationView.setCheckedItem(R.id.nav_mensaje);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new mensajesFragment()).commit();

        startService(new Intent(this.getApplicationContext(), MyService.class));

        if(tipo.equals("1")){
            Intent mServiceIntent = new Intent(this.getApplicationContext(), MyServiceLocalizame.class);
            mServiceIntent.putExtra("usr", usuario);
            mServiceIntent.putExtra("tip", tipo);
            startService(mServiceIntent);
        }


    }

    public void salirSistema(){

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Mensaje de Alerta");
        ab.setMessage("¿Deseas Cerrar Sesion?");
        ab.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopService(new Intent(getApplicationContext(), MyServiceLocalizame.class));
                stopService(new Intent(getApplicationContext(), MyService.class));
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = ab.create();
        ad.show();
    }

    @Override
    public void onBackPressed() {
        salirSistema();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getFragmentManager();

        if (id == R.id.nav_inventario) {
           fm.beginTransaction().replace(R.id.content_frame, new InventarioFragment()).commit();
        } else if (id == R.id.nav_despacho) {
            fm.beginTransaction().replace(R.id.content_frame, new DespachoFragment()).commit();
        }  else if (id == R.id.nav_materiales) {
            fm.beginTransaction().replace(R.id.content_frame, new MaterialesFragment()).commit();
        }else if (id == R.id.nav_producto) {
            fm.beginTransaction().replace(R.id.content_frame, new ProductosFragment()).commit();
        }else if (id == R.id.nav_perdidas) {
             fm.beginTransaction().replace(R.id.content_frame, new PerdidasFragment()).commit();
        }else if (id == R.id.nav_ubicacion) {
            fm.beginTransaction().replace(R.id.content_frame, new GmapFragment()).commit();
        } else if (id == R.id.nav_reportes) {
            fm.beginTransaction().replace(R.id.content_frame, new ReptsFragment()).commit();
        } else if (id == R.id.nav_usuarios) {
            fm.beginTransaction().replace(R.id.content_frame, new UsursFragment()).commit();
        }else if (id == R.id.nav_mensaje) {

            fm.beginTransaction().replace(R.id.content_frame, new mensajesFragment()).commit();
        }else if (id == R.id.nav_cerrar) {
            salirSistema();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() { super.onResume(); }

}
