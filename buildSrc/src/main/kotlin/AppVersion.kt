data class AppVersion(
    private val defaultMajor: Int,
    private val defaultMinor: Int,
    private val defaultRevision: Int,
    private val defaultBuild: Int
) {

    var major: Int = defaultMajor
        private set

    var minor: Int = defaultMinor
        private set

    var revision: Int = defaultRevision
        private set

    var build: Int = defaultBuild
        private set

    fun init(
        majorVersion: Int,
        minorVersion: Int,
        revisionVersion: Int,
        buildVersion: Int
    ) {
        this.major = majorVersion
        this.minor = minorVersion
        this.revision = revisionVersion
        this.build = buildVersion
    }

    fun init(
        majorVersion: String?,
        minorVersion: String?,
        revisionVersion: String?,
        buildVersion: String?
    ) {
        init(
            majorVersion?.toIntOrNull() ?: defaultMajor,
            minorVersion?.toIntOrNull() ?: defaultMinor,
            revisionVersion?.toIntOrNull() ?: defaultRevision,
            buildVersion?.toIntOrNull() ?: defaultBuild
        )
    }

    override fun toString(): String =
        "$major.$minor.$revision.$build"

}