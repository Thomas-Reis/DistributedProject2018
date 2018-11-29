package client;

import org.zeromq.ZContext;
import shared.Point;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import shared.PointGroup;

public class Client {
    public int weight = 1;
    private static ArrayList<Point> AssignedPoints = new ArrayList<>();
    private static ArrayList<Point> Centroids = new ArrayList<>();
    static String SERVERIP = "127.0.0.1";
    private static int client_uid;
    static int PORT = 5555;
    private static ZMQ.Socket client_sub;
    private static ZMQ.Socket client_req;
    private static ZMQ.Context zmq_context;

    public static void main(String[] args) throws InterruptedException {
        zmq_context = ZMQ.context(3);
        connect_to_socket(SERVERIP);

        /*
        try {
            int num_points = Integer.parseInt(r.readLine());
            System.out.println(num_points);
            for (int i=0;i<num_points;i++){
                Point tmp_point = (Point)Obj_r.readObject();
                AssignedPoints.add(tmp_point);
            }
        } catch (Exception e){
            e.printStackTrace();
            System.exit(-100);
        }*/

        //NEW CODE
        while(true){
            while(AssignedPoints.size() > 0){
                AssignedPoints.clear();
                System.out.println("Received Points and Cleared Them");
            }
            Thread.sleep(20);
        }
    }

    public static void connect_to_socket(String ip){
        try {
            client_req = zmq_context.socket(SocketType.REQ);
            client_req.connect("tcp://" + ip + ":10001");
            String tmp_msg = "-1 JOIN";
            client_req.send(tmp_msg.getBytes(ZMQ.CHARSET), 0);
            byte[] uid_rep = client_req.recv();
            String uid_rep_string = new String(uid_rep, ZMQ.CHARSET);
            String[] reply_msg = uid_rep_string.split(" ");
            if(reply_msg[1].equals("GOOD")) {
                client_uid = Integer.parseInt(reply_msg[0]);
            }

            client_sub = zmq_context.socket(SocketType.SUB);
            client_sub.connect("tcp://" + ip + ":10010");
            client_sub.subscribe("BROADCAST");
            client_sub.subscribe(reply_msg[0]);



        } catch (Exception e){
            e.printStackTrace();
            System.exit(-200);
        }
    }

    public static void send_update(ArrayList<Double> sum_per_centroid, ArrayList<Integer> points_per_centroid){
        //TODO update the sums and number of points per centroid to the server somehow
    }

    public static void ReceiveCentroids(ArrayList<Point> updated_centroids){
        Centroids = updated_centroids;
    }

    //REDUNDANT METHOD
    public static void ReceivePoints(ArrayList<Point> GivenPoints){
        //Adds all points sent from the server to the client's AssignedPoints list
        AssignedPoints.addAll(GivenPoints);
    }
}