package fpt.com.vn.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.com.vn.demo.model.ColorItem;
import fpt.com.vn.demo.repositories.ColorItemRepository;

@Service
public class ColorItemService {
    @Autowired
    private ColorItemRepository colorItemRepository;

    public void saveColorItem(ColorItem colorItem){
        colorItemRepository.save(colorItem);
    }
    // public void saveListColorItem(List<ColorItem> colorItems){
    //     colorItemRepository.save(colorItems);
    // }
}
