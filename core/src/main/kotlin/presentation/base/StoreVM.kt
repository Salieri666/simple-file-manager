package presentation.base

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class StoreVM<S : State>(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    protected val tag: String = this::class.java.simpleName
    private val savedStateKey = tag

    @Suppress("LeakingThis")
    private val _state = MutableStateFlow(
        savedStateHandle[savedStateKey] ?: initialState
    )
    val state = _state.asStateFlow()
    val actualState: S
        get() = state.value
    protected abstract val initialState: S

    private val actions = MutableSharedFlow<Action>()

    private val _effects = MutableSharedFlow<Any>()
    val effects = _effects.asSharedFlow()
    val showMessageEffects = effects.filter { it is CommonEffect.ShowMessage }
        .map { (it as CommonEffect.ShowMessage).msg }

    init {
        viewModelScope.launch {
            actions.collect {
                _state.emitAll(
                    reduce(it, actualState).catch { t ->
                        Log.d(tag, "Reduce error", t)
                    }
                )
                savedStateHandle[savedStateKey] = _state.value
            }
        }
    }


    fun dispatch(action: Action) {
        viewModelScope.launch {
            actions.emit(action)
            if (action is CommonAction.ShowMessage)
                effect(CommonEffect.ShowMessage(action.msg))
        }
    }

    protected open fun reduce(action: Action, state: S): Flow<S> = this.state

    public fun effect(effect: Any) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    protected fun <T> Flow<T>.toState(
        onLoading: (() -> S)? = null,
        onError: ((error: Throwable) -> S)? = null,
        onContent: suspend (data: T) -> S = { actualState }
    ): Flow<S> =
        this
            .map { onContent(it) }
            .onStart { onLoading?.let { emit(it.invoke()) } }
            .catch { error ->
                Log.d(tag, "toState error", error)
                onError?.let { emit(it.invoke(error)) }
            }

    protected inline fun <T> flowOf(crossinline action: suspend () -> T): Flow<T> =
        flow { emit(action()) }

    protected inline fun Action.ignoreState(crossinline action: suspend () -> Unit): Flow<S> = flow {
        action()
        emit(actualState)
    }

    protected fun Action.ignoreState(): Flow<S> = flowOf(actualState)

    inline fun <T : Action> T.handle(handler: (T) -> Flow<S>): Flow<S> = handler(this)
}