package utils;

import java.util.Map;
import java.util.Set;

//jaccard相似系数计算
public class Jaccard {
    public double getJaccardSimilarity(Map<String,Integer> text_1, Map<String,Integer> text_2){
        //计算词频映射中交集数量
        int intersection = 0;//交集
        //两个Set分别存储词频映射中的键集
        Set<String> origKeySet = text_1.keySet();
        Set<String> targetKeySet = text_2.keySet();
        //遍历Set中的每个键取交集
        for (String word : origKeySet){
            if(targetKeySet.contains(word)){
                intersection ++;
            }
        }

        //计算并集数量
        int union = origKeySet.size() + targetKeySet.size() - intersection;

        //返回jaccard相似度
        return (double) intersection / union;
    }
}
