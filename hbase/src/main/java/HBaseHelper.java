import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/3.
 */
public class HBaseHelper {
    private Configuration conf;
    private HBaseAdmin admin;

    /**
     * HBase配置
     * @param conf
     * @throws IOException
     */
    public HBaseHelper(Configuration conf) throws IOException{
        this.conf= HBaseConfiguration.create(conf);
        this.admin=new HBaseAdmin(this.conf);
        System.out.println("创建HBase配置成功");
    }

    /**
     * 创建表
     * @param tableName
     * @param colFamilies
     * @throws Exception
     */
    public void createTable(String tableName,String colFamilies[])
            throws Exception{
        if(this.admin.tableExists(tableName)){
            System.out.println("Table:"+tableName + "already exists!");
        }else{
            HTableDescriptor dsc = new HTableDescriptor(tableName);
            int len=colFamilies.length;
            for(int i=0;i<len;i++){
                HColumnDescriptor family=new HColumnDescriptor(colFamilies[i]);
                dsc.addFamily(family);
                admin.createTable(dsc);
                System.out.println("创建表："+tableName+"成功！");
            }
        }
    }

    /**
     * 删除表
     * @param tableName
     * @throws IOException
     */
    public void deleteTable(String tableName) throws IOException{
        if(this.admin.tableExists(tableName)){
            admin.disableTable(tableName);
            System.out.println("禁用表"+tableName+"!");
            admin.deleteTable(tableName);
            System.out.println("删除表成功！");
        }else{
            System.out.println(tableName+"表不存在！");
        }
    }

    /**
     * 获取所有表
     * @return
     */
    public List getAllTables(){
        List<String> tables = null;
        if(admin != null){
            try {
                HTableDescriptor[]  allTable = admin.listTables();
                if(allTable.length > 0) {
                    tables = new ArrayList<String>();
                    for (HTableDescriptor hTableDescriptor : allTable) {
                        tables.add(hTableDescriptor.getNameAsString());
                        System.out.println(hTableDescriptor.getNameAsString());
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return tables;
    }

    /**
     * 插入记录
     * @param tableName
     * @param rowkey
     * @param family
     * @param qualifier
     * @param value
     * @throws IOException
     */
    public  void insertRecord(String tableName,String rowkey,String family,
              String qualifier,String value) throws IOException{
        HTable table = new HTable(this.conf,tableName);
        Put put = new Put(rowkey.getBytes());
        put.add(family.getBytes(),qualifier.getBytes(),value.getBytes());
        table.put(put);
        System.out.println(tableName+"插入key："+rowkey+"行成功！");
    }

    /**
     * 删除一行记录
     * @param tableName
     * @param rowkey
     * @throws IOException
     */
    public  void deleteRecord(String tableName,String rowkey) throws IOException{
        HTable table = new HTable(this.conf,tableName);
        Delete del = new Delete(rowkey.getBytes());
        table.delete(del);
        System.out.println(tableName+"删除行"+rowkey+"成功！");
    }

    /**
     * 获取一条记录
     * @param tableName
     * @param rowkey
     * @return
     * @throws IOException
     */
    public Result getOneRecord(String tableName,String rowkey)
        throws IOException{
        HTable table = new HTable(this.conf,tableName);
        Get get = new Get(rowkey.getBytes());
        Result rs = table.get(get);
        return rs;
    }

    /**
     * 获取所有记录
     * @param tableName
     * @return
     * @throws IOException
     */
    public List<Result> getAllRecord(String tableName) throws IOException{
        HTable table = new HTable(this.conf,tableName);
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        List<Result> list = new ArrayList<Result>();
        for(Result r:scanner){
            list.add(r);
        }
        scanner.close();
        return list;
    }
}
