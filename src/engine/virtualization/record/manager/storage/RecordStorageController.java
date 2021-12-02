package engine.virtualization.record.manager.storage;

import engine.virtualization.record.Record;
import engine.virtualization.record.RecordStream;

import java.util.List;

public interface RecordStorageController {
	
	/*
	 * Inicia um arquivo do zero
	 * Reinicia todos os dados necess�rios
	 */
	public void restartFileSet() ;
	
	/*
	 * For�a os buffers a liberarem as modifica��es escritas
	 */
	public void flush() ;

	public Record read(long key) ;
	public void read(long key,byte[] buffer) ;

	/*
	 * Sobrescreve o record naquela posi��o
	 */
	public long write(Record r,long key);
	
	/*
	 * Escreve um record na posi��o anterior aquela
	 */
	public long writeNew(Record r);
	public void writeNew(List<Record> list);
	

	/*
	 * Retorna um objeto para efetuar a leitura sequencial
	 */
	public RecordStream sequencialRead();
}
