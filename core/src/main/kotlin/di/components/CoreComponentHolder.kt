package di.components

import di.base.BaseComponentHolder

object CoreComponentHolder : BaseComponentHolder<CoreComponent, CoreComponentDependencies>() {
    override fun build(dependencies: CoreComponentDependencies): CoreComponent {
        component = DaggerCoreComponent.builder()
            .appComponent(dependencies.appComponent)
            .build()
        return component!!
    }
}