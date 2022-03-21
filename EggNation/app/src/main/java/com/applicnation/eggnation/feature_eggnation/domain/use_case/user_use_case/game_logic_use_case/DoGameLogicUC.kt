import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.PrizeUseCases
import com.applicnation.eggnation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import kotlin.random.Random

class DoGameLogicUC @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val prizeUseCases: PrizeUseCases,
    private val databaseRepository: DatabaseRepository,
) {
    /**
     * Error's caught by @availablePrizeGetByRNGUC function
     */
    operator fun invoke(): Flow<Resource<AvailablePrize?>> = flow {
        emit(Resource.Loading<AvailablePrize?>())

        val rng = (0..5).random()
        var prizes: ArrayList<AvailablePrize> = ArrayList()

        if (rng % 2 != 0) { // This is the probability of winning
            Timber.i("Lost")
            emit(Resource.Success<AvailablePrize?>(data = null, message = ""))
        } else {
            Timber.i("Won")
            //Get the prizes so that I can choose a random won for the user to win
            try {
                prizes = databaseRepository.getAllAvailablePrizes()
            } catch (e: Exception) {
                Timber.e("REALTIME DATABASE: Failed to get all available prizes -> $e")
                emit(Resource.Error<AvailablePrize?>(message = ""))
                return@flow
            }

            // no prizes available, so the user CANNOT win
            if (prizes.isNullOrEmpty()) {
                Timber.w("REALTIME DATABASE: No prizes to be won")
                emit(Resource.Error<AvailablePrize?>(message = ""))
                return@flow
            }

            // Choose a random prize from the available prizes
            val randomIndex = Random.nextInt(prizes.size);
            val prize = prizes[randomIndex]

            // get user Id to add the prize to the user's document in database
            val userId = authenticationRepository.getUserId()
            if (userId == null) {
                Timber.wtf("!!!! userId is null, but user in in game stack")
                emit(Resource.Error<AvailablePrize?>(message = ""))
                return@flow
            }

            // TODO - uncomment below in production
//            if(!addedPrizeToUserCollection(userId, prize)) {
//                Timber.e("FIRESTORE: Failed to add prize to user document")
//                emit(Resource.Error<AvailablePrize?>(message = ""))
//                return@flow
//            }
//            deleteAvailablePrizeFromDatabase(prize)
//            addPrizeToWonPrizesCollection(userId, prize)

            emit(Resource.Success<AvailablePrize?>(data = prize, message = ""))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * adds the prize to the user's document in the database.
     * @param userId The uid of the user used to determine which document to add the prize to
     * @param prize The prize that the user won
     * @return true - prizeUseCases.wonPrizeAddToUserAccountUC(...) was successful
     * @return false - prizeUseCases.wonPrizeAddToUserAccountUC(...) was unsuccessful
     * @note prizeUseCases.wonPrizeAddToUserAccountUC(...) returns a boolean value indicating whether
     *       or not it was successful
     * @note If this fails, then the user will be considered to have lost because their prize
     *       never made it to their account. I do not want the user to see that they won and then
     *       not have their prize appear in their account. Very bad user experience
     */
    private suspend fun addedPrizeToUserCollection(userId: String, prize: AvailablePrize): Boolean {
        return prizeUseCases.wonPrizeAddToUserAccountUC(
            userId = userId,
            prizeId = prize.prizeId,
            prizeTitle = prize.prizeTitle,
            prizeDesc = prize.prizeDesc,
            prizeType = prize.prizeType,
            prizeTier = prize.prizeTier
        )
    }

    /**
     * Deletes the won prize from the availablePrizes collection
     * @param prize The prize that the user won
     * @note if this fails, then I need to notify myself to delete the prize manually
     *       however, the user will still be considered to have won
     */
    private suspend fun deleteAvailablePrizeFromDatabase(prize: AvailablePrize) {
        val prizeDeletedSuccessfully = prizeUseCases.availablePrizeDeleteUC(prize.prizeId)

        if (!prizeDeletedSuccessfully) {
            Timber.w("Failed to delete prize from realtime database")
            // TODO - email myself that this prize needs to be manually deleted
        }
    }

    /**
     * Adds prize to the wonPrizes collection. This collection shows a summary of all the prizes
     * that have been won.
     * @param userId The uid of the user used to determine which document to add the prize to
     * @param prize The prize that the user won
     * @note if this fails, then I need to notify myself to add the prize manually
     *       however, the user will still be considered to have won
     */
    private suspend fun addPrizeToWonPrizesCollection(userId: String, prize: AvailablePrize) {
        val prizeAddedSuccessfully = prizeUseCases.wonPrizesAddToAllWonPrizesUC(
            userId = userId,
            prizeId = prize.prizeId,
            prizeTitle = prize.prizeTitle,
            prizeDesc = prize.prizeDesc,
            prizeType = prize.prizeType,
            prizeTier = prize.prizeTier
        )

        if (!prizeAddedSuccessfully) {
            Timber.w("Failed to add prize to wonPrizeCollection")
            // TODO - email myself that this prize needs to be manually added
        }
    }
}
