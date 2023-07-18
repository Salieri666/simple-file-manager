package di.base

open class BaseComponentHolder<Component, Dependencies : ComponentDependencies> {
    open var component: Component? = null

    fun init(): Component = component ?: build()

    fun init(dependencies: Dependencies): Component = component ?: build(dependencies)

    open fun build(dependencies: Dependencies): Component = get()

    open fun build(): Component = get()

    fun get(): Component =
        component
            ?: error("${this::class.java.simpleName}: component isn't initialized")

    open fun clear() {
        component = null
    }
}