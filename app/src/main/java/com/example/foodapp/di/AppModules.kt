package com.example.foodapp.di

import android.content.SharedPreferences
import com.example.foodapp.data.datasource.auth.AuthDataSource
import com.example.foodapp.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodapp.data.datasource.cart.CartDataSource
import com.example.foodapp.data.datasource.cart.CartDatabaseDataSource
import com.example.foodapp.data.datasource.category.CategoryApiDataSource
import com.example.foodapp.data.datasource.category.CategoryDataSource
import com.example.foodapp.data.datasource.menu.MenuApiDataSource
import com.example.foodapp.data.datasource.menu.MenuDataSource
import com.example.foodapp.data.datasource.preference.PreferenceDataSource
import com.example.foodapp.data.datasource.preference.PreferenceDataSourceImpl
import com.example.foodapp.data.datasource.user.UserDataSource
import com.example.foodapp.data.datasource.user.UserDataSourceImpl
import com.example.foodapp.data.repository.CartRepository
import com.example.foodapp.data.repository.CartRepositoryImpl
import com.example.foodapp.data.repository.CategoryRepository
import com.example.foodapp.data.repository.CategoryRepositoryImpl
import com.example.foodapp.data.repository.MenuRepository
import com.example.foodapp.data.repository.MenuRepositoryImpl
import com.example.foodapp.data.repository.UserPreferenceRepository
import com.example.foodapp.data.repository.UserPreferenceRepositoryImpl
import com.example.foodapp.data.repository.UserRepository
import com.example.foodapp.data.repository.UserRepositoryImpl
import com.example.foodapp.data.source.firebase.FirebaseService
import com.example.foodapp.data.source.firebase.FirebaseServiceImpl
import com.example.foodapp.data.source.local.database.AppDatabase
import com.example.foodapp.data.source.local.database.dao.CartDao
import com.example.foodapp.data.source.network.services.FoodAppApiService
import com.example.foodapp.presentation.cart.CartViewModel
import com.example.foodapp.presentation.checkout.CheckoutViewModel
import com.example.foodapp.presentation.detailproduct.DetailMenuViewModel
import com.example.foodapp.presentation.home.HomeViewModel
import com.example.foodapp.presentation.login.LoginViewModel
import com.example.foodapp.presentation.main.MainViewModel
import com.example.foodapp.presentation.profile.ProfileViewModel
import com.example.foodapp.presentation.register.RegisterViewModel
import com.example.foodapp.utils.SharedPreferenceUtils
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    val networkModule =
        module {
            single<FoodAppApiService> { FoodAppApiService.invoke() }
        }

    // todo: add firebase module
    private val firebaseModule =
        module {
            single<FirebaseAuth> { FirebaseAuth.getInstance() }
            single<FirebaseService> { FirebaseServiceImpl(get()) }
        }

    val localModule =
        module {
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<CartDao> { get<AppDatabase>().cartDao() }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    PreferenceDataSourceImpl.PREF_NAME,
                )
            }
            single<PreferenceDataSource> { PreferenceDataSourceImpl(get()) }
        }
    private val dataSource =
        module {
            single<CartDataSource> { CartDatabaseDataSource(get()) }
            single<CategoryDataSource> { CategoryApiDataSource(get()) }
            single<MenuDataSource> { MenuApiDataSource(get()) }
            single<UserDataSource> { UserDataSourceImpl() }
            single<AuthDataSource> { FirebaseAuthDataSource(get()) }
        }

    private val repository =
        module {
            single<CartRepository> { CartRepositoryImpl(get()) }
            single<CategoryRepository> { CategoryRepositoryImpl(get()) }
            single<MenuRepository> { MenuRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
            single<UserPreferenceRepository> { UserPreferenceRepositoryImpl(get()) }
        }
    private val viewModelModule =
        module {
            viewModelOf(::HomeViewModel)
            viewModelOf(::CartViewModel)
            viewModelOf(::CheckoutViewModel)
            viewModel { params ->
                // assisted injection
                DetailMenuViewModel(
                    extras = params.get(),
                    cartRepository = get(),
                )
            }
            viewModelOf(::LoginViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::RegisterViewModel)
        }

    val modules =
        listOf<Module>(
            networkModule,
            localModule,
            dataSource,
            repository,
            viewModelModule,
            firebaseModule,
        )
}
