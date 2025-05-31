package io.github.aj8gh.weather.config

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@Configuration
open class ClientConfig {

  @Bean
  open fun okhttpClient() = OkHttpClient.Builder()
    .addInterceptor(DefaultContentTypeInterceptor())
    .build()
}

class DefaultContentTypeInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    return chain.proceed(
      chain.request()
        .newBuilder()
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .build()
    );
  }
}
