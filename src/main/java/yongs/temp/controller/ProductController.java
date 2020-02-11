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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import yongs.temp.model.Product;
import yongs.temp.service.ProductService;
import yongs.temp.util.MediaUtils;

@RestController
@RequestMapping("/product")
public class ProductController {
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
    private ProductService service;
    
    @GetMapping("/all")
    public Flux<Product> findAll() {
    	logger.debug("flex-product|ProductController|findAll()");
        return service.findAll();
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
