package fpt.com.vn.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fpt.com.vn.demo.model.Brand;
import fpt.com.vn.demo.model.Color;
import fpt.com.vn.demo.model.ColorItem;
import fpt.com.vn.demo.model.Item;
import fpt.com.vn.demo.model.Staff;
import fpt.com.vn.demo.service.BrandService;
import fpt.com.vn.demo.service.ColorItemService;
import fpt.com.vn.demo.service.ColorService;
import fpt.com.vn.demo.service.ItemService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private ColorItemService colorItemService;
    @GetMapping
    public String itemList(Model model, HttpSession session){
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());
        
        List<Item> items = itemService.getAllItem();
        model.addAttribute("items", items);
        return "admin/items";
    }
    @GetMapping("/add_item")
    public String addItem(Model model, HttpSession session){
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());

        Item item = new Item();
        model.addAttribute("item", item);
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        List<Color> colors = colorService.getAllColors();
        model.addAttribute("colors", colors);
        return "admin/add_item";
    }
    @PostMapping("/save_item")
    public String saveItem(Model model,
        @ModelAttribute("item") Item newItem, 
        @RequestParam("id_brand") Integer idBrand, 
        @RequestParam("id_color") List<Integer> idColors,
        @RequestParam("image") MultipartFile multipartFile,
        HttpSession session
        ) throws IOException{
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());
        Brand brand = brandService.getBrandById(idBrand);

        newItem.setBrand(brand);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        if(fileName.equals("")) newItem.setPhoto(null);
        else newItem.setPhoto(fileName);
        
        log.info("fileName: "+fileName);
        // log.info("Info item: "+newItem.toString());
        Item item = itemService.addNewItem(newItem);
        for (Integer id : idColors) {
            Color choseColor = colorService.getColorById(id);
            if(!choseColor.equals(null)){
                ColorItem colorItem = new ColorItem();
                colorItem.setItem(item);
                colorItem.setColor(choseColor);
                colorItem.setQuantity(0);
                colorItemService.saveColorItem(colorItem);
            }
        }
        if(item.getPhoto() != null){
            String uploadDir = "./src/main/resources/static/img/item/"+item.getId();
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            log.info("File path: "+filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        
        return "admin/index";
    }
    public Double extractNumberFromString(String s) {
		Pattern pattern = Pattern.compile("[^0-9]");
		String numberOnly = pattern.matcher(s).replaceAll("");
		return Double.valueOf(numberOnly);
	}
}
