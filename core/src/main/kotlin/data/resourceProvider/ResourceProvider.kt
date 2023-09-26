package data.resourceProvider

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import di.scope.CoreScope
import javax.inject.Inject

@CoreScope
class ResourceProvider @Inject constructor(
    private val context: Context
) : IResourceProvider {

    override fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    override fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(id, quantity, *formatArgs)
    }
}