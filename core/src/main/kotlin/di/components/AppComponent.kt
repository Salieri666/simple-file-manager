package di.components

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import di.modules.AppModule
import javax.inject.Singleton

@Component(
    modules = [AppModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun provideContext(): Context
}