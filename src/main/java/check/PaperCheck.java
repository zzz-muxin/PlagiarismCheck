package check;

import java.io.IOException;

//论文查重接口
public interface PaperCheck {
    double paperCheck(String origFilePath, String targetFilePath, String outputFilePath) throws IOException;
}
