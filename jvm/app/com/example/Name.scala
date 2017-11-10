package com.example

final case class Name(value: String) extends AnyVal with ValueClass[String]
object Name extends (String => Name)