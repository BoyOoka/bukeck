import redis.clients.jedis.Jedis;

import java.util.ArrayList;

public class jedisShow {
    public static void main(String[] args){
//        Jedis jedis = new Jedis("localhost", 6379);
//        String data = jedis.srandmember("proxy:proxy_info");
//        System.out.println(data);
//        jedis.close();
        Integer args1[]= {1,2,3,4,5};
        for (Integer b: args1){
            System.out.println(b);

        }
    }
}
