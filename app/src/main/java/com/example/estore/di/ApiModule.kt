package com.example.estore.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.estore.data.local.Database
import com.example.estore.data.service.EStoreApiService
import com.example.estore.data.service.ProductService
import com.example.estore.data.service.UserService
import com.example.estore.repositories.CartRepository
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
    fun provideApiService(retrofit: Retrofit): EStoreApiService {
        return retrofit.create(EStoreApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(EStoreApiService: EStoreApiService): UserService {
        return UserService(EStoreApiService)
    }

    @Provides
    @Singleton
    fun provideProductService(EStoreApiService: EStoreApiService): ProductService {
        return ProductService(EStoreApiService)
    }

    //Repos
    @Provides
    @Singleton
    fun provideUserRepository(userService: UserService): UserRepository {
        return UserRepository(userService)
    }

    @Provides
    @Singleton
    fun provideCartRepository(db: Database): CartRepository {
        return CartRepository(db.cartDao)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productService: ProductService, db: Database): ProductRepository {
        return ProductRepository(productService, db.dao)
    }

    //Storage
    @Provides
    @Singleton
    fun provideUserStorage(app: Application): UserStorage {
        return UserStorage(app.applicationContext)
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