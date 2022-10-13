package app

import zhttp.http.*
import zhttp.http.middleware.*
import zhttp.service.*
import zhttp.service.server.*
import zio.*

val noAuthApp = Http.collect[Request] { case Method.GET -> !! / "no-auth" =>
  Response.text("Hello World!")
}

val basicAuthApp = Http.collect[Request] { case Method.GET -> !! / "basic-auth" =>
  Response.text("Hello World!")
} @@ Middleware.basicAuth("username", "password")

val oauthApp = Http.collect[Request] { case Method.GET -> !! / "oauth-auth" =>
  Response.text("Hello World!")
} @@ Middleware.bearerAuth(_ == "token")

object App extends ZIOAppDefault:

  override def run =
    for _ <- Server.start(8080, noAuthApp ++ basicAuthApp ++ oauthApp)
    yield ()

object App2 extends ZIOAppDefault:

  override def run =
    (for
      _ <- Server
             .make(
               Server.port(8080)
                 ++ Server.app(noAuthApp)
                 ++ Server.app(basicAuthApp)
                 ++ Server.app(oauthApp)
             )
             .fork
      _ <- ZIO.never
    yield ())
      .provideSome[Scope](
        EventLoopGroup.auto(),
        ServerChannelFactory.auto,
      )
