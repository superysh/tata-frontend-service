package rml.util; /**
 * Created by Administrator on 2015/9/29.
 */
import redis.clients.jedis.Jedis;


public class JedisConn {
    static String constr = "222.73.17.5" ;
    public static Jedis getRedis(){
        Jedis jedis = new Jedis(constr) ;
        return jedis ;
    }
    public static void main(String[] args){
        Jedis j = JedisConn. getRedis() ;
        System. out.println( j.lrange("16 125",0,-1)) ;
    }
}
