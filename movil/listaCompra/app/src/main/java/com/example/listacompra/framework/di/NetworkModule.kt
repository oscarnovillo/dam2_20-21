package com.example.listacompra.framework.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.listacompra.data.datasource.IProductoRemoteDatasource
import com.example.listacompra.framework.data.datasource.FirebaseProductoDataSource
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun proviceSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }


    @Singleton
    @Provides
    fun provideIProductoRemoteDatasource(): IProductoRemoteDatasource {
        return FirebaseProductoDataSource()
    }
}