package study.project.issueservice.config

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import study.project.issueservice.exception.UnauthorizedException

@Component
class AuthUserHandlerArgumentResolver(
    @Value("\${auth.url}") val authUrl: String,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType) // 리플렉션 기능을 통해 parameter와 AuthUser의 타입이 동일한  resolveArgument 함수가 동작하게 된다.

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {

        val authHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION)
            ?: throw UnauthorizedException()

        return runBlocking { // WebClient는 suspend이기 때문에 runBlocking으로 감싸주면 된다.
            WebClient.create()
                .get()
                .uri(authUrl)
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .retrieve()
                .awaitBody<AuthUser>()
        }
    }
}