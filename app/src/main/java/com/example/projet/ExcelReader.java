package com.example.projet;


import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.InputStream;


public class ExcelReader {

    public static EtudiantsMatieresGroupes getXlsData(InputStream inputStream, int index, String nom, String prenom) {
        EtudiantsMatieresGroupes emg = null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(index);


            for (int i = 1; i < sheet.getLastRowNum(); i++) {

                HSSFRow row = sheet.getRow(i);
                if(getCellFormatValue(row.getCell(2)).equals(nom)&&getCellFormatValue(row.getCell(3)).equals(prenom)){
                    emg = new EtudiantsMatieresGroupes();
                    emg.setIdEtudiant(getCellFormatValue(row.getCell(0)));
                    emg.setPromoEtudiant(getCellFormatValue(row.getCell(1)));
                    emg.setNomEtudiant(getCellFormatValue(row.getCell(2)));
                    emg.setPrenomEtudiant(getCellFormatValue(row.getCell(3)));
                    emg.addMatiereGroupe(getCellFormatValue(row.getCell(6)), getCellFormatValue(row.getCell(7)));
                    emg.addMatiereGroupe(getCellFormatValue(row.getCell(8)), getCellFormatValue(row.getCell(9)));
                    emg.addMatiereGroupe(getCellFormatValue(row.getCell(10)), getCellFormatValue(row.getCell(11)));
                    emg.addMatiereGroupe(getCellFormatValue(row.getCell(12)), getCellFormatValue(row.getCell(13)));
                    emg.addMatiereGroupe(getCellFormatValue(row.getCell(14)), getCellFormatValue(row.getCell(15)));
                    emg.addMatiereGroupe(getCellFormatValue(row.getCell(20)), getCellFormatValue(row.getCell(21)));
                    emg.addMatiereGroupe("CYB-3001", getCellFormatValue(row.getCell(26)));
                    emg.addMatiereGroupe("ETH-3001", getCellFormatValue(row.getCell(27)));
                    emg.addMatiereGroupe("MSH-3002", getCellFormatValue(row.getCell(29)));
                    emg.addMatiereGroupe(getCellFormatValue(row.getCell(30)), getCellFormatValue(row.getCell(31)));
                    emg.addMatiereGroupe("P5-3001", getCellFormatValue(row.getCell(32)));
                }

            }

        } catch (Exception e) {
            Log.e("ExcelReader","read error=" + e, e);
        }

        return emg;
    }


    /**
     * traiter le contenu du cell en fonction du type
     * @param cell
     * @return le contenu en String
     */
    private static String getCellFormatValue(HSSFCell cell){
        String cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA:
                    cellvalue = String.valueOf((int)cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
            }
        }
        return cellvalue;
    }
}

