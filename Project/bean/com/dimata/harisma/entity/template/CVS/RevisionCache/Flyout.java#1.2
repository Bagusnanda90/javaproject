/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.template;

import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author Satrya Ramayu
 */
public class Flyout {
    //Menu Employee

    //private boolean mnMenuEmployee;
    private Vector vNameUrlJsp = new Vector();
    private Vector vDataCategoryMenu = new Vector();
    private Vector vDataSubCategoryMenu = new Vector();
    private Vector vDataNoBranceMenuAvailabe = new Vector();//tidak ada cabang menu
    private Vector vDataNameMenu = new Vector();
    private Vector vDataLinkSubMenu = new Vector();
    private Vector vDataLinkMenu = new Vector();
    private Hashtable vDataPriviledgePerSubMenu = new Hashtable();
    private Vector vDataPriviledgePerMenu = new Vector();
    private Vector vDataCcsNameMenuId = new Vector();
    private Hashtable categoryMenu = new Hashtable();
    private Hashtable subCategoryMenu = new Hashtable();
    private Hashtable nameUrlMenus = new Hashtable();
    private String urlKlik;
   
    // private Hashtable tmpPriviledgeSubMenu= new Hashtable();

    /**
     * Create By satrya 2014-06-06 <p>Keterangan: untuk melakukan add data pada
     * menu</p>
     *
     * @param nameUrl : ex. > home.jsp
     * @param categoryMenu : ex. > EMPLOYEE
     * @param subCategory: untuk nama sub category tidak boleh sama jika tidak ada cabang'nya, karena akan di anggap jadi satu jika cabangnya di buat true ex. > Leave Application
     * @param noBranceMenuAvailable : ini untuk menentukan apakah ada cabang
     * @param nameMenu : ex. > Leave Form, Leave DP dll
     * @param nameCssMenuId : ini untuk menentukan Id css yg digunakan, seperti
     * gambar akan berpengaruh, ex. >id=data_bank
     * @param linkSubMenu : jika tidak ada cabang maka sub menu'nya akan ada
     * link'nya, jika tidak cukup tambahkan tanda #
     * @param linkMenu : alamat yg ingin di tuju. ex. >
     * www.saya-penggemar-ramayu.com
     * @param priviledgePerMenu : priviledge masing- masing menu, jika tidak
     * dapat hak akses maka tidak akan di munculkan
     */
    public void addMenuFlyOut(String nameUrl, String categoryMenu, String subCategory, boolean noBranceMenuAvailable, String nameMenu, String nameCssMenuId, String linkSubMenu, String linkMenu, boolean priviledgePerMenu) {
        if (nameUrl != null && nameUrl.length() > 0 && this.urlKlik != null && this.urlKlik.length() > 0 && this.urlKlik.equalsIgnoreCase(nameUrl)) {
            if (nameMenu != null && nameMenu.length() > 0 && linkMenu != null && linkMenu.length() > 0) {
                this.vDataNameMenu.add(nameMenu+"_"+subCategory);
                this.vDataLinkMenu.add(linkMenu);
                 this.vDataPriviledgePerMenu.add(priviledgePerMenu);
                 
            }


           

            //berfungsi untuk mengelompokkan berapa julah kategory yg ada
            if (this.nameUrlMenus != null && this.nameUrlMenus.size() > 0 && this.nameUrlMenus.containsKey(nameUrl)) {
                //tidak ada apa"
            } else {
                this.nameUrlMenus.put(nameUrl, nameUrl);
                this.vNameUrlJsp.add(nameUrl);

            }
            //berfungsi untuk mengelompokkan berapa julah kategory yg ada
            if (this.categoryMenu != null && this.categoryMenu.size() > 0 && this.categoryMenu.containsKey(categoryMenu)) {
                //tidak ada apa"
            } else {
                this.vDataCategoryMenu.add(categoryMenu);
                this.categoryMenu.put(categoryMenu, categoryMenu);
            }
            //berfungsi untuk mengelompokkan berapa julah sub-kategory yg ada

            if (this.subCategoryMenu != null && this.subCategoryMenu.size() > 0 && this.subCategoryMenu.containsKey(subCategory)) {
                //tidak ada apa"
                //jika submenuSama maka maka rubahlah nilai privSubMenu'nya

                if (this.vDataPriviledgePerSubMenu != null && this.vDataPriviledgePerSubMenu.size() > 0) {
                    boolean privOld = (Boolean) this.vDataPriviledgePerSubMenu.get(subCategory);
                    if (privOld==false || priviledgePerMenu) {
                        this.vDataPriviledgePerSubMenu.put(subCategory, priviledgePerMenu);
                    }
                }
            } else {
                this.vDataLinkSubMenu.add(linkSubMenu);
                this.vDataCcsNameMenuId.add(nameCssMenuId);
                this.vDataNoBranceMenuAvailabe.add(noBranceMenuAvailable);
                this.vDataPriviledgePerSubMenu.put(subCategory, priviledgePerMenu);
                this.vDataSubCategoryMenu.add(subCategory);
                this.subCategoryMenu.put(subCategory, subCategory);

            }
        }


    }

