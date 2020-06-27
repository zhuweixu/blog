package com.zwx.web.admin;

import com.zwx.pojo.Tag;
import com.zwx.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    public String List(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                       Pageable pageable,
                       Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/html/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/html/tags-input";
    }
    @GetMapping("/tags/{id}/input")
    public String editinput(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/html/tags-edit";
    }
    @PostMapping("/tags")
    @ResponseBody
    public Map<String,String> post(Tag tag){
        Map<String,String> map = new HashMap<String, String>();
        Tag tag1=tagService.getTagByName(tag.getName());
        if(tag1!=null){
            map.put("result","该标签已存在");
            return map;
        }
        Tag tag2=tagService.saveTag(tag);
        if(tag2==null){
            map.put("result","未知错误，请联系开发者");
        }else{
            map.put("result","新增成功");
        }
        return map;
    }
    @PostMapping("/tags/edit")
    @ResponseBody
    public Map<String,String> editpost(Tag tag, Long id){
        Map<String,String> map = new HashMap<String, String>();
        Tag tag1=tagService.getTagByName(tag.getName());
        if(tag1!=null){
            map.put("result","该标签已存在");
            return map;
        }
        Tag tag2=tagService.updateTag(id,tag);
        if(tag2==null){
            map.put("result","未知错误，请联系开发者");
        }else{
            map.put("result","更新成功");
        }
        return map;
    }
    @GetMapping("/tags/delete")
    @ResponseBody
    public Map<String,String> delete(Long id){
        Map<String,String> map = new HashMap<String, String>();
        tagService.deleteTag(id);
        map.put("result","删除成功");
        return map;
    }

}
