package com.applicnation.eggnation.di

import android.app.Application
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Authentication
import com.applicnation.eggnation.feature_eggnation.data.repository.AuthenticationRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.AuthUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.UserPasswordReset
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.UserSignIn
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.UserSignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthentication(): Authentication {
        return Authentication()
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(auth: Authentication): AuthenticationRepository {
        return AuthenticationRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(authenticator: AuthenticationRepository): AuthUseCases {
        return AuthUseCases(
            userSignUp = UserSignUp(authenticator),
            userSignIn = UserSignIn(authenticator),
            userPasswordReset = UserPasswordReset(authenticator)
        )
    }

}