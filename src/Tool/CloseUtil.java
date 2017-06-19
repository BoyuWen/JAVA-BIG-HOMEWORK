package Tool;

import java.io.Closeable;

/**
 * Created by Dr.Wen on 2017/6/19.
 */
public class CloseUtil {
    public static void closeAll(Closeable... io){
        for (Closeable temp:io){
            try{
                if (temp != null){
                    temp.close();
                }
            }catch (Exception e){

            }
        }
    }
}
