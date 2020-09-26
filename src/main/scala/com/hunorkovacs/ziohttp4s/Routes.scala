package com.hunorkovacs.ziohttp4s

import zio._
import zio.interop.catz._

import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._

object Routes {

  val dsl = Http4sDsl[Task]
  import dsl._

  val helloWorldService = HttpRoutes
    .of[Task] {
      case GET -> Root / "hello" => Ok("Hello, Joe")
    }
    .orNotFound

}
