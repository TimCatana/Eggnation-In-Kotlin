package com.applicnation.eggnation.di

import DoGameLogicUC
import android.content.Context
import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.*
import com.applicnation.eggnation.feature_eggnation.data.repository.AuthenticationRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.data.repository.DatabaseRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.data.repository.FunctionsRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.data.repository.PreferencesRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.FunctionsRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdLoadUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdPlayUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case.AvailablePrizeGetAllUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case.AvailablePrizeGetByRNGUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.game_logic_use_case.IncrementGlobalCounterUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case.AvailablePrizeDeleteUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.game_logic_use_case.ClaimPrizeUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.game_logic_use_case.DoClaimPrizeUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.get_user_data_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserEmailAddressUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserPasswordUC
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

    /**
     * Backend Code Providers
     */

    @Provides
    @Singleton
    fun provideFunctions(): Functions {
        return Functions()
    }

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

    @Provides
    @Singleton
    fun provideAdmob(): AdMob {
        return AdMob()
    }


    /**
     * Repository Providers
     */
    @Provides
    @Singleton
    fun provideFunctionsRepository(functions: Functions): FunctionsRepository {
        return FunctionsRepositoryImpl(functions)
    }

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
        databaseRepo: DatabaseRepository,
        functionsRepo: FunctionsRepository
    ): UserUseCases {
        return UserUseCases(
            signUpUserUC = SignUpUserUC(authenticationRepo),
            signInUserUC = SignInUserUC(authenticationRepo),
            signOutUserUC = SignOutUserUC(authenticationRepo),
            deleteUserUC = DeleteUserUC(authenticationRepo),
            reauthenticateUser = ReauthenticateUser(authenticationRepo),
            reloadUserUC = ReloadUserUC(authenticationRepo),

            sendUserPasswordResetEmailUC = SendUserPasswordResetEmailUC(authenticationRepo),
            sendUserVerificationEmailUC = SendUserVerificationEmailUC(authenticationRepo),

            getUserIdUC = GetUserIdUC(authenticationRepo),
            getUserEmailUC = GetUserEmailUC(authenticationRepo),
            getUserEmailVerificationStatusUC = GetUserEmailVerificationStatusUC(authenticationRepo),

            updateUserEmailAddressUC = UpdateUserEmailAddressUC(authenticationRepo, databaseRepo, functionsRepo),
            updateUserPasswordUC = UpdateUserPasswordUC(authenticationRepo),
        )
    }

    @Provides
    @Singleton
    fun providePrizeUseCases(
        databaseRepo: DatabaseRepository,
        authenticationRepo: AuthenticationRepository,
    ): PrizeUseCases {
        return PrizeUseCases(
            wonPrizeAddToUserAccountUC = WonPrizeAddToUserAccountUC(databaseRepo),
            wonPrizesAddToAllWonPrizesUC = WonPrizeAddToAllWonPrizesUC(databaseRepo),
            wonPrizeUpdatePrizeClaimedUC = WonPrizeUpdatePrizeClaimedUC(databaseRepo),
            wonPrizeGetAllUC = WonPrizeGetAllUC(databaseRepo, authenticationRepo),
            wonPrizeGetByIdUC = WonPrizeGetByIdUC(databaseRepo),

            availablePrizeGetAllUC = AvailablePrizeGetAllUC(databaseRepo),
            availablePrizeGetByRNGUC = AvailablePrizeGetByRNGUC(databaseRepo),
            availablePrizeDeleteUC = AvailablePrizeDeleteUC(databaseRepo)
        )
    }

    @Provides
    @Singleton
    fun provideMainGameLogicUseCases(
        databaseRepo: DatabaseRepository,
        authenticationRepo: AuthenticationRepository,
        availablePrizeUseCases: PrizeUseCases,
        functionsRepo: FunctionsRepository
    ): MainGameLogicUseCases {
        return MainGameLogicUseCases(
            claimPrizeUC = ClaimPrizeUC(authenticationRepo),
            incrementGlobalCounterUC = IncrementGlobalCounterUC(databaseRepo),
            doGameLogicUC = DoGameLogicUC(authenticationRepo, availablePrizeUseCases, databaseRepo),
            doClaimPrizeUC = DoClaimPrizeUC(functionsRepo, authenticationRepo, databaseRepo)
        )
    }

    // TODO - create admobe repository and add that
    @Provides
    @Singleton
    fun provideAdUseCases(adMob: AdMob): AdUseCases {
        return AdUseCases(
            adLoadUseCase = AdLoadUseCase(adMob),
            adPlayUseCase = AdPlayUseCase(adMob)
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
