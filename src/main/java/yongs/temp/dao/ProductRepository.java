package yongs.temp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import yongs.temp.model.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
	public Flux<Product> findByCode(final String code);
	public Flux<Product> findByName(final String name);
}
