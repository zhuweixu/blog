package com.zwx.web.admin;

import com.zwx.pojo.User;
import com.zwx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    @GetMapping
    public String loginPage(){
        return "login";
    }

    @PostMapping("/register")
    @ResponseBody
    public Map<String,String> register_user(User user){
        String email=user.getEmail();
        String username=user.getUsername();
        User user1=userService.checkEmailorname(email,username);
        Map<String,String> map = new HashMap<String, String>();
        if(user1 != null) {
            map.put("result","该邮箱或用户名已存在");
        }else {
            userService.saveUser(user);
            map.put("result","注册成功");
        }
        return map;
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String,String> user_login(User user, HttpSession session,
                             @PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                             Model model){
        String email=user.getEmail();
        String password=user.getPassword();
        User user1 =userService.checkUser(email,password);
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        Map<String,String> map = new HashMap<String, String>();
        Date time=new Date();
        userService.updateTime(email,time);
        if(user1 !=null){
            session.setAttribute("user", user1);
            map.put("result","登录成功");
        }else{
            map.put("result","邮箱或密码错误");
        }
        return map;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/admin/users")
    public String tables(@PageableDefault(size = 9,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         Model model){
        model.addAttribute("page",userService.listUser(pageable));
        return "admin/html/users";
    }

    @GetMapping("/admin/users/delete")
    @ResponseBody
    public Map<String,String> delete(Long id){
        userService.deleteUser(id);
        Map<String,String> map = new HashMap<String, String>();
        map.put("result","删除成功");
        return map;
    }

    @PostMapping("/background")
    @ResponseBody
    public Map<String,String> background(Long id,String url,HttpSession session){
        userService.setbackground(id,url);
        Map<String,String> map = new HashMap<String, String>();
        map.put("result","更换成功");
        return map;
    }

    @PostMapping("/avatar")
    @ResponseBody
    public Map<String,String> avatar(Long id,String url,HttpSession session){
        userService.setavatar(id,url);
        Map<String,String> map = new HashMap<String, String>();
        map.put("result","更换成功");
        return map;
    }

}
