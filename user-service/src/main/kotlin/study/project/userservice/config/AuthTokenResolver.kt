package study.project.userservice.config

import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import study.project.userservice.dto.AuthToken

@Component
class AuthTokenResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.hasParameterAnnotation(AuthToken::class.java)

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {

        val authHeader = exchange.request.headers[HttpHeaders.AUTHORIZATION]
            ?.first()
        checkNotNull(authHeader)

        val token = authHeader.split(" ")[1] // Authorization: Bearer token에서 token만 추출

        return token.toMono()
    }
}