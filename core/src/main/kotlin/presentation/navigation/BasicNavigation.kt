package presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController

interface IBasicNavigation {

    val navController: NavController
    var screenResultCallback: ((IEmptyResult) -> Unit)?

    fun back()
    fun <T : IEmptyResult> sendResult(result: T)
}

@Stable
class BasicNavigation(override val navController: NavController) : IBasicNavigation {

    override var screenResultCallback: ((IEmptyResult) -> Unit)? = null

    override fun <T : IEmptyResult> sendResult(result: T) {
        screenResultCallback?.invoke(result)
    }

    override fun back() {
        navController.popBackStack()
    }
}

@Composable
fun rememberBasicNavigation(navController: NavController): IBasicNavigation = remember(navController) {
    BasicNavigation(navController)
}

