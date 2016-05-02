package com.techno_twit.harshal.pharmahelp;

/**
 * Created by kaavi_000 on 01-05-2016.
 */
public class Connectivity {

    static String ip="192.168.43.21";
        static int port=8080;

        public static String getIpPort(){
            return ip+":"+String.valueOf(port);
        }
        public static String getIp(){
            return ip;
        }
        public static int getPort() {
            return port;
        }
}
