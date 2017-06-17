# scala-boolean-extension
A less-if scala library

### Examples

```scala

import org.scalax.BooleanExtension._

2 > 1 ifTrue "truly" map {
  case Some(test) if test == "truly" => Some(test.length)
  case None => Some(78)
} orElse 34

2 > 3 ifTrue "truly" flatMap {
  case Some(test) => test == "truly" ifTrue 678
} orElse 567

1 == 2 ifFalse "falsie" orElse "truly"

(1 == 2) ? "then" | "else"

1 == 2 thenElse "falsie" | "truly"

val notNull: String = "foo"

notNull ifNotNull { value =>
  value + " is not null"
} orElse "yes is null"


```
