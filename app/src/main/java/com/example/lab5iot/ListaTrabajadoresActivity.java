package com.example.lab5iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaTrabajadoresActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://localhost:8080/api/employees/";

    private EditText etTutorCode;
    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_trabajadores);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        etTutorCode = findViewById(R.id.etTutorCode);
        btnDownload = findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tutorCode = etTutorCode.getText().toString();
                if (!tutorCode.isEmpty()) {
                    downloadWorkerList(tutorCode);
                } else {
                    Toast.makeText(ListaTrabajadoresActivity.this, "Por favor, ingrese el código del tutor", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void downloadWorkerList(String tutorCode) {
        // Crea una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeApi api = retrofit.create(EmployeeApi.class);

        int managerId;
        try {
            managerId = Integer.parseInt(tutorCode);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El código del tutor debe ser un número entero", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<Employee>> call = api.getEmployeesByManagerId(managerId);
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    List<Employee> workerList = response.body();
                    saveWorkersToFile(workerList);
                } else {
                    Toast.makeText(ListaTrabajadoresActivity.this, "Error al descargar la lista de trabajadores", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(ListaTrabajadoresActivity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveWorkersToFile(List<Employee> workerList) {
        File file = new File(getExternalFilesDir(null), "workers.txt");
        try {
            FileWriter writer = new FileWriter(file);
            for (Employee worker : workerList) {
                writer.write(worker.toString() + "\n");
            }
            writer.close();
            Toast.makeText(this, "Lista de trabajadores guardada en " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar la lista de trabajadores", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}