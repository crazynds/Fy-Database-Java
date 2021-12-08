package engine.virtualization.record;

import java.math.BigInteger;

public interface RecordInterface {

	public BigInteger getPrimaryKey(Record r) ;

	public boolean isActiveRecord(Record r);
	
	public void setActiveRecord(Record r,boolean active);
	
	/*
	 * Atualiza a referencia da primary key na tabela de referencias do manipulador;
	 * N�o � ncess�rio a implementa��o de uma l�gica dentro dessa fun��o
	 */
	public void updeteReference(BigInteger pk,long key);
	
}
