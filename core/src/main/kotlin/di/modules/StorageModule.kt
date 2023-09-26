package di.modules

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import data.pref.IPrefsStorage
import data.pref.PrefsStorage
import data.resourceProvider.IResourceProvider
import data.resourceProvider.ResourceProvider
import di.scope.CoreScope


@Module(includes = [StorageModule.Bindings::class])
class StorageModule {

    @CoreScope
    @Provides
    fun providePrefsStorage(context: Context): IPrefsStorage = PrefsStorage(context)

    @Module
    interface Bindings {

        @CoreScope
        @Binds
        fun bind(resourceProvider: ResourceProvider): IResourceProvider
    }
}