import java.util.Scanner;
class Robinout{
    int conT,serT,pno,waiT;
    String name;
    static int arrArrT[];
    void printout(Robinout out[],int no){
        System.out.println("Name"+"\t"+"Cont"+"\t"+"serT"+"\t"+"Pno"+"\t"+"WaitT");
       
        for(int i=0;i<no;i++){
            System.out.println(out[i].name+"\t"+out[i].conT+"\t"+out[i].serT+"\t"+out[i].pno+"\t"+out[i].waiT);
            
        }
    }
    void times(Robinout out[],int no,int n){
        int i=0,j;
        //calculating wait times for each process






        // while(i<n){
        //     for(j=0;j<no;j++){
        //         if((out[i].pno==out[j].pno)&&(j>i)){
                    
        //         }
        //     }
        // }
    }
}




class Robin {
    private int burT,arrT;
    static int t;
    String name;
    void setter(Scanner s,Robin in[],int n){
        int i;
    
        for(i=0;i<n;i++){
            in[i] = new Robin();//to avoid null pointer exception
            System.out.print("Enter the name of the Process ");
            System.out.print(i+1);
            System.out.print(" :");
            in[i].name = s.next();
            System.out.print("Enter the arrival time of " + in[i].name+ " :");
            in[i].arrT = s.nextInt();
            while (in[i].arrT < 0) {
                System.out.println("Please enter the correct value of arrival time !!: ");
                in[i].arrT = s.nextInt();
            }
            System.out.print("Enter the burst time of " + in[i].name+ " :");
            in[i].burT = s.nextInt();
            while (in[i].burT < 0) {
                System.out.println("Please enter the correct value of burst time !!: ");
                in[i].burT = s.nextInt();
            }
            System.out.print("\n\n");
            
        }
        
        System.out.print("Enter the time slot for running each process :");
        t = s.nextInt();
    
    }
    // object calculator 
    // need to work more on this method d
    int noOfSlabs(Robin in[],int n){
        int i,totBur=0,slabs;
        for(i=0; i<n;i++){
            totBur = in[i].burT + totBur;
        }
        if(totBur%t==0){
            slabs = totBur/t;
        }
        else{
            slabs = (totBur/t)+1;
        }
        return slabs;
    }
    void delete(Robin in[],int i,int n){
        int j;
        if(n>1){
            for (j = i; j < n - 1; j++) {
                in[j] = in[j + 1];
            }
        }
        return;
        
    }
    int schedule(Robin in[],Robinout out[],int n, int no){
        int i,j,l=0;
        Robin temp;
        //in this scheduling i have used waitT , burT and taT for input array while serT and arrT for output array of objects
        //arranging process according to arrival times
        
        for(i=0; i<n; i++){
            for(j=0;j<n-1;j++){
                if (in[j].arrT > in[j + 1].arrT)
			    {
				    temp = in[j];
				    in[j] = in[j + 1];
				    in[j + 1] = temp;
			    }
            }
        }
        System.out.println("Original");
        print(in,n);
        // real scheduling happens here
        // better to update tat and wait time side by side
        // for first process
        out[0] = new Robinout();
        out[0].waiT = 0;
        if(in[0].burT>t){
            
            in[0].burT = in[0].burT -t;
            out[0].name=in[0].name;
            
            // in[0].waitT =0;
            out[0].conT = in[0].arrT;
            System.out.println("out[0].conT = "+ out[0].conT);
            out[0].serT = t + out[0].conT;
            out[0].pno = 0;//relate it to process no. 0 in output
            //implement taT
        }
        else if((in[0].burT<=t)&&(in[0].burT>0)){
            out[0].name = in[0].name;
            
            out[0].conT = in[0].arrT;
            out[0].serT = in[0].burT+out[0].conT;
            // in[0].waitT= 0;
            out[0].pno = 0;//relate it to process no. 0 in output
             //implement taT
            //deleting that object from input array as process is now over
            delete(in,i,n);
            n--;
        }
        else if(in[0].burT==0){
            out[0].name = in[0].name;
            
            out[0].conT = in[0].arrT;
            out[0].serT = 0+out[0].conT;
            // in[0].waitT= 0;
            out[0].pno = 0;//relate it to process no. 0 in output
             //implement taT
            //deleting that object from input array as process is now over
            delete(in, i, n);
            n--;
        }
        System.out.println("Stage 0:");
        print(in,n);
        while(n>0){
            
            myloop:
            for(i=0;i<n;i++){
                //process the process according to time lapse
                
                if(l==0){
                    l++;
                    continue myloop;
                }
                out[l] = new Robinout();
                if((in[i].burT>t)&&(in[i].arrT<=out[l-1].serT)){
                    System.out.println(l);
                    in[i].burT = in[i].burT- t;
                    out[l].name = in[i].name;
                    out[l].pno = i;
                
                    out[l].conT= out[l-1].serT;
                    out[l].serT = out[l].conT + t;
                    l++;
                    
                }
                else if((in[i].burT<=t)&&(in[i].burT>0)&&(in[i].arrT<=out[l-1].serT)){
                    System.out.println(l);
                    out[l].name = in[i].name;
                    out[l].pno = i;
                    out[l].conT= out[l-1].serT;
                    out[l].serT = out[l].conT + in[i].burT;
                    
                    delete(in, i, n);
                    n--;
                    l++;
                    
                }
                else if(in[i].burT==0&&(in[i].arrT<=out[l-1].serT)){  // if burst time of any process is over then n--
                    System.out.println(l);
                    out[l].name = in[i].name;
                    out[l].pno = i;
                    out[l].conT= out[l-1].serT;
                    out[l].serT = out[l].conT + in[i].burT;
                    
                    delete(in, i, n);
                    n--;
                    l++;
                }
                else if(out[l-1].serT<in[i].arrT){
                    i=-1;
                    continue myloop;
                }
                
                System.out.println("Stage "+l+":");
                System.out.println("out[0].conT = "+ out[0].conT);
                print(in,n);
                
            }
        }
        return l;
     }
    void print(Robin in[],int no){
        System.out.println("Name"+"\t"+"Arrival"+"\t"+"Burst");
       
        for(int i=0;i<no;i++){
            System.out.println(in[i].name+"\t"+in[i].arrT+"\t"+in[i].burT);
            
        }
    }
    
    public static void main(String as[]){
        int n,no=20,totTat,totwaiting;
        float avgWait,avgTat;
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the no. of processes :");
        n = s.nextInt();
        Robin in[]= new Robin[n];// this is just array of references without any object
        Robin master = new Robin();
        Robinout masterout = new Robinout();
        //transfer arrival times to output array
        
        master.setter(s,in,n);
        no = master.noOfSlabs(in, n);//need to rectified this method
        Robinout out[] = new Robinout[no];// this array contains the output

        master.schedule(in,out,n,no);
        masterout.printout(out,no);
        


    }
}