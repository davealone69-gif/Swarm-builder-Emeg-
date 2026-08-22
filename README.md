# Standalone Swarm Builder 🐝

A standalone Android-first app builder intended to recover and operationalise the proven Swarm Builder workflow.

## Purpose

The Builder accepts a natural-language build request, inspects existing project/repository code, reuses the strongest available implementations, integrates missing pieces, builds the project, reads failures, repairs them, and repeats until the result is verified or a concrete blocker remains.

## Priority

**Recover proven behaviour before inventing new architecture.**

The working web-based Swarm Builder is the reference implementation for the swarm engine. Existing repositories are donor sources. The three new application skeletons are downstream projects:

- WorkshopManualOrganiser
- AuraAvatarStudio
- MandelaMatrixReimagenator

## Truth standard

A feature is only marked implemented when executable code supports it. UI labels, README claims, placeholder callbacks and Toast messages are not implementation evidence.

See [`BUILD_PROMPT.md`](BUILD_PROMPT.md) for the text-to-build contract.

## Current status

Phase 0: repository established and build contract committed.

Next: recover the proven Swarm Builder engine and its complete dependency chain, then integrate it into the standalone Android project.
