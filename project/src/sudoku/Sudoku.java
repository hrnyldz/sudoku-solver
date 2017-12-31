package sudoku;
import java.io.*;
import java.util.Scanner;
import java.applet.* ;
import java.awt.* ;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Sudoku  extends Applet implements Runnable {

   
class Sayi {
    public int deger;
    public boolean doluluk;
    public Sayi() {
        deger = 0;
        doluluk = false;
    }
}
public int boyut = 9;
public int hucresayi = (int) Math.sqrt(boyut);
protected  Sayi[][] sudoku_dizi = new Sayi[boyut][boyut];
@Override
 public void start()
   {
      (new Thread(this)).start() ;


   }

@Override
public void run()
{
   try
   {
      Thread.sleep( 10) ;
      Cozum() ;
   }
   catch( Exception e )
   {
   }
}
protected TextField text_dizi[][] ;
   protected void model_olustur() throws Exception
{
 Sudoku a = new Sudoku();
 a.DosyadanCekme("sudoku.txt");
    }
   
    
   protected void Guncelleme_Yap()
   {       String strn="\n" ;
    for (int i = 0; i < 9; i++){
    for (int j = 0; j < 9; j++) {
    strn +=""+sudoku_dizi[i][j].deger;
    }
    strn+=""+"\n";
    } 
       Texte_Yazdir(strn);
      for( int i = 0; i < 9; i++ ){
        for( int j = 0; j < 9; j++ ){
             if(sudoku_dizi[i][j].deger != 0){
                text_dizi[i][j].setText(String.valueOf(sudoku_dizi[i][j].deger) ) ;

            }
            else
               text_dizi[i][j].setText("" ) ;
            
        }
      }

            

   }
   public void init() 
   {
    
  
    try {
        DosyadanCekme("sudoku.txt");
        model_olustur() ;
        
        
        model_goruntusu();
        Guncelleme_Yap() ;
    } catch (Exception ex) {
        Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
    }
      
   }



private int Index_al(int i, int sayi) {
        if (i < 0) {
            Yazdir();
            System.exit(1);
    }
    for (int j = 0; j < boyut; j++) {
        if (sudoku_dizi[i][j].deger == sayi && sudoku_dizi[i][j].doluluk) {
                return -2;
        }
        if (sudoku_dizi[i][j].deger == sayi && !sudoku_dizi[i][j].doluluk) {
                return j;
        }
    }
    return -1;
}

    private static void Texte_Yazdir(String metin){
    try{
          File dosya = new File("adimlar.txt");
          FileWriter yazici = new FileWriter(dosya,true);
          BufferedWriter yaz = new BufferedWriter(yazici);
          yaz.write(metin);
          yaz.close();
    }
    catch (Exception hata){
          hata.printStackTrace();
    }
      }
private boolean Yer_Tespit(int i, int j, int sayi) {
     if (i >= boyut) {
        Yazdir();
        System.exit(0);
    }
    if (sudoku_dizi[i][j].doluluk && sudoku_dizi[i][j].deger == sayi) {
            return true;
    }
    for (int k = 0; k < boyut; k++) {
            if (sudoku_dizi[i][j].deger != 0|| (sudoku_dizi[i][k].deger == sayi || sudoku_dizi[k][j].deger == sayi)|| !Kontrol_3X3(i, j, sayi)) {
                    return false;
            }
    }
    return true;
    }

public void DosyadanCekme(String dosyaadi) throws Exception {
    File dosya = new File(dosyaadi);
    Scanner giris=new Scanner(dosya);
    char dizi [][]=new char[9][9];
    int sayac=0;
    while (giris.hasNext()) {
    dizi[sayac]=giris.next().toCharArray();
    sayac++;
    }

    giris.close();
    for (int i = 0; i < 9; i++)
    for (int j = 0; j < 9; j++)
        if(dizi[i][j]=='*')
            dizi[i][j] = '0';


    try{

    File file = new File(dosyaadi);
    file.delete();
    }catch(Exception e){
    e.printStackTrace();
    }
    String str="" ;
    for (int i = 0; i < 9; i++){
    for (int j = 0; j < dizi.length; j++) {
    str +=""+dizi[i][j];
    }
    str+=""+"\n";
    }
    File file2 = new File("sudoku.txt");
    if (!file2.exists()) {
    file2.createNewFile();
    }
    FileWriter fileWriter = new FileWriter(file2, false);
    BufferedWriter bWriter = new BufferedWriter(fileWriter);
    bWriter.write(str);
    bWriter.close();
    File file = new File(dosyaadi);
    if (!file.exists())
            throw new Exception(dosyaadi + " Bulunamadi.");
    BufferedReader br = new BufferedReader(new FileReader(file));

    int i = 0;
    String satir;

    while ((satir = br.readLine()) != null) {
        String[] satirlar = satir.split("");
        for (int j = 0; j < satirlar.length; j++) {
            int deger;
            boolean doluluk_durumu = false;
            deger = Integer.parseInt(satirlar[j]);
            if (deger != 0) {
                    doluluk_durumu = true;
            }
            sudoku_dizi[i][j] = new Sayi();

            sudoku_dizi[i][j].deger = deger;
            sudoku_dizi[i][j].doluluk = doluluk_durumu;
        }
        i++;
    }
    br.close();
}

private void Yazdir() {
    Guncelleme_Yap();
        String gecici = new String();
        for (int k = 0; k < boyut; k++) {
                for (int l = 0; l < boyut; l++) {
                        gecici += sudoku_dizi[k][l].deger + " ";
                }
                gecici += "\n";
        }
        System.out.println(gecici);
           try{
          File dosya = new File("sonuc.txt");
          FileWriter yazici = new FileWriter(dosya,true);
          BufferedWriter yaz = new BufferedWriter(yazici);
          yaz.write(gecici);
          yaz.close();
    }
    catch (Exception hata){
          hata.printStackTrace();
    }
}

private boolean Kontrol_3X3(int i, int j, int n) {
    for (int k = (i /hucresayi ) * hucresayi; k < ((i + hucresayi) / hucresayi)* hucresayi; k++) {
            for (int l = (j / hucresayi) * hucresayi; l < ((j + hucresayi) / hucresayi)* hucresayi; l++) {
                    if (n == sudoku_dizi[k][l].deger) {
                            return false;
                    }
            }
    }
        return true;
}

 protected void model_goruntusu()
   {
      setLayout( new GridLayout(9,9) ) ;
      text_dizi = new TextField[9][9] ;
      for( int i = 0; i < 9; i++ )
         for( int j = 0; j < 9; j++ )
         {
            text_dizi[i][j]  = new TextField() ;
            add( text_dizi[i][j] ) ;
            text_dizi[i][j].setBackground(Color.black);
            text_dizi[i][j].setForeground(Color.red);
 
         }
   }
public void Cozum() {
    int k = 0, l = 0;
    while (k > -1) {
        int sayi = 1;
        l = 0;
        while (sayi > 0 && sayi <= boyut) {
            while (l> -1 && l < boyut) {
                if (sayi > boyut)
                        break;
                if (Yer_Tespit(k, l, sayi)) {
                        sudoku_dizi[k][l].deger = sayi;
                        Guncelleme_Yap();
//double p=0;
//while(p<10000)
//    p=p+0.05;
                        sayi++;
                        l = 0;
                } else {
                        l++;
                }
            }
            if (sayi == boyut + 1) {
                    k++;
                    sayi = 1;
                    l = 0;
            } else {
                    if (sayi > 1) {
                            sayi--;
                    } else {
                            l = Index_al(k, sayi);
                            if (l != -2 && l != -1) {
                                sudoku_dizi[k][l].deger = 0;
                                Guncelleme_Yap();                                    
//            double p=0;
//while(p<10000)
//    p=p+0.05;
                                l++;
                            }
                            k--;
                            sayi = boyut;
                    }
                    while (true) {
                            l = Index_al(k, sayi);
                            if (l != -2) {
                                    sudoku_dizi[k][l].deger = 0;
                                    Guncelleme_Yap();  
//double p=0;
//while(p<10000)
//    p=p+0.05;
                                
                                    l++;
                                    break;
                            } else {
                                    if (sayi == 1) {
                                            l = Index_al(k, sayi);
                                            if (l != -2) {
                                                sudoku_dizi[k][l].deger = 0;
                                                Guncelleme_Yap();
//    double p=0;
//while(p<10000)
//    p=p+0.05;
 
                                                    l++;
                                            }
                                            sayi = boyut;
                                            k--;
                                    } else {
                                            sayi--;
                                    }
                            }
                    }
                 }
    }
 
    }
    
}
}

