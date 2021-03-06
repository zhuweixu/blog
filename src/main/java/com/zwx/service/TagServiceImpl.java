package com.zwx.service;

import com.zwx.NotFoundException;
import com.zwx.dao.TagRepository;
import com.zwx.pojo.Blog;
import com.zwx.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    @Override
    public Long countTag() {
        return tagRepository.count();
    }

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        List<Tag> tags=tagRepository.findTop(pageable);
        List<Tag> tags1=new ArrayList<>();
        for(Tag tag:tags){
            Tag tag1=new Tag();
            BeanUtils.copyProperties(tag,tag1);
            tags1.add(tag1);
        }
        tealBlog(tags1);
        return tags1;
    }

    private void tealBlog(List<Tag> tagList){
        for(Tag tag:tagList){
            List<Blog> blogList=tag.getBlogs();
            List<Blog> blogList1=new ArrayList<>();
            for(Blog blog:blogList){
                if(blog.isPublished()==true){
                    blogList1.add(blog);
                }
            }
            tag.setBlogs(blogList1);
        }
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByname(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t=tagRepository.findOne(id);
        if(t==null){
            throw new NotFoundException("不存在该标签");
        }else{
            BeanUtils.copyProperties(tag,t);
            return tagRepository.save(t);
        }
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) { //1,2,3
        return tagRepository.findAll(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

}
