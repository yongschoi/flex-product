package yongs.temp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
	
	public Mono<Product> findByCode(String code) {
		logger.debug("flex-product|ProductService|findByCode({})", code);
		return repo.findByCode(code);
	}
	
	public Mono<Product> findByName(String name) {
		logger.debug("flex-product|ProductService|findByName({})", name);
		return repo.findByName(name);
	}	
	
	public Flux<Product> findByRegexpName(String name) {
		logger.debug("flex-product|ProductService|findByRegexpName()");
		return repo.findByRegexpName(name);
	}

	/*
	 * webfulx(reactive) 방식 파일업로드
	 * 
    public String saveFile(FilePart filePart) {
    	String fileName = filePart.filename();
    	Path targetLocation = this.fileLocation.resolve(fileName);

        try {
        	Path targetFile = Files.createFile(targetLocation);
        	AsynchronousFileChannel channel = AsynchronousFileChannel.open(targetFile, StandardOpenOption.WRITE);
        	DataBufferUtils.write(filePart.content(), channel, 0)
        				   .doOnComplete(() -> { 
        					   logger.debug("제품 이미지 파일이 저장되었습니다.");
        				   }).subscribe();
            
            return fileName;
        }catch(Exception e) {
            throw new FileException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.");
        }
    } 
    */
}
