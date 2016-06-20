package org.pico.bits.syntax

import org.pico.bits.Bits

package object anyVal {
  implicit class ByteOps_2s8EdpV(val self: Byte) extends AnyVal {
    @inline final def byteSize = 1
    @inline final def bitSize = Bits(8)
    @inline final def <<(that: Bits) = self << that.value
    @inline final def bits: Bits = Bits(self.toLong)
  }

  implicit class ShortOps_2s8EdpV(val self: Short) extends AnyVal {
    @inline final def byteSize = 2
    @inline final def bitSize = Bits(16)
    @inline final def <<(that: Bits) = self << that.value
    @inline final def bits: Bits = Bits(self.toLong)
  }

  implicit class IntOps_2s8EdpV(val self: Int) extends AnyVal {
    @inline final def byteSize = 4
    @inline final def bitSize = Bits(32)
    @inline final def <<(that: Bits) = self << that.value
    @inline final def bits: Bits = Bits(self.toLong)
  }

  implicit class LongOps_2s8EdpV(val self: Long) extends AnyVal {
    @inline final def byteSize = 8
    @inline final def bitSize = Bits(64)
    @inline final def <<(that: Bits) = self << that.value
    @inline final def bits: Bits = Bits(self.toLong)
  }
}
