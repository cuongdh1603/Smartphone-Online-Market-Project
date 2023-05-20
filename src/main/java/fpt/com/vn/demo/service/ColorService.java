package fpt.com.vn.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.com.vn.demo.model.Color;
import fpt.com.vn.demo.repositories.ColorRepository;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public List<Color> getAllColors(){
        return colorRepository.findAll();
    }
    public Color getColorById(Integer id){
        Optional<Color> op = colorRepository.findById(id);
        if(op.isPresent()) return op.get();
        else return null;
    }
}
