package com.im.controllers;

import com.im.domain.SearchHotelsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static net.javacrumbs.futureconverter.springjava.FutureConverter.toCompletableFuture;

@RestController
public class HotelSearchRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    AsyncRestTemplate restTemplate;
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    @Autowired
//    private LoadBalancerClient loadBalancer;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

//    @RequestMapping("/search-hotels")
//    public DeferredResult<String> searchHotels() {
//
//	// Load balansing
//	ServiceInstance instance = loadBalancer.choose("hotel-service-client");
//        URI storesUri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
//
//        final DeferredResult<String> result = new DeferredResult<String>;
//
//        // Let's call the backend
//        ListenableFuture<ResponseEntity<String>> future =
//                restTemplate.getForEntity("http://www.google.com", String.class);
//
//        future.addCallback(
//                new ListenableFutureCallback<ResponseEntity<String>>() {
//                    @Override
//                    public void onSuccess(ResponseEntity<String> response) {
//                        // Will be called in HttpClient thread
//                        log("Success");
//                        result.setResult(response.getBody());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        result.setErrorResult(t.getMessage());
//                    }
//                });
//        // Return the thread to servlet container,
//        // the response will be processed by another thread.
//        return result;
//    }

    @RequestMapping("/search-hotels")
    public  DeferredResult<String> searchHotels() {
        final DeferredResult<String> result = new DeferredResult<>();

        List<String> urls = Arrays.asList("http://hotel-service-client/search-hotels");

        List<CompletableFuture<ResponseEntity<SearchHotelsResponse>>> futures =
                urls.stream()
                        .map(url -> toCompletableFuture(restTemplate.getForEntity(url, SearchHotelsResponse.class)))
                        .collect(toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenAccept(v -> {
                    List<Hotel> hotels = futures.stream()
                            .map(CompletableFuture::join)
                            .map(HttpEntity::getBody)
                            .map(shr -> shr.getSearchHotelsResult())
                            .collect(Collectors.joining(","));
                    result.setResult(hotels);
                });

        return result;
    }
}
