package javazone_demo.security

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.RequestFilter
import io.micronaut.http.annotation.ServerFilter

@ServerFilter("/datasets/**")
class ApiTokenFilter {

    @Value("\${demo.security.token:javazone-secret}")
    lateinit var expectedToken: String

    @RequestFilter
    fun filterRequest(request: HttpRequest<*>): HttpResponse<*>? {
        val method = request.method
        val mutating = method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.DELETE
        if (!mutating) {
            return null
        }
        val header = request.headers.get("Authorization")
        if ("Bearer $expectedToken" != header) {
            return HttpResponse.unauthorized<Any>()
        }
        return null
    }
}
