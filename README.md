> **本次作业GitHub项目链接：https://github.com/zzz-muxin/PlagiarismCheck**

# 作业详情

| 这个作业属于哪个课程 |        [工程概论](https://edu.cnblogs.com/campus/jmu/ComputerScience21)         |
|:----------:|:---------------------------------------------------------------------------:|
| 这个作业要求在哪里  | [作业要求](https://edu.cnblogs.com/campus/jmu/ComputerScience21/homework/13034) |
|  这个作业的目标   |                      学会利用GitHub进行项目开发，完成一个工程项目开发的详细过程                       |

## 需求

> 题目：论文查重
>
> 描述如下：
>
> 设计一个论文查重算法，给出一个原文文件和一个在这份原文上经过了增删改的抄袭版论文的文件，在答案文件中输出其重复率。
>
> - 原文示例：今天是星期天，天气晴，今天晚上我要去看电影。
> - 抄袭版示例：今天是周天，天气晴朗，我晚上要去看电影。
>
> 要求输入输出采用文件输入输出，规范如下：
>
> - 从**命令行参数**给出：论文原文的文件的**绝对路径**。
> - 从**命令行参数**给出：抄袭版论文的文件的**绝对路径**。
> - 从**命令行参数**给出：输出的答案文件的**绝对路径**。
>
> 我们提供一份样例，课堂上下发，上传到班级群，使用方法是：orig.txt是原文，其他orig_add.txt等均为抄袭版论文。
>
> 注意：答案文件中输出的答案为浮点型，精确到小数点后两位

# 一、PSP表格

| ***\*PSP2.1\****                        | ***\*Personal Software Process Stages\**** | ***\*预估耗时（分钟）\**** | ***\*实际耗时（分钟）\**** |
|:----------------------------------------|--------------------------------------------|:-------------------|:-------------------|
| Planning                                | 计划                                         |                    |                    |
| · Estimate                              | · 估计这个任务需要多少时间                             | 90                 | 60                 |
| Development                             | 开发                                         |                    |                    |
| · Analysis                              | · 需求分析 (包括学习新技术)                           | 300                | 350                |
| · Design Spec                           | · 生成设计文档                                   | 100                | 90                 |
| · Design Review                         | · 设计复审                                     | 20                 | 30                 |
| · Coding Standard                       | · 代码规范 (为目前的开发制定合适的规范)                     | 30                 | 20                 |
| · Design                                | · 具体设计                                     | 300                | 280                |
| · Coding                                | · 具体编码                                     | 600                | 630                |
| · Code Review                           | · 代码复审                                     | 100                | 90                 |
| · Test                                  | · 测试（自我测试，修改代码，提交修改）                       | 300                | 250                |
| Reporting                               | 报告                                         |                    |                    |
| · Test Repor                            | · 测试报告                                     | 100                | 90                 |
| · Size Measurement                      | · 计算工作量                                    | 60                 | 30                 |
| · Postmortem & Process Improvement Plan | · 事后总结, 并提出过程改进计划                          | 30                 | 20                 |
|                                         | · 合计                                       | 2030               | 1940               |

# 二、开发环境

- 操作系统：**widows10**
- 使用语言：**java**
- jdk版本：**jdk-16.0.1**
- IDE：**IntelliJ IDEA 2023.2.2**

其他开发工具：

- 性能分析工具：**JProfiler 11.1.4**
- Code Quality Analysis工具：**IDEA自带**
- maven包管理工具

外部依赖库：

- **jieba-analysis 1.0.2**（“结巴”java中文分词库）
- **junit 4.13.1**（junit测试框架）
- **commons-lang3**（一个java外部工具库）
- **hamcrest-core 1.3**（一个标准字符集库）

# 三、基本思路

基于**TF算法的余弦相似度**与**jaccard相似度系数**判断两篇文章查重率：

**最终总体查重率 = 余弦相似度 * 0.5 +jaccard相似度 * 0.5**

1. **TF（term frequency 词频）**

   <img src="https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917162416665.png" alt="image-20230917162416665" style="zoom:25%;" />

   如果某个单词在一篇文章中出现的频率TF高，那么它就越“紧扣主题”。如果该词在其他文章中很少出现，则认为此词或者短语具有很好的类别区分能力，适合用来分类。

   对于一篇文本，某些词频率高却没有明确意义和区分度，如：的、地、是、有等没有意义这类词，称为**停用词**，不去计算这些词的词频，包括各种**标点符号和特殊字符**。

   抛开停用词，一些词出现频率高，也不一定意味着它是有强区分度的关键词，关键词应该是更能区分不同文章的词语，和更能概况文章的词语。因此一般还会在词频TF的基础上，赋予每个词一个权重，这个权重叫**IDF逆⽂档频率**（Inverse Document Frequency），比如将较常见的词“中国”赋予较小的权重，而将一些专有性强的词赋予较大的权重，TF和IDF相乘，就得到一个**TF-IDF值**，这个值越大，一个词就越关键。

   > 本次项目出于个人考虑，只用到了TF，配合jieba中文分词库使用。

2. **cos余弦相似度**

   余弦距离，也称为余弦相似度，是用向量空间中两个向量夹角的余弦值作为衡量两个个体间差异的大小的度量。

   余弦值越接近1，就表明夹角越接近0度，也就是两个向量越相似，这就叫"余弦相似性"。

   <img src="https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917164708486.png" alt="image-20230917164708486" style="zoom:80%;" />

   上图两个向量a,b的夹角很小可以说a向量和b向量有很高的的相似性，极端情况下，a和b向量完全重合，**cos0 = 1**。如下图：

   <img src="https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917164726098.png" alt="image-20230917164726098" style="zoom:80%;" />

   两个向量重合，可以认为文本100%相似，如果a和b向量夹角较大，或者反方向，就可以说明两个文本相似度不大。假设a向量是（x1, y1），b向量是(x2, y2)，那么可以将**余弦定理**改写成下面的形式：

   <img src="https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917165213183.png" alt="image-20230917165213183" style="zoom:50%;" />

   <img src="https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917165346572.png" alt="image-20230917165346572" style="zoom:67%;" />

   **上述二维向量可以扩展到n维向量，将两篇文本提炼出来的词频TF集看作两个n维向量**，我们就可以通过公式计算出两个文本的余弦相似度

3. **jaccard相似度**

   给定两个集合A,B，Jaccard 系数定义为A与B交集的大小与A与B并集的大小的比值，定义如下：

   <img src="https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917170844920.png" alt="image-20230917170844920" style="zoom:33%;" />

   <img src="https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917170307280.png" alt="image-20230917170307280" style="zoom:80%;" />

   jaccard相似系数非常简单，就是把两个文本看成两个集合，相似部分就是交集，用交集除并集就可以得到结果，我们可以**直接用提炼的词频TF集合求解**

# 四、主要算法设计说明

1. 基于TF的余弦相似度算法

   - 利用jieba中文分词库分词后，计算每个词的词频
   - 计算两个文本向量的点积
   - 分别计算两个向量的模长
   - 用n维的余弦定理计算得到余弦相似度

2. jaccard相似系数算法

   - 第一步得到词频库后，用Set集合类型接收结果
   - 用Set集合运算交集和并集
   - 交集除以并集得到jaccard相似系数

3. 总体查重率

   两相似度各占50%权重：

   **最终总体查重率 = 余弦相似度 * 0.5 +jaccard相似度 * 0.5**

# 五、计算模块接口设计与实现过程

## 5.1项目结构

<img src="https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917172903691.png" alt="image-20230917172903691" style="zoom:80%;" />

## 5.2模块设计

- 主类

  - Main类：main()检查参数个数，判断参数文件合法性，调用论文查重接口

- 接口类

  - PaperCheck()接口：定义一个论文查重的接口，传入三个路径参数，抛出文件IO异常
  - PaperCheckImpl()接口实现：调用工具类中的各种方法实现论文查重率的计算

- 工具类

  - FileIO类：实现.txt文件的读写

    readFile()方法：实现从.txt文件读取内容，返回一个构建的String对象

    writeFile()方法：实现用字节数组编码格式UTF-8写入查重结果到指定文件

  - CosTF类：是基于词频(TF)的余弦相似度算法类

    getNorm()方法：实现计算词频映射的向量的模长

    getCosSimilarity()方法：通过余弦定理公式计算余弦相似度

  - Jaccard类：实现jaccard相似系数的计算

    getJaccardSimilarity()方法：读取词频映射中键值对的键，然后取交集和并集，返回jaccard相似度

  - TextSegmentation类：用jieba分词器进行去除stop_word文件内停用词的分词操作

    cutWord()方法：进行分词器初始化，用Lambda表达式分词，返回一个Map类型，即词频键值对的映射

- 外部依赖库

  - **jieba-analysis 1.0.2**（“结巴”java中文分词库）
  - **junit 4.13.1**（junit测试框架，测试用例时用到）
  - **commons-lang3**（一个java外部工具库）
  - **hamcrest-core 1.3**（一个标准字符集库，用于文件写入时转码）

# 六、计算模块接口部分的性能改进

使用**Jprofiler 11.1.4**测试打包后的jar软件，Override性能分析图如下，jar软件进行一次比对查重不超过2秒耗时

![image-20230917191947473](https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917191947473.png)

Memory占用图如下，其中Double类和HashMap类占用最多，Double类主要来自于余弦相似度的计算，HashMap类主要来自于使用分词器分词需要用到Map进行词频映射，可从这两个方面进行改进

![image-20230917192451232](https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917192451232.png)

# 七、计算模块部分单元测试展示

`CosAndJaccardTest.java`测试类代码如下：

```java
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

```

测试类覆盖率如下，项目代码较简单，基本能实现一个测试类就能全覆盖

![image-20230917204326682](https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917204326682.png)

该测试所使用的.txt文件如下，比对了两个文件的相似度，分别计算余弦相似度和jaccard相似度，并把结果写入out.txt文件中

![image-20230917204609689](https://md-wendang.oss-cn-shenzhen.aliyuncs.com/image-20230917204609689.png)

# 八、计算模块部分异常处理说明

1. 命令行参数个数错误

   ```
   		//检查参数个数
           if (args.length != 3){
               System.out.println("请输入正确的参数和文件路径!\n");
               return;
           }
   ```

2. 输入文件路径的合法性检查

   ```
           //判断参数文件是否以.txt结尾
           for(String index : args){
               if(!index.endsWith(".txt")){
                   System.out.println("参数文件需为.txt文件!\n");
                   return;
               }
           }
   ```

3. 文件读取打开失败异常

   ```
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
   ```

# 九、参考

> jieba中文分词库github：https://github.com/fxsjy/jieba