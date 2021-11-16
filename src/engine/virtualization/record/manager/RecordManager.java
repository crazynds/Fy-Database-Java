package engine.virtualization.record.manager;

import java.math.BigInteger;

import engine.file.FileManager;
import engine.file.blocks.BlockBuffer;
import engine.virtualization.record.Record;
import engine.virtualization.record.RecordInterface;
import engine.virtualization.record.RecordStream;

public abstract class RecordManager{
	
	protected FileManager fileManager;
	protected RecordInterface recordInterface;
	
	public RecordManager(FileManager fm,RecordInterface ri) {
		this.fileManager=fm;
		this.recordInterface=ri;
	}

	protected BlockBuffer getBlockBuffer() {
		return fileManager.getBuffer();
	}
	protected RecordInterface getRecordInterface() {
		return recordInterface;
	}
	protected FileManager getFileManager() {
		return fileManager;
	}
	
	/*
	 * Inicia um arquivo do zero
	 * Reinicia todos os dados necess�rios
	 */
	public abstract void restart(FileManager fm) ;
	
	/*
	 * Abre um arquivo e carrega as informa��es necess�rias
	 */
	public abstract void open(FileManager fm) ;
	
	/*
	 * For�a os buffers a liberarem as modifica��es escritas
	 */
	public abstract void flush() ;
	
	/*
	 * Fecha a manipula��o do arquivo e faz o salvametno dos dados
	 */
	public abstract void close() ;

	/*
	 * Le um record a partir de uma chave de armazenamento
	 * Essa chave � gerenciada pelo record manager e n�o � necess�ria ser salva, mas 
	 * torna a tarefa do record manager mais facil caso enviada
	 */
	public abstract Record read(BigInteger pk);
	public abstract void read(BigInteger pk,byte[] buffer);
	
	/*
	 * Essa fun��o tem como objetivo procurar algum record que tenha a chave primaria correspondente
	 * e atualiza-la com as informa��es do record correspondente.
	 * Caso n�o encontre ela deve ser adicionada a lista
	 */
	public abstract Object write(Record r,BigInteger pk) ;
	
	/*
	 * Retorna true se o record manager garante os dados ordenados
	 */
	public abstract boolean isOrdened();	
	
	/*
	 * Retorna um objeto que vai fazer a leitura sequencial dos records.
	 * Esse objeto possui func�es auxiliares de controle
	 */
	public abstract RecordStream sequencialRead();
	
}
