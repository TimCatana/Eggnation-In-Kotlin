import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.PrizeUseCases
import com.applicnation.eggnation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class DoGameLogicUC @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val prizeUseCases: PrizeUseCases,
) {
    /**
     * Error's caught by @availablePrizeGetByRNGUC function
     */
    operator fun invoke(): Flow<Resource<AvailablePrize?>> = flow {
        emit(Resource.Loading<AvailablePrize?>())

        // TODO - check internet connection first

        val rng = (0..5).random()
        val prize = prizeUseCases.availablePrizeGetByRNGUC(rng.toString())

        if (prize == null) {
            emit(Resource.Success<AvailablePrize?>(data = null, message = "Lost!!"))
        } else {
            // TODO - add prize to user table in firestore (upon failure, send an error with null as data)
            val userId = authenticationRepository.getUserId()

            if (userId == null) {
                Timber.wtf("userId is null, something is horribly wrong. This indicates that the user is in the game stack while Firebase Authentication thinks the user is logged out")
                emit(Resource.Error<AvailablePrize?>(message = ""))
                return@flow
            }

            /**
             * Add prize to user's account
             * @note If this fails, then the user will be considered to have lost because their prize
             *       never made it to their account. I do not want the user to see that they won and then
             *       not have their prize appear in their account. Very bad user experience
             */
            if (!prizeUseCases.wonPrizeAddToUserAccountUC(
                    userId = userId,
                    prizeId = prize.prizeId,
                    prizeTitle = prize.prizeTitle,
                    prizeDesc = prize.prizeDesc,
                    prizeType = prize.prizeType,
                    prizeTier = prize.prizeTier
                )
            ) {
                emit(Resource.Error<AvailablePrize?>(message = ""))
                return@flow
            }

            /**
             * Delete prize from realtime database
             * @note If deletion fails, I will notify myself to manually delete the item.
             *       This will not be considered an error however since the user's prize
             *       has been successfully added to their account.
             */
            if (!prizeUseCases.availablePrizeDeleteUC(rng.toString())) {
                // TODO - email myself that this prize needs to be manually deleted
            }

            /**
             * Add the user to allWonPrizes collection in database.
             * This collection allows me to easily see who won what prize without needing to run queries
             * to see who won prizes. This is a bit of overhead for maintenence, but it's worth it
             * @note If this fails, I will notify myself to manually add the item.
             *       This will not be considered an error however since the user's prize
             *       has been successfully added to their account.
             */
            // TODO - uncomment this in production, I have it commented for development purposes
//            if (!prizeUseCases.wonPrizesAddToAllWonPrizesUC(
//                    userId = userId,
//                    prizeId = prize.prizeId,
//                    prizeTitle = prize.prizeTitle,
//                    prizeDesc = prize.prizeDesc,
//                    prizeType = prize.prizeType,
//                    prizeTier = prize.prizeTier
//                )
//            ) {
//                // TODO - email myself that this prize needs to be manually added
//            }


            emit(Resource.Success<AvailablePrize?>(data = prize, message = "Won!!"))
        }
    }.flowOn(Dispatchers.IO)
}
