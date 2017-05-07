package com.example.albert.p7_restaurant_albert;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jandro on 01/02/2017.
 */

public class AdaptadorPlatosMetre extends ArrayAdapter<Plato> {

    Activity contexto;
    private List<Plato> listaPlatos;
    public static List<String> listaPlatosMarcados;
    int maxPlatos = 0;

    AdaptadorPlatosMetre(Activity contexto, List<Plato> listaPlatos) {
        super(contexto, R.layout.activity_maitre, listaPlatos);
        this.listaPlatos = listaPlatos;
        this.listaPlatosMarcados = new ArrayList<>();
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return listaPlatos.size();
    }


    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Plato getItem(int position) {
        return listaPlatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View item = convertView;
        final VistaTagMetre vistaTag;
        if(item == null){
            LayoutInflater inflater = contexto.getLayoutInflater();
            item = inflater.inflate(R.layout.lista_platos_maitre, null);
            vistaTag = new VistaTagMetre();

//            vistaTag.id=0;
            vistaTag.nombre = (TextView)item.findViewById(R.id.lblNombrePlatoMetre);
//            vistaTag.tipoPlato = (TextView)item.findViewById(R.id.lblTipoPlato);
            vistaTag.calorias = (TextView)item.findViewById(R.id.lblCaloriasMetre);
            vistaTag.checkbox = (CheckBox) item.findViewById(R.id.checkBoxMetre);
//            vistaTag.calorias.setTextColor(Color.parseColor("#00FF00"));
//            vistaTag.check = (CheckBox) item.findViewById(R.id.cbCaja);

//            //listener de los checbox
//            vistaTag.checkbox.setOnCheckedChangeListener(checkListener);

            // Guardamos el objeto en el elemento
            item.setTag(vistaTag);
        }else{
            // Si estamos reutilizando una Vista, recuperamos el objeto interno
            vistaTag = (VistaTagMetre)item.getTag();
        }

        // Cargamos los datos de las opciones de la matriz de datos

        vistaTag.nombre.setText(listaPlatos.get(position).getNom().toString());
//        vistaTag.tipoPlato.setText(listaPlatos.get(position).getTipoPlato());
        vistaTag.calorias.setText(listaPlatos.get(position).getKcal());

        Plato obj = getItem(position);
        vistaTag.checkbox.setTag(obj);
        vistaTag.checkbox.setChecked(obj.isSeleccionado());

        vistaTag.checkbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b && maxPlatos < 3){
                    maxPlatos++;
                    listaPlatosMarcados.add(listaPlatos.get(position).getId());
                }else if(!b && maxPlatos <= 3){
                    maxPlatos--;
                    listaPlatosMarcados.remove(listaPlatos.get(position).getId());
                }else{
                    vistaTag.checkbox.setChecked(false);
                    Toast.makeText(contexto,"No se pueden seleccionar mas de 3 platos",Toast.LENGTH_LONG).show();
                }
            }
        });
        // Devolvemos la Vista (nueva o reutilizada) que dibuja la opción
        return(item);
    }


}
class VistaTagMetre {
    //    int id;
    TextView nombre;
    //    TextView tipoPlato;
    TextView calorias;
    CheckBox checkbox;
}

