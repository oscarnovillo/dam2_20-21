package com.example.listacompra.framework.di

import com.example.listacompra.data.datasource.IProductoRemoteDatasource
import com.example.listacompra.framework.data.datasource.FirebaseProductoDataSource
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideDatabaseReference(): DatabaseReference {
        return Firebase.database.reference
    }



    @Singleton
    @Provides
    fun provideIProductoRemoteDatasource(): IProductoRemoteDatasource {
        return FirebaseProductoDataSource()
    }
}