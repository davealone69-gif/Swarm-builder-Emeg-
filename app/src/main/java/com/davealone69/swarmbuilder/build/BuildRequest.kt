package com.davealone69.swarmbuilder.build

/** User-facing text-to-build request. */
data class BuildRequest(
    val prompt: String,
    val projectName: String = "GeneratedProject",
)

/** Explicit pipeline stages so the UI and engine share one contract. */
enum class BuildStage {
    PLAN,
    DISCOVER,
    INTEGRATE,
    GENERATE,
    BUILD,
    REPAIR,
    VERIFY,
    COMPLETE,
}
