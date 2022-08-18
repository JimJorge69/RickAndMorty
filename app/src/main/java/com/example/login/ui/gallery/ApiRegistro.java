package com.example.login.ui.gallery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRegistro {
    @FormUrlEncoded
    @POST("registro")
    Call<Registro> REGISTER_CALL(
            @Field("name") String name,
            @Field("apPat") String apPat,
            @Field("apMat") String apMat,
            @Field("email") String email,
            @Field("password") String password,
            @Field("validate") String validate,
            @Field("fecha_nacimiento") String fecha_nacimiento
    );
}
