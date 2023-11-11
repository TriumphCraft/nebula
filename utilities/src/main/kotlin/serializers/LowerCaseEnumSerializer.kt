package dev.triumphteam.nebula.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.reflect.KClass

/** Serializer for enums that are case-insensitive, and will always deserialize to lowercase. */
public abstract class LowerCaseEnumSerializer<E : Enum<E>>(private val clazz: Class<E>) : KSerializer<E> {

    public constructor(clazz: KClass<E>) : this(clazz.java)

    private val constants = clazz.enumConstants.associateBy { it.name.lowercase() }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(clazz.name, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): E {
        val decoded = decoder.decodeString().lowercase().trim('"')
        return requireNotNull(constants[decoded]) {
            "Could not find enum constant '$decoded' for type '${clazz.simpleName}'!"
        }
    }

    override fun serialize(encoder: Encoder, value: E) {
        encoder.encodeString(value.name.lowercase())
    }
}
