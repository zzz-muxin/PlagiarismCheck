package check;

import utils.CosTF;
import utils.FileIO;
import utils.Jaccard;
import utils.TextSegmentation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PaperCheckImpl implements PaperCheck {
    //余弦相似度权重
    private final static double COS_WEIGHT = 0.5;
    //jaccard相似度权重
    private final static double JACCARD_WEIGHT = 0.5;

    @Override
    public double paperCheck(String origFilePath, String targetFilePath, String outputFilePath) throws IOException {
        //初始化分词器
        TextSegmentation textSegmentation = new TextSegmentation();
        //初始化TF-Cos余弦工具类
        CosTF cosTF = new CosTF();
        //初始化jaccard工具类
        Jaccard jaccard = new Jaccard();

        //读取源文件内容和目标文件内容
        String origFile = FileIO.readFile(origFilePath);
        String targetFile = FileIO.readFile(targetFilePath);

        //用分词器分为两个map词频映射
        Map<String,Integer> origText = textSegmentation.cutWord(origFile);
        Map<String,Integer> targetText = textSegmentation.cutWord(targetFile);

        //得到基于TF的余弦相似度
        double cosSimilarity = cosTF.getCosSimilarity(origText, targetText);

        //得到jaccard相似度
        double jaccardSimilarity = jaccard.getJaccardSimilarity(origText,targetText);

        //计算最终总体查重率
        double finalResult = cosSimilarity * COS_WEIGHT + jaccardSimilarity * JACCARD_WEIGHT;

        //将比对结果进行格式化并输出
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        String result = String.format(formattedDate + " %s 文件总体查重率:%.2f%%\n",targetFilePath,finalResult * 100);

        //将结果输出到文件中
        FileIO.writeFile(outputFilePath,result);

        //返回最终总体查重率
        return finalResult;
    }
}
