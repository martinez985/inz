package com.example.inz2;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inz2.Model.DataPackage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<DataPackage> dataPackageList;

    public AddressAdapter(List<DataPackage> dataPackageList) {
        this.dataPackageList = dataPackageList;
    }

    public void updateData(List<DataPackage> dataPackageList) {
        this.dataPackageList = dataPackageList;
        notifyDataSetChanged();
    }


    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        private TextView addressTextView;
        private AddressAdapter adapter;
        private Button loadButton, unloadButton, undoButton;
        private TextView IDTextView;

        public AddressViewHolder(View itemView, AddressAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.addressTextView = itemView.findViewById(R.id.addressTextView);
            this.IDTextView = itemView.findViewById(R.id.IDtextView);
            this.loadButton = itemView.findViewById(R.id.loadedButton);
            this.unloadButton = itemView.findViewById(R.id.deliveredButton);
            this.undoButton = itemView.findViewById(R.id.undoButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        DataPackage selectedPackage = adapter.dataPackageList.get(position);
                        Intent intent = new Intent(itemView.getContext(), PackageDetailsActivity.class);
                        intent.putExtra("SelectedPackage", selectedPackage);
                        itemView.getContext().startActivity(intent);
                    }
                }
            });
            loadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            DataPackage clickedPackage = adapter.dataPackageList.get(position);
                            Log.e("ClickListener", "Button clicked for package ID: " + clickedPackage.getCommission_id());
                            adapter.sendPostDataToServer(itemView.getContext(), clickedPackage.getCommission_id());
                        }
                    } else {
                        Log.e("ClickListener", "Adapter is null");
                    }
                }
            });
            unloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            DataPackage clickedPackage = adapter.dataPackageList.get(position);
                            Log.e("ClickListener", "Unload button clicked for package ID: " + clickedPackage.getCommission_id());
                            adapter.sendUnloadDataToServer(itemView.getContext(), clickedPackage.getCommission_id());
                        }
                    } else {
                        Log.e("ClickListener", "Adapter is null");
                    }
                }
            });

            undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            DataPackage clickedPackage = adapter.dataPackageList.get(position);
                            Log.e("ClickListener", "Undo button clicked for package ID: " + clickedPackage.getCommission_id());
                            adapter.sendUndoDataToServer(itemView.getContext(), clickedPackage.getCommission_id());

                        }
                    } else {
                        Log.e("ClickListener", "Adapter is null");
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        AddressViewHolder holder = new AddressViewHolder(view, this);
        holder.IDTextView = view.findViewById(R.id.IDtextView);
        holder.addressTextView = view.findViewById(R.id.addressTextView);
        holder.loadButton = view.findViewById(R.id.loadedButton);
        holder.unloadButton = view.findViewById(R.id.deliveredButton);
        holder.undoButton = view.findViewById(R.id.undoButton);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        DataPackage currentPackage = dataPackageList.get(position);
        holder.IDTextView.setText(String.valueOf(currentPackage.getCommission_id()));
        if (currentPackage.getIs_loaded() != null && !currentPackage.getIs_loaded()) {
            holder.loadButton.setVisibility(View.VISIBLE);
            holder.unloadButton.setVisibility(View.INVISIBLE);
            holder.undoButton.setVisibility(View.GONE);
            holder.addressTextView.setText(currentPackage.getDelivery_pickup().getAddress());
        } else {
            holder.loadButton.setVisibility(View.GONE);
            holder.unloadButton.setVisibility(View.VISIBLE);
            holder.undoButton.setVisibility(View.VISIBLE);
            holder.addressTextView.setText(currentPackage.getDelivery_endpoint().getAddress());
        }
    }


    @Override
    public int getItemCount() {
        return dataPackageList.size();
    }

    private String readTokenFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("token_key", "");
    }
    private void sendPostDataToServer(Context context, int packageId) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);
        String JWTToken = readTokenFromSharedPreferences(context);

        Call<Void> call = interfaceAPI.commissionLoaded("Bearer " + JWTToken, packageId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("AddressesActivity", "Dane zostały pomyślnie wysłane na serwer (load)");
                    ((AddressesActivity) context).refreshData();
                } else {
                    Log.e("AddressesActivity", "Błąd w odpowiedzi z serwera");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AddressesActivity", "Błąd wysyłania danych na serwer", t);
            }
        });
    }
    private void sendUnloadDataToServer(Context context, int packageId) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);
        String JWTToken = readTokenFromSharedPreferences(context);

        Call<Void> call = interfaceAPI.commissionUnloaded("Bearer " + JWTToken, packageId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("AddressesActivity", "Dane zostały pomyślnie wysłane na serwer (Unload)");
                    ((AddressesActivity) context).refreshData();
                } else {
                    Log.e("AddressesActivity", "Błąd w odpowiedzi z serwera (Unload)");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AddressesActivity", "Błąd wysyłania danych na serwer (Unload)", t);
            }
        });
    }

    private void sendUndoDataToServer(Context context, int packageId) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);
        String JWTToken = readTokenFromSharedPreferences(context);

        Call<Void> call = interfaceAPI.commissionUndo("Bearer " + JWTToken, packageId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("AddressesActivity", "Dane zostały pomyślnie wysłane na serwer (Undo)");
                    ((AddressesActivity) context).refreshData();
                } else {
                    Log.e("AddressesActivity", "Błąd w odpowiedzi z serwera (Undo)");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AddressesActivity", "Błąd wysyłania danych na serwer (Undo)", t);
            }
        });
    }


}

