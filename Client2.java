package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

    public class Client2 {

        private Client2() {}

        public static void main(String[] args) {
            long i, responseTime;
            double sum = 0, sum2 = 0, avg, std;
			int n = 100, a, b;
			Random rng = new Random();
			
			int response;
            String host = (args.length < 1) ? null : args[0];
            
			try 
            {
                Registry registry = LocateRegistry.getRegistry(host);
                Hola stub = (Hola) registry.lookup("Hola");
				
				//Prueba suma
				
				for(i=0;i<n;i++)
                {
				  a = rng.nextInt(10000);
				  b = rng.nextInt(10000);
				  
                  responseTime = System.nanoTime();
				  response = stub.suma(a, b);
                  responseTime = System.nanoTime() - responseTime;
				  
				  sum += responseTime;
				  sum2 += Math.pow(responseTime, 2);
				  
				  System.out.println("Resultado: " + a + " + " + b + " = " + response);
				  System.out.println("Tiempo de espera: " + responseTime + "ns");
                }
				
				avg = sum/n;
				std = Math.sqrt((sum2 - n*avg*avg)/(n-1));
				System.out.println("============================");
				System.out.println("Promedio (ms): " + avg/1000000);
				System.out.println("Desviacion Estandar (ms): " + std/1000000);
				System.out.println("============================");
				
            } 
            
			catch (Exception e) 
            {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }
        }
    }

