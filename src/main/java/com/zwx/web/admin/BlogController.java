package com.zwx.web.admin;

import com.zwx.pojo.Admin;
import com.zwx.pojo.Blog;
import com.zwx.service.BlogService;
import com.zwx.service.TagService;
import com.zwx.service.TypeService;
import com.zwx.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                        Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/html/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                         Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page",blogService.searchBlogs(pageable,blog));
        return "admin/html/blogs :: blogList";
    }
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return "admin/html/blogs-input";
    }
    @GetMapping("/blogs/{id}/edit")
    public String input(Model model,@PathVariable Long id){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/html/blogs-edit";
    }
    @PostMapping("/blogs")
    @ResponseBody
    public Map<String,String> post(Blog blog, HttpSession session) {
        blog.setAdmin((Admin) session.getAttribute("admin"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        Map<String,String> map = new HashMap<String, String>();
        if (blog.getId() == null) {
            Blog blog2=blogService.getBlogBytitle(blog.getTitle());
            if(blog2!=null){
                map.put("result","该标题已存在");
                return map;
            }
            b =  blogService.saveBlog(blog);
        } else {
            Blog blog1=blogService.getBlog(blog.getId());
            String title=blog1.getTitle();
            Blog blog2=blogService.getBlogBytitle(title);
            if(blog2!=null && !title.equals(blog.getTitle())){
                map.put("result","该标题已存在");
                return map;
            }
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if (b == null ) {
            map.put("result","未知错误，请联系开发者");
        } else {
            map.put("result","文章新增成功");
        }
        return map;
    }
    @GetMapping("/blogs/delete")
    @ResponseBody
    public Map<String,String> delete(Long id){
        blogService.deleteBlog(id);
        Map<String,String> map = new HashMap<String, String>();
        map.put("result","删除成功");
        return map;
    }
}
