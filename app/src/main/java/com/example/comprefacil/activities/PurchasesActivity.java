package com.example.comprefacil.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.comprefacil.R;
import com.example.comprefacil.adapters.AdapterPurchases;
import com.example.comprefacil.models.CompraData;
import com.example.comprefacil.models.HomeViewModel;
import com.example.comprefacil.models.MercadoData;
import com.example.comprefacil.models.PurchasesViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PurchasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);

        RecyclerView rvCompras = findViewById(R.id.rv_compras_purchases);
        rvCompras.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvCompras.setLayoutManager(layoutManager);

        PurchasesViewModel purchasesViewModel = new ViewModelProvider(this).get(PurchasesViewModel.class);

        LiveData<List<CompraData>> compraLv = purchasesViewModel.getItens();
        compraLv.observe(this, new Observer<List<CompraData>>() {
            @Override
            public void onChanged(List<CompraData> compraData) {

                AdapterPurchases adapterPurchases = new AdapterPurchases(PurchasesActivity.this, compraData);
                rvCompras.setAdapter(adapterPurchases);
            }
        });


    }

}