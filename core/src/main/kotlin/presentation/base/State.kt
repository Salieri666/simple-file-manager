package presentation.base

import android.os.Parcelable
import kotlinx.coroutines.flow.flowOf

interface State : Parcelable

inline fun <S : State> S.change(action: (S) -> S) = flowOf(action(this))
