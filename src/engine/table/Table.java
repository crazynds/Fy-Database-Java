package engine.table;

import java.io.FileNotFoundException;
import java.io.IOException;

import engine.exceptions.DataBaseException;
import engine.file.FileManager;
import engine.table.prototype.Prototype;
import engine.table.prototype.RowData;

public class Table {

	private FileManager header,body;
	
	private Prototype columns;
	
	public Table(Prototype pt,String table)  {
		header = new FileManager(table+"header.dat");
		body = new FileManager(table+"body.dat");
		pt.validateColumns();
		this.columns=pt;
		writeHeader();
	}
	
	private void writeHeader() {
		
	}
	
	public void close() {
		header.close();
		body.close();
	}
	

	public void insert(RowData r) {
		
	}
	public void select() {
		
	}
	public void update() {
		
	}
	public void delete() {
		
	}
}
