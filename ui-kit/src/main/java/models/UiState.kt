package models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class UiState : Parcelable {

    @Parcelize
    @Immutable
    object Loading : UiState()

    sealed class Error(open val message: String) : UiState() {
        @Immutable
        @Parcelize
        data class CommonError(override val message: String = "") : Error(message)
    }

    @Parcelize
    @Immutable
    object Success : UiState()

    companion object {
        val UiState.isError
            get() = this is Error

        val UiState.isLoading
            get() = this is Loading
    }
}