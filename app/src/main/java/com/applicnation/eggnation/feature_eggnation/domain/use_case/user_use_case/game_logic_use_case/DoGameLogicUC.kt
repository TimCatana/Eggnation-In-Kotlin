import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.PrizeUseCases
import com.applicnation.eggnation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DoGameLogicUC @Inject constructor(
    private val availablePrizeUseCases: PrizeUseCases
) {
    /**
     * Error's caught by @availablePrizeGetByRNGUC function
     */
    operator fun invoke(): Flow<Resource<AvailablePrize?>> = flow {
        emit(Resource.Loading<AvailablePrize?>())

        val rng = (0..5).random()
        val prize = availablePrizeUseCases.availablePrizeGetByRNGUC(rng.toString())

        if (prize == null) {
            emit(Resource.Success<AvailablePrize?>(data = null, message = "Lost!!"))
        } else {
            // TODO - add prize to user table in firestore (upon failure, send an error with null as data)

            // TODO - remove prize from realtime database (upon failure, send sucess with prize data. I will need to notify myself that this prize was won and something went wromg. therefore, I'll need to manually remove it)

            // TODO - add prize to wonPrizes table in firestore (upon failure, send a success with prize as data. This operation is useful but not necessary. The above operatio is necessaru)

            emit(Resource.Success<AvailablePrize?>(data = prize, message = "Won!!"))
        }
    }.flowOn(Dispatchers.Default)
}