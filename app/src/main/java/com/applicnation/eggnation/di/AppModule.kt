package com.applicnation.eggnation.di

import android.app.Activity
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Authentication
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Firestore
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.RealtimeDatabase
import com.applicnation.eggnation.feature_eggnation.data.repository.AuthenticationRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.data.repository.DatabaseRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.data.repository.PreferencesRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdLoadUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdPlayUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import java.util.prefs.PreferenceChangeEvent
import java.util.prefs.Preferences
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
    fun provideAuthenticationRepository(authenticator: Authentication): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authenticator)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(authenticationRepo: AuthenticationRepository): AuthUseCases {
        return AuthUseCases(
            userSignUp = UserSignUp(authenticationRepo),
            userSignIn = UserSignIn(authenticationRepo),
            userPasswordReset = UserPasswordReset(authenticationRepo),
            userGetUserId = UserGetUserId(authenticationRepo),
        )
    }


    /**
     * Preferences stuff
     */
    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(preferencesManager: PreferencesManager): PreferencesRepository {
        return PreferencesRepositoryImpl(preferencesManager)
    }

    @Provides
    @Singleton
    fun providePreferencesUseCases(preferencesRepo: PreferencesRepository): PreferencesUseCases {
        return PreferencesUseCases(
            preferencesGetTapCount = PreferencesGetTapCount(preferencesRepo),
            preferencesUpdateTapCount = PreferencesUpdateTapCount(preferencesRepo),
            preferencesGetSelectedSkin = PreferencesGetSelectedSkin(preferencesRepo),
            preferencesUpdateSelectedSkin = PreferencesUpdateSelectedSkin(preferencesRepo),
            preferencesGetLastResetTime = PreferencesGetLastResetTime(preferencesRepo),
            preferencesUpdateLastResetTime = PreferencesUpdateLastResetTime(preferencesRepo),
            preferencesGetReceivesNotifications = PreferencesGetReceivesNotifications(preferencesRepo),
            preferencesUpdateReceivesNotifications = PreferencesUpdateReceivesNotifications(preferencesRepo),
            preferencesDecrementTapCount = PreferencesDecrementTapCount(preferencesRepo)
        )
    }

    /**
     * Database Stuff
     */
    @Provides
    @Singleton
    fun provideRealtimeDatabase(): RealtimeDatabase {
        return RealtimeDatabase()
    }

    @Provides
    @Singleton
    fun provideFirestore(): Firestore {
        return Firestore()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(firestore: Firestore, database: RealtimeDatabase): DatabaseRepository {
        return DatabaseRepositoryImpl(firestore, database)
    }

    @Provides
    @Singleton
    fun provideDatabaseUseCases(databaseRepo: DatabaseRepository): DatabaseUseCases {
        return DatabaseUseCases(
            databaseGetAvailablePrizeByRNG = DatabaseGetAvailablePrizeByRNG(databaseRepo),
            databaseGetAvailablePrizes = DatabaseGetAvailablePrizes(databaseRepo),
            databaseGetWonPrizes = DatabaseGetWonPrizes(databaseRepo),
            databaseIncrementGlobalCounter = DatabaseIncrementGlobalCounter(databaseRepo),
            databaseRegisterUser = DatabaseRegisterUser(databaseRepo),
            databaseGetWonPrizeById = DatabaseGetWonPrizeById(databaseRepo)
        )
    }

}