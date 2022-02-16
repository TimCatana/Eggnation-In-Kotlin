package com.applicnation.eggnation.di

import android.content.Context
import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Authentication
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Firestore
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.RealtimeDatabase
import com.applicnation.eggnation.feature_eggnation.data.repository.AuthenticationRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.data.repository.DatabaseRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.data.repository.PreferencesRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
     * Authentication Injections
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
    fun provideAuthUseCases(authenticationRepo: AuthenticationRepository): AllAuthUseCases {
        return AllAuthUseCases(
            signUpUserUC = SignUpUserUC(authenticationRepo),
            signInUserUC = SignInUserUC(authenticationRepo),
            signOutUserUC = SignOutUserUC(authenticationRepo),
            deleteUserAccountUC = DeleteUserAccountUC(authenticationRepo),

            sendUserVerificationEmailUC = SendUserVerificationEmailUC(authenticationRepo),
            sendUserPasswordResetEmailUC = SendUserPasswordResetEmailUC(authenticationRepo),

            getUserIdUC = GetUserIdUC(authenticationRepo),
            getUserEmailUC = GetUserEmailUC(authenticationRepo),
            getUserUsernameUC = GetUserUsernameUC(authenticationRepo),
            getUserProfilePictureUC = GetUserProfilePictureUC(authenticationRepo),
            getUserEmailVerificationStatusUC = GetUserEmailVerificationStatusUC(authenticationRepo),

            updateUserEmailAddressUC = UpdateUserEmailAddressUC(authenticationRepo),
            updateUserPasswordUC = UpdateUserPasswordUC(authenticationRepo),
            updateUserUsernameUC = UpdateUserUsernameUC(authenticationRepo),
            updateUserProfilePictureUC = UpdateUserProfilePictureUC(authenticationRepo)
        )
    }

    /**
     * Database Injections
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
    fun provideDatabaseRepository(
        firestore: Firestore,
        database: RealtimeDatabase
    ): DatabaseRepository {
        return DatabaseRepositoryImpl(firestore, database)
    }

    @Provides
    @Singleton
    fun provideDatabaseUseCases(databaseRepo: DatabaseRepository): AllDatabaseUseCases {
        return AllDatabaseUseCases(
            /**
             * Firestore
             */
            userRegisterUC = UserRegisterUC(databaseRepo),
            userUpdateEmailUC = UserUpdateEmailUC(databaseRepo),
            userUpdateUsernameUC = UserUpdateUsernameUC(databaseRepo),

            wonPrizeAddToUserAccountUC = WonPrizeAddToUserAccountUC(databaseRepo),
            wonPrizeGetAllUC = WonPrizeGetAllUC(databaseRepo),
            wonPrizeGetByIdUC = WonPrizeGetByIdUC(databaseRepo),
            wonPrizeUpdatePrizeClaimedUC = WonPrizeUpdatePrizeClaimedUC(databaseRepo),

            /**
             * Realtime Database
             */
            incrementGlobalCounterUC = IncrementGlobalCounterUC(databaseRepo),

            availablePrizeGetAllUC = AvailablePrizeGetAllUC(databaseRepo),
            availablePrizeGetByRNGUC = AvailablePrizeGetByRNGUC(databaseRepo)
        )
    }


    /**
     * Preferences Injections
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
    fun providePreferencesUseCases(preferencesRepo: PreferencesRepository): AllPreferencesUseCases {
        return AllPreferencesUseCases(
            getSelectedSkinPrefUC = GetSelectedSkinPrefUC(preferencesRepo),
            getTapCountPrefUC = GetTapCountPrefUC(preferencesRepo),
            getReceivesNotificationsPrefUC = GetReceivesNotificationsPrefUC(preferencesRepo),
            getLastResetTimePrefUC = GetLastResetTimePrefUC(preferencesRepo),

            updateSelectedSkinPrefUC = UpdateSelectedSkinPrefUC(preferencesRepo),
            updateTapCountPrefUC = UpdateTapCountPrefUC(preferencesRepo),
            updateReceivesNotificationsPrefUC = UpdateReceivesNotificationsPrefUC(preferencesRepo),
            updateLastResetTimePrefUC = UpdateLastResetTimePrefUC(preferencesRepo),

            decrementTapCountPrefUC = DecrementTapCountPrefUC(preferencesRepo)
        )
    }

}
