package com.zwx;

import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class test {
    public static void main(String args[]){
        Integer[] aele = new Integer[]{1,2,3,4};
        Integer[] bele = new Integer[]{4,5,6,7,0};
        System.out.println(getSameElement(aele,bele));
    }

    static Set<Integer> getSameElement(Integer[] aEle, Integer[] bEle) {
        //sameElement：最后返回的相同的元素
        Set<Integer> sameElement = new HashSet<>();
        //tempElement：临时存放的元素（aEle or bEle）
        Set<Integer> tempElement = new HashSet<>();
        //将aEle放到集合中
        Collections.addAll(tempElement, aEle);
        //判断bEle中的元素是否存在于tempElement
        for (Integer ele : bEle) {
            if (tempElement.contains(ele)) {
                sameElement.add(ele);
            }
        }
        return sameElement;
    }
}

