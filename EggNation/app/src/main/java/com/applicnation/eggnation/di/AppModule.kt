package com.applicnation.eggnation.di

import DoGameLogicUC
import android.content.Context
import com.applicnation.eggnation.feature_eggnation.data.local.Internet
import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.*
import com.applicnation.eggnation.feature_eggnation.data.repository.*
import com.applicnation.eggnation.feature_eggnation.domain.repository.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.ads_use_case.AdLoadUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.ads_use_case.AdPlayUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.preference_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.available_prize_use_case.AvailablePrizeGetAllUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.available_prize_use_case.AvailablePrizeGetByRNGUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.game_logic_use_case.IncrementGlobalCounterUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.available_prize_use_case.AvailablePrizeDeleteUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.won_prize_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.game_logic_use_case.ClaimPrizeUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.game_logic_use_case.DoClaimPrizeUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.game_logic_use_case.InternetConnectivityUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.get_user_data_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.update_user_data_use_case.UpdateUserEmailAddressUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.update_user_data_use_case.UpdateUserPasswordUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.edit_settings.EditEmailScreenUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.edit_settings.EditPasswordScreenUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.auth.ForgotPasswordScreenUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.auth.LoginScreenUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.auth.RegisterScreenUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.game.*
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
    fun provideInternet(): Internet {
        return Internet()
    }

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

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }


    /**
     * Repository Providers
     */
    @Provides
    @Singleton
    fun provideInternetRepository(internet: Internet): InternetRepository {
        return InternetRepositoryImpl(internet)
    }

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

    @Provides
    @Singleton
    fun providePreferencesRepository(preferencesManager: PreferencesManager): PreferencesRepository {
        return PreferencesRepositoryImpl(preferencesManager)
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








    /**
     * Screen Use Cases
     */
    @Provides
    @Singleton
    fun provideLoginScreenUseCases(authenticationRepo: AuthenticationRepository): LoginScreenUseCases {
        return LoginScreenUseCases(
            signInUserUC = SignInUserUC(authenticationRepo)
        )
    }

    @Provides
    @Singleton
    fun provideRegisterScreenUseCases(authenticationRepo: AuthenticationRepository): RegisterScreenUseCases {
        return RegisterScreenUseCases(
            signUpUserUC = SignUpUserUC(authenticationRepo)
        )
    }

    @Provides
    @Singleton
    fun provideForgotPasswordScreenUseCases(authenticationRepo: AuthenticationRepository): ForgotPasswordScreenUseCases {
        return ForgotPasswordScreenUseCases(
            sendUserPasswordResetEmailUC = SendUserPasswordResetEmailUC(authenticationRepo)
        )
    }


    @Provides
    @Singleton
    fun provideAvailablePrizeScreenUseCases(databaseRepo: DatabaseRepository): AvailablePrizeScreenUseCases {
        return AvailablePrizeScreenUseCases(
            availablePrizeGetAllUC = AvailablePrizeGetAllUC(databaseRepo)
        )
    }

    @Provides
    @Singleton
    fun provideClaimPrizeScreenUseCases(functionsRepo: FunctionsRepository, authenticationRepo: AuthenticationRepository, databaseRepo: DatabaseRepository): ClaimPrizeScreenUseCases {
        return ClaimPrizeScreenUseCases(
            doClaimPrizeUC = DoClaimPrizeUC(functionsRepo, authenticationRepo, databaseRepo)
        )
    }

    @Provides
    @Singleton
    fun provideHomeScreenUseCases(
        preferencesRepo: PreferencesRepository,
        databaseRepo: DatabaseRepository,
        internetRepo: InternetRepository,
        adMob: AdMob,
        authenticationRepo: AuthenticationRepository,
        availablePrizeUseCases: PrizeUseCases,
    ): HomeScreenUseCases {
        return HomeScreenUseCases(
            getTapCountPrefUC = GetTapCountPrefUC(preferencesRepo),
            incrementGlobalCounterUC = IncrementGlobalCounterUC(databaseRepo),
            internetConnectivityUC = InternetConnectivityUC(internetRepo),
            decrementTapCountPrefUC = DecrementTapCountPrefUC(preferencesRepo),

            getLastResetTimePrefUC = GetLastResetTimePrefUC(preferencesRepo),
            updateLastResetTimePrefUC = UpdateLastResetTimePrefUC(preferencesRepo),
            updateTapCountPrefUC = UpdateTapCountPrefUC(preferencesRepo),

            adPlayUseCase = AdPlayUseCase(adMob),
            adLoadUseCase = AdLoadUseCase(adMob),

            doGameLogicUC = DoGameLogicUC(authenticationRepo, availablePrizeUseCases, databaseRepo)
        )
    }

    @Provides
    @Singleton
    fun provideSettingsScreenUseCases(
        authenticationRepo: AuthenticationRepository,
    ): SettingsScreenUseCases {
        return SettingsScreenUseCases(
            reloadUserUC = ReloadUserUC(authenticationRepo),
            getUserEmailUC = GetUserEmailUC(authenticationRepo),
            getUserEmailVerificationStatusUC = GetUserEmailVerificationStatusUC(authenticationRepo),
            signOutUserUC = SignOutUserUC(authenticationRepo),
            deleteUserUC = DeleteUserUC(authenticationRepo),
            sendUserVerificationEmailUC = SendUserVerificationEmailUC(authenticationRepo)
        )
    }

    @Provides
    @Singleton
    fun provideWonPrizeScreenUseCases(
        databaseRepo: DatabaseRepository,
        authenticationRepo: AuthenticationRepository,
    ): WonPrizeScreenUseCases {
        return WonPrizeScreenUseCases(
            wonPrizeGetAllUC = WonPrizeGetAllUC(databaseRepo, authenticationRepo),
            getUserEmailVerificationStatusUC = GetUserEmailVerificationStatusUC(authenticationRepo)
        )
    }

    @Provides
    @Singleton
    fun provideEditEmailScreenUseCases(
        databaseRepo: DatabaseRepository,
        authenticationRepo: AuthenticationRepository,
        functionsRepo: FunctionsRepository
    ): EditEmailScreenUseCases {
        return EditEmailScreenUseCases(
            updateUserEmailAddressUC = UpdateUserEmailAddressUC(authenticationRepo, databaseRepo, functionsRepo)
        )
    }

    @Provides
    @Singleton
    fun provideEditPasswordScreenUseCases(
        authenticationRepo: AuthenticationRepository,
    ): EditPasswordScreenUseCases {
        return EditPasswordScreenUseCases(
            updateUserPasswordUC = UpdateUserPasswordUC(authenticationRepo)
        )
    }






}
