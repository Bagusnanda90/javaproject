/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Dimata 007
 */
public class Test {

    private static String tgl, bln, thn, buln = "";
    private static int tg, bl, th;            //tgl sekarang
    private static int tgL, blL, thL;        //tgl lahir
    private static int h, b, t;            //usia

    public static void main(String args[]) {
        try {
            //new Test().MakeBackup();
            //new Test().test();
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            Calendar cal = Calendar.getInstance();
            Date dtNewDate = cal.getTime();
            String ab = dateFormat.format(cal.getTime());
            char a[] = new char[8];
            for (int i = 0; i < 8; i++) {
                a[i] = ab.charAt(i);
            }
            tgl = "" + a[0] + a[1];
            bln = "" + a[2] + a[3];
            thn = "" + a[4] + a[5] + a[6] + a[7];
            if (bln.equals("01")) {
                buln = "Januari";
            } else if (bln.equals("02")) {
                buln = "Februari";
            } else if (bln.equals("03")) {
                buln = "Maret";
            } else if (bln.equals("04")) {
                buln = "April";
            } else if (bln.equals("05")) {
                buln = "Mei";
            } else if (bln.equals("06")) {
                buln = "Juni";
            } else if (bln.equals("07")) {
                buln = "Juli";
            } else if (bln.equals("08")) {
                buln = "Agustus";
            } else if (bln.equals("09")) {
                buln = "September";
            } else if (bln.equals("10")) {
                buln = "Oktober";
            } else if (bln.equals("11")) {
                buln = "November";
            } else if (bln.equals("12")) {
                buln = "Desember";
            }
            tg = Integer.parseInt(tgl);
            bl = Integer.parseInt(bln);
            th = Integer.parseInt(thn);
            long time = new SimpleDateFormat("ddMMyyyy").parse("11052014").getTime();
            Date tglLahir = new Date(time);
            if (dtNewDate.getTime() > tglLahir.getTime()) {
                new Test().setTglLahir(11, 05, 2014);
                new Test().hitungUsia();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void MakeBackup() throws IOException {
        String dump = "mysqldump " //Path to mysql
                + "--host=localhost " //Mysql hostname
                + "--port=3306 " //Mysql portnumber
                + "--user=root " //Mysql username
                + "--password=root " //Mysql password
                + "--add-drop-table " //Add a DROP TABLE statement before each CREATE TABLE statement
                + "--add-drop-database " //Add a DROP DATABASE statement before each CREATE DATABASE statement
                + "--complete-insert " //Use complete INSERT statements that include column names.
                + "--extended-insert " //Use multiple-row INSERT syntax that include several VALUES lists
                + "databank";                   //Mysql databasename

        Process run = Runtime.getRuntime().exec(dump);

        InputStream in = run.getInputStream();

        int nextChar = 0;
        StringBuffer sb = new StringBuffer();

        while ((nextChar = in.read()) != -1) {
            sb.append((char) nextChar);
        }

        //Here, you can for example write it to a file and save it
        System.out.println(sb);
    }

    public void test() throws IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField nameTextField = new JTextField();
        frame.add(nameTextField, BorderLayout.NORTH);

        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent keyEvent) {
                printIt("Typed", keyEvent);
            }

            public void keyPressed(KeyEvent keyEvent) {
                printIt("Pressed", keyEvent);
            }

            public void keyReleased(KeyEvent keyEvent) {
                printIt("Released", keyEvent);
            }

            private void printIt(String title, KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                String keyText = KeyEvent.getKeyText(keyCode);
                System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
            }
        };
        nameTextField.addKeyListener(keyListener);


        frame.setSize(250, 100);
        frame.setVisible(true);
    }

    public static String getAge(Date dateOfBirth) throws Exception {

        int bLahir = dateOfBirth.getMonth() + 1;
        int tgLahir = dateOfBirth.getDate();
        int tahunLahir = dateOfBirth.getYear() + 1900;
        int bSekarang = new Date().getMonth() + 1;
        int tgSekarang = new Date().getDate();
        int tahunSekarang = new Date().getYear() + 1900;
        int uHari = 0;
        int uBulan = 0;
        int uTahun = 0;
        if (bLahir > bSekarang && tgLahir > tgSekarang) {
            uHari = tgLahir - tgSekarang;
            uBulan = 12 - (bLahir - bSekarang);
            uTahun = (tahunSekarang - tahunLahir) - 1;
        } else if (bLahir > bSekarang && tgLahir < tgSekarang) {
            uHari = tgSekarang - tgLahir;
            uBulan = 12 - (bLahir - bSekarang);
            uTahun = (tahunSekarang - tahunLahir) - 1;
        }

        if (bLahir < bSekarang && tgLahir > tgSekarang) {
            uHari = tgLahir - tgSekarang;
            uBulan = bSekarang - bLahir;
            uTahun = tahunSekarang - tahunLahir;
        } else if (bLahir <= bSekarang && tgLahir < tgSekarang) {
            uHari = tgSekarang - tgLahir;
            uBulan = bSekarang - bLahir;
            uTahun = tahunSekarang - tahunLahir;
        }

        return ("Hari" + uHari + ", Bulan" + uBulan + "Tahun" + uTahun);
    }

    public void getUmur(String tanggal) {
        String umur = "";
        Date tglLahir = null;
        long time;
        try {
            time = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal).getTime();
            tglLahir = new Date(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            umur = new Test().getAge(tglLahir);
            System.out.println("Umur" + umur);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return umur;

    }

    public String getTanggal() {
        String hasil = tgl + " " + buln + " " + thn;
        return hasil;
    }

    public void setTglLahir(int h, int b, int t) {
        tgL = h;
        blL = b;
        thL = t;
    }

    public void hitungUsia() {
        h = tg - tgL;
        b = bl - blL;
        t = th - thL;
        if (b < 0) {
            bl += 12;
            t -= 1;
            b = bl - blL;
        }
        if (h < 0) {
            b -= 1;
            bl -= 1;
            if (bl == 2 && t % 4 == 0) {
                h = (tg + 29) - tgL;
            } else if (bl == 2 && t % 4 != 0) {
                h = (tg + 28) - tgL;
            } else if (bl == 1 || bl == 3 || bl == 5 || bl == 7 || bl == 8 || bl == 10 || bl == 12) {
                h = (tg + 31) - tgL;
            } else {
                h = (tg + 30) - tgL;
            }
        }
        System.out.print("hari:" + h + "bulan:" + b + "tahun:" + t);
    }

    public String getTgl() {
        return Integer.toString(h);
    }

    public String getBln() {
        return Integer.toString(b);
    }

    public String getThn() {
        return Integer.toString(t);
    }
}
