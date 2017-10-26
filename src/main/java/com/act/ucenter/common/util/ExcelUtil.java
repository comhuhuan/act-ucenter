/**   
 * @Title: ExcelUtil.java 
 * @Package com.act.web.util 
 * @Description: (Excel 操作类) 
 * @author   fmj
 * @date 2017-2-20 下午6:04:46 
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import jxl.write.biff.RowsExceededException;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

public class ExcelUtil {

	private static WritableFont contentFont = null;
	private static WritableCellFormat contentFormat = null;

	public static void writeSheetForBasic(List<Object[]> resultList,
			WritableSheet sheet) throws Exception {
		// 有记录
		if (resultList != null && !resultList.isEmpty()) {
			// 格式化数字
			formatNumber(resultList);
			// 写数据
			writeExcelDataForBasic(sheet, resultList, 1);

		}

	}

	public static void formatNumber(List<Object[]> resultList) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setGroupingUsed(false);
		// 统计合计数据
		if (resultList != null && !resultList.isEmpty()) {
			for (int j = 0; j < resultList.size(); ++j) {
				Object[] value = (Object[]) resultList.get(j);
				for (int i = 0; i < value.length; i++) {
					Object obj = value[i];
					if (obj == null) {
						value[i] = "";
					}
				}
			}

		}
	}

	public static int writeExcelDataForBasic(WritableSheet sheet,
			List resultList, int titleRow) throws Exception, WriteException,
			RowsExceededException {
		Label label;
		Number numCell;
		int colTitle = 0;
		if (resultList != null && resultList.size() > 1) {
			for (int j = 1; j < resultList.size(); ++j) {
				int colNum = 0;
				Object[] value = (Object[]) resultList.get(j);
				for (int i = 0; i < value.length; i++) {
					Object obj = value[i];
					if (obj.getClass().isInstance(BigDecimal.ONE)) {
						numCell = new Number(colNum++, (j - 1) + titleRow,
								((BigDecimal) obj).doubleValue(),
								getContentStyle());
						sheet.addCell(numCell);
						colTitle = colNum;
					} else {
						label = new Label(colNum++, (j - 1) + titleRow,
								String.valueOf(obj), getContentStyle());
						sheet.addCell(label);
						colTitle = colNum;
					}
				}
			}
		}
		return colTitle;
	}

	public static WritableCellFormat getContentStyle() throws Exception {
		if (null == contentFont) {
			contentFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD);
			contentFont.setColour(Colour.LIGHT_BLUE);
		}
		if (null == contentFormat) {
			contentFormat = new WritableCellFormat(contentFont);
			contentFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			contentFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		}
		return contentFormat;
	}

	public static void writeSheet(List resultList, WritableSheet sheet,
			String reportName) throws Exception {

		int titleRow = 0;

		// 有记录
		if (resultList != null && !resultList.isEmpty()) {

			// 写标题
			titleRow = writerExcelTitleNoID(sheet, reportName, resultList);

			// 格式化数字
			formatNumber(resultList);

			// 写数据
			writeExcelData(sheet, resultList, titleRow);

		}

		// 没有记录也要设置导出信息
		// setExportUser(sheet, titleRow, resultList.size(),map);

	}

	// 写Excel标题
	public static int writerExcelTitleNoID(WritableSheet sheet,
			String titleName, List resultList) throws Exception {

		sheet.getSettings().setDefaultColumnWidth(20); // 设置列默认长度

		Label label = new Label(0, 0, titleName, getTitelStyle()); // 标题Label
		sheet.addCell(label);

		// 动态填充Excel标题
		int titleRow = fillExcelTitle(0, (Object[]) resultList.get(0), sheet);
		return titleRow;
	}

	// 写Excel数据
	public static int writeExcelData(WritableSheet sheet, List resultList,
			int titleRow) throws Exception, WriteException,
			RowsExceededException {
		Label label;
		Number numCell;
		int colTitle = 0;
		if (resultList != null && resultList.size() > 1) {
			for (int j = 1; j < resultList.size(); ++j) {
				int colNum = 0;
				Object[] value = (Object[]) resultList.get(j);
				for (int i = 0; i < value.length; i++) {
					Object obj = value[i];
					if (obj.getClass().isInstance(BigDecimal.ONE)) {
						numCell = new Number(colNum++, (j - 1) + titleRow,
								((BigDecimal) obj).doubleValue(),
								getContentStyle());
						sheet.addCell(numCell);
						colTitle = colNum;
					} else {
						label = new Label(colNum++, (j - 1) + titleRow,
								String.valueOf(obj), getContentStyle());
						sheet.addCell(label);
						colTitle = colNum;
					}
				}
			}
		}

		sheet.setRowView(0, 600);
		sheet.setRowView(1, 400);
		return colTitle;
	}

	// 标题栏的字体样式
	public static WritableCellFormat getTitelStyle() throws Exception {
		// 标题的格式
		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 14,
				WritableFont.BOLD); // 标题字体
		titleFont.setColour(Colour.DARK_BLUE); // 标题颜色
		WritableCellFormat titleFormat = new WritableCellFormat(titleFont); // 标题格式
		titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐方式
		titleFormat.setAlignment(Alignment.CENTRE); // 水平对齐方式

		return titleFormat;
	}

	// 填充Excel表头,并返回标题占用行数
	public static int fillExcelTitle(int start, Object[] obj,
			jxl.write.WritableSheet ws) throws Exception {
		int row = 1;
		WritableCellFormat format1 = new WritableCellFormat();
		format1.setAlignment(jxl.format.Alignment.CENTRE);
		// 把垂直对齐方式指定为居中
		format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		// 设置自动换行
		format1.setWrap(true);
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] == null) {
				obj[i] = "";
			}
			// 处理行
			jxl.write.Label tmp = new jxl.write.Label(i, 0 + start,
					obj[i].toString(), getColStyle());
			ws.addCell(tmp);
		}
		return row + start;
	}

	// 表格数据列的格式
	public static WritableCellFormat getColStyle() throws Exception {
		// 标题的格式
		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD);
		titleFont.setColour(Colour.DARK_BLUE);
		WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
		titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		titleFormat.setAlignment(Alignment.CENTRE);
		titleFormat.setBackground(Colour.PALE_BLUE);
		titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

		return titleFormat;
	}
	
	//关闭excel
	public static void closeExcel(OutputStream os, WritableWorkbook wwb)
			throws IOException, WriteException {
		if (wwb != null) {
			wwb.write();
			wwb.close();
		}
		if (os != null) {
			os.close();
		}

	}
}
