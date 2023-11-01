import java.util.*;
import java.io.*;
public class Playfair
{
private char pfmatrix[][] = new char[5][5];
public String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
String plain,cipher;
int jflg=0,xpad=0;
int row,col;
public void matrixgen(String key)
{
char keychar;
int count=0;
int alphacount=0;
int p,k,flg=1;
for(int i=0; i<5; i++)
{
for(int j=0;j<5;j++)
{
if (count<key.length())
{
keychar=key.charAt(count);
if (keychar == 'J')
keychar='I';
p=0;
while (p<count)
{
flg=1;
if (keychar==key.charAt(p))
{
count++;
if (count == key.length())
{
flg=0;
break;
}
keychar=key.charAt(count);
p=0;
}
else
p++;
}
if (flg!=0)
{
pfmatrix[i][j]=keychar;
count++;
}
if ((count==key.length()) && (flg==0))
{
if(alphacount<26)
{
keychar=ALPHABET.charAt(alphacount);
k=0;
while (k<key.length())
{
if ((keychar==key.charAt(k)) || (keychar=='J'))
{
alphacount++;
keychar=ALPHABET.charAt(alphacount);
k=0;
}
else
k++;
}
//if (keychar!='J' && k==key.length())
pfmatrix[i][j]=keychar;
alphacount++;
}
}
}
else
{
if(alphacount<26)
{
keychar=ALPHABET.charAt(alphacount);
k=0;
while (k<key.length())
{
if ((keychar==key.charAt(k)) || (keychar=='J'))
{
alphacount++;
keychar=ALPHABET.charAt(alphacount);
k=0;
}
else
k++;
}
pfmatrix[i][j]=keychar;
alphacount++;
}
}
}
}
}
public void matrixdisplay()
{
for(int i=0;i<5;i++)
{
for(int j=0;j<5;j++)
System.out.print(pfmatrix[i][j] + " ");
System.out.println();
}
}
public String pfencryption(String txt)
{
int i,j,k;
int ch1row,ch2row,ch1col,ch2col;
char ch1,ch2,tmp1,tmp2;
String nutext="";
String text="";
i=0;
while (i<(txt.length()-1))
{
text += txt.charAt(i);
if (txt.charAt(i) == txt.charAt(i+1))
{
text += 'X';
xpad++;
}
i++;
}
text += txt.charAt(txt.length()-1);
System.out.println("TEXT : " + text);
if (text.length()%2 != 0)
{
text += 'X';
xpad++;
}
System.out.println("FINAL TEXT : "+ text);
for(k=0;k<text.length();k=k+2)
{
ch1=text.charAt(k);
ch2=text.charAt(k+1);
System.out.println("CHARACTER PAIR :" + ch1 +" " + ch2);
matsearch(ch1);
ch1row=row;
ch1col=col;
matsearch(ch2);
ch2row=row;
ch2col=col;
// System.out.println("ch1row:" + ch1row + "ch1col:" + ch1col);
// System.out.println("ch2row:" + ch2row + "ch2col:" + ch2col);
if (ch1row==ch2row)
{
tmp1=pfmatrix[ch1row][(ch1col+1)%5];
tmp2=pfmatrix[ch2row][(ch2col+1)%5];
}
else if (ch1col==ch2col)
{
tmp1=pfmatrix[(ch1row+1)%5][ch1col];
tmp2=pfmatrix[(ch2row+1)%5][ch2col];
}
else
{
tmp1=pfmatrix[ch1row][ch2col];
tmp2=pfmatrix[ch2row][ch1col];
}
nutext += tmp1;
nutext += tmp2;
System.out.println("TRANSLATED TEXT :" + tmp1 + " " + tmp2);
}
return nutext;
}
public String pfdecryption(String text)
{
int i,j,k;
int ch1row,ch2row,ch1col,ch2col;
char ch1,ch2,tmp1,tmp2;
String nutext="";
String txt="";
for(k=0;k<text.length();k=k+2)
{
ch1=text.charAt(k);
ch2=text.charAt(k+1);
System.out.println("CHARACTER PAIR :" + ch1 +" " + ch2);
matsearch(ch1);
ch1row=row;
ch1col=col;
matsearch(ch2);
ch2row=row;
ch2col=col;
// System.out.println("ch1row:" + ch1row + "ch1col:" + ch1col);
// System.out.println("ch2row:" + ch2row + "ch2col:" + ch2col);
if (ch1row==ch2row)
{ int c1,c2;
c1 = ch1col-1;
if (c1<0)
c1 = c1+5;
c2 = ch2col-1;
if (c2<0)
c2=c2+5;
tmp1=pfmatrix[ch1row][c1];
tmp2=pfmatrix[ch2row][c2];
}
else if (ch1col==ch2col)
{
int r1,r2;
r1=ch1row-1;
r2=ch2row-1;
if (r1<0)
r1=r1+5;
if (r2<0)
r2=r2+5;
tmp1=pfmatrix[r1][ch1col];
tmp2=pfmatrix[r2][ch2col];
}
else
{
tmp1=pfmatrix[ch1row][ch2col];
tmp2=pfmatrix[ch2row][ch1col];
}
nutext += tmp1;
nutext += tmp2;
System.out.println("TRANSLATED TEXT :" + tmp1 + " " + tmp2);
}
if (xpad != 0)
{
i=0;
while (i<nutext.length())
{
if (nutext.charAt(i) == 'X')
{
i++;
continue;
}
txt += nutext.charAt(i);
i++;
}
System.out.println("TEXT :" + txt);
return txt;
}
else
{
System.out.println("TEXT : " + nutext);
return nutext;
}
}
public void matsearch(char ch)
{
int i,j;
if (ch=='J')
{
ch='I';
jflg=1;
}
for(i=0;i<5;i++)
{
for(j=0;j<5;j++)
{
if (pfmatrix[i][j] == ch)
{
row=i;
col=j;
}
}
}
}
public static void main(String[] args)
{
Playfair pf = new Playfair();
Scanner sc = new Scanner(System.in);
System.out.println("Enter the PLAYFAIR KEY: ");
String pfkey = new String();
pfkey = sc.next();
System.out.println("PLAYFAIR MATRIX");
pf.matrixgen(pfkey);
pf.matrixdisplay();
String ptext = new String();
System.out.println("Enter PLAIN TEXT");
ptext = sc.next();
String ctext = new String();
ctext = pf.pfencryption(ptext);
System.out.println();
System.out.println("CIPHER TEXT :" + ctext);
System.out.println();
String plaintext = new String();
plaintext = pf.pfdecryption(ctext);
System.out.println();
System.out.println("PLAIN TEXT :" + plaintext);
sc.close();
} }