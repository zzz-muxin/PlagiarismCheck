package utils;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class CosAndJaccardTest {
    private final CosTF cosTF = new CosTF();
    private final Jaccard jaccard = new Jaccard();
    private final TextSegmentation segmentation = new TextSegmentation();

    @Test
    public void testCosAndJaccard() throws IOException{
        //读取测试文件
        String word_1 = FileIO.readFile("D:\\IDEAProject\\PlagiarismCheck\\src\\test\\resources\\test.txt");
        String word_2 = FileIO.readFile("D:\\IDEAProject\\PlagiarismCheck\\src\\test\\resources\\test_add.txt");

        //分词器分词
        Map<String,Integer> map_1 = segmentation.cutWord(word_1);
        Map<String,Integer> map_2 = segmentation.cutWord(word_2);

        //调用测试输出
        double cos = cosTF.getCosSimilarity(map_1, map_2);
        double jresult = jaccard.getJaccardSimilarity(map_1, map_2);
        System.out.printf("余弦相似度为%.2f%%\n",cos * 100);
        System.out.printf("jaccard相似度为%.2f%%\n",jresult * 100);

        //写入结果文件
        String finalResult = String.format("余弦相似度为%.2f%% jaccard相似度为%.2f%%\n",cos * 100, jresult * 100);
        FileIO.writeFile("D:\\IDEAProject\\PlagiarismCheck\\src\\test\\resources\\out.txt",finalResult);
    }
}
