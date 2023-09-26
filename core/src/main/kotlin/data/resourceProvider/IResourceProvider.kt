package data.resourceProvider

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface IResourceProvider {

    fun getString(@StringRes id: Int): String

    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String
}