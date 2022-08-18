package com.example.login.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.login.databinding.FragmentGalleryBinding;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final Button BtnRegistro = binding.btnRegistrar;
        BtnRegistro.setOnClickListener(new View.OnClickListener() {
            final EditText EtName = binding.nameEt;
            final EditText EtApPat = binding.apPatEt;
            final EditText EtApMat = binding.apMatEt;
            final EditText EtEmail = binding.emailEt;
            final EditText EtPass = binding.passEt;
            final EditText EtValidate = binding.validateEt;
            final EditText Fecha = binding.editTextDate4;
            final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            @Override
            public void onClick(View view) {
                String name = EtName.getText().toString().trim();
                String apPat = EtApPat.getText().toString().trim();
                String apMat = EtApMat.getText().toString().trim();
                String email = EtEmail.getText().toString().trim();
                String password = EtPass.getText().toString().trim();
                String validate = EtValidate.getText().toString().trim();
                String fecha_nacimiento = Fecha.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty() || validate.isEmpty()){
                    Toast.makeText(getContext(), "Faltan datos para poder registrarte", Toast.LENGTH_SHORT).show();
                }

                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://eyiogthd.lucusvirtual.es/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                ApiRegistro register = retrofit.create(ApiRegistro.class);
                Call<Registro> call = register.REGISTER_CALL(name, apPat, apMat, email, password, validate, fecha_nacimiento);
                call.enqueue(new Callback<Registro>() {
                    @Override
                    public void onResponse(Call<Registro> call, Response<Registro> response) {
                        if(response.isSuccessful() && response.body() != null){
                            EtName.getText().clear();
                            EtApPat.getText().clear();
                            EtApMat.getText().clear();
                            EtEmail.getText().clear();
                            EtPass.getText().clear();
                            EtValidate.getText().clear();
                            Fecha.getText().clear();
                            Toast.makeText(getContext(), "Registrado Correctamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "verifica tus datos", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Registro> call, Throwable t) {
                        Toast.makeText(getContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}