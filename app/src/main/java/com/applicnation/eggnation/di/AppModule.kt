package com.applicnation.eggnation.di

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase_firestore.Firestore
import com.applicnation.eggnation.feature_eggnation.data.repository.FirestoreRepositoryImpl
import com.applicnation.eggnation.feature_eggnation.domain.repository.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideFirestoreDatabase(): FirebaseFirestore {
//        return FirebaseFirestore.getInstance()
//    }
//
//    @Provides
//    @Singleton
//    fun provideFirestoreRepository(database: Firestore): FirestoreRepository {
//        return FirestoreRepositoryImpl(database)
//    }

}