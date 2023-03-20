package sgbd.query.binaryop;

import sgbd.info.Query;
import sgbd.query.Operator;
import sgbd.query.Tuple;
import sgbd.util.ComparableFilter;

public class LeftNestedLoopJoin extends NestedLoopJoin{

    protected int qtdFinded = 0;
    public LeftNestedLoopJoin(Operator left, Operator right, ComparableFilter<Tuple> comparator) {
        super(left, right, comparator);
    }

    @Override
    public void open() {
        super.open();
        qtdFinded = 0;
    }

    @Override
    protected Tuple findNextTuple(){
        //Executa apenas quando o next tuple n�o existe
        if(nextTuple!=null)return nextTuple;
        //Loopa pelo operador esquerdo
        while(currentLeftTuple!=null || left.hasNext()){
            if(currentLeftTuple==null){
                currentLeftTuple = left.next();
                right.open();
                qtdFinded = 0;
            }
            //Loopa pelo operador direito
            while(right.hasNext()){
                Tuple rightTuple = right.next();
                //Faz a compara��o do join
                Query.COMPARE_JOIN++;
                if(comparator.match(currentLeftTuple,rightTuple)){
                    qtdFinded++;
                    nextTuple = new Tuple(currentLeftTuple,rightTuple);
                    return nextTuple;
                }
            }
            right.close();
            if(qtdFinded==0){
                nextTuple = currentLeftTuple;
                currentLeftTuple = null;
                return nextTuple;
            }else currentLeftTuple=null;
        }
        return null;
    }
}
