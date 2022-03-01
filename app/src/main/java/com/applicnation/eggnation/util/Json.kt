import android.content.Context
import com.applicnation.eggnation.feature_eggnation.domain.modal.Countries
import com.google.gson.Gson
import timber.log.Timber
import java.io.IOException

fun getJsonDataFromAsset(
    context: Context,
    fileName: String
): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    } catch (exp: IOException) {
        Timber.e("Faailed to read json --> $exp")
        return null
    }

    return jsonString
}

/**
 * For Countries In Claim Prize Dropdown
 */
fun countryList(context: Context): Countries {
    val jsonFileString = getJsonDataFromAsset(context = context, "Countries.json") // TODO - get rid of hard coded string
    val lol = Gson().fromJson(jsonFileString, Countries::class.java)
    Timber.i("$lol")
    Timber.w("${lol.countries[0]}")
    return lol
}

fun localeToEmoji(
    countryCode: String
): String {
    val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}