    public void drawMenuFlayOut(JspWriter out, String url, boolean isMSIE, String approot) {
        try {
            if (true) { //isMSIE
                out.print("<div>");
                out.print("<table width=\"50%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                out.print("<tr>");
                int jumlahMaksimalColom = 2;
                if (this.nameUrlMenus != null && this.nameUrlMenus.size() > 0) {
                    for (int idxMenuUrl = 0; idxMenuUrl < this.nameUrlMenus.size(); idxMenuUrl++) {
                        String namaUrl = vNameUrlJsp == null ? "" : (String) vNameUrlJsp.get(idxMenuUrl);

                        //mencari Url'nya
                        if (url != null && url.length() > 0 && url.equalsIgnoreCase(namaUrl)) {
                            //mencari category'nya , misalkan Employee, Report, Master Data, System dll
                            if (vDataCategoryMenu != null && vDataCategoryMenu.size() > 0) {
                                for (int idxCat = 0; idxCat < vDataCategoryMenu.size(); idxCat++) {
                                    String namaCategory = ((String) vDataCategoryMenu.get(idxCat));
                                    //String namaCategory = tmpNamaCategory!=null && tmpNamaCategory.length>0 ? tmpNamaCategory[0]:"";
                                    //String urlCategory = tmpNamaCategory!=null && tmpNamaCategory.length>0 ? tmpNamaCategory[1]:null;
                                    if (true) {

                                        out.print("<div id=\"menuheader\"><h2><spacing>" + namaCategory + "</spacing></h2></div>");
                                        boolean blmDiambilIdMenuKiri = true;
                                        boolean blmDiambilIdMenuKanan = true;

                                        //boolean blmFooterDiambilIdMenuKiri = true;
                                        //boolean blmFooterDiambilIdMenuKanan = true;
                                        //boolean blmDiambil = true;
                                        //mencari sub'categorynya, misalkan dataBank, Leave Application, Excue Leave, dll
                                        if (vDataSubCategoryMenu != null && vDataSubCategoryMenu.size() > 0) {
                                            int no=0;
                                            for (int idxSubCat = 0; idxSubCat < vDataSubCategoryMenu.size(); idxSubCat++) {
                                                String namaSubCategory = (String) vDataSubCategoryMenu.get(idxSubCat);
                                                boolean branceMenuAvailabe = vDataNoBranceMenuAvailabe == null ? false : ((Boolean) vDataNoBranceMenuAvailabe.get(idxSubCat));
                                                boolean priviledgePerSubMenu = vDataPriviledgePerSubMenu == null ? false : ((Boolean) vDataPriviledgePerSubMenu.get(namaSubCategory));
                                                String nameCssMenuId = vDataCcsNameMenuId == null ? "x" : (String) vDataCcsNameMenuId.get(idxSubCat);
                                                String linkSubMenu = vDataLinkSubMenu == null ? "#" : (String) vDataLinkSubMenu.get(idxSubCat);
                                                if (priviledgePerSubMenu) {
                                                    no++;
                                                    if ( (no <= (vDataSubCategoryMenu.size() / jumlahMaksimalColom)) || no <= 1) {
                                                        if (blmDiambilIdMenuKiri) {
                                                            //out.print("<div id=\"kiri\">");
                                                            //out.print("<div id='cssmenu'>");
                                                            out.print("<td width=\"50%\" valign=\"top\" id=\"batasanAtas\">");
                                                            out.print("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\">");
                                                            out.print("<tr>");
                                                            out.print("<td valign=\"top\">");
                                                            out.print("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\">");
                                                            out.print("<tr>");
                                                            out.print("<td></td>");
                                                            out.print("</tr>");
                                                            out.print("<tr>");
                                                            out.print("<td valign=\"top\">");
                                                            out.print("<ul>");
                                                            //out.print("<li>xxxx</li>");
                                                            out.print("<div id='cssmenu'>");
                                                            out.print("<ul>");

//                                                        out.print("</ul>");
//                                                        out.print("</td>");
//                                                        out.print("</tr>");
//                                                        out.print("</table>");
//                                                        out.print("</td>");
//                                                        out.print("</tr>");
//                                                        out.print("</table>");
//                                                        out.print("</td>"); 
                                                            
                                                        }
                                                        blmDiambilIdMenuKiri = false;
                                                    } else {
                                                        //karena di awal harus di ambil
                                                        if (blmDiambilIdMenuKanan) {
                                                            out.print("</ul>");
                                                            out.print("</td>");
                                                            out.print("</tr>");
                                                            out.print("</table>");
                                                            out.print("</td>");
                                                            out.print("</tr>");
                                                            out.print("</table>");
                                                            out.print("</td>");
                                                            // out.print("</div>");
                                                            //  out.print("</div>");
                                                            //di tutup dahulu bau buat lagi
//                                                        out.print("<div id=\"kanan\">");
//                                                        out.print("<div id='cssmenu'>");
//                                                        out.print("<ul>");
                                                            out.print("<td width=\"1%\">&nbsp;</td>");

                                                            out.print("<td width=\"50%\" valign=\"top\" id=\"batasanAtas\">");
                                                            out.print("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\">");
                                                            out.print("<tr>");
                                                            out.print("<td valign=\"top\">");
                                                            out.print("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\">");
                                                            out.print("<tr>");
                                                            out.print("<td></td>");
                                                            out.print("</tr>");
                                                            out.print("<tr>");
                                                            out.print("<td valign=\"top\">");
                                                            out.print("<ul>");
                                                            //out.print("<li>xxxx</li>");
                                                            out.print("<div id='cssmenu'>");
                                                            out.print("<ul>");
                                                        }
                                                        blmDiambilIdMenuKanan = false;
                                                    }
                                                    //if (blmDiambil) {

                                                    //}

                                                    if (branceMenuAvailabe == false) {
                                                        out.print("<li><a id=\"" + nameCssMenuId + "\" href='" + linkSubMenu + "'><span><p id=\"has-sub-align\">" + namaSubCategory + "</p></span></a></li>");
                                                    } else {
                                                        //jika ada cabang maka di cari cabangnya
                                                        //menampilkan nama menu'nya, misalkan data bank Leave Application > Leave Form,Leave Al Closing dll

                                                        if (this.vDataNameMenu != null && this.vDataNameMenu.size() > 0) {
                                                            out.print("<li><a id=\"" + nameCssMenuId + "\" href='" + linkSubMenu + "'><span><p id=\"has-sub-align\">" + namaSubCategory + "</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                                                            out.print("<ul>");
                                                            int number = 0;
                                                            for (int idxNamaMenu = 0; idxNamaMenu < this.vDataNameMenu.size(); idxNamaMenu++) {
                                                                String tmpNamaMenu = vDataNameMenu == null ? "" : (String) vDataNameMenu.get(idxNamaMenu);
                                                                String linkMenu = vDataLinkMenu == null ? "" : (String) vDataLinkMenu.get(idxNamaMenu);
                                                                boolean priviledgePerMenu = vDataPriviledgePerMenu == null ? false : ((Boolean) vDataPriviledgePerMenu.get(idxNamaMenu));
                                                                
                                                               if(tmpNamaMenu!=null && tmpNamaMenu.split("_").length>1){
                                                                   String namaMenu = tmpNamaMenu.split("_")[0];
                                                                    String namaSubCat =tmpNamaMenu.split("_")[1];
                                                                    if(namaSubCat.equalsIgnoreCase(namaSubCategory)){ 
                                                                        //cek apakah privilednya true
                                                                        if (priviledgePerMenu) {
                                                                             number++;
                                                                            out.print("<li id='has-sub-menu1'><a href='" + linkMenu + "'><px>" + (number) + " " + namaMenu + "</px></a></li>");
                                                                        }
                                                                        vDataNameMenu.remove(idxNamaMenu);
                                                                        vDataLinkMenu.remove(idxNamaMenu);
                                                                        idxNamaMenu = idxNamaMenu -1;
                                                                    }
                                                               }
                                                               
                                                            }
                                                            out.print("</ul>");
                                                            out.print("</li>");
                                                        }
                                                    }
                                                }

                                            }//end loop sub category
//                                        out.print("</ul>");
//                                        out.print("</div");
//                                        out.print("</div>");
                                            out.print("</ul>");
                                            out.print("</td>");
                                            out.print("</tr>");
                                            out.print("</table>");
                                            out.print("</td>");
                                            out.print("</tr>");
                                            out.print("</table>");
                                            out.print("</td>");


                                        }
                                        //out.print("</div>");
                                    }//end loop category menu
                                }

                            }//else nya ya tidak di bentuk menunya

                        } else {
                            //out.print("No Menu Available");
                        }
                    }
                }else{
                    out.print("No Menu Available");
                }

                //out.print("</div>");


                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
            } else {
            }
        } catch (Exception e) {
        }

    }
    
    public void drawMenuFlayOutMcHen(JspWriter out, String url, boolean isMSIE, String approot) {
        try {
            if (true) { //isMSIE
                out.print("<div class=\"menu-main\">");
                if (vDataCategoryMenu != null && vDataCategoryMenu.size() > 0) {
                    String namaCategory = ((String) vDataCategoryMenu.get(0));
                    out.print("<div id=\"menuheader\"><h2>" + namaCategory + "</h2></div>");
                }
                out.print("<table><tr>");
                int jumlahMaksimalColom = 2;
                if (this.nameUrlMenus != null && this.nameUrlMenus.size() > 0) {
                    for (int idxMenuUrl = 0; idxMenuUrl < this.nameUrlMenus.size(); idxMenuUrl++) {
                        String namaUrl = vNameUrlJsp == null ? "" : (String) vNameUrlJsp.get(idxMenuUrl);

                        //mencari Url'nya
                        if (url != null && url.length() > 0 && url.equalsIgnoreCase(namaUrl)) {
                            //mencari category'nya , misalkan Employee, Report, Master Data, System dll
                                    if (true) {

                                        boolean blmDiambilIdMenuKiri = true;
                                        boolean blmDiambilIdMenuKanan = true;
                                        if (vDataSubCategoryMenu != null && vDataSubCategoryMenu.size() > 0) {
                                            int no = 0;
                                            for (int idxSubCat = 0; idxSubCat < vDataSubCategoryMenu.size(); idxSubCat++) {
                                                String namaSubCategory = (String) vDataSubCategoryMenu.get(idxSubCat);
                                                boolean branceMenuAvailabe = vDataNoBranceMenuAvailabe == null ? false : ((Boolean) vDataNoBranceMenuAvailabe.get(idxSubCat));
                                                boolean priviledgePerSubMenu = vDataPriviledgePerSubMenu == null ? false : ((Boolean) vDataPriviledgePerSubMenu.get(namaSubCategory));
                                                String nameCssMenuId = vDataCcsNameMenuId == null ? "x" : (String) vDataCcsNameMenuId.get(idxSubCat);
                                                String linkSubMenu = vDataLinkSubMenu == null ? "#" : (String) vDataLinkSubMenu.get(idxSubCat);
                                                if (priviledgePerSubMenu) {
                                                    no++;
                                                    if ((no <= (vDataSubCategoryMenu.size() / jumlahMaksimalColom)) || no <= 1) {
                                                        if (blmDiambilIdMenuKiri) {
                                                            out.print("<td valign=\"top\">");
                                                            out.print("<div id='cssmenu'>");
                                                            out.print("<ul>");
                                                        }
                                                        blmDiambilIdMenuKiri = false;
                                                    } else {
                                                        //karena di awal harus di ambil
                                                        if (blmDiambilIdMenuKanan) {
                                                            out.print("</ul>");
                                                            out.print("</div>");
                                                            out.print("</td>");
                                                            //di tutup dahulu bau buat lagi
                                                            out.print("<td valign=\"top\">");
                                                            out.print("<div id='cssmenu'>");
                                                            out.print("<ul>");
                                                        }
                                                        blmDiambilIdMenuKanan = false;
                                                    }

                                                    if (branceMenuAvailabe == false) {
                                                        out.print("<li><a href='" + linkSubMenu + "'><div class=\"menu-main-item\">" + namaSubCategory + "</div></a></li>");
                                                    } else {
                                                        //jika ada cabang maka di cari cabangnya
                                                        //menampilkan nama menu'nya, misalkan data bank Leave Application > Leave Form,Leave Al Closing dll

                                                        if (this.vDataNameMenu != null && this.vDataNameMenu.size() > 0) {
                                                            out.print("<li><a href='" + linkSubMenu + "'><div class=\"menu-main-item\">" + namaSubCategory + "</div></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                                                            out.print("<ul>");
                                                            int number = 0;
                                                            for (int idxNamaMenu = 0; idxNamaMenu < this.vDataNameMenu.size(); idxNamaMenu++) {
                                                                String tmpNamaMenu = vDataNameMenu == null ? "" : (String) vDataNameMenu.get(idxNamaMenu);
                                                                String linkMenu = vDataLinkMenu == null ? "" : (String) vDataLinkMenu.get(idxNamaMenu);
                                                                boolean priviledgePerMenu = vDataPriviledgePerMenu == null ? false : ((Boolean) vDataPriviledgePerMenu.get(idxNamaMenu));

                                                                if (tmpNamaMenu != null && tmpNamaMenu.split("_").length > 1) {
                                                                    String namaMenu = tmpNamaMenu.split("_")[0];
                                                                    String namaSubCat = tmpNamaMenu.split("_")[1];
                                                                    if (namaSubCat.equalsIgnoreCase(namaSubCategory)) {
                                                                        //cek apakah privilednya true
                                                                        if (priviledgePerMenu) {
                                                                            number++;
                                                                            out.print("<li><a href='" + linkMenu + "'><div class=\"sub-menu\">" + (number) + ") " + namaMenu + "</div></a></li>");
                                                                        }
                                                                        vDataNameMenu.remove(idxNamaMenu);
                                                                        vDataLinkMenu.remove(idxNamaMenu);
                                                                        idxNamaMenu = idxNamaMenu - 1;
                                                                    }
                                                                }

                                                            }
                                                            out.print("</ul>");
                                                            out.print("</li>");
                                                        }
                                                    }
                                                }

                                            }//end loop sub category
                                            out.print("</ul>");
                                            out.print("</div>");
                                            out.print("</td>");
                                        }
                                    }//end loop category menu

                        } else {
                            //out.print("No Menu Available");
                        }
                    }
                } else {
                    out.print("No Menu Available");
                }
                out.print("</table></tr>");
                out.print("</div>");
            } else {
            }
        } catch (Exception e) {
        }
    }

    /**
     * @return the urlKlik
     */
    public String getUrlKlik() {
        return urlKlik;
    }

    /**
     * @param urlKlik the urlKlik to set
     */
    public void setUrlKlik(String urlKlik) {
        this.urlKlik = urlKlik;
    }
}
