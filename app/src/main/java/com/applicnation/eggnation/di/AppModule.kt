package com.applicnation.eggnation.di

import android.app.Activity
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Authentication
import com.applicnation.eggnation.feature_eggnation.data.repository.AuthenticationRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdLoadUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdPlayUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // TODO - I need to somehow get the activity context to be able to inject admob with the activity context
//    /**
//     * Admob
//     */
//    @Provides
//    @Singleton
//    fun provideAdmob(@ActivityContext activity: Activity): AdMob {
//        return AdMob(activity)
//    }
//
//    @Provides
//    @Singleton
//    fun provideAdUseCases(adMob: AdMob): AdUseCases {
//        return AdUseCases(
//            adLoadUseCase = AdLoadUseCase(adMob),
//            adPlayUseCase = AdPlayUseCase(adMob)
//        )
//    }

    /**
     * Auth stuff
     */
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