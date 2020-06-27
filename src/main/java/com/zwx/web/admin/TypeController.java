package com.zwx.web.admin;


import com.zwx.pojo.Type;
import com.zwx.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    public String list(@PageableDefault(size = 8,sort = {"id"},
                        direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/html/types";
    }
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/html/types-input";
    }
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/html/types-edit";
    }
    @PostMapping("/types")
    @ResponseBody
    public Map<String,String> post(Type type){
        Map<String,String> map = new HashMap<String, String>();
        Type type1=typeService.getTypeByName(type.getName());
        if(type1!=null){
            map.put("result","该分类已存在");
            return map;
        }
        Type t=typeService.saveType(type);
        if(t==null){
            map.put("result","未知错误，请联系开发者");
        }else{
            map.put("result","新增成功");
        }
        return map;
    }

    @PostMapping("/types/edit")
    @ResponseBody
    public Map<String,String> editpost(Type type,Long id){
        Map<String,String> map = new HashMap<String, String>();
        Type type1=typeService.getTypeByName(type.getName());
        if(type1!=null){
            map.put("result","该分类已存在");
            return map;
        }
        Type t=typeService.updateType(id,type);
        if(t==null){
            map.put("result","未知错误，请联系开发者");
        }else{
            map.put("result","更新成功");
        }
        return map;
    }

    @GetMapping("/types/delete")
    @ResponseBody
    public Map<String,String> delete(Long id){
        typeService.deleteType(id);
        Map<String,String> map = new HashMap<String, String>();
        map.put("result","删除成功");
        return map;
    }
}
