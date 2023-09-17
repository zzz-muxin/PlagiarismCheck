import check.PaperCheck;
import check.PaperCheckImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //检查参数个数
        if (args.length != 3){
            System.out.println("请输入正确的参数和文件路径!\n");
            return;
        }

        //判断参数文件是否以.txt结尾
        for(String index:args){
            if(!index.endsWith(".txt")){
                System.out.println("参数文件需为.txt文件!\n");
                return;
            }
        }

        //初始化论文查重的接口
        PaperCheck check = new PaperCheckImpl();
        //调用接口
        try {
            double result = check.paperCheck(args[0], args[1], args[2]);
            System.out.printf("%s 文件总体查重率：%.2f%% \n",args[1],result * 100);
        } catch (IOException e){
            System.out.println("error:文件打开失败!\n");
            System.out.println(e.getMessage());
        }
    }
}
