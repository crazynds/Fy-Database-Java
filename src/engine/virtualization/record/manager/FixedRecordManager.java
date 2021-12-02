package engine.virtualization.record.manager;

import com.sun.source.tree.Tree;
import engine.Main;
import engine.file.FileManager;
import engine.file.buffers.OptimizedFIFOBlockBuffer;
import engine.virtualization.record.Record;
import engine.virtualization.record.RecordInterface;
import engine.virtualization.record.RecordStream;
import engine.virtualization.record.instances.GenericRecord;
import engine.virtualization.record.manager.storage.FixedRecordStorage;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class FixedRecordManager extends RecordManager{


    private TreeMap<BigInteger,Long> recordMap= new TreeMap<>();
    private FixedRecordStorage recordStorage;

    private RecordInterface customInterface;

    private int sizeOfEachRecord;


    public FixedRecordManager(FileManager fm, RecordInterface ri, int sizeOfEachRecord) {
        super(fm, ri);
        customInterface = new AuxRecordInterface();
        recordStorage = new FixedRecordStorage(fm,customInterface,sizeOfEachRecord);
        this.sizeOfEachRecord = sizeOfEachRecord;
    }

    @Override
    public void restart() {
        recordStorage.restartFileSet();
    }

    @Override
    public void flush() {
        recordStorage.flush();
    }

    @Override
    public void close() {
        this.flush();
        recordMap.clear();
    }

    @Override
    public Record read(BigInteger pk) {
        Record r = new GenericRecord(new byte[sizeOfEachRecord]);
        read(pk,r.getData());
        return r;
    }

    @Override
    public void read(BigInteger pk, byte[] buffer) {
        if(recordMap.containsKey(pk)){
            Long pos = recordMap.get(pk);
            recordStorage.read(pos,buffer);
        }else{
            recordStorage.search(pk,buffer);
        }
    }

    @Override
    public void write(Record r) {
        recordStorage.writeNew(r);
    }

    @Override
    public void write(List<Record> list) {
        recordStorage.writeNew(list);
    }

    @Override
    public boolean isOrdened() {
        return true;
    }

    @Override
    public RecordStream sequencialRead() {
        return recordStorage.sequencialRead();
    }

    private class AuxRecordInterface implements RecordInterface{

        private RecordInterface origin = getRecordInterface();

        @Override
        public BigInteger getPrimaryKey(Record r)  {
            return origin.getPrimaryKey(r);
        }

        @Override
        public boolean isActiveRecord(Record r) {
            return origin.isActiveRecord(r);
        }

        @Override
        public void updeteReference(BigInteger pk, long key) {
            recordMap.put(pk,key);
        }

        @Override
        public void setActiveRecord(Record r, boolean active) {
            origin.setActiveRecord(r,active);
        }

    }
}
