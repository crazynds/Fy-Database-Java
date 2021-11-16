package engine.virtualization.record;


public interface RecordStream {
	
	/*
	 * Abre e fecha o leitor sequencial
	 * � importante caso seja necess�rio bloquear a tabela dependendo do record manager
	 */
	public void open();
	public void close();
	
	/*
	 * Verifica se existe um record para a pr�xima leitura
	 */
	public boolean hasNext() ;

	/*
	 * Retorna o record no ponteiro atual, e atualiza o ponteiro para o pr�ximo ponteiro;
	 */
	public Record next();
	public void next(byte[] buffer);
	
	/*
	 * Faz a leitura do record ou a posi��o em que ele est� armazendo no banco de dados
	 */
	public Record getRecord();
	public void getRecord(byte[] buffer);
	
	/*
	 * Faz a chamada de escrita do record na posi��o em que estava
	 * Retorna a nova posi��o no arquivo. Pode ser a mesma em que estava
	 * Caso seja necess�rio, o objeto ira fazer chamadas de atualiza��o da posi��o dos outros records
	 */
	public long write(Record r);
	
	/*
	 * Informa se os records est�o ordenados 
	 */
	public boolean isOrdened();
	
	/*
	 * Reinicia a leitura da stream, voltando para a primieira posi��o
	 */
	public void reset();
	/*
	 * Define a posi��o para a leitura, caso seja passado uma posi��o inv�lida, a leitura pode ocorrer de forma errada.
	 */
	public void setPointer(long position);
	public long getPointer();
	
}
