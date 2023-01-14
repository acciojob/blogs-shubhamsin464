package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository2;
    @Autowired
    private BlogRepository blogRepository;

    public Image createAndReturn(Blog blog, String description, String dimensions){
        //create an image based on given parameters and add it to the imageList of given blog
        Image image = new Image();
        image.setDimensions(dimensions);
        image.setDescription(description);
        Blog blog1 = blogRepository.findAll().getClass(blog);
        List<Image> imageList = blog1.getImageList();
        imageList.add(image);
        blogRepository.save(blog);
        return image;
    }

    public void deleteImage(Image image){

          imageRepository2.delete(image);
    }

    public Image findById(int id) {
       Image image=imageRepository2.findById(id).get();
       return image;
    }

    public int countImagesInScreen(Image image, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        //In case the image is null, return 0
        int imageDimensions=Integer.parseInt(image.getDimensions());
        int screen = Integer.parseInt(screenDimensions);
        if(screen/imageDimensions>0){
            return screen/imageDimensions;
        }else {
            return 0;
        }
    }
}
