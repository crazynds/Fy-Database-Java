package sgbd.query.agregation;

import engine.util.Util;
import sgbd.query.Tuple;

import java.math.BigInteger;

public class MinAgregation extends MaxAgregation{


    public MinAgregation(String sourceSrc, String columnSrc, String sourceDst, String columnDst) {
        super(sourceSrc, columnSrc, sourceDst, columnDst);
    }

    public MinAgregation(String sourceSrc, String columnSrc) {
        super(sourceSrc, columnSrc);
    }

    @Override
    public String getAgregationName() {
        return "min";
    }

    @Override
    public void process(Tuple acumulator, Tuple newData) {
        byte[] arr = newData.getContent(sourceSrc).getData(columnSrc);
        if(arr==null)return;
        if(meta==null)meta = newData.getContent(sourceSrc).getMeta(columnSrc);
        BigInteger check = Util.convertByteArrayToNumber(arr);
        if(number.compareTo(check)>0)number = check;
    }

}
