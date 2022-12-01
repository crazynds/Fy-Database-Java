import engine.file.FileManager;
import engine.file.blocks.Block;
import engine.file.buffers.FIFOBlockBuffer;
import engine.file.buffers.OptimizedFIFOBlockBuffer;
import sgbd.prototype.Column;
import sgbd.prototype.ComplexRowData;
import sgbd.prototype.Prototype;
import sgbd.prototype.RowData;
import sgbd.query.Operator;
import sgbd.query.Tuple;
import sgbd.query.binaryop.NestedLoopJoin;
import sgbd.query.sourceop.TableScan;
import sgbd.query.unaryop.FilterOperator;
import sgbd.table.SimpleTable;
import sgbd.table.Table;
import sgbd.table.components.RowIterator;
import sgbd.util.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Main {

    public static Table openUser(){

        Prototype p1 = new Prototype();
        p1.addColumn("id",4,Column.PRIMARY_KEY);
        p1.addColumn("nome",255,Column.DINAMIC_COLUMN_SIZE);
        p1.addColumn("anoNascimento",4,Column.NONE);
        p1.addColumn("email",120,Column.NONE);
        p1.addColumn("idade",4,Column.CAN_NULL_COLUMN);
        p1.addColumn("salario",4,Column.NONE);
        p1.addColumn("idCidade",4,Column.NONE);

        Table tableUsers = SimpleTable.openTable("users",p1);
        return tableUsers;
    }
    public static Table openCidade(){
        Prototype p2 = new Prototype();
        p2.addColumn("id",4,Column.PRIMARY_KEY);
        p2.addColumn("nome",255,Column.DINAMIC_COLUMN_SIZE);

        Table tableCidades = SimpleTable.openTable("cidades",p2);
        return tableCidades;
    }


    public static void main(String[] args){
        Table users = openUser();
        Table cidades = openCidade();
        users.open();
        cidades.open();

        // TableScan faz a leitura completa das tabelas
        Operator selectAllUsers = new TableScan(users);
        Operator selectAllCities = new TableScan(cidades);
        // FilterOperator remove os items que n�o passarem na condi��o
        Operator selectSomeUsers = new FilterOperator(selectAllUsers,(Tuple t)->{
            return t.getContent("users").getInt("idade") >=18;
        });
        Operator selectSomeCities = new FilterOperator(selectAllCities,(Tuple t)->{
            return t.getContent("cidades").getString("nome").compareToIgnoreCase("Santa Maria")==0;
        });

        // NestedLoopJoin faz a ju��o da tupla A com a tupla B se a condi��o for verdadeira
        Operator joinUsersCities = new NestedLoopJoin(selectSomeUsers,selectSomeCities,(t1, t2) -> {
            return t1.getContent("users").getInt("idCidade") == t2.getContent("cidades").getInt("id");
        });

        Operator query = joinUsersCities;

        // Itera sobre cada linha dos operadores, note que operadores trabalham com Tupla's ao invez de RowData
        for (query.open(); query.hasNext(); ) {
            // Uma Tuple � um conjunto de um ou mais ComplexRowData, dependendo da quanitadade de joins que acontecerem
            Tuple tuple = query.next();
            String str = "";
            for (Map.Entry<String, ComplexRowData> row: tuple){
                for(Map.Entry<String,byte[]> data:row.getValue()) {
                    switch(Util.typeOfColumnByName(data.getKey())){
                        case "int":
                            str+=row.getKey()+"."+data.getKey()+"="+row.getValue().getInt(data.getKey());
                            break;
                        case "float":
                            str+=row.getKey()+"."+data.getKey()+"="+row.getValue().getFloat(data.getKey());
                            break;
                        case "string":
                        default:
                            str+=row.getKey()+"."+data.getKey()+"="+row.getValue().getString(data.getKey());
                            break;
                    }
                    str+=" | ";
                }
            }
            System.out.println(str);
        }

        users.close();
        cidades.close();
    }
}
