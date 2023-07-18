data class AppCredential(
    private val defaultStorePassword: String,
    private val defaultKeyAlias: String,
    private val defaultKeyPassword: String
) {

    var storePassword: String = defaultStorePassword
        private set

    var keyAlias: String = defaultKeyAlias
        private set

    var keyPassword: String = defaultKeyPassword
        private set

    fun init(
        storePassword: String?,
        keyAlias: String?,
        keyPassword: String?
    ) {
        this.storePassword = storePassword ?: defaultStorePassword
        this.keyAlias = keyAlias ?: defaultKeyAlias
        this.keyPassword = keyPassword ?: defaultKeyPassword
    }

}