package com.newland.nativepackage;

import java.util.List;

public class PrintContent {
	private PrintSet globalPrintSet;
	  private List<PrintBlock> printBlocks;

	  public PrintSet getGlobalPrintSet()
	  {
	    return this.globalPrintSet;
	  }

	  public List<PrintBlock> getPrintBlocks()
	  {
	    return this.printBlocks;
	  }

	  public void setGlobalPrintSet(PrintSet paramPrintSet)
	  {
	    this.globalPrintSet = paramPrintSet;
	  }

	  public void setPrintBlocks(List<PrintBlock> paramList)
	  {
	    this.printBlocks = paramList;
	  }
}
