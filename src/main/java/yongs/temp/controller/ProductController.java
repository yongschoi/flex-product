package yongs.temp.controller;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yongs.temp.model.Product;
import yongs.temp.service.ProductService;
import yongs.temp.util.MediaUtils;

@RestController
@RequestMapping("/product")
public class ProductController {
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
    private ProductService service;

	/*
	 * webfulx(reactive) 방식 파일업로드
	 * 
	@PostMapping(value = "/create",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void create(@RequestPart("file") FilePart filePart, @RequestPart("productStr") String  productStr) throws Exception{
    	logger.debug("flex-product|ProductController|create({})", productStr);
    	
        eventService.create(filePart, productStr);
    }
    */

    @GetMapping("/all")
    public Flux<Product> findAll() {
    	logger.debug("flex-product|ProductController|findAll()");
        return service.findAll();
    }
    
	@GetMapping("/code/{code}")
	public Mono<Product> findByCode(@PathVariable("code") String code) {
		logger.debug("flex-product|ProductController|findByCode({})", code);
		return service.findByCode(code);	 	
		/*
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productMono, Product.class)
                .switchIfEmpty(ServerResponse.notFound().build());
        */	
	}
	@GetMapping("/name/{name}")
	public Mono<Product> findByName(@PathVariable("name") String name) {
		logger.debug("flex-product|ProductController|findByName({})", name);
		return service.findByName(name);	
	}
    @GetMapping("/search/{name}")
    public Flux<Product> findByRegexpName(@PathVariable("name") String name) {
    	logger.debug("flex-product|ProductController|findByRegexpName({})", name);
    	return service.findByRegexpName(name);
    }
	@GetMapping("/displayImg")
	public ResponseEntity<byte[]> displayFile(@RequestParam("name") String fileName)throws Exception{
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		String productRoot = "D:\\temp\\product";
		
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(productRoot + "\\" + fileName);
			
			//step: change HttpHeader ContentType
			if(mType != null) {
				//image file(show image)
				headers.setContentType(mType);
			}else {
				//another format file(download file)
				fileName = fileName.substring(fileName.indexOf("_") + 1);//original file Name
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		
		return entity;	
	}
}
