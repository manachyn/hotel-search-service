package com.im;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Random;

@Configuration
public class HotelSearchServiceConfig {

//	@Primary
//	@Bean
//	AsyncRestTemplate restTemplate() {
//		return new AsyncRestTemplate();
//	}
//
	@LoadBalanced
	@Bean
	AsyncRestTemplate asyncRestTemplate() {
		return new AsyncRestTemplate();
	}


//	@LoadBalanced
//	@Bean
//	RestTemplate restTemplate(){
//		return new RestTemplate();
//	}

//	@Bean
//	LoadBalancerClient loadBalancerClient() {
//		return new NoopLoadBalancerClient();
//	}
//
//	private static class NoopLoadBalancerClient implements LoadBalancerClient {
//		private final Random random = new Random();
//
//		@Override
//		public ServiceInstance choose(String serviceId) {
//			return new DefaultServiceInstance(serviceId, serviceId,
//					this.random.nextInt(40000), false);
//		}
//
//		@Override
//		public <T> T execute(String serviceId, LoadBalancerRequest<T> request) {
//			try {
//				return request.apply(choose(serviceId));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return null;
//		}
//
//		@Override
//		public <T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws IOException {
//			try {
//				return request.apply(choose(serviceId));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return null;
//		}
//
//		@Override
//		public URI reconstructURI(ServiceInstance instance, URI original) {
//			return DefaultServiceInstance.getUri(instance);
//		}
//	}
}
