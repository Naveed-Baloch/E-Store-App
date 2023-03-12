package com.example.estore.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.estore.data.local.Database
import com.example.estore.data.network.ApiService
import com.example.estore.data.services.ProductService
import com.example.estore.data.services.UserService
import com.example.estore.repositories.ProductRepository
import com.example.estore.repositories.UserRepository
import com.example.estore.storage.UserStorage
import com.example.estore.utils.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    // Services
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(apiService: ApiService): UserService {
        return UserService(apiService)
    }

    @Provides
    @Singleton
    fun provideProductService(apiService: ApiService): ProductService {
        return ProductService(apiService)
    }

    //Repos
    @Provides
    @Singleton
    fun provideUserRepository(userService: UserService): UserRepository {
        return UserRepository(userService)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productService: ProductService,db: Database): ProductRepository {
        return ProductRepository(productService,db.dao)
    }

    //Storage
    @Provides
    @Singleton
    fun provideUserStorage(context: Context): UserStorage {
        return UserStorage(context)
    }

    //Room Database
    @Provides
    @Singleton
    fun provideProductDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app, Database::class.java, "product_db"
        ).build()
    }
}