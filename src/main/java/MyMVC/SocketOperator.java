package MyMVC;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketOperator {
    private static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static String socketReadAll(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(input);
        int bufferSize = 100;
        // 初始化指定长度的数组
        char[] data = new char[bufferSize];
        // 通常我们会用 StringBuilder 来处理字符串拼接
        // 它比 String 相加运行效率快很多
        StringBuilder sb = new StringBuilder();
        while (true) {
            // 读取数据到 data 数组，从 0 读到 data.length
            // size 是读取到的字节数
            int size = reader.read(data, 0, data.length);

            // 判断是否读到数据
            if (size > 0) {
                sb.append(data, 0, size);
            }
            // 把字符数组的数据追加到 sb 中
            log("size and data: " + size + " || " + data.length);
            // 读到的 size 比 bufferSize 小，说明已经读完了
//            if (size < bufferSize) {
//                break;
//            }
            if (!reader.ready()) {
                break;
            }
        }
        return sb.toString();
    }

    public static void socketSendAll(Socket socket, byte[] r) throws IOException {
        OutputStream output = socket.getOutputStream();
        output.write(r);
    }
}
