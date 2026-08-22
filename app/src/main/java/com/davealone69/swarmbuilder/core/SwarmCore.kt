package com.davealone69.swarmbuilder.core

/**
 * Android-native foundation for the harvested Emergent Swarm core.
 *
 * This keeps the proven concepts explicit: module lifecycle, routing,
 * experience recording, and bounded self-healing. Platform adapters can
 * provide real project/file/build execution without pretending that a
 * local stub is a finished build engine.
 */
class SwarmCore(
    private val maxRetries: Int = 3,
    private val experience: ExperienceStore = InMemoryExperienceStore(),
) {
    private val modules = linkedMapOf<String, SwarmModule>()

    fun register(module: SwarmModule) {
        modules[module.id] = module
    }

    fun moduleIds(): List<String> = modules.keys.toList()

    suspend fun execute(task: SwarmTask): SwarmResult {
        val candidates = modules.values
            .filter { it.supports(task) }
            .sortedByDescending { experience.score(it.id) }

        if (candidates.isEmpty()) {
            return SwarmResult.Failure("No registered module supports: ${task.description}")
        }

        var lastError = "Unknown failure"
        for (module in candidates) {
            var attempt = 0
            while (attempt < maxRetries) {
                attempt++
                try {
                    val result = module.execute(task)
                    experience.record(module.id, success = true)
                    return result
                } catch (t: Throwable) {
                    lastError = t.message ?: t::class.simpleName.orEmpty()
                    experience.record(module.id, success = false)
                    module.heal(lastError)
                }
            }
        }
        return SwarmResult.Failure(lastError)
    }
}

data class SwarmTask(
    val description: String,
    val payload: String = "",
)

sealed interface SwarmResult {
    data class Success(val output: String) : SwarmResult
    data class Failure(val reason: String) : SwarmResult
}

interface SwarmModule {
    val id: String
    fun supports(task: SwarmTask): Boolean
    suspend fun execute(task: SwarmTask): SwarmResult
    suspend fun heal(reason: String) = Unit
}

interface ExperienceStore {
    fun score(moduleId: String): Double
    fun record(moduleId: String, success: Boolean)
}

class InMemoryExperienceStore : ExperienceStore {
    private data class Stats(var attempts: Int = 0, var successes: Int = 0)
    private val stats = mutableMapOf<String, Stats>()

    override fun score(moduleId: String): Double {
        val s = stats[moduleId] ?: return 0.5
        return if (s.attempts == 0) 0.5 else s.successes.toDouble() / s.attempts
    }

    override fun record(moduleId: String, success: Boolean) {
        val s = stats.getOrPut(moduleId) { Stats() }
        s.attempts++
        if (success) s.successes++
    }
}
