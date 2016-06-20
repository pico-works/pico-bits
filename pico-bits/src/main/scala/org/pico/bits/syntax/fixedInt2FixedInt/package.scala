package org.pico.bits.syntax

import org.pico.bits.FixedInt2FixedInt

package object fixedInt2FixedInt {
  implicit class FixedInt2FixedIntOps[A](val self: A) extends AnyVal {
    def fixAs[B](implicit ev: FixedInt2FixedInt[A, B]): B = ev.fixAs(self)
  }
}
