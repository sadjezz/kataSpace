package xiaonan.demo.kata;


import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Branchbound {

	int time[][];                               //每个人对于完成每个作业的时间矩阵
	int num;                                    //工人数量，作业数量
	int present[];                              //当前第i个人完成的作业present[i]
	int last[];                                 //最终第i个人完成的作业last[i]
	int visit[];
	int status[];                               //当前作业是否有人完成，0表示没人做，1表示有人做
	int result;                                 //最后完成所有作业的最少时间
	int min;
	int max;
	int a[],b[];
	Node node;
	
	public void readdata(){
		result = 999999;               //最少时间初始化
		String s[]={"first","second","third","fourth","fifth","sixth","seventh","eighth","ninth","tenth"};
		try{
			FileWriter fw = new FileWriter("result.txt");                       //创建一个用于存放输出结果的文档
			for(int m=1;m<6;m++){                                      //一次读取每一个文档中的数据
				Scanner in;
				in = new Scanner(new File("branchbound_input/input_assign04_0" + m + ".dat"));
				num = in.nextInt();                                     //读取文档中的第一个数，即人和作业的个数
				time = new int[num+1][num+1];                           //实例化人和作业对应的时间矩阵
				node = new Node();
				for(int i=1;i<=num;i++){
					for(int j=1;j<=num;j++){
						time[i][j] = in.nextInt();                        //存储文档内的矩阵内容
					}
				}              
				initalization();
				allocaltion();
				
				fw.write("The " + s[m-1] + " data:\n");                 //将每次得到的结果依次输出到文本文档中
				for(int i=1;i<=num;i++){
					fw.write("Person " + i + " do " + last[i] + " job\n");
				}
				fw.write("The shortest time:" + result +"\n");
				fw.write("\n");
				fw.flush();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void initalization(){

		min = Integer.MAX_VALUE;
		max = 0;
		int sum = 0;
		a = new int[num+1];
		b = new int[num+1];
		present = new int[num+1];             //实例化当前人完成作业数组
		last = new int[num+1];                //实例化最终人完成作业数组
		status = new int[num+1];              //实例化作业状态数组
		for(int i=0;i<=num;i++){
			last[i] = 0;
			status[i] = 0;
			present[i] = 0;
			a[i] = Integer.MAX_VALUE;
		}
		for(int i=1;i<=num;i++){
			for(int j=1;j<=num;j++){
				if(a[i] > time[i][j]){
					a[i] = time[i][j];
					b[i] = a[i];
				}
			}
			sum = sum + a[i];
		}
		min = sum;
		sum = 0;
		for(int i=1;i<=num;i++){
			sum = sum + time[i][i];
		}
		max = sum;
	}
	
	public void allocaltion(){
		
		int k,f,lb,m,n,t;
		Node[] PT= new Node[num+1]; 
		Node st = new Node();
		lb = 0;
		k = 1;
		f = 1;
		t = 0;
		m = Integer.MAX_VALUE;
		n = 0;
		while(k >= 1){
			present[k] = 1;
			while(present[k] <= num){
				if(status[present[k]] == 0){
					lb = min - a[k] + time[k][present[k]];
					if(lb <= max){
						PT[f] = new Node();				
						PT[f].pp = t;
						PT[f].person = k;
						PT[f].task = present[k];
						PT[f].lb = lb;
						status[present[k]] = 1;
						f++;
					}
				}
				present[k] = present[k] + 1;
			}
			for(int i=1;i<PT.length;i++){
				if(m > PT[i].lb){
					m = PT[i].lb;
					System.out.println(m);
					st = PT[i];
				}
			}
			for(int i=1;i<PT.length;i++){
				if(PT[i].person == num){
					if(PT[i].lb == m){
						for(int p=num;p==1;p--){
							
						}
					}else if(PT[i].lb != m){
						max = m;
						n = PT.length;
						for(int z=1;z<n;z++){
							if(PT[z].lb > m){
								for(int j=z;j<n-1;j++){
									PT[j] = PT[j + 1];
								}
								n--;
							}
						}
					}
				}
			}
			/*if(k == num && m == PT[PT.length - 1].lb){
				for(int i=1;i<PT.length;i++){
					last[i] = PT[i].task;
				}
				
				break;
			}else if(k == num && m != PT[PT.length - 1].lb){
				max = m;
				n = PT.length;
				for(int i=1;i<n;i++){
					if(PT[i].lb > m){
						for(int j=i;j<n - 1;j++){
							PT[j] = PT[j + 1];
						}
						n--;
					}
				}
			}System.out.println("222");
			for(int i=0;i<=PT.length;i++){
				if(m == PT[i].lb){
					t = PT[i].task;
					k = PT[i].person;
					k++;
				}
			}*/
			t = st.task;
			k = st.person;
			System.out.println(k);
			k++;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Branchbound().readdata();
	}

}

class Node{
	int pp;
	int task;
	int person;
	int lb;
   Node(){
		pp = 0;
		task = 0;
		person = 0;
		lb = 0;
	}
}
