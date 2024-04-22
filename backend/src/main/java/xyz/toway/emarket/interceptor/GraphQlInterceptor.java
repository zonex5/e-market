package xyz.toway.emarket.interceptor;

import com.google.common.base.Strings;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static xyz.toway.emarket.GenericConstants.*;

@Component
public class GraphQlInterceptor implements WebGraphQlInterceptor {
    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        request.configureExecutionInput(
                (input, inputBuilder) -> inputBuilder.graphQLContext(contextBuilder -> contextBuilder
                        .put(LANGUAGE_HEADER, Objects.requireNonNullElse(request.getHeaders().getFirst(LANGUAGE_HEADER), LANGUAGE_DEFAULT))
                        .put(CUSTOMER_HEADER, Strings.nullToEmpty(request.getHeaders().getFirst(CUSTOMER_HEADER)))
                ).build()
        );
        return chain.next(request);
    }
}
