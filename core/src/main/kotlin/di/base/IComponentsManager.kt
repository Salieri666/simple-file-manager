package di.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import presentation.navigation.Destination

/**
 * CompositionLocal of IComponentsManager. Throws the IllegalStateException if IComponentsManager implementation wasn't provided
 */
val LocalComponentsManager: ProvidableCompositionLocal<IComponentsManager> =
    compositionLocalOf { throw IllegalStateException("ComponentsManager isn't initialized") }

/**
 * Attaches and detaches dagger components to navigation destination
 */
interface IComponentsManager {
    /**
     * Attaches Dagger component to specific destination
     */
    fun attachComponentTo(destination: Destination)

    /**
     * Detaches Dagger component from specific destination
     */
    fun detachComponentFrom(destination: Destination)
}

/**
 * Binds attaching and detaching dagger components to composition lifecycle
 */
@Composable
fun ComponentsLifecycleHandler(
    destination: Destination,
    componentManager: IComponentsManager = LocalComponentsManager.current
) {
    componentManager.attachComponentTo(destination)
    DisposableEffect(true) {
        onDispose {
            componentManager.detachComponentFrom(destination)
        }
    }
}