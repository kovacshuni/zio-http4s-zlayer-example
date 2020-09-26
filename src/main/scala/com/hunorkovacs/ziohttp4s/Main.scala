package com.hunorkovacs.ziohttp4s

import zio._
import zio.console.Console
import org.http4s.server.Server
import Http4Server.Http4Server

object Main extends App {

  def run(args: List[String]): URIO[ZEnv, ExitCode] = {

    val program: ZIO[Has[Server] with Console, Nothing, Nothing] =
      ZIO.never

    val httpServerLayer: ZLayer[ZEnv, Throwable, Http4Server] = Http4Server.createHttp4sLayer

    program
      .provideLayer(httpServerLayer ++ Console.live)
      .exitCode
  }

}
