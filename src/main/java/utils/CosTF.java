package utils;


import java.util.Map;

//基于词频(TF)的余弦相似度算法类
public class CosTF {
    //计算词频映射对应的向量的模
    private double getNorm(Map<String,Integer> text){
        double norm = 0.0;
        //遍历词频映射，将频率的平方累积到freq，再开根号
        for (double freq : text.values()){
            norm += Math.pow(freq, 2);
        }
        return Math.sqrt(norm);
    }

    //计算两个文本的余弦相似度
    public double getCosSimilarity(Map<String,Integer> text_1, Map<String,Integer> text_2){
        double innerProduct = 0.0;//用于存储两个向量的内积，初值0
        for (Map.Entry<String,Integer> entry : text_1.entrySet()){
            int freq_1 = entry.getValue();
            int freq_2 = text_2.getOrDefault(entry.getKey(), 0);
            innerProduct += freq_1 * freq_2;
        }
        //得到两个文本的向量的模
        double norm_1 = getNorm(text_1);
        double norm_2 = getNorm(text_2);
        //返回计算的余弦相似度
        if (norm_1 == 0 || norm_2 == 0){
            return 0.0;
        } else {
            return innerProduct / (norm_1 * norm_2);
        }
    }
}
