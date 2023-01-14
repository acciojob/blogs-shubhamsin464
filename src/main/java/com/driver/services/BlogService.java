package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository1;

    @Autowired
    ImageService imageService1;

    @Autowired
    UserRepository userRepository1;
    @Autowired
    ImageRepository imageRepository;

    public int getAllBlogsfromDb(){
        return showBlogs().size();
    }
    public List<Blog> showBlogs(){
        //find all blogs
        List<Blog> allBlogs = blogRepository1.findAll();

        return allBlogs;
    }

    public void createAndReturnBlog(Integer userId, String title, String content) {
        //create a blog at the current time
        Blog blog = new Blog();
        blog.setContent(content);
        blog.setTitle(title);
        long milliSecond = System.currentTimeMillis();

        blog.setPubDate(new Date(milliSecond));

        //updating the blog details

        //Updating the userInformation and changing its blogs
        User user = userRepository1.findById(userId).get();
        blog.setUser(user);
        List<Blog> blogList = user.getBlogList();
        blogList.add(blog);
        user.setBlogList(blogList);

        userRepository1.save(user);

    }

    public Blog findBlogById(int blogId){
        //find a blog
        return blogRepository1.findById(blogId).get();
    }

    public void addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog after creating it
        Blog blog = blogRepository1.findAll().get(blogId);
        Image image = new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);
        List<Image> imageList = blog.getImageList();
        imageList.add(image);
        blog.setImageList(imageList);
        blogRepository1.save(blog);
    }

    public void deleteBlog(int blogId){
        //delete blog and corresponding images
        Blog blog= blogRepository1.findById(blogId).get();

        List<Image> imageList=new ArrayList<>();

        imageList=blog.getImageList();
        for(Image image: imageList){
            imageRepository.delete(image);
        }

        User user= new User();
        user=blog.getUser();
        List<Blog> blogList=new ArrayList<>();

        blogList =user.getBlogList();

        blogList.remove(blog);

        user.setBlogList(blogList);

        blogRepository1.delete(blog);

        userRepository1.save(user);
    }
}
