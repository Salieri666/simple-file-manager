package presentation.base

import androidx.annotation.StringRes

interface Action

sealed class CommonAction : Action {
    data class ShowMessage(@StringRes val msg: Int) : CommonAction()
}

sealed class CommonEffect {
    data class ShowMessage(@StringRes val msg: Int) : CommonEffect()
}