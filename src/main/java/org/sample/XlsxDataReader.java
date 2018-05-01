package org.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxDataReader implements DataReader {

    @Override
    public List<Map<String, String>> readData(String path, String sheet) throws IOException {
        List<Map<String, String>> listMap = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(OPCPackage.open(path, PackageAccess.READ))) {
            Sheet sh = workbook.getSheet(sheet);
            List<String> header = new ArrayList<>();
            Row headerRow = sh.getRow(0);
            for (Cell cell : headerRow) {
                header.add(getCellValue(cell));
            }
            for (int i = 1; i <= sh.getLastRowNum(); i++) {
                Row row = sh.getRow(i);
                Map<String, String> map = new HashMap<>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    map.put(header.get(j), getCellValue(row.getCell(j)));
                }
                listMap.add(map);
            }
        } catch (InvalidOperationException | InvalidFormatException e) {
            throw new IOException("テストデータ読み込み中にエラー発生。path=" + path, e);
        }
        return listMap;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return cell.getStringCellValue();
    }

}
