package di.components

import android.content.Context
import dagger.Component
import data.pref.IPrefsStorage
import di.modules.CoreModule
import di.modules.DefaultDispatcher
import di.modules.DispatcherModule
import di.modules.IoDispatcher
import di.modules.MainDispatcher
import di.modules.StorageModule
import di.scope.CoreScope
import kotlinx.coroutines.CoroutineDispatcher


@Component(
    modules = [CoreModule::class, StorageModule::class, DispatcherModule::class],
    dependencies = [AppComponent::class]
)
@CoreScope
interface CoreComponent {
    fun provideContext(): Context
    fun providePrefsStorage(): IPrefsStorage

    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher

    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher

    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher
}