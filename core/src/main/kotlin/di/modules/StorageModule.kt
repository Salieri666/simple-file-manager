package di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import data.pref.IPrefsStorage
import data.pref.PrefsStorage
import di.scope.CoreScope


@Module
class StorageModule {

    @CoreScope
    @Provides
    fun providePrefsStorage(context: Context): IPrefsStorage = PrefsStorage(context)
}