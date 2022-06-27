import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import static org.apache.hadoop.hbase.trace.SpanReceiverHost.getConfiguration;

public class hbaseShow {
    public static void main(String[] args) throws IOException {

        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.property.clientPort", "2181");
        config.set("hbase.zookeeper.quorum", "localhost");
//        config.set("zookeeper.znode.parent", "/hbase-unsecure");

        TableName table1 = TableName.valueOf("users");
        String family1 = "Family1";
        String family2 = "Family2";
        System.out.println(table1);
        Connection connection = ConnectionFactory.createConnection(config);
        System.out.println(connection.getConfiguration());

//        Admin admin = connection.getAdmin();
//        HTableDescriptor desc = new HTableDescriptor(table1);
//        desc.addFamily(new HColumnDescriptor(family1));
//        desc.addFamily(new HColumnDescriptor(family2));
//        admin.createTable(desc);
//        System.out.println(table1);
    }
}
