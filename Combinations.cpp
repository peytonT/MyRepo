/* 
 * 
 * Author: peytonT
 *
 * Created on October 30, 2014, 12:17 PM
 */

#include <iostream>

using namespace std;


int factorial(int n)
{
  return (n == 1 || n == 0) ? 1 : factorial(n - 1) * n;
}

int combination(int n, int r)
{
    
    return factorial(n)/ (factorial(r) * factorial(n-r));
}

int generateCombinations(int n, int r)
{
    
    int s[10];
     
    for(int i=0; i<r; i++)
    {
      
        s[i] = i+1;
        cout << s[i];
    }
    cout <<"\t";
  
    int m, max_val;
       
    for (int i=1; i<combination(n,r); i++)
    {
        m=r;
        max_val=n;
        
        while (s[m-1] == max_val)
        {
           
            m -= 1;
            max_val--;
        }
       
        s[m-1] = s[m-1]+1;
        
        for(int j=m; j<r;j++)
        {
            s[j] = s[j-1]+1;
            
        }
        
        for(int i=0; i<r; i++)
        {
            cout<< s[i];
        }
        cout <<"\t";
              
    }
     
    return 0;
}

int main() {

    int r;
    int n;
     
    cout <<"*** This program lists all the r-combinations of {1,2,...,n} in increasing lexicographic order ***\n";
    cout << "Input n: ";
    cin >> n;
    
    cout <<"Input r: ";
    cin >> r;
    cout <<"\n";
    generateCombinations(n,r);
    
    return 0;
}

