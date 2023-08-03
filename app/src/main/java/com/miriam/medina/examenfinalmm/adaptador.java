package com.miriam.medina.examenfinalmm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miriam.medina.examenfinalmm.modelo.Usuarios;

import java.util.List;

public class adaptador extends RecyclerView.Adapter<adaptador.ViewHolder> implements View.OnClickListener{

    public List<Usuarios> listaData;
    private View.OnClickListener listener;

public void setOnClickListener(View.OnClickListener listenerr){
    this.listener = listenerr;
}

    @Override
    public void onClick(View view) {
    if (listener!=null){
        listener.onClick(view);
    }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, email, gender, status;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            //id = (TextView) view.findViewById(R.id.tv_id);
            name = (TextView) view.findViewById(R.id.tv_nombre);
            email = (TextView) view.findViewById(R.id.tv_email);
            gender = (TextView) view.findViewById(R.id.tv_genero);
            status = (TextView) view.findViewById(R.id.tv_estado);

        }

    }

    public adaptador(List<Usuarios> modeloP) {
        this.listaData = modeloP;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_lista, parent, false);
        vista.setOnClickListener(this);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.id.setText(listaData.get(position).getId());
        holder.name.setText(listaData.get(position).getName());
        holder.email.setText(listaData.get(position).getEmail());
        holder.gender.setText(listaData.get(position).getGender());
        holder.status.setText(listaData.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return listaData.size();
    }
}
