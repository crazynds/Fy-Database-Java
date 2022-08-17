package engine.virtualization.record;

import engine.file.streams.ReadByteStream;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class RecordInterface{

	private RecordInfoExtraction infoExtraction;
	public RecordInterface(RecordInfoExtraction extraction){
		this.infoExtraction=extraction;
	}

	/*
	 * Atualiza a referencia da primary key na tabela de referencias do manipulador;
	 * N�o � ncess�rio a implementa��o de uma l�gica dentro dessa fun��o
	 * mas pode ser utilizada por otmizadores e cache para busca futura
	 */
	public void updeteReference(BigInteger pk,long key){

	}

	public RecordInfoExtraction getExtractor(){
		return infoExtraction;
	}

}
