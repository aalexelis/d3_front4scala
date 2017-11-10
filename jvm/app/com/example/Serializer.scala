package com.example

import play.api.libs.json.Writes

trait Serializer {
  implicit def valueClassWrites[A](implicit writes: Writes[A]): Writes[ValueClass[A]] = {
    Writes { o =>
      writes.writes(o.value)
    }
  }
}
