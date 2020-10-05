package yongs.temp.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yongs.temp.model.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
	// @Query("{ 'code': ?0 }")
	public Mono<Product> findByCode(String code);
	// @Query("{ 'name': ?0 }")
	public Mono<Product> findByName(String name);

	@Query("{ 'name' : { $regex: ?0 } }")
	public Flux<Product> findByRegexpName(String regexp);
}
