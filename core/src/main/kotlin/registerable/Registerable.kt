package dev.triumphteam.nebula.registerable

/** An object that has registration. */
public interface Registerable {

    /** Registers this registerable. */
    public fun register()

    /** Unregisters this registerable. */
    public fun unregister()
}