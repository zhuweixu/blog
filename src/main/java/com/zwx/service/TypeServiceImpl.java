package com.zwx.service;

import com.zwx.NotFoundException;
import com.zwx.dao.TypeRepository;
import com.zwx.pojo.Blog;
import com.zwx.pojo.Type;
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
public class TypeServiceImpl implements TypeService {

    @Override
    public Long countType() {
        return typeRepository.count();
    }

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        List<Type> types=typeRepository.findTop(pageable);
        List<Type> types1=new ArrayList<>();
        for(Type type:types){
            Type type1=new Type();
            BeanUtils.copyProperties(type,type1);
            types1.add(type1);
        }
        tealBlog(types1);
        return types1;
    }

    private void tealBlog(List<Type> typeList){
        for(Type type:typeList){
            List<Blog> blogList=type.getBlogs();
            List<Blog> blogList1=new ArrayList<>();
            for(Blog blog:blogList){
                if(blog.isPublished()==true){
                    blogList1.add(blog);
                }
            }
            type.setBlogs(blogList1);
        }
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t=typeRepository.findOne(id);
        if(t==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.delete(id);
    }
}
