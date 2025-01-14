package com.sksamuel.hoplite.hdfs

import arrow.data.invalid
import arrow.data.valid
import com.sksamuel.hoplite.ConfigFailure
import com.sksamuel.hoplite.ConfigResult
import com.sksamuel.hoplite.Node
import com.sksamuel.hoplite.StringNode
import com.sksamuel.hoplite.decoder.DecoderRegistry
import com.sksamuel.hoplite.decoder.NonNullableDecoder
import org.apache.hadoop.fs.Path
import kotlin.reflect.KType
import kotlin.reflect.full.createType

class PathDecoder : NonNullableDecoder<Path> {

  override fun supports(type: KType): Boolean = type.classifier == Path::class

  override fun safeDecode(node: Node,
                          type: KType,
                          registry: DecoderRegistry): ConfigResult<Path> {
    return when (node) {
      is StringNode -> Path(node.value).valid()
      else -> ConfigFailure.DecodeError(node, Path::class.createType()).invalid()
    }
  }
}
