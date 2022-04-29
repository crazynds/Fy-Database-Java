package engine.virtualization.record;

import engine.file.streams.ReadByteStream;

import java.math.BigInteger;

public interface RecordInterface extends RecordInfoExtraction {
	
	/*
	 * Atualiza a referencia da primary key na tabela de referencias do manipulador;
	 * N�o � ncess�rio a implementa��o de uma l�gica dentro dessa fun��o
	 * mas pode ser utilizada por otmizadores e cache para busca futura
	 */
	public void updeteReference(BigInteger pk,long key);
	
}
