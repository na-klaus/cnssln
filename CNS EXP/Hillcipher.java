import java.util.*;
import java.io.*;
public class Hillcipher
{
public int keyinverse[][] = new int[3][3];
public int key[][] = { {17, 17, 5}, {21, 18, 21}, {2, 2, 19} };
public int plainmat[][] = new int[8][3];
public int ciphermat[][] = new int[8][3];
public String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
String plain,cipher;
int row, flag=0, decrypt=0;
public void matdisplay(int mat[][])
{
int i, j;
for(i=0;i<row;i++)
{
for(j=0;j<3;j++)
System.out.print(mat[i][j] + " ");
System.out.println();
}
}
public void keydisplay(int mat[][])
{
int i, j;
for(i=0;i<3;i++)
{
for(j=0;j<3;j++)
System.out.print(mat[i][j] + " ");
System.out.println();
}
}
public void inverse()
{
int dtrmnt = 0;
int mulinvdtrmnt = 0;
int x, y, z, i, j, a, tmp, p, q;
int transkey[][] = new int[3][3];
int minormat[][] = new int[3][3];
int temp[][] = new int[2][2];
System.out.println("HILL CIPHER KEY");
keydisplay(key);
x = key[0][0] * (( key[1][1] * key[2][2]) - (key[1][2] * key[2][1]));
y = key[0][1] * (( key[1][0] * key[2][2]) - (key[1][2] * key[2][0]));
z = key[0][2] * (( key[1][0] * key[2][1]) - (key[1][1] * key[2][0]));
dtrmnt = (x-y+z)%26;
if ( dtrmnt < 0 )
dtrmnt = dtrmnt + 26;
System.out.println("DETERMINANT :" + dtrmnt);
a = dtrmnt;
for(i=0;i<25;i++)
{
tmp = ( a * i ) % 26;
if ( tmp == 1 )
{
mulinvdtrmnt = i;
break;
}
}
System.out.println("MULTIPLICATIVE INVERSE OF DETERMINANT:" +
mulinvdtrmnt);
for(i=0;i<3;i++)
{
for(j=0;j<3;j++)
transkey[i][j] = key[j][i];
}
// keydisplay(transkey);
for(i=0;i<3;i++)
{
for(j=0;j<3;j++)
{
p=0;
q=0;
for(x=0;x<3;x++)
{
for(y=0;y<3;y++)
{
if ((x!=i) && (y!=j))
{
temp[p][q] = transkey[x][y];
q++;
if ( q == 2)
{
q=0;
p++;
}
}
}
}
minormat[i][j]=(temp[0][0]*temp[1][1])-(temp[0][1]*temp[1][0]);
minormat[i][j] = minormat[i][j] * (int)Math.pow(-1,(i+j));
}
}
for(i=0;i<3;i++)
{
for(j=0;j<3;j++)
{
keyinverse[i][j]=(mulinvdtrmnt * minormat[i][j])%26;
if (keyinverse[i][j] < 0)
keyinverse[i][j] = keyinverse[i][j] + 26;
}
}
System.out.println("KEY INVERSE");
keydisplay(keyinverse);
} 
public void str2matrix(String text)
{
int k, p, n;
if ((text.length() % 3) == 1)
{
n=text.length();
text += 'X';
text += 'X';
flag = 2;
}
if ((text.length() % 3) == 2)
{
text += 'X';
flag = 1;
}
row = (text.length()) / 3;
k = 0;
for(int i=0; i<row; i++)
{
for(int j=0;j<3;j++)
{
for (p=0;p<26;p++)
{
if (text.charAt(k) == ALPHABET.charAt(p))
{
plainmat[i][j] = p;
k++;
break;
}
}
}
}
// System.out.println("PLAIN TEXT MATRIX");
matdisplay(plainmat);
System.out.println();
}
public String matrix2str(int mat[][])
{
int i, j, k;
String txt="";
String tmp="";
for(i=0;i<row;i++)
{
for(j=0;j<3;j++)
{
k = mat[i][j];
txt += ALPHABET.charAt(k);
}
}
if (decrypt == 1)
{
if ( flag == 1 )
{
tmp = txt.substring(0, (txt.length()-1));
return tmp;
}
if ( flag == 2 )
{
tmp = txt.substring(0, (txt.length()-2));
return tmp;
}
}
return txt;
}
public String hcencryption(String ptxt)
{
int i,j,k;
int sum=0;
String ctxt="";
decrypt=0;
System.out.println("HILL CIPHER ENCRYPTION");
System.out.println("PLAIN TEXT MATRIX");
str2matrix(ptxt);
for(i=0;i<row;i++)
{
for(j=0;j<3;j++)
{
for(k=0;k<3;k++)
sum += plainmat[i][k] * key[k][j];
ciphermat[i][j] = sum % 26;
sum = 0;
}
}
System.out.println("CIPHER TEXT MATRIX");
matdisplay(ciphermat);
ctxt = matrix2str(ciphermat);
return ctxt;
}
public String hcdecryption(String ctxt)
{
int i,j,k;
int sum=0;
String ptxt="";
decrypt=1;
System.out.println("HILL CIPHER DECRYPTION");
System.out.println("CIPHER TEXT MATRIX");
str2matrix(ctxt);
for(i=0;i<row;i++)
{
for(j=0;j<3;j++)
{
for(k=0;k<3;k++)
sum += ciphermat[i][k] * keyinverse[k][j];
plainmat[i][j] = sum % 26;
sum = 0;
}
}
System.out.println("PLAIN TEXT MATRIX");
matdisplay(plainmat);
ptxt = matrix2str(plainmat);
return ptxt;
}
public static void main(String[] args)
{
Hillcipher hc = new Hillcipher();
Scanner sc = new Scanner(System.in);
hc.inverse();
String ptext = new String();
System.out.println("Enter PLAIN TEXT");
ptext = sc.next();
String ctext = new String();
ctext = hc.hcencryption(ptext);
System.out.println();
System.out.println("CIPHER TEXT :" + ctext);
System.out.println();
String plaintext = new String();
plaintext = hc.hcdecryption(ctext);
System.out.println();
System.out.println("PLAIN TEXT :" + plaintext);
sc.close();
}
}
//zyda word input dena
