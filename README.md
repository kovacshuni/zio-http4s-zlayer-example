# ZIO http4s Simple Example

Example of how to put together an [http4s](https://http4s.org/) server with ZLayers in [ZIO](https://zio.dev/).

This is the next step after [zio-http4s-example](https://github.com/kovacshuni/zio-http4s-example)

I tried to define the simplest http server application possible in the most modular, strucured, consise way.

Extract the server as a `ZLayer` that contains a `Managed` of an http4s `Server`. Whatever and however long
you run something through this layer, the resource will be used meanwhile. So we run through a `ZIO.never`.

### Run:

`sbt run`

### Try:

```
curl localhost:8080/hello
Hello Joe
```

### Dependencies:

```scala
"org.http4s" %% "http4s-blaze-server" % "1.0.0-M4",
"org.http4s" %% "http4s-dsl"          % "1.0.0-M4",
"dev.zio"    %% "zio"                 % "1.0.2",
"dev.zio"    %% "zio-interop-cats"    % "2.2.0.1"
```

### Code:

```scala
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
```

```scala
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
```

### random keywords:
zio, zlayer, http4s, modular, modules, structured, http server, managed, resource, effect tracking, typesafe,
strongly typed, functional, monad, cats, cats-effect, final tagless
