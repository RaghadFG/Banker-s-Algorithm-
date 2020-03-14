import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BankersAlgorithm {
    public static void main(String[] args){
        try{ 
            Scanner d = new Scanner(System.in);
            Scanner s = new Scanner(new File("SampleInput.txt"));
            int n = s.nextInt();
            int m = s.nextInt();
            int [][] allocation = new int[n][m];
            int [][] max = new int[n][m];
            int [] available = new int[m];
            int [] a = new int[m];
            int [][] need = new int[n][m];
            
            System.out.println("There are " + n + " processes in the system.");
            System.out.println();
            System.out.println("There are " + m + " resource type.");
            System.out.println();
            
            for (int i=0; i < n; i++)
                for (int j=0; j < m; j++)
                    allocation[i][j] = s.nextInt(); 
            
            for (int i=0; i < n; i++)
                for (int j=0; j < m; j++)
                    max[i][j] = s.nextInt(); 
             
            for (int i=0; i < m; i++){
                available[i] = s.nextInt(); 
                a[i] = available[i];
            }
            
            for (int i=0; i < n; i++)
                for (int j=0; j < m; j++)
                    need[i][j] = max[i][j] - allocation[i][j];
            
            
            print2D(allocation, "Allocation", n,m);
            print2D(max, "Max", n,m);
            print2D(need, "Need", n,m);
            print1D(available, m);
            
            if (bankersAlghorithm(allocation, need, available, n, m)){
                System.out.println("THE SYSTEM IS IN A SAVE STATE :)");
                System.out.println();
                System.out.println("Is there any request ?");
                String r = d.nextLine();
                System.out.println();
                request(allocation, need, a,r, n, m);
            } 
            else
                System.out.println("THE SYSTEM IS IN AN UNSAVE STATE :(");
                    
        } catch(FileNotFoundException e){
            System.out.print(e.getMessage());
        }   
    }
    
   
    public static boolean bankersAlghorithm(int [][] allocation, int [][] need, 
    int[] available, int n, int m){
        
        boolean [] finish = new boolean[n];
        int [] work = available;
        int count =0;
        boolean flag;
        do {
            flag = false;
            outer:  for (int i=0; i < n; i++){
                        if (finish[i] == false){
                            for (int j=0; j < m; j++){
                                if (need[i][j] > work[j])
                                    continue outer;       
 
                            for (int k=0; k < m; k++)
                                work[k] = work[k] + allocation[i][k];
                            finish[i] = true;
                            count++;
                            flag = true;
                            }
                        }
                    }
            if (flag == false)
                break;
        } while (count < n);
        
        if (count < n)
            return false;
        else
            return true;  
    }  
    
    
    public static void request(int [][] allocation, int [][] need, int[] available, 
    String r, int n, int m){
        
        int [] newAvailable = new int[m];
        int p = Character.getNumericValue((r.charAt(0)));
        boolean flag = false;
        int request [] = new int[m];
        int j = 0;
        
        for (int i=3; i < r.length(); i++)
            if (r.charAt(i) != ' ')
               request[j++] = Character.getNumericValue(r.charAt(i));
         
        for (int i=0; i < m; i++)
            if (request[i] <= need[p][i] && request[i] <= available[i])
                flag=true;
            else {  
                flag=false;
                break;
            }
        if (flag){
            for (int i=0; i < m; i++){
                newAvailable[i]=available[i] - request[i];
                available[i] = available[i] - request[i];

                need[p][i] = need[p][i] - request[i];
                allocation[p][i] = allocation[p][i] + request[i];
            }
            
            if (bankersAlghorithm(allocation, need, available, n, m)){
                System.out.println("THE REQUESR CAN BE GRANTED!");
                System.out.println();
                print1D(newAvailable,m);
            }       
        } else {
            System.out.println();
            System.out.println("THE REQUESR CAN NOT BE GRANTED!"); 
        }       
    }
    
    
      public static void print1D(int[] matrix, int m){
          
        System.out.println("The Available Vector is...");
        char c = 'A';
        
        for (int i=0; i < m; i++)
            System.out.print(c++ + " ");
        System.out.println();
        
        for (int i=0; i < m; i++)
            System.out.print(matrix[i] + " ");
        System.out.println();
        System.out.println();
    }
    
      
    public static void print2D(int[][] matrix, String s, int n, int m){
        
        System.out.println("The " + s + " Matrix is...");
        System.out.print("   ");
        char c = 'A';
        
        for (int i=0; i < m; i++)
            System.out.print(c++ + " ");
        
        System.out.println();
        
        for (int i=0; i < n; i++){
            System.out.print(i + ": ");
            for (int j=0; j < m; j++)
                    System.out.print(matrix[i][j] + " ");  
            System.out.println();
        }
        System.out.println();
    }          
}




