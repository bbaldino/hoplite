package com.sksamuel.hoplite.decoder

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.data.invalid
import arrow.data.valid
import com.sksamuel.hoplite.BooleanNode
import com.sksamuel.hoplite.ConfigFailure
import com.sksamuel.hoplite.ConfigResult
import com.sksamuel.hoplite.DoubleNode
import com.sksamuel.hoplite.LongNode
import com.sksamuel.hoplite.Node
import com.sksamuel.hoplite.NullNode
import com.sksamuel.hoplite.StringNode
import com.sksamuel.hoplite.UndefinedNode
import com.sksamuel.hoplite.arrow.flatMap
import kotlin.reflect.KType

class OptionDecoder : Decoder<Option<*>> {

  override fun supports(type: KType): Boolean = type.classifier == Option::class

  override fun decode(node: Node,
                      type: KType,
                      registry: DecoderRegistry): ConfigResult<Option<*>> {
    require(type.arguments.size == 1)
    val t = type.arguments[0].type!!

    fun <T> decode(node: Node, decoder: Decoder<T>): ConfigResult<Option<T>> {
      return decoder.decode(node, t, registry).map { Some(it) }
    }

    return registry.decoder(t).flatMap { decoder ->
      when (node) {
        is UndefinedNode, is NullNode -> None.valid()
        is StringNode, is LongNode, is DoubleNode, is BooleanNode -> decode(node, decoder)
        else -> ConfigFailure.DecodeError(node, type).invalid()
      }
    }
  }
}
