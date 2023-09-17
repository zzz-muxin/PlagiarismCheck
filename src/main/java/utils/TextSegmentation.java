package utils;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


//文本分词类
public class TextSegmentation {
    //读取jieba分词库
    private final JiebaSegmenter segmenter = new JiebaSegmenter();
    //标点符号和各种特殊字符停用词数组
    private final List<String> stopWordList = new LinkedList<>();

    //初始化块
    {
        //读取停用词.txt文件
        InputStream inputStream = this.getClass().getResourceAsStream("/stop_word.txt");
        BufferedReader bufferedReader = null;
        if (inputStream != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }
        //逐行读取添加到数组
        String line;
        try {
            if (bufferedReader != null) {
                while ((line = bufferedReader.readLine()) != null){
                    stopWordList.add(line);
                }
            }
        } catch (IOException e){
            System.out.println("error: 读取文件出错\n");
        }
    }

    //进行分词并计算每个词的词频
    public Map<String,Integer> cutWord(String text){
        //初始化分词结果集
        List<String> resultList = segmenter.sentenceProcess(text);
        //创建一个空的词频的词汇映射
        Map<String,Integer> wordMap = new HashMap<>();
        //过滤各种标点符号和特殊字符
        resultList.stream()
                .map(String::trim)//去除单词两端的空白字符
                .filter(o -> !stopWordList.contains(o))//Lambda表达式过滤掉stop_word中的字符
                .toList()//过滤后的词汇重新收集到一个列表,JDK 16前的版本用.collect(Collectors.toList())
                .forEach(item ->{
                    //装入map中
                    Integer frequency = wordMap.getOrDefault(item, 0);//获取词汇频率,默认值0
                    wordMap.put(item, frequency + 1);//将词汇和频率+1存入映射
                });
                //.forEach(item -> { ... })是一个 Java 8+ 中的流操作
        return wordMap;//返回词汇词频map
    }
}
