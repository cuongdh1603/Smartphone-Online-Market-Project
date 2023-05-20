package fpt.com.vn.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.com.vn.demo.model.Brand;
import fpt.com.vn.demo.repositories.BrandRepository;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    public Brand addNewBrand(Brand brand){
        brandRepository.save(brand);
        return brandRepository.getLastestBrand();
    }
    public Brand getBrandByName(String name){
        return brandRepository.findByName(name);
    }
    public Brand getBrandById(Integer id){
        Optional<Brand> optional = brandRepository.findById(id);
        if(optional.isPresent()) return optional.get();
        return null;
    }

    public Brand getBrandByNameAndNotId(Integer id, String name){
        return brandRepository.findByNameAndNotId(id, name);
    }

    public void updateBrand(Brand brand, Brand newBrand){
        if(brand != null){
            brand.setName(newBrand.getName());
            brand.setLogo(newBrand.getLogo());
            brandRepository.save(brand);
        }
    }
}
