package com.applicnation.eggnation.di

import DoGameLogicUC
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
import com.applicnation.eggnation.feature_eggnation.domain.use_case.AllPreferencesUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.PrizeUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case.AvailablePrizeGetAllUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case.AvailablePrizeGetByRNGUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case.WonPrizeAddToUserAccountUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case.WonPrizeGetAllUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case.WonPrizeGetByIdUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case.WonPrizeUpdatePrizeClaimedUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.UserUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.game_logic_use_case.IncrementGlobalCounterUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.MainGameLogicUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.get_user_data_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserEmailAddressUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserPasswordUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserProfilePictureUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserUsernameUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case.*
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
     * Backend Code Providers
     */
    @Provides
    @Singleton
    fun provideAuthentication(): Authentication {
        return Authentication()
    }

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


    /**
     * Repository Providers
     */
    @Provides
    @Singleton
    fun provideAuthenticationRepository(authenticator: Authentication): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authenticator)
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(
        firestore: Firestore,
        database: RealtimeDatabase
    ): DatabaseRepository {
        return DatabaseRepositoryImpl(firestore, database)
    }


    /**
     * Use Case Providers
     */
    @Provides
    @Singleton
    fun provideUserUseCases(
        authenticationRepo: AuthenticationRepository,
        databaseRepo: DatabaseRepository
    ): UserUseCases {
        return UserUseCases(
            signUpUserUC = SignUpUserUC(authenticationRepo, databaseRepo),
            signInUserUC = SignInUserUC(authenticationRepo),
            signOutUserUC = SignOutUserUC(authenticationRepo),
            deleteUserUC = DeleteUserUC(authenticationRepo),

            sendUserPasswordResetEmailUC = SendUserPasswordResetEmailUC(authenticationRepo),
            sendUserVerificationEmailUC = SendUserVerificationEmailUC(authenticationRepo),

            getUserIdUC = GetUserIdUC(authenticationRepo),
            getUserEmailUC = GetUserEmailUC(authenticationRepo),
            getUserUsernameUC = GetUserUsernameUC(authenticationRepo),
            getUserProfilePictureUC = GetUserProfilePictureUC(authenticationRepo),
            getUserEmailVerificationStatusUC = GetUserEmailVerificationStatusUC(authenticationRepo),

            updateUserEmailAddressUC = UpdateUserEmailAddressUC(authenticationRepo, databaseRepo),
            updateUserPasswordUC = UpdateUserPasswordUC(authenticationRepo),
            updateUserUsernameUC = UpdateUserUsernameUC(authenticationRepo, databaseRepo),
            updateUserProfilePictureUC = UpdateUserProfilePictureUC(authenticationRepo)
        )
    }

    @Provides
    @Singleton
    fun providePrizeUseCases(databaseRepo: DatabaseRepository): PrizeUseCases {
        return PrizeUseCases(
            wonPrizeAddToUserAccountUC = WonPrizeAddToUserAccountUC(databaseRepo),
            wonPrizeUpdatePrizeClaimedUC = WonPrizeUpdatePrizeClaimedUC(databaseRepo),
            wonPrizeGetAllUC = WonPrizeGetAllUC(databaseRepo),
            wonPrizeGetByIdUC = WonPrizeGetByIdUC(databaseRepo),

            availablePrizeGetAllUC = AvailablePrizeGetAllUC(databaseRepo),
            availablePrizeGetByRNGUC = AvailablePrizeGetByRNGUC(databaseRepo),
        )
    }

    @Provides
    @Singleton
    fun provideMainGameLogicUseCases(databaseRepo: DatabaseRepository, availablePrizeUseCases: PrizeUseCases): MainGameLogicUseCases {
        return  MainGameLogicUseCases(
            incrementGlobalCounterUC = IncrementGlobalCounterUC(databaseRepo),
            doGameLogicUC = DoGameLogicUC(availablePrizeUseCases)
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
