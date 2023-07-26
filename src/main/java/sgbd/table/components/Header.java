package sgbd.table.components;

import com.google.gson.Gson;
import sgbd.prototype.Prototype;
import sgbd.table.Table;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Header {

    public static final String TABLE_TYPE = "table-type";
    public static final String FILE_PATH = "file-path";
    public static final String TABLE_NAME = "tablename";
    public static final String TRUE = "true";
    public static final String FALSE = "false";


    private HashMap<String,String> information;
    private Prototype prototype;

    private String path = null;


    public Header(Prototype pt,String tableName){
        this.information = new HashMap<>();
        this.prototype= pt;
        this.set(TABLE_NAME,tableName);
    }

    public static Header load(String path)throws IOException{
        String json = Files.readString(Paths.get(path),StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Header header = (Header)gson.fromJson(json,Header.class);
        header.path = path;
        return header;
    }
    public void save(String path) throws IOException {
        String filePath = get(Header.FILE_PATH);
        if(filePath!=null){
            String filePathRelative = new File(new File(path).getAbsolutePath()).getParentFile().toURI().relativize(new File(new File(filePath).getAbsolutePath()).toURI()).getPath();
            set(Header.FILE_PATH,filePathRelative);
        }
        setBool("saved",true);
        Gson gson = new Gson();
        String json = gson.toJson(this);
        Files.writeString(Paths.get(path),json, StandardCharsets.UTF_8);
    }

    public void set(String key, String value){
        information.put(key,value);
    }

    public String get(String key){
        return information.get(key);
    }
    public void setBool(String key, boolean value){
        information.put(key,value? Header.TRUE : Header.FALSE);
    }

    public boolean getBool(String key){
        return information.get(key) == Header.TRUE;
    }

    public String getTablePath(){
        String filePath = this.get(Header.FILE_PATH);
        if(this.path!=null && filePath!=null){
            filePath = new File(new File(this.path).getAbsolutePath()).getParentFile() + "/" + filePath;
        }
        if(filePath == null){
            filePath = this.get(Header.TABLE_NAME)+".dat";
        }
        return filePath;
    }

    public Prototype getPrototype() {
        return prototype;
    }
}
