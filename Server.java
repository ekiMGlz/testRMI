package example.hello;
        
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
        
public class Server implements Hola {
    long cuantos = 0;    
    String strHostname = System.getenv("COMPUTERNAME");
    public Server() {}

    public String sayHello() 
    { 
        cuantos++;
        System.out.println("Proporcionando el servicio no. " + cuantos);
        
		//Delay distr exponencial
		double lam = 1;
		double delay = -1.0/lam * (Math.log(Math.random()));
		//Sacar milis y nanos
		long milis=(long) Math.floor(delay);
		int nanos=(int) Math.floor((delay-milis)*(1e6));
		/*
		long milis = 0;
		int nanos = 0;
		double delay = 0;
		*/
		try{
			Thread.currentThread().sleep(milis, nanos);
			
		} catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
		
		
		return "Servicio no. " + cuantos + " proporcionado desde " + strHostname + ". Espere "+ delay+"ms";
    }
	
	public int suma(int a, int b){
		cuantos++;
        System.out.println("Proporcionando el servicio no. " + cuantos);
        int c = a + b;
		
		//Espera un milisegundo
		try{
			Thread.currentThread().sleep(1);
			
		} catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
		
		return c;
	}
        
    public static void main(String args[]) {
        
        try {
            Server obj = new Server();
            Hola stub = (Hola) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Hola", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}