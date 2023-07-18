package presentation.navigation

import android.os.Parcelable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import kotlinx.parcelize.Parcelize

@Parcelize
open class Destination(val route: String) : Parcelable {
}

/**
 * For destinations with [Parcelable] dto as argument
 */
interface DtoDestination<T> : ArgsDestination<T> {
    val argsKey: String
    val dto: Parcelable?

    fun putArgs(entry: NavBackStackEntry?) {
        entry?.arguments?.putParcelable(argsKey, dto)
    }
}

/**
 * For destinations with arguments as placeholders in route e.g., "profile/{userId}"
 */
interface ArgsDestination<T> {
    val args: List<NamedNavArgument>?
        get() = null

    fun getDto(entry: NavBackStackEntry): T
}
