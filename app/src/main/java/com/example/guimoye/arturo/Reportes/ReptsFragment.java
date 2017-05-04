package com.example.guimoye.arturo.Reportes;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guimoye.arturo.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

public class ReptsFragment extends Fragment {

    ListView lista;
    ArrayList<Double> total =    new ArrayList<>();
    ArrayList<String> fecha    =    new ArrayList<>();

    private LinearLayout mainLayout;
    private PieChart mChart;
    // we're going to display pie chart for smartphones martket shares
    private float[] yData   = { 5, 10, 15, 30, 40,90,20 };
    private String[] xData  = { "Sony", "Huawei", "LG", "Apple", "Samsung", "sapo", "mmgb" };
    Random rnd = new Random();


    AlertDialog.Builder builder;
    AlertDialog ad;
    AutoCompleteTextView umFechaDsde, umFechaHast;

    JsonReportes ju;
    View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v           =   inflater.inflate(R.layout.fragment_reportes,container,false);
        mainLayout  =   (LinearLayout) v.findViewById(R.id.fondo);
        mChart      =   (PieChart) v.findViewById(R.id.piegraph);
        lista       =   (ListView) v.findViewById(R.id.listView1);
        ju          =   new JsonReportes(v,inflater,container,getActivity().getBaseContext(),lista, total,fecha);





        /************** ventana de busqueda *****************/
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.consultar_fecha_reportes, null);

        final Spinner listaBusqueda                 = (Spinner) dialoglayout.findViewById(R.id.selectReportes);
        umFechaDsde                                 = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtInventario_fechaDesde);
        umFechaHast                                 = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtInventario_fechaHasta);
        ArrayAdapter<CharSequence> adapterSpinner   = ArrayAdapter.createFromResource(this.getActivity(),R.array.opciones,
                                                      android.R.layout.simple_spinner_item);
        listaBusqueda.setAdapter(adapterSpinner);
        final Button btnMoficiarUsuario             = (Button) dialoglayout.findViewById(R.id.btn_BuscarInventario_Fecha);

        btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("esto es loq selecciono ","seleccione: "+listaBusqueda.getSelectedItemPosition());
                if(listaBusqueda.getSelectedItemPosition()==0){
                    ju.cambiarFechas(umFechaDsde.getText().toString(),umFechaHast.getText().toString());
                    ju.cambiarGrafica(mChart);
                    ju.Hilo("http://192.168.11.115:8282/arturo/reportes.php?modelo=2","1");
                }else{
                    if(listaBusqueda.getSelectedItemPosition()==1){
                        ju.cambiarFechas(umFechaDsde.getText().toString(),umFechaHast.getText().toString());
                        ju.cambiarGrafica(mChart);
                        ju.Hilo("http://192.168.11.115:8282/arturo/reportes.php?modelo=1","2");
                    }
                }

                ad.dismiss();
            }
        });

        builder.setView(dialoglayout);
        ad = builder.create();
        ad.show();




        return v;
    }


}
