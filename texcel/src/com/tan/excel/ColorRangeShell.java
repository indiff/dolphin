//package com.tan.excel;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.layout.FillLayout;
//import org.eclipse.swt.ole.win32.OLE;
//import org.eclipse.swt.ole.win32.OleAutomation;
//import org.eclipse.swt.ole.win32.OleClientSite;
//import org.eclipse.swt.ole.win32.OleFrame;
//import org.eclipse.swt.ole.win32.Variant;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;
//
//public class ColorRangeShell {
//	public static void main(String[] args) {
//		new ColorRangeShell().open();
//	}
//
//	public void open() {
//		Display display = Display.getDefault();
//		Shell shell = new Shell();
//		shell.setText("ColorRangeShell");
//		shell.setSize(400, 300);
//		shell.setLayout(new FillLayout());
//
//		createExcelPart(shell);
//
//		shell.open();
//		while (!shell.isDisposed())
//			if (!display.readAndDispatch())
//				display.sleep();
//		display.dispose();
//	}
//
//	private final int SHEET_ID = 0x000001e5;
//
//	private final int CELL_ID = 0x000000c5;
//
//	private void createExcelPart(Shell shell) {
//		OleFrame frame = new OleFrame(shell, SWT.NONE);
//		OleClientSite clientSite = new OleClientSite(frame, SWT.NONE, "Excel.Sheet");
//		clientSite.doVerb(OLE.OLEIVERB_SHOW);
//
//		OleAutomation workbook = new OleAutomation(clientSite);
//		OleAutomation worksheet = workbook.getProperty(SHEET_ID, new Variant[] { new Variant(1) }).getAutomation();
//
//		// 获得单元格
//		OleAutomation cellA1 = worksheet.getProperty(CELL_ID, new Variant[] { new Variant("A1") }).getAutomation();
//		OleAutomation cellD1 = worksheet.getProperty(CELL_ID, new Variant[] { new Variant("D1") }).getAutomation();
//
//		// 获得单元格区域
//		OleAutomation areaA3D5 = worksheet.getProperty(CELL_ID, new Variant[] { new Variant("A3"), new Variant("D5") }).getAutomation();
//
//		colorRangeByRed(cellA1);
//		colorRangeByRed(cellD1);
//		colorRangeByRed(areaA3D5);
//	}
//
//	/**
//	 *获得erior思路方法在Range中Id
//	 */
//	private final int INTERIOR = 0x00000081;
//
//	/**
//	 *为ColorIndex赋值思路方法在erior中Id
//	 */
//	private final int COLOR_INDEX = 0x00000061;
//
//	/**
//	 *红色在ExcelIndex为3
//	 */
//	private final int RED = 3;
//
//	/**
//	 *用红色作为Range背景色
//	 * 
//	 * @paramautomation
//	 */
//	private void colorRangeByRed(OleAutomation automation) {
//		// 获得erior
//		OleAutomation erior = automation.getProperty(INTERIOR).getAutomation();
//		// 设置颜色
//		erior.setProperty(COLOR_INDEX, new Variant(RED));
//	}
//}