package engine.file.blocks;

import engine.file.Block;
import engine.file.streams.BlockStream;

public abstract class BlockBuffer implements BlockStream{

	public abstract void startBuffering(BlockStream stream);
	
	public abstract Block getBlockIfExistInBuffer(int num);//Retorna um bloco
	public abstract void hintBlock(int num);//Avisa que determinado bloco ser� necess�rio para manter em cache
	public abstract void forceBlock(int num);//For�a um bloco em cache

	public abstract void clearBuffer(); // Limpa o buffer independente se � necess�rio salvar ou n�o

}
