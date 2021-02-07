import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Description:
 * @ClassName: PoiExprotTest
 * @Author: yanbobo
 * @CreateDate: 2020/5/25 11:20
 */
public class PoiExprotTest {


    public static void main(String[] args) {
        try {
            export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void export() throws Exception {
        String[] xilie = {"学习人数（人）", "平均学习时长（分）", "满意度（%）"};
        String[] business = {"学校1", "学校2", "学校3", "学校4", "学校5"};
        String[] pepolenum = {"1", "2", "3", "5", "3"};
        String[] miunum = {"6", "1", "2", "6", "5"};
        String[] manyidu = {"5", "1", "4", "7", "4"};
        FileInputStream is = new FileInputStream("D:/moban.xls");
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        FileOutputStream os = new FileOutputStream("D:/moban2.xls");
        //获取创建工作簿的第一页
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //给指定的sheet命名
        hssfWorkbook.setSheetName(0, "学习情况");
        //存储当前表格的样式
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        //填充数据
        for (int i = 0; i <= business.length; i++) {
            HSSFRow row = sheet.createRow(i);
            //遍历列
            for (int j = 0; j <= xilie.length; j++) {
                HSSFCell cell = row.createCell(j);
                if (i == 0 && j == 0) {
                    continue;
                }
                int b = j - 1;
                if (i == 0) {
                    cell.setCellValue(xilie[b]);
                } else {
                    int a = i - 1;
                    switch (j) {
                        case 0:
                            cell.setCellValue(business[a]);
                            break;
                        case 1:
                            cell.setCellValue(Integer.valueOf(pepolenum[a]));
                            break;
                        case 2:
                            cell.setCellValue(Integer.valueOf(miunum[a]));
                            break;
                        case 3:
                            cell.setCellValue(Integer.valueOf(manyidu[a]));
                            break;
                        default:
                            cell.setCellValue(Integer.valueOf(0));
                            break;
                    }
                }
            }
        }

        //写出
        hssfWorkbook.write(os);
        is.close();
        os.flush();
        os.close();
    }


}
