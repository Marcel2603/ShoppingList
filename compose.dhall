let package =
      https://raw.githubusercontent.com/Marcel2603/DhallDockerCompose/master/package.dhall
        sha256:b3873be1a41883f89d24aa42e7fdff7f6261bee16fe05a21aa4c269978819c0e

let server_service =
      package.service::{
      , build = Some
          (package.text_or_build.Object package.build::{ context = "./server" })
      , container_name = Some "server"
      , environment = Some
          ( toMap
              { SPRING_PROFILES_ACTIVE = "compose"
              , DB_URL = "postgres:5432/shop"
              , DB_USER = "shop"
              , DB_PW = "shop"
              }
          )
      , ports = Some [ "9000:9000", "9010:9010" ]
      , volumes = Some [ "./server/target/server.jar:/opt/app.jar:ro" ]
      }

let postgres_service =
      package.service::{
      , image = Some "postgres:14.1"
      , container_name = Some "postgres"
      , environment = Some
          ( toMap
              { POSTGRES_PASSWORD = "postgres"
              , POSTGRES_USER = "postgres"
              , POSTGRES_ADDITIONAL_DATABASES = "shop:shop"
              }
          )
      , ports = Some [ "5432:5432" ]
      , volumes = Some
        [ "./scripts/create_multiple_db.sh:/docker-entrypoint-initdb.d/create_multiple_db.sh"
        ]
      }

in  package.compose::{
    , services = Some
        (toMap { server = server_service, postgres = postgres_service })
    }
