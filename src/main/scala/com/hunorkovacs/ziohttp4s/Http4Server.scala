package com.hunorkovacs.ziohttp4s

import zio._
import zio.interop.catz._
import zio.interop.catz.implicits._

import org.http4s.server.Server
import org.http4s.server.blaze.BlazeServerBuilder

object Http4Server {

  type Http4Server = Has[Server]

  def createHttp4Server: ZManaged[ZEnv, Throwable, Server] =
    ZManaged.runtime[ZEnv].flatMap { implicit runtime: Runtime[ZEnv] =>
      BlazeServerBuilder[Task](runtime.platform.executor.asEC)
        .bindHttp(8080, "localhost")
        .withHttpApp(Routes.helloWorldService)
        .resource
        .toManagedZIO
    }

  def createHttp4sLayer: ZLayer[ZEnv, Throwable, Http4Server] =
    ZLayer.fromManaged(createHttp4Server)

}
