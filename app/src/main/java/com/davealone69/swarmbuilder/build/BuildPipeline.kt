package com.davealone69.swarmbuilder.build

import com.davealone69.swarmbuilder.core.SwarmCore
import com.davealone69.swarmbuilder.core.SwarmResult
import com.davealone69.swarmbuilder.core.SwarmTask

/**
 * Orchestrates a build request without claiming that Android itself can
 * execute an arbitrary Gradle project. A real workspace/build adapter is
 * registered separately.
 */
class BuildPipeline(private val swarm: SwarmCore) {
    suspend fun run(request: BuildRequest): SwarmResult = swarm.execute(
        SwarmTask(
            description = "Build ${request.projectName} from text prompt",
            payload = request.prompt,
        ),
    )
}
