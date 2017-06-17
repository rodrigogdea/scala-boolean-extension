package org.scalax

object BooleanExtension {

  implicit class Bool(exp: => Boolean) {

    def ifTrue[R](t: => R) = new Else[R](toOption(exp, t))

    def ifFalse[R](t: => R) = new Else[R](toOption(!exp, t))

    def ?[R](t: => R): Else[R] = ifTrue(t)

    def thenElse[R](te: ThenElseBlock[R]): R = te(this)

    private def toOption[R](exp: => Boolean, t: => R) = if (exp) Some(t) else None
  }

  implicit final class IfNull[A](any: A) {

    def isNull[R]: Boolean = any == null

    def isNotNull[R]: Boolean = !isNull

    def ifNull[R](t: => R): Else[R] = isNull ifTrue t

    def ifNotNull[R](t: A => R): Else[R] = isNotNull ifTrue t(any)
  }

  implicit final class ThenElseConverter[R](t: => R) {
    def |(e: => R): ThenElseBlock[R] = new ThenElseBlock[R](t, e)
  }

  class ThenElseBlock[R](t: => R, e: => R) {
    def apply(bool: Bool): R = bool.ifTrue(t).orElse(e)
  }

  class Else[R](pipeResult: Option[R]) {

    def orElse[E >: R](e: => E): E = pipeResult.getOrElse(e)

    def |[E >: R](e: => E): E = orElse(e)

    def map[M](partial: PartialFunction[Option[R], Option[M]]): Else[M] =
      new Else[M](partial.isDefinedAt(pipeResult) ? partial(pipeResult) | None)

    def flatMap[M](partial: PartialFunction[Option[R], Else[M]]): Else[M] =
      partial.isDefinedAt(pipeResult) ? partial(pipeResult) | new Else(None)

  }

}
