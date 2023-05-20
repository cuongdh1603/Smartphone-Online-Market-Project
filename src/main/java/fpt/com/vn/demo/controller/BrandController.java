package fpt.com.vn.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fpt.com.vn.demo.model.Brand;
import fpt.com.vn.demo.model.Staff;
import fpt.com.vn.demo.service.BrandService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping
    public String listBrand(Model model, HttpSession session){
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());
        
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        return "admin/brands";
    }
    @GetMapping("/add_brand")
    public String addBrand(Model model, HttpSession session){
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());

        Brand brand = new Brand();
        model.addAttribute("brand", brand);
        return "admin/add_brand";
    }
    @PostMapping("/save_brand")
    public String saveBrand(@ModelAttribute("brand") Brand newBrand, Model model,
            @RequestParam("image") MultipartFile multipartFile, HttpSession session) throws IOException{
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());
        if(brandService.getBrandByName(newBrand.getName())!=null){
            model.addAttribute("invalid_name", "Existed name. Please enter another one");
            return "admin/add_brand";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        newBrand.setLogo(fileName);
        if(fileName.equals("")){
            newBrand.setLogo(null);
        }
        Brand brand = brandService.addNewBrand(newBrand);
        if(brand.getLogo() != null){
            String uploadDir = "./src/main/resources/static/img/brand/" + brand.getId();
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            log.info("Path: "+filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        log.info(brand.getName()+" - "+ StringUtils.cleanPath(multipartFile.getOriginalFilename()));
        return "redirect:";
    }
    @GetMapping("/update/{id}")
    public String updateBrand(Model model,@PathVariable("id") Integer id, HttpSession session){
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());

        Brand brand = brandService.getBrandById(id);
        model.addAttribute("brand", brand);
        return "admin/update_brand";
    }
    @PostMapping("/post_update/{id}") 
    public String saveUpdatedBrand(Model model, 
                @PathVariable("id") Integer id, 
                @ModelAttribute("brand") Brand newBrand,
                HttpSession session,
                @RequestParam("image") MultipartFile multipartFile) throws IOException{
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());
        if(brandService.getBrandByNameAndNotId(newBrand.getId(), newBrand.getName())!=null){
            model.addAttribute("invalid_code", "Existed code. Please enter another one");
            return "admin/update_brand";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Brand brand = brandService.getBrandById(id);
        if(fileName.equals("")) newBrand.setLogo(brand.getLogo());
        else newBrand.setLogo(fileName);
        if(!fileName.equals("")){
            String uploadDir = "./src/main/resources/static/img/brand/"+id;
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            else{
                File file = new File(uploadDir);
                deleteFiles(file);
            }
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            log.info("Path file: "+filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        brandService.updateBrand(brand, newBrand);
        return "redirect:/brands";
    }
    //delete all file in a directory
	public static void deleteFiles(File dirPath) {
		File filesList[] = dirPath.listFiles();
		for(File file:filesList) {
			if(file.isFile()) file.delete();
			else deleteFiles(file);
		}
	}
	//delete an entire directory
	public static void deleteDirectory(File dirPath) {
		for(File subfile : dirPath.listFiles()) {
			if(subfile.isDirectory()) {
				deleteDirectory(subfile);
			}
			subfile.delete();
		}
	}
}
