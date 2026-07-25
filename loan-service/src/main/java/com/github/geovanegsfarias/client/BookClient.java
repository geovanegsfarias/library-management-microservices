package com.github.geovanegsfarias.client;

import com.github.geovanegsfarias.client.dto.BookResponse;
import com.github.geovanegsfarias.configuration.OpenFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "BookClient",
        url = "${book-service.url}",
        configuration = OpenFeignConfiguration.class
)
public interface BookClient {

    @PutMapping(value = "/v1/books/{id}/reserve")
    BookResponse reserveBook(@PathVariable Long id, @RequestHeader("X-Api-Key") String apiKey);
    @PutMapping(value = "/v1/books/{id}/return")
    BookResponse returnBook(@PathVariable Long id, @RequestHeader("X-Api-Key") String apiKey);
}