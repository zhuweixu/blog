package com.zwx.web.admin;

import com.zwx.pojo.Admin;
import com.zwx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @GetMapping("/index")
    public String index(){
        return "admin/index";
    }

    @GetMapping("/welcome")
    public String welcome(Model model){
        Date date=new Date();
        model.addAttribute("userNmumber",userService.countUser());
        model.addAttribute("commentNumber",commentService.countComment());
        model.addAttribute("likeNumber",likeService.countLike());
        model.addAttribute("typeNumber",typeService.countType());
        model.addAttribute("tagNumber",tagService.countTag());
        model.addAttribute("blogsNumber",blogService.countBlogs());
        model.addAttribute("time",date);
        return "admin/html/welcome";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String,String> admin_login(Admin admin,
                                          HttpSession session){
        String adminname=admin.getAdminname();
        String password=admin.getPassword();
        Admin admin1 =adminService.checkAdmin(adminname,password);
        Map<String,String> map = new HashMap<String, String>();
        if(admin1 !=null){
            session.setAttribute("admin", admin1);
            map.put("result","登录成功");
        }else{
            map.put("result","账号或密码错误");
        }
        return map;
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/admin";
    }

}
