package data

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

val httpClient = HttpClient(Js) {
    defaultRequest {
        host = "sdui-test-database.herokuapp.com"
        url {
            protocol = URLProtocol.HTTPS
        }
        contentType(ContentType.Application.Json)
      /*  headers {
            append(HttpHeaders.AccessControlAllowOrigin,"*")
        }

       */
    }

    //  expectSuccess=false

    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            //   isLenient = true
            ignoreUnknownKeys = true
        })
    }
}