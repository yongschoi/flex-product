package yongs.temp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import yongs.temp.dao.ProductRepository;
import yongs.temp.model.Product;

@Service
public class ProductService {
	private Logger logger = LoggerFactory.getLogger(ProductService.class);	

	@Autowired
    ProductRepository repo;
	
	public Flux<Product> findAll() {
		logger.debug("flex-product|ProductService|findAll()");
		return repo.findAll();
	}
}
