package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<Image> list=new ArrayList<>();

        int id=blog.getId();
        if(blogRepository.existsById(id)) {
            Blog newBlog = blogRepository.findById(id).get();

            list = newBlog.getImageList();
            list.add(image);

            newBlog.setImageList(list);

            image.setBlog(newBlog);
            blogRepository.save(newBlog);
        }else {
            list.add(image);
            imageRepository2.save(image);
        }


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
