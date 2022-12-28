package org.example.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.List;

/**
 * @author zhangguangze
 * @version v1.0
 * @project: zgz
 * @description: 这里描述类的用处
 * @copyright: © 2018
 * @company:
 * @date 2018/5/16 14:23
 */
public class PdfUtil {

    private static String FILEPATH = "F:\\testpdf\\pdf\\";

    /**
     *
     * @param fileName
     *            生成pdf文件
     * @param imagesPath
     *            需要转换的图片路径的数组
     */
    public static void imagesToPdf(String fileName, String imagesPath) {
        try {
//            fileName = FILEPATH+fileName+".pdf";
            File file = new File(fileName);
            // 第一步：创建一个document对象。
            Document document = new Document();
            document.setMargins(0, 0, 0, 0);
            // 第二步：
            // 创建一个PdfWriter实例，
            PdfWriter.getInstance(document, new FileOutputStream(file));
            // 第三步：打开文档。
            document.open();
            // 第四步：在文档中增加图片。
            File files = new File(imagesPath);
            String[] images = files.list();
            int len = images.length;

            for (int i = 0; i < len; i++)
            {
                if (images[i].toLowerCase().endsWith(".bmp")
                        || images[i].toLowerCase().endsWith(".jpg")
                        || images[i].toLowerCase().endsWith(".jpeg")
                        || images[i].toLowerCase().endsWith(".gif")
                        || images[i].toLowerCase().endsWith(".png")) {
                    String temp = imagesPath + "\\" + images[i];
                    Image img = Image.getInstance(temp);
                    img.setAlignment(Image.ALIGN_CENTER);
                    // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
//                    document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    document.setPageSize(PageSize.A3);
                    document.newPage();
                    document.add(img);
                }
            }
            // 第五步：关闭文档。
            document.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * pdf合并
     * @param inputStreams 要合并的pdf的InputStream数组
     * @return 合并后的pdf的二进制内容
     */
    private static byte[] mergePdfFiles(List<String> fileList) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Document document = new Document();// 创建一个新的PDF
        byte[] pdfs = new byte[0];
        try {
            PdfCopy copy = new PdfCopy(document, bos);
            document.open();
            for (String file : fileList) {// 取出单个PDF的数据
                PdfReader reader = new PdfReader(file);
                int pageTotal= reader.getNumberOfPages();
//                logger.info("pdf的页码数是 ==> {}",pageTotal);
                for (int pageNo=1;pageNo<=pageTotal;pageNo++){
                    document.newPage();
                    document.setPageSize(PageSize.A3);
                    PdfImportedPage page = copy.getImportedPage(reader, pageNo);
                    copy.addPage(page);
                }
                reader.close();
            }
            document.close();
            pdfs = bos.toByteArray();
            bos.close();
            copy.close();
        } catch (DocumentException | IOException e) {
            System.out.println("合并PDF出错：" + e);
        }
        return pdfs;
    }

    public static void fileWrite(byte[] bytes,String path) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(path);
        outputStream.write(bytes);
    }

    public static void main(String[] args) throws Exception {
//        String name = "./pdf/pdf1/4.aaa";
//        String imagesPath = "./pdf/img2";
//        imagesToPdf(name, imagesPath);
//          byte[] bytes = mergePdfFiles(Arrays.asList("./pdf/pdf2/1.pdf","./pdf/pdf2/2.pdf","./pdf/pdf2/3.aaa","./pdf/pdf2/4.aaa"));
//          fileWrite(bytes,"./pdf/pdf2/5.aaa");
    }
